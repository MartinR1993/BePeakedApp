package project.martin.bepeakedprojekt.Backend.ServerTasks;

/**
 * Created by Lasse on 17/01-17.
 */

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import project.martin.bepeakedprojekt.Backend.BackendData;
import project.martin.bepeakedprojekt.MainMenu_akt;
import project.martin.bepeakedprojekt.R;

import static project.martin.bepeakedprojekt.Backend.ServerTasks.ServerTags.*;

public class TaskGetUserType extends ServerTask
{
    private final MainMenu_akt mainMenu;

    public TaskGetUserType(MainMenu_akt mainMenu, String host, int port) {
        super(host, port);
        this.mainMenu = mainMenu;
    }

    @Override
    public String[] doInBackground(String... params) {
        String[] result = new String[0];
        JSONObject jsonObj = new JSONObject();

        try {
            jsonObj.put(TAG_COMMAND, TAG_CMD_GET);
            jsonObj.put(TAG_CMD_SESSION_ID, params[1]);
            JSONArray argsJA = new JSONArray();
            argsJA.put(0, TAG_USER_TYPE);
            argsJA.put(1, Integer.parseInt(params[0]));
            jsonObj.put(TAG_ARGS, argsJA);

            JSONObject reply = sendRequest(jsonObj.toString());
            try {
                if(reply.getString(TAG_ERROR).equals(ERROR_NO_HOST))
                    return new String[]{ERROR_NO_HOST};
            } catch (JSONException e) {}

            result = new String[1];
            result[0] = "" + reply.getInt(TAG_USER_TYPE);
            return result;
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void onPostExecute(String... result) {
        if(!result[0].equals(ERROR_NO_HOST))
            mainMenu.setUserType(Integer.parseInt(result[0]));
        else
            showMessageDialouge(mainMenu, mainMenu.getString(R.string.sc_tskConnError_title), mainMenu.getString(R.string.sc_tskConnError_msg) + BackendData.SERVER_ADRESS);
    }
}