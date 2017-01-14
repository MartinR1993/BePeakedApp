package project.martin.bepeakedprojekt.Backend;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;

import project.martin.bepeakedprojekt.Logind_akt;
import project.martin.bepeakedprojekt.User.User;
import scSecurity.hashing.MD5Hashing;

/**
 * Created by Lasse on 13-01-2017.
 */

public class ServerComm extends AsyncTask<String, Void, String[]>
{
    public static final String TASK_GETSALT = "salt";
    public static final String TASK_LOGIN = "login";

    private static final String TAG_COMMAND = "cmd";
    private static final String TAG_CMD_TEST = "SCTest";
    private static final String TAG_CMD_STOP = "stop";
    private static final String TAG_CMD_CREATE = "create";
    private static final String TAG_CMD_UPDATE = "update";
    private static final String TAG_CMD_VALIDATE = "validate";
    private static final String TAG_CMD_GET = "get";
    private static final String TAG_CMD_SESSION_ID = "session";
    private static final String TAG_ARGS = "args";
    private static final String TAG_USER = "user";
    private static final String TAG_PASSWORD = "password";
    private static final String TAG_SALT = "salt";
    private static final String TAG_RESULT = "result";
    private static final String TAG_EXERCISE = "exercise";
    private static final String TAG_ERROR = "error";
    private static final String TAG_ERROR_NONE = "none";

    private final String host;
    private final int port;
    private Logind_akt login;
    private String task;

    public ServerComm(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public ServerComm(Logind_akt login, String task, String host, int port) {
        this.login = login;
        this.task = task;
        this.host = host;
        this.port = port;
    }

    public void getSalt(Logind_akt login, String username) {
        new ServerComm(login, TASK_GETSALT, host, port).execute(username);
    }

    public void login(Logind_akt login, String username, String password, String salt) {
        new ServerComm(login, TASK_LOGIN, host, port).execute(username, password, salt);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String[] doInBackground(String... params) {
        String[] result;

        switch (task) {
            case TASK_GETSALT: {
                JSONObject jsonObj = new JSONObject();
                try {
                    jsonObj.put(TAG_COMMAND, TAG_CMD_GET);
                    JSONArray argsJA = new JSONArray();
                    argsJA.put(TAG_SALT);
                    argsJA.put(params[0]);
                    jsonObj.put(TAG_ARGS, argsJA);

                    result = new String[1];
                    result[0] = sendRequest(jsonObj.toString()).getString(TAG_SALT);
                    return result;
                } catch (JSONException | IOException e) {
                    e.printStackTrace();
                }
                break;
            }
            case TASK_LOGIN: {
                JSONObject jsonObj = new JSONObject();
                try {
                    jsonObj.put(TAG_COMMAND, TAG_CMD_VALIDATE);
                    JSONArray argsJA = new JSONArray();
                    argsJA.put(TAG_USER);
                    argsJA.put(params[0]);

                    String[] hash = new MD5Hashing().encryptHash(params[1], params[2]);
                    JSONArray hashArray = new JSONArray();

                    for (String hex : hash)
                        hashArray.put(hex);

                    argsJA.put(hashArray);

                    jsonObj.put(TAG_ARGS, argsJA);

                    JSONObject reply = sendRequest(jsonObj.toString());
                    String errorMsg = reply.getString(TAG_ERROR);
                    if(errorMsg.equals(TAG_ERROR_NONE)) {
                        result = new String[2];
                        result[1] = reply.getString(TAG_CMD_SESSION_ID);
                    }
                    else
                        result = new String[1];

                    result[0] = errorMsg;

                    return result;
                } catch (JSONException | IOException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(String... result) {
        switch (task) {
            case TASK_GETSALT: {
                login.login(result[0]);
                break;
            }
            case TASK_LOGIN: {
                System.out.println("RESULT=" + Arrays.toString(result));
                if (result.length == 2) {
                    User.setSessionID(result[1]);
                    login.gotoMenu();
                }
                break;
            }
        }
    }

    private JSONObject sendRequest(String request) throws JSONException, IOException {
        InputStream connIS = null;
        PrintWriter connOut = null;
        BufferedReader connBR = null;

        try (Socket socket = new Socket(host, port)) {
            connIS = socket.getInputStream();
            connOut = new PrintWriter(socket.getOutputStream(), true);

            connOut.print(request + '\n');
            connOut.flush();

            connBR = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String input = connBR.readLine();
            JSONObject replyJO = new JSONObject(input);

            return replyJO;
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (connIS != null)
                try {
                    connIS.close();
                } catch (IOException ex) {
                    System.err.println("Could not close input stream!");
                    ex.printStackTrace();
                }

            if(connOut != null) {
                connOut.flush();
                connOut.close();
            }

            if(connBR != null)
                try {
                    connBR.close();
                }
                catch (IOException ex) {
                    System.err.println("Could not close buffered reader!");
                    ex.printStackTrace();
                }
        }
        throw new IOException("Could not get reply from server!");
    }
}