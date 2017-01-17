package project.martin.bepeakedprojekt.Backend.ServerTasks;

import org.json.JSONException;

import java.io.IOException;

/**
 * Created by Lasse on 17/01-17.
 */

public interface IServerTask {
    public String[] doInBackground(String... params);

    public void onPostExecute(String... result);
}
