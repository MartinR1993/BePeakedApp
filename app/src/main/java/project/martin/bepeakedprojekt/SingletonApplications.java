package project.martin.bepeakedprojekt;

import android.app.Application;

import java.util.ArrayList;

import project.martin.bepeakedprojekt.Exercises.ExerciseElement;

/**
 * Created by Martin on 16-01-2017.
 */

public class SingletonApplications extends Application {
    public static boolean changepic = false;
    public static ArrayList<ExerciseElement> data = new ArrayList();
    public static ArrayList<String> dataNames = new ArrayList();

    @Override
    public void onCreate() {
        super.onCreate();
    }

}
