package project.martin.bepeakedprojekt.Backend.ServerTasks;

/**
 * Created by Lasse on 17/01-17.
 */

public interface IServerTask {
    public String[] doInBackground(String... params);

    public void onPostExecute(String... result);
}
