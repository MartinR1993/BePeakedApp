package project.martin.bepeakedprojekt.Backend.ServerTasks;

/**
 * Created by Lasse on 17/01-17.
 */

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import project.martin.bepeakedprojekt.Backend.BackendData;
import project.martin.bepeakedprojekt.Diet_Plan.DietPlanMenu_akt;
import project.martin.bepeakedprojekt.R;

import static project.martin.bepeakedprojekt.Backend.ServerTasks.ServerTags.*;

public class TaskGetDietplanProfile extends ServerTask
{
    private final DietPlanMenu_akt dietplanMenu;

    public TaskGetDietplanProfile(DietPlanMenu_akt dietplanMenu, String host, int port) {
        super(host, port);
        this.dietplanMenu = dietplanMenu;
    }

    @Override
    public String[] doInBackground(String... params) {
        String[] result = new String[0];
        JSONObject jsonObj = new JSONObject();

        try {
            jsonObj.put(TAG_COMMAND, TAG_CMD_GET);
            jsonObj.put(TAG_CMD_SESSION_ID, params[1]);
            JSONArray argsJA = new JSONArray();
            argsJA.put(0, TAG_DIETPLAN);
            argsJA.put(1, Integer.parseInt(params[0]));
            jsonObj.put(TAG_ARGS, argsJA);

            result = new String[4];
            JSONObject reply = sendRequest(jsonObj.toString());
            try {
                if(reply.getString(TAG_ERROR).equals(ERROR_NO_HOST))
                    return new String[]{ERROR_NO_HOST};
            } catch (JSONException e) {}

            result[0] = "" + reply.getDouble("dp_protein");
            result[1] = "" + reply.getDouble("dp_calories");
            result[2] = "" + reply.getDouble("dp_culhydrates");
            result[3] = "" + reply.getDouble("dp_fat");
            return result;
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void onPostExecute(String... result) {
        if(!result[0].equals(ERROR_NO_HOST)) {
            double prot = Double.parseDouble(result[0]);
            double cal = Double.parseDouble(result[1]);
            double col = Double.parseDouble(result[2]);
            double fat = Double.parseDouble(result[3]);

            dietplanMenu.setUserData(prot, cal, col, fat);
        }
        else
            showMessageDialouge(dietplanMenu, dietplanMenu.getString(R.string.sc_tskConnError_title), dietplanMenu.getString(R.string.sc_tskConnError_msg) + BackendData.SERVER_ADRESS);
    }
}