package project.martin.bepeakedprojekt.Backend.ServerTasks;

/**
 * Created by Lasse on 17/01-17.
 */

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import project.martin.bepeakedprojekt.Backend.BackendData;
import project.martin.bepeakedprojekt.Settings.User_akt;

import static project.martin.bepeakedprojekt.Backend.ServerTasks.ServerTags.*;

public class TaskGetUserProfile extends ServerTask
{
    private final User_akt userAct;

    public TaskGetUserProfile(User_akt userAct, String host, int port) {
        super(host, port);
        this.userAct = userAct;
    }

    @Override
    public String[] doInBackground(String... params) {
        String[] result = new String[0];
        JSONObject jsonObj = new JSONObject();

        try {
            jsonObj.put(TAG_COMMAND, TAG_CMD_GET);
            jsonObj.put(TAG_CMD_SESSION_ID, params[1]);
            JSONArray argsJA = new JSONArray();
            argsJA.put(0, TAG_USER_PROFILE);
            argsJA.put(1, Integer.parseInt(params[0]));
            jsonObj.put(TAG_ARGS, argsJA);

            result = new String[10];
            JSONObject reply = sendRequest(jsonObj.toString());
            try {
                if(reply.getString(TAG_ERROR).equals(ERROR_NO_HOST))
                    return new String[]{ERROR_NO_HOST};
            } catch (JSONException e) {}

            result[0] = "" + reply.getString("first");
            result[1] = "" + reply.getString("last");
            result[2] = "" + reply.getInt("age");
            result[3] = "" + reply.getDouble("height");
            result[4] = "" + reply.getDouble("weight");
            result[5] = "" + reply.getDouble("prot");
            result[6] = "" + reply.getDouble("cal");
            result[7] = "" + reply.getDouble("col");
            result[8] = "" + reply.getDouble("fat");
            result[9] = "" + reply.getInt("dpid");
            return result;
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void onPostExecute(String... result) {
        if(!result[0].equals(ERROR_NO_HOST)) {
            String firstName = result[0];
            String lastName = result[1];
            int age = Integer.parseInt(result[2]);
            double height = Double.parseDouble(result[3]);
            double weight = Double.parseDouble(result[4]);
            double prot = Double.parseDouble(result[5]);
            double cal = Double.parseDouble(result[6]);
            double col = Double.parseDouble(result[7]);
            double fat = Double.parseDouble(result[8]);
            int dpid = Integer.parseInt(result[9]);

            userAct.setUserData(firstName, lastName, age, height, weight, prot, cal, col, fat, dpid);
        }
        else
            showMessageDialouge(userAct, "Connection error", "Lost connection to server " + BackendData.SERVER_ADRESS);
    }
}