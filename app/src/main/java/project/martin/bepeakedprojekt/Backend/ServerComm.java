package project.martin.bepeakedprojekt.Backend;

import android.os.AsyncTask;

import java.util.Arrays;

import project.martin.bepeakedprojekt.Backend.ServerTasks.ServerTask;
import project.martin.bepeakedprojekt.Backend.ServerTasks.TaskActivateKey;
import project.martin.bepeakedprojekt.Backend.ServerTasks.TaskCreateUser;
import project.martin.bepeakedprojekt.Backend.ServerTasks.TaskGetDietplanProfile;
import project.martin.bepeakedprojekt.Backend.ServerTasks.TaskGetExercisesForWorkout;
import project.martin.bepeakedprojekt.Backend.ServerTasks.TaskGetSalt;
import project.martin.bepeakedprojekt.Backend.ServerTasks.TaskGetUserType;
import project.martin.bepeakedprojekt.Backend.ServerTasks.TaskGetWorkoutList;
import project.martin.bepeakedprojekt.Backend.ServerTasks.TaskLogin;
import project.martin.bepeakedprojekt.Diet_Plan.DietPlanMenu_akt;
import project.martin.bepeakedprojekt.Logind_akt;
import project.martin.bepeakedprojekt.MainMenu_akt;
import project.martin.bepeakedprojekt.Settings.ActivationKey_akt;
import project.martin.bepeakedprojekt.User.CreateUser_akt;
import project.martin.bepeakedprojekt.Workout.WorkoutExercises.Workout_Exercises_akt;
import project.martin.bepeakedprojekt.Workout.WorkoutMenu_akt;

/**
 * Created by Lasse on 13-01-2017.
 */

public class ServerComm extends AsyncTask<String, Void, String[]>
{
    private String host;
    private int port;

    private ServerTask serverTask;

    public ServerComm(String host, int port) {
        this.host = host;
        this.port = port;
    }

    private ServerComm(ServerTask serverTask) {
        this.serverTask = serverTask;
    }

    public void getSalt(Logind_akt login, String username) {
        new ServerComm(new TaskGetSalt(login, host, port)).execute(username);
    }

    public void login(Logind_akt login, String username, String password, String salt) {
        new ServerComm(new TaskLogin(login, host, port)).execute(username, password, salt);
    }

    public void activateUser(ActivationKey_akt activationAct, int userID, String activationKey, String sessionID) {
        new ServerComm(new TaskActivateKey(activationAct, host, port)).execute("" + userID, activationKey, sessionID);
    }

    public void getUserType(MainMenu_akt mainMenu, int userID, String sessionID) {
        new ServerComm(new TaskGetUserType(mainMenu, host, port)).execute("" + userID, sessionID);
    }

    public void createUser(CreateUser_akt createUserAct, String firstName, String lastName, String nickName, String passHash, String salt, String email) {
        new ServerComm(new TaskCreateUser(host, port)).execute(firstName, lastName, nickName, passHash, salt, email);
    }

    public void getDieatplanProfile(DietPlanMenu_akt dietplanMenu, int userID, String sessionID) {
        new ServerComm(new TaskGetDietplanProfile(dietplanMenu, host, port)).execute("" + userID, sessionID);
    }

    public void getWorkoutlist(WorkoutMenu_akt workoutsMenu, int userID, String sessionID) {
        new ServerComm(new TaskGetWorkoutList(workoutsMenu, host, port)).execute("" + userID, sessionID);
    }

    public void getExercisesByWorkoutID(Workout_Exercises_akt workoutMenu, int userID, int workoutID, String sessionID) {
        new ServerComm(new TaskGetExercisesForWorkout(workoutMenu, host, port)).execute("" + userID, "" + workoutID, sessionID);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String[] doInBackground(String... params) {
        return serverTask.doInBackground(params);
    }

    @Override
    protected void onPostExecute(String... result) {
        if(result != null)
            System.out.println("RESULT=" + Arrays.toString(result));
        else
            System.out.println("RESULT=NULL");

        serverTask.onPostExecute(result);
    }
}