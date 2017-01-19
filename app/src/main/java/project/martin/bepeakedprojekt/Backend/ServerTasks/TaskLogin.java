package project.martin.bepeakedprojekt.Backend.ServerTasks;

/**
 * Created by Lasse on 17/01-17.
 */

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import project.martin.bepeakedprojekt.Backend.BackendData;
import project.martin.bepeakedprojekt.Login_akt;
import project.martin.bepeakedprojekt.R;
import project.martin.bepeakedprojekt.User.User;
import scSecurity.hashing.MD5Hashing;

import static project.martin.bepeakedprojekt.Backend.ServerTasks.ServerTags.*;

public class TaskLogin extends ServerTask
{
    private final Login_akt login;

    public TaskLogin(Login_akt login, String host, int port) {
        super(host, port);
        this.login = login;
    }

    @Override
    public String[] doInBackground(String... params) {
        String[] result = null;
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
            try {
                if(reply.getString(TAG_ERROR).equals(ERROR_NO_HOST))
                    return new String[]{ERROR_NO_HOST};
            } catch (JSONException e) {}

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
        return result;
    }

    @Override
    public void onPostExecute(String... result) {
        if(!result[0].equals(ERROR_NO_HOST)) {
            if (result.length == 3) {
                User.setSessionID(result[1]);
                User.setUserID(Integer.parseInt(result[2]));
                login.gotoMenu();
            }
            else {
                showMessageDialouge(login, login.getString(R.string.sc_tskWrongPass_title) ,login.getString(R.string.login_error));
            }
        }
        else
            showMessageDialouge(login, login.getString(R.string.sc_tskConnError_title), login.getString(R.string.sc_tskConnError_msg) + BackendData.SERVER_ADRESS);
    }
}