package project.martin.bepeakedprojekt.Workout;

import java.io.Serializable;
import java.util.ArrayList;

import project.martin.bepeakedprojekt.Exercises.ExerciseElement;

public class WorkoutElement implements Serializable
{
    private String workoutName;
    private ArrayList<ExerciseElement> exercises;
    private int workoutID;

    public WorkoutElement(int workoutID, String workoutName, ArrayList<ExerciseElement> exercises) {
        this.workoutID = workoutID;
        this.workoutName = workoutName;
        this.exercises = exercises;
    }

    public int getWorkoutID() {
        return workoutID;
    }

    public void setWorkoutID(int id) {
        this.workoutID = id;
    }

    public String getName() {
        return workoutName;
    }

    public void setName(String name) {
        this.workoutName = name;
    }

    public ArrayList<ExerciseElement> getExercises() {
        return exercises;
    }

    public void setExercises(ArrayList<ExerciseElement> exercises) {
        this.exercises = exercises;
    }

    @Override
    public String toString() {
        return "WorkoutElement{" +
                "workoutName='" + workoutName + '\'' +
                ", exercises=" + exercises +
                ", workoutID=" + workoutID +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        WorkoutElement that = (WorkoutElement) o;

        if (workoutID != that.workoutID)
            return false;
        if (!workoutName.equals(that.workoutName))
            return false;
        return exercises != null ? exercises.equals(that.exercises) : that.exercises == null;

    }

    @Override
    public int hashCode() {
        int result = workoutName.hashCode();
        result = 31 * result + (exercises != null ? exercises.hashCode() : 0);
        result = 31 * result + workoutID;
        return result;
    }
}