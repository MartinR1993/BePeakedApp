package project.martin.bepeakedprojekt.Exercises;

import java.util.ArrayList;

/**
 * Created by Martin on 14-11-2016.
 */

public class ExerciseElement {
    private String ExerciseName;
    private int imageID;


    public ExerciseElement(String ExerciseName, int imageID) {
        this.ExerciseName = ExerciseName;
        this.imageID = imageID;
    }

    public String getExerciseName() {
        return ExerciseName;
    }

    public void setExerciseName(String exerciseName) {
        ExerciseName = exerciseName;
    }

    public int getImageID() {
        return imageID;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }

    @Override
    public String toString() {
        return "ExerciseElement{" +
                "ExerciseName='" + ExerciseName + '\'' +
                ", imageID=" + imageID +
                '}';
    }
}
