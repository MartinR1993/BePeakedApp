package project.martin.bepeakedprojekt.Backend.ServerTasks;

import android.content.DialogInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import project.martin.bepeakedprojekt.Backend.BackendData;
import project.martin.bepeakedprojekt.Logind_akt;
import project.martin.bepeakedprojekt.User.User;

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

            JSONObject reply = sendRequest(jsonObj.toString());
            try {
                if(reply.getString(TAG_ERROR).equals(ERROR_NO_HOST))
                    return new String[]{ERROR_NO_HOST};
            } catch (JSONException e) {}

            result = new String[1];
            result[0] = reply.getString(TAG_SALT);
            return result;
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void onPostExecute(String... result) {
        if(!result[0].equals(ERROR_NO_HOST)) {
            User.setOffline(false);
            login.login(result[0]);
        }
        else
            showConfirmDialoge(login, "Connection not fount", "Could not connect to server " + BackendData.SERVER_ADRESS + " Do you want to continue in offline mode?",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            System.out.println("OFFLINE MODE ACTIVE!");
                            User.setOffline(true);
                            login.gotoMenu();
                            dialog.dismiss();
                        }
                    },
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
    }
}
