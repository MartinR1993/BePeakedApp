package project.martin.bepeakedprojekt.Backend.ServerTasks;

/**
 * Created by Lasse on 17/01-17.
 */

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import project.martin.bepeakedprojekt.Exercises.ExerciseElement;

import static project.martin.bepeakedprojekt.Backend.ServerTasks.ServerTags.*;

public class TaskGetExercise extends ServerTask
{
    public TaskGetExercise(String host, int port) {
        super(host, port);
    }

    @Override
    public String[] doInBackground(String... params) {
        String[] result = new String[0];
        JSONObject jsonObj = new JSONObject();

        try {
            jsonObj.put(TAG_COMMAND, TAG_CMD_GET);
            jsonObj.put(TAG_CMD_SESSION_ID, params[1]);
            JSONArray argsJA = new JSONArray();
            argsJA.put(0, TAG_EXERCISE);
            argsJA.put(1, Integer.parseInt(params[0]));
            jsonObj.put(TAG_ARGS, argsJA);

            result = new String[4];
            JSONObject reply = sendRequest(jsonObj.toString());
            result[0] = "" + reply.getInt("id");
            result[1] = reply.getString("name");
            result[2] = reply.getString("description");
            result[3] = "" + reply.getInt("imageID");
            return result;
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void onPostExecute(String... result) {
        int id = Integer.parseInt(result[0]);
        String name = result[1];
        String description = result[2];
        int imageID = Integer.parseInt(result[3]);


        ExerciseElement exercise = new ExerciseElement(id, -1, null, name, description, imageID);
        //TODO: Send denne øvelse et eller andet sted hen.
    }
}