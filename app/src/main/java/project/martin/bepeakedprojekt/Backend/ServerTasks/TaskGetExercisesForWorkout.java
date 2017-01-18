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
import project.martin.bepeakedprojekt.Workout.WorkoutExercises.Workout_Exercises_akt;

import static project.martin.bepeakedprojekt.Backend.ServerTasks.ServerTags.ERROR_NO_HOST;
import static project.martin.bepeakedprojekt.Backend.ServerTasks.ServerTags.TAG_ARGS;
import static project.martin.bepeakedprojekt.Backend.ServerTasks.ServerTags.TAG_CMD_GET;
import static project.martin.bepeakedprojekt.Backend.ServerTasks.ServerTags.TAG_CMD_SESSION_ID;
import static project.martin.bepeakedprojekt.Backend.ServerTasks.ServerTags.TAG_COMMAND;
import static project.martin.bepeakedprojekt.Backend.ServerTasks.ServerTags.TAG_ERROR;
import static project.martin.bepeakedprojekt.Backend.ServerTasks.ServerTags.TAG_EXERCISELIST;

public class TaskGetExercisesForWorkout extends ServerTask
{
    private final Workout_Exercises_akt workoutMenu;

    public TaskGetExercisesForWorkout(Workout_Exercises_akt workoutMenu, String host, int port) {
        super(host, port);
        this.workoutMenu = workoutMenu;
    }

    @Override
    public String[] doInBackground(String... params) {
        String[] result = new String[0];
        JSONObject jsonObj = new JSONObject();

        try {
            jsonObj.put(TAG_COMMAND, TAG_CMD_GET);
            jsonObj.put(TAG_CMD_SESSION_ID, params[2]);
            JSONArray argsJA = new JSONArray();
            argsJA.put(0, TAG_EXERCISELIST);
            argsJA.put(1, Integer.parseInt(params[0]));
            argsJA.put(2, Integer.parseInt(params[1]));
            jsonObj.put(TAG_ARGS, argsJA);

            JSONObject reply = sendRequest(jsonObj.toString());
            try {
                if(reply.getString(TAG_ERROR).equals(ERROR_NO_HOST))
                    return new String[]{ERROR_NO_HOST};
            } catch (JSONException e) {}

            result = new String[1];
            result[0] = reply.getString(TAG_EXERCISELIST);
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
                ArrayList<ExerciseElement> exerciseList = new ArrayList<>();
                JSONArray exerciselistJSON = new JSONArray(result[0]);

                JSONObject exerciseJSON;
                for(int i = 0; i < exerciselistJSON.length(); i++) {
                    exerciseJSON = (JSONObject) exerciselistJSON.get(i);
                    int id = exerciseJSON.getInt("id");
                    int sets = exerciseJSON.getInt("sets");
                    String name = exerciseJSON.getString("name");
                    String description = exerciseJSON.getString("description");
                    int imageID = exerciseJSON.getInt("imageID");
                    String reps = exerciseJSON.getString("reps");

                    exerciseList.add(new ExerciseElement(id, sets, reps, name, description, imageID));
                }

                workoutMenu.addExercises(exerciseList);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else
            showMessageDialouge(workoutMenu, workoutMenu.getString(R.string.sc_tskConnError_title), workoutMenu.getString(R.string.sc_tskConnError_msg) + BackendData.SERVER_ADRESS);
    }
}