package project.martin.bepeakedprojekt.Backend.ServerTasks;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import project.martin.bepeakedprojekt.Logind_akt;

import static project.martin.bepeakedprojekt.Backend.ServerTasks.ServerTags.*;

/**
 * Created by Lasse on 17/01-17.
 */

public class TaskGetSalt extends ServerTask
{
    private final Logind_akt login;

    public TaskGetSalt(Logind_akt login, String host, int port) {
        super(host, port);
        this.login = login;
    }

    @Override
    public String[] doInBackground(String... params) {
        String[] result = null;
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
        return result;
    }

    @Override
    public void onPostExecute(String... result) {
        login.login(result[0]);
    }
}
