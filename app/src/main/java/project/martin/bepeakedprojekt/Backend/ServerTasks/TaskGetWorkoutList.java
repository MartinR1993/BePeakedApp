package project.martin.bepeakedprojekt.Backend.ServerTasks;

/**
 * Created by Lasse on 17/01-17.
 */

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import project.martin.bepeakedprojekt.Backend.BackendData;
import project.martin.bepeakedprojekt.Exercises.ExerciseElement;
import project.martin.bepeakedprojekt.R;
import project.martin.bepeakedprojekt.Workout.WorkoutElement;
import project.martin.bepeakedprojekt.Workout.WorkoutMenu_akt;

import static project.martin.bepeakedprojekt.Backend.ServerTasks.ServerTags.*;

public class TaskGetWorkoutList extends ServerTask
{
    private final WorkoutMenu_akt workoutsMenu;

    public TaskGetWorkoutList(WorkoutMenu_akt workoutsMenu, String host, int port) {
        super(host, port);
        this.workoutsMenu = workoutsMenu;
    }

    @Override
    public String[] doInBackground(String... params) {
        String[] result = new String[0];
        JSONObject jsonObj = new JSONObject();

        try {
            jsonObj.put(TAG_COMMAND, TAG_CMD_GET);
            jsonObj.put(TAG_CMD_SESSION_ID, params[1]);
            JSONArray argsJA = new JSONArray();
            argsJA.put(0, TAG_WORKOUTLIST);
            argsJA.put(1, Integer.parseInt(params[0]));
            jsonObj.put(TAG_ARGS, argsJA);

            JSONObject reply = sendRequest(jsonObj.toString());
            try {
                if(reply.getString(TAG_ERROR).equals(ERROR_NO_HOST))
                    return new String[]{ERROR_NO_HOST};
            } catch (JSONException e) {}

            result = new String[1];
            result[0] = reply.getString(TAG_WORKOUTLIST);
            return result;
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public void onPostExecute(String... result) {
        if(!result[0].equals(ERROR_NO_HOST)) {
            try {
                ArrayList<WorkoutElement> workoutList = new ArrayList<>();
                JSONArray workoutlistJSON = new JSONArray(result[0]);

                JSONObject workoutJSON;
                for(int i = 0; i < workoutlistJSON.length(); i++) {
                    workoutJSON = (JSONObject) workoutlistJSON.get(i);
                    int id = workoutJSON.getInt("id");
                    String name = workoutJSON.getString("name");

                    workoutList.add(new WorkoutElement(id, name, new ArrayList<ExerciseElement>(), true));
                }

                workoutsMenu.addWorkouts(workoutList);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else
            showMessageDialouge(workoutsMenu, workoutsMenu.getString(R.string.sc_tskConnError_title), workoutsMenu.getString(R.string.sc_tskConnError_msg) + BackendData.SERVER_ADRESS);
    }
}