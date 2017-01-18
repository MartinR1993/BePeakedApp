package project.martin.bepeakedprojekt.Backend.ServerTasks;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import project.martin.bepeakedprojekt.Backend.BackendData;
import project.martin.bepeakedprojekt.R;
import project.martin.bepeakedprojekt.Settings.ActivationKey_akt;

import static project.martin.bepeakedprojekt.Backend.ServerTasks.ServerTags.ERROR_NO_HOST;
import static project.martin.bepeakedprojekt.Backend.ServerTasks.ServerTags.TAG_ACTIVATIONKEY;
import static project.martin.bepeakedprojekt.Backend.ServerTasks.ServerTags.TAG_ARGS;
import static project.martin.bepeakedprojekt.Backend.ServerTasks.ServerTags.TAG_CMD_SESSION_ID;
import static project.martin.bepeakedprojekt.Backend.ServerTasks.ServerTags.TAG_CMD_VALIDATE;
import static project.martin.bepeakedprojekt.Backend.ServerTasks.ServerTags.TAG_COMMAND;
import static project.martin.bepeakedprojekt.Backend.ServerTasks.ServerTags.TAG_ERROR;
import static project.martin.bepeakedprojekt.Backend.ServerTasks.ServerTags.TAG_ERROR_NONE;

/**
 * Created by Lasse on 17/01-17.
 */

public class TaskActivateKey extends ServerTask
{
    private final ActivationKey_akt activationAct;

    public TaskActivateKey(ActivationKey_akt activationAct, String host, int port) {
        super(host, port);
        this.activationAct = activationAct;
    }

    @Override
    public String[] doInBackground(String... params) {
        String[] result;
        JSONObject jsonObj = new JSONObject();

        try {
            jsonObj.put(TAG_COMMAND, TAG_CMD_VALIDATE);
            jsonObj.put(TAG_CMD_SESSION_ID, params[2]);
            JSONArray argsJA = new JSONArray();
            argsJA.put(TAG_ACTIVATIONKEY);
            argsJA.put(Integer.parseInt(params[0]));
            argsJA.put(params[1]);

            jsonObj.put(TAG_ARGS, argsJA);

            JSONObject reply = sendRequest(jsonObj.toString());

            try {
                if(reply.getString(TAG_ERROR).equals(ERROR_NO_HOST))
                    return new String[]{ERROR_NO_HOST};
            } catch (JSONException e) {}

            String errorMsg;
            if(reply != null)
                errorMsg = reply.getString(TAG_ERROR);
            else
                errorMsg = activationAct.getString(R.string.activationkey_error1);

            result = new String[1];
            result[0] = errorMsg;

            return result;
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
        return new String[0];
    }

    @Override
    public void onPostExecute(String... result) {
        if(!result[0].equals(ERROR_NO_HOST))
            activationAct.activateUser(result[0].equals(TAG_ERROR_NONE));
        else
            showMessageDialouge(activationAct, "Connection error", "Lost connection to server " + BackendData.SERVER_ADRESS);
    }
}