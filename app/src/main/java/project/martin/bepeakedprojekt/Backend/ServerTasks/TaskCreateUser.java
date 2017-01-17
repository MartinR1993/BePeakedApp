package project.martin.bepeakedprojekt.Backend.ServerTasks;

/**
 * Created by Lasse on 17/01-17.
 */

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import static project.martin.bepeakedprojekt.Backend.ServerTasks.ServerTags.*;

public class TaskCreateUser extends ServerTask
{
    public TaskCreateUser(String host, int port) {
        super(host, port);
    }

    @Override
    public String[] doInBackground(String... params) {
        String[] result = new String[0];
        JSONObject jsonObj = new JSONObject();

        try {
            jsonObj.put(TAG_COMMAND, TAG_CMD_CREATE);
            JSONArray argsJA = new JSONArray();
            argsJA.put(0, TAG_USER);
            argsJA.put(1, params[0]);
            argsJA.put(2, params[1]);
            argsJA.put(3, params[2]);
            argsJA.put(4, params[3]);
            argsJA.put(5, params[4]);
            argsJA.put(6, params[5]);
            jsonObj.put(TAG_ARGS, argsJA);

            result = new String[1];
            result[0] = sendRequest(jsonObj.toString()).getString(TAG_ERROR);
            return result;
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void onPostExecute(String... result) {
        if(result != null)
            System.out.println("Create user errors: " + result[0]);
    }
}