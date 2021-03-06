package project.martin.bepeakedprojekt.Backend.ServerTasks;

/**
 * Created by Lasse on 17/01-17.
 */

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import project.martin.bepeakedprojekt.Backend.BackendData;
import project.martin.bepeakedprojekt.R;
import project.martin.bepeakedprojekt.User.CreateUser_akt;

import static project.martin.bepeakedprojekt.Backend.ServerTasks.ServerTags.*;

public class TaskCreateUser extends ServerTask
{
    private final CreateUser_akt createUserAct;

    public TaskCreateUser(CreateUser_akt createUserAct, String host, int port) {
        super(host, port);
        this.createUserAct = createUserAct;
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

            JSONObject reply = sendRequest(jsonObj.toString());
            try {
                if(reply.getString(TAG_ERROR).equals(ERROR_NO_HOST))
                    return new String[]{ERROR_NO_HOST};
            } catch (JSONException e) {}

            result[0] = reply.getString(TAG_ERROR);
            return result;
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void onPostExecute(String... result) {
        if(result != null) {
            if(result[0] != null) {
                switch (result[0]) {
                    case ERROR_NO_HOST:
                        showMessageDialouge(createUserAct, createUserAct.getString(R.string.sc_tskConnError_title), createUserAct.getString(R.string.sc_tskConnError_msg) + BackendData.SERVER_ADRESS);
                        break;
                    case TAG_ERROR_NONE:
                        createUserAct.userCreated();
                        break;
                    default:
                        showMessageDialouge(createUserAct, createUserAct.getString(R.string.sc_tskUserDuplicate_title), createUserAct.getString(R.string.sc_tskUserDuplicate_msg));
                        break;
                }
            }
        }
    }
}