package project.martin.bepeakedprojekt.Exercises;

import java.io.Serializable;

public class ExerciseElement implements Serializable
{
    private String exerciseName, description;
    private int exerciseID, imageID;

    public ExerciseElement(int exerciseID, String ExerciseName, String description, int imageID) {
        this.exerciseID = exerciseID;
        this.exerciseName = ExerciseName;
        this.description = description;
        this.imageID = imageID;
    }

    public int getExerciseID() {
        return exerciseID;
    }

    public void setExerciseID(int id) {
        this.exerciseID = id;
    }

    public String getName() {
        return exerciseName;
    }

    public void setName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getImageID() {
        return imageID;
    }

    public void setImageID(int id) {
        this.imageID = id;
    }

    @Override
    public String toString() {
        return "ExerciseElement{" +
                "exerciseName='" + exerciseName + '\'' +
                ", description='" + description + '\'' +
                ", exerciseID=" + exerciseID +
                ", imageID=" + imageID +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        ExerciseElement that = (ExerciseElement) o;

        if (exerciseID != that.exerciseID)
            return false;
        if (imageID != that.imageID)
            return false;
        if (!exerciseName.equals(that.exerciseName))
            return false;
        return description != null ? description.equals(that.description) : that.description == null;

    }

    @Override
    public int hashCode() {
        int result = exerciseName.hashCode();
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + exerciseID;
        result = 31 * result + imageID;
        return result;
    }
}
