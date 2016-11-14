package project.martin.bepeakedprojekt.Workout;

import java.util.ArrayList;

/**
 * Created by Martin on 14-11-2016.
 */

public class WorkoutElement {
    private String workoutName;
    ArrayList<String> exercises;
    private int image;


    public WorkoutElement(String workoutName, ArrayList<String> exercises, int image) {
        this.exercises = exercises;
        this.workoutName = workoutName;
        this.image = image;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getWorkoutName() {
        return workoutName;
    }

    public void setWorkoutName(String workoutName) {
        this.workoutName = workoutName;
    }

    public ArrayList<String> getExercises() {
        return exercises;
    }

    public void setExercises(ArrayList<String> exercises) {
        this.exercises = exercises;
    }

    @Override
    public String toString() {
        return "WorkoutElement{" +
                "workoutName='" + workoutName + '\'' +
                ", exercises=" + exercises +
                ", image=" + image +
                '}';
    }
}
