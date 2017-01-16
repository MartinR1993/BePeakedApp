package project.martin.bepeakedprojekt;

import android.app.Application;

import java.util.ArrayList;

import project.martin.bepeakedprojekt.Backend.DatabaseCommunication;
import project.martin.bepeakedprojekt.Exercises.ExerciseElement;
import project.martin.bepeakedprojekt.Workout.WorkoutElement;

/**
 * Created by Martin on 16-01-2017.
 */

public class SingletonApplications extends Application {
    public static boolean changepic = false;
    public static ArrayList<ExerciseElement> data = new ArrayList();
    public static ArrayList<String> dataNames = new ArrayList();
    public static WorkoutElement workout;
    public static DatabaseCommunication DBcom;
    public static int currentExerciseID;
    @Override
    public void onCreate() {
        super.onCreate();
        DBcom = new DatabaseCommunication(this);
    }

}
