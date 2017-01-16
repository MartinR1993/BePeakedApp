package project.martin.bepeakedprojekt.Backend;

import android.app.Activity;
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
import java.util.ArrayList;
import java.util.Arrays;

import project.martin.bepeakedprojekt.Exercises.ExerciseElement;
import project.martin.bepeakedprojekt.Logind_akt;
import project.martin.bepeakedprojekt.MainMenu_akt;
import project.martin.bepeakedprojekt.User.User;
import project.martin.bepeakedprojekt.Workout.WorkoutElement;
import project.martin.bepeakedprojekt.Workout.WorkoutExercises.Workout_Exercises_akt;
import project.martin.bepeakedprojekt.Workout.WorkoutMenu_akt;
import scSecurity.hashing.MD5Hashing;

/**
 * Created by Lasse on 13-01-2017.
 */

public class ServerComm extends AsyncTask<String, Void, String[]>
{
    private static final String TASK_GETSALT = "salt";
    private static final String TASK_LOGIN = "login";
    private static final String TASK_GETUSERTYPE = "get_usertype";
    private static final String TASK_GETWORKOUTS = "get_workouts";
    private static final String TASK_GETEXERCISES = "get_exercises";

    private static final String TAG_COMMAND = "cmd";
    private static final String TAG_CMD_CREATE = "create";
    private static final String TAG_CMD_UPDATE = "update";
    private static final String TAG_CMD_VALIDATE = "validate";
    private static final String TAG_CMD_GET = "get";
    private static final String TAG_CMD_SESSION_ID = "session";
    private static final String TAG_ARGS = "args";
    private static final String TAG_USER = "user";
    private static final String TAG_USER_TYPE = "us_type";
    private static final String TAG_WORKOUTLIST = "workouts";
    private static final String TAG_PASSWORD = "password";
    private static final String TAG_SALT = "salt";
    private static final String TAG_EXERCISE = "exercise";
    private static final String TAG_ERROR = "error";
    private static final String TAG_ERROR_NONE = "none";

    private final String host;
    private final int port;
    private Activity act;
    private String task;

    public ServerComm(String host, int port) {
        this.host = host;
        this.port = port;
    }

    private ServerComm(Activity act, String task, String host, int port) {
        this.act = act;
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

    public void getUserType(MainMenu_akt mainMenu, int userID, String sessionID) {
        new ServerComm(mainMenu, TASK_GETUSERTYPE, host, port).execute("" + userID, sessionID);
    }

    public void getWorkoutlist(WorkoutMenu_akt workoutsMenu, String sessionID) {
        new ServerComm(workoutsMenu, TASK_GETWORKOUTS, host, port).execute(sessionID);
    }

    public void getExercisesByWorkoutID(Workout_Exercises_akt workoutMenu, int workoutID, String sessionID) {
        new ServerComm(workoutMenu, TASK_GETEXERCISES, host, port).execute("" + workoutID, sessionID);
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
                        result = new String[3];
                        result[1] = reply.getString(TAG_CMD_SESSION_ID);
                        result[2] = "" + reply.getInt(TAG_USER);
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
            case TASK_GETUSERTYPE: {
                JSONObject jsonObj = new JSONObject();
                try {
                    jsonObj.put(TAG_COMMAND, TAG_CMD_GET);
                    jsonObj.put(TAG_CMD_SESSION_ID, params[1]);
                    JSONArray argsJA = new JSONArray();
                    argsJA.put(0, TAG_USER_TYPE);
                    argsJA.put(1, Integer.parseInt(params[0]));
                    jsonObj.put(TAG_ARGS, argsJA);

                    result = new String[1];
                    JSONObject reply = sendRequest(jsonObj.toString());
                    result[0] = "" + reply.getInt(TAG_USER_TYPE);
                    return result;
                } catch (JSONException | IOException e) {
                    e.printStackTrace();
                }
                break;
            }
            case TASK_GETWORKOUTS: {
                JSONObject jsonObj = new JSONObject();
                try {
                    jsonObj.put(TAG_COMMAND, TAG_CMD_GET);
                    jsonObj.put(TAG_CMD_SESSION_ID, params[0]);
                    JSONArray argsJA = new JSONArray();
                    argsJA.put(0, TAG_WORKOUTLIST);
                    jsonObj.put(TAG_ARGS, argsJA);

                    result = new String[1];
                    result[0] = sendRequest(jsonObj.toString()).getString(TAG_WORKOUTLIST);
                    return result;
                } catch (JSONException | IOException e) {
                    e.printStackTrace();
                }
                break;
            }
            case TASK_GETEXERCISES: {
                JSONObject jsonObj = new JSONObject();
                try {
                    jsonObj.put(TAG_COMMAND, TAG_CMD_GET);
                    jsonObj.put(TAG_CMD_SESSION_ID, params[1]);
                    JSONArray argsJA = new JSONArray();
                    argsJA.put(0, TAG_EXERCISE);
                    argsJA.put(1, Integer.parseInt(params[0]));
                    jsonObj.put(TAG_ARGS, argsJA);

                    result = new String[1];
                    result[0] = sendRequest(jsonObj.toString()).getString(TAG_EXERCISE);
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
                Logind_akt login = (Logind_akt) act;
                login.login(result[0]);
                break;
            }
            case TASK_LOGIN: {
                System.out.println("RESULT=" + Arrays.toString(result));
                if (result.length == 3) {
                    Logind_akt login = (Logind_akt) act;
                    User.setSessionID(result[1]);
                    User.setUserID(Integer.parseInt(result[2]));
                    login.gotoMenu();
                }
                break;
            }
            case TASK_GETUSERTYPE: {
                MainMenu_akt mainMenu = (MainMenu_akt) act;
                mainMenu.setUserType(Integer.parseInt(result[0]));
                break;
            }
            case TASK_GETWORKOUTS: {
                System.out.println("RESULT=" + Arrays.toString(result));
                try {
                    ArrayList<WorkoutElement> workoutList = new ArrayList<>();
                    JSONArray workoutlistJSON = new JSONArray(result[0]);
                    WorkoutMenu_akt workoutsMenu = (WorkoutMenu_akt) act;

                    JSONObject workoutJSON;
                    for(int i = 0; i < workoutlistJSON.length(); i++) {
                        workoutJSON = (JSONObject) workoutlistJSON.get(i);
                        int id = workoutJSON.getInt("id");
                        String name = workoutJSON.getString("name");

                        workoutList.add(new WorkoutElement(id, name, new ArrayList<ExerciseElement>(), true));
                    }

                    workoutsMenu.addWorkouts(workoutList);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            }
            case TASK_GETEXERCISES: {
                System.out.println("RESULT=" + Arrays.toString(result));
                try {
                    ArrayList<ExerciseElement> exerciseList = new ArrayList<>();
                    JSONArray exerciselistJSON = new JSONArray(result[0]);
                    Workout_Exercises_akt workoutMenu = (Workout_Exercises_akt) act;

                    JSONObject exerciseJSON;
                    for(int i = 0; i < exerciselistJSON.length(); i++) {
                        exerciseJSON = (JSONObject) exerciselistJSON.get(i);
                        int id = exerciseJSON.getInt("id");
                        int sets = exerciseJSON.getInt("sets");
                        String name = exerciseJSON.getString("name");
                        String description = exerciseJSON.getString("description");
                        int imageID = exerciseJSON.getInt("imageID");

                        exerciseList.add(new ExerciseElement(id, sets, null, name, description, imageID));
                    }

                    workoutMenu.addExercises(exerciseList);
                } catch (JSONException e) {
                    e.printStackTrace();
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

            System.out.println("REQUEST=" + request);
            connOut.print(request + '\n');
            connOut.flush();

            connBR = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String input = connBR.readLine();
            System.out.println("REPLY=" + input);

            if(input != null)
                return new JSONObject(input);
            else
                return null;
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