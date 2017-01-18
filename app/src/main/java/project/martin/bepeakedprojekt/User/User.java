package project.martin.bepeakedprojekt.User;

/**
 * Created by Martin on 21-11-2016.
 */

public class User {
    private int height, weight, age, kostplanID, calories, culhydrates;
    private double protien, fat;
    private static int userID;
    private static String sessionID;
    private static boolean offline = true;

    public User(int height, int weight, int age, int kostplanID, int calories, int culhydrates, double protien, double fat) {
        this.height = height;
        this.weight = weight;
        this.age = age;
        this.kostplanID = kostplanID;
        this.calories = calories;
        this.culhydrates = culhydrates;
        this.protien = protien;
        this.fat = fat;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getKostplanID() {
        return kostplanID;
    }

    public void setKostplanID(int kostplanID) {
        this.kostplanID = kostplanID;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public int getCulhydrates() {
        return culhydrates;
    }

    public void setCulhydrates(int culhydrates) {
        this.culhydrates = culhydrates;
    }

    public double getProtien() {
        return protien;
    }

    public void setProtien(double protien) {
        this.protien = protien;
    }

    public double getFat() {
        return fat;
    }

    public void setFat(double fat) {
        this.fat = fat;
    }

    public static String getSessionID() {
        return sessionID;
    }

    public static void setSessionID(String sessionID) {
        User.sessionID = sessionID;
    }

    public static int getUserID() {
        return userID;
    }

    public static void setUserID(int userID) {
        User.userID = userID;
    }

    public static boolean isOffline() {
        return offline;
    }

    public static void setOffline(boolean offline) {
        User.offline = offline;
    }

    @Override
    public String toString() {
        return "User{" +
                "height=" + height +
                ", weight=" + weight +
                ", age=" + age +
                ", kostplanID=" + kostplanID +
                ", calories=" + calories +
                ", culhydrates=" + culhydrates +
                ", protien=" + protien +
                ", fat=" + fat +
                '}';
    }
}
