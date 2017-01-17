package project.martin.bepeakedprojekt.Exercises;

import java.io.Serializable;

public class ResultElement implements Serializable {
    private int exerciseID, reps, resultID;
    private double weight, OneRM;

    public ResultElement(int resultID, int exerciseID, double weight, int reps, double OneRM) {
        this.resultID = resultID;
        this.exerciseID = exerciseID;
        this.weight = weight;
        this.reps = reps;
        this.OneRM = OneRM;
    }

    public int getResultID() { return resultID; }
    public void setResultID(int resultID) { this.resultID = resultID; }
    public int getExerciseID() { return exerciseID; }
    public void setExerciseID(int exerciseID) { this.exerciseID = exerciseID; }
    public int getReps() { return reps; }
    public void setReps(int reps) { this.reps = reps; }
    public double getWeight() { return weight; }
    public void setWeight(double weight) { this.weight = weight; }
    public double getOneRM() { return OneRM; }
    public void setOneRM(double oneRM) { OneRM = oneRM; }

}
