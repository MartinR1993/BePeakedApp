package project.martin.bepeakedprojekt.Backend;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import project.martin.bepeakedprojekt.Exercises.ExerciseElement;
import project.martin.bepeakedprojekt.Misc.DummyData;
import project.martin.bepeakedprojekt.Workout.WorkoutElement;

/**
 * Created by Martin on 22-11-2016.
 */

public class DatabaseCommunication extends SQLiteOpenHelper {
    private static final String HOST     = "nasie.diskstation.me"; //Lasses server
    private static final int    PORT     = 3306;
    private static final String DATABASE = "bepeakedwebserver_com_db";
    private static final String USERNAME = "DTU123";
    private static final String PASSWORD = "Gruppe9";

    // Workout Table Columns names
    private static final String WORKOUT_TABLE = "Workouts";
    private static final String WORKOUT_ID = "WorkoutID";
    private static final String WORKOUT_NAME = "WorkoutName";

    // Exercise Table Columns names
    private static final String EXERCISE_TABLE = "Exercises";
    private static final String EXERCISE_ID = "ExerciseID";
    private static final String EXERCISE_SETS = "ExerciseSets";
    private static final String EXERCISE_REPS = "ExerciseReps";
    private static final String EXERCISE_NAME = "ExerciseName";
    private static final String EXERCISE_DESC = "ExerciseDesc";
    private static final String EXERCISE_IMAGE = "ExerciseImage";

    // WorkoutExercises Table Columns names
    private static final String WORKOUTEXERCISE_TABLE = "WorkoutExercises";
    //private static final String WORKOUT_ID = "WorkoutID";
    //private static final String EXERCISE_ID = "ExerciseID";

    public DatabaseCommunication(Context context) {
        super(context, HOST, null, 2);
    }

//CREATE TABLE operatoer(opr_id INT PRIMARY KEY, opr_navn TEXT, ini TEXT, cpr TEXT, password TEXT, aktiv BOOLEAN, type TEXT)
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_WORKOUT_TABLE = "CREATE TABLE " + WORKOUT_TABLE + "(" +WORKOUT_ID + " INTEGER AUTO_INCREMENT PRIMARY KEY," + WORKOUT_NAME + " TEXT)";
        db.execSQL(CREATE_WORKOUT_TABLE);

        String CREATE_WORKOUTEXERCISE_TABLE = "CREATE TABLE " + WORKOUTEXERCISE_TABLE + "(" +WORKOUT_ID + " INTEGER," + EXERCISE_ID + " INTEGER)";
        db.execSQL(CREATE_WORKOUTEXERCISE_TABLE);

        String CREATE_EXERCISE_TABLE = "CREATE TABLE " + EXERCISE_TABLE + "(" +EXERCISE_ID + " INTEGER PRIMARY KEY,"
                + EXERCISE_SETS + " INTEGER, "+ EXERCISE_REPS +" INTEGER," + EXERCISE_NAME + " TEXT," + EXERCISE_DESC + " TEXT," + EXERCISE_IMAGE + " INTEGER)";
        db.execSQL(CREATE_EXERCISE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table " + WORKOUT_TABLE);
        db.execSQL("drop table " + WORKOUTEXERCISE_TABLE);
        db.execSQL("drop table " + EXERCISE_TABLE);
        this.onCreate(db);

    }

    public ArrayList<WorkoutElement> getAllWorkouts() {
        ArrayList<WorkoutElement> workoutElements = new ArrayList<WorkoutElement>();

        // Select All Query
        String selectQuery = "SELECT * FROM Workouts";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                //public WorkoutElement(int workoutID, String workoutName, ArrayList<ExerciseElement> exercises)
                WorkoutElement elem = new WorkoutElement(1,"", new ArrayList<ExerciseElement>());

                //elem.setWorkoutID(Integer.parseInt(cursor.getString(0)));
                elem.setWorkoutID(cursor.getInt(0));
                elem.setName(cursor.getString(1));
                // Adding to list
                workoutElements.add(elem);
            } while (cursor.moveToNext());
        }

        // return  list
        return workoutElements;
    }



    public ArrayList<ExerciseElement> getAllWorkoutExercises(int ID) {
        ArrayList<ExerciseElement> exerciseList = new ArrayList<ExerciseElement>();

        // Select All Query
        String selectQuery = "SELECT * FROM WorkoutExercises NATURAL JOIN Exercises WHERE WorkoutID="+ID+"";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                //public WorkoutElement(int workoutID, String workoutName, ArrayList<ExerciseElement> exercises)
                ExerciseElement elem = new ExerciseElement(0, 0, "", "", "", 0);
                //int exerciseID, int sets, String reps, String ExerciseName, String description, int imageID

                //elem.setWorkoutID(Integer.parseInt(cursor.getString(0)));
                elem.setExerciseID(cursor.getInt(0));
                elem.setSets(cursor.getInt(1));
                elem.setReps(cursor.getString(2));
                elem.setName(cursor.getString(3));
                elem.setDescription(cursor.getString(4));
                elem.setImageID(cursor.getInt(5));
                // Adding to list
                exerciseList.add(elem);
            } while (cursor.moveToNext());
        }

        // return  list
        return exerciseList;
    }



    public void addWorkout(String name) {
        //WorkoutElement elem = new WorkoutElement(0, name, null);
        //String selectQuery = "INSERT INTO Workout(WorkoutID, WorkoutName, WorkoutExercises) VALUES " + "('0', '" + name + "', '" + name + "')";
        String insertQuery = "INSERT INTO Workouts(WorkoutID, WorkoutName) VALUES " + "(NULL, '" + name + "')";
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(insertQuery);
        //Cursor cursor = db.rawQuery(selectQuery, null);
    }
    //new WorkoutElement(1, "Bench", new ArrayList<>(Arrays.asList(getExercise(2), getExercise(3), getExercise(4), getExercise(6)))),
    //new WorkoutElement(2, "Dumbell", new ArrayList<>(Arrays.asList(getExercise(1), getExercise(4), getExercise(5), getExercise(7)))),
    //new WorkoutElement(3, "Curl", new ArrayList<>(Arrays.asList(getExercise(8), getExercise(9)))),
    //new WorkoutElement(4, "All", new ArrayList<>(Arrays.asList(exerciseList)))



    public void addWorkoutExercise(int workoutID, int exerciseID) {
        String insertQuery = "INSERT INTO "+ WORKOUTEXERCISE_TABLE +"(WorkoutID, ExerciseID) VALUES " + "("+workoutID+","+exerciseID+")";
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(insertQuery);
    }

    public void addExercises(ExerciseElement[] exerciseList) {

        for (int i=0; i<exerciseList.length; i++) {
            String insertQuery = "INSERT INTO "+ EXERCISE_TABLE +"(ExerciseID, ExerciseSets, ExerciseReps, ExerciseName, ExerciseDesc, ExerciseImage) " +
                    "VALUES ("+ exerciseList[i].getExerciseID() +","+ exerciseList[i].getSets() +","+ exerciseList[i].getReps() +",'"+ exerciseList[i].getName() +"','"+ exerciseList[i].getDescription() +"', "+ exerciseList[i].getImageID() +")";
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL(insertQuery);
        }
    }

    public ArrayList<ExerciseElement> getAllExercises() {
        ArrayList<ExerciseElement> exerciseList = new ArrayList<ExerciseElement>();

        // Select All Query
        String selectQuery = "SELECT * FROM Exercises";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                //public WorkoutElement(int workoutID, String workoutName, ArrayList<ExerciseElement> exercises)
                ExerciseElement elem = new ExerciseElement(0, 0, "", "", "", 0);
                //int exerciseID, int sets, String reps, String ExerciseName, String description, int imageID

                //elem.setWorkoutID(Integer.parseInt(cursor.getString(0)));
                elem.setExerciseID(cursor.getInt(0));
                elem.setSets(cursor.getInt(1));
                elem.setReps(cursor.getString(2));
                elem.setName(cursor.getString(3));
                elem.setDescription(cursor.getString(4));
                elem.setImageID(cursor.getInt(5));
                // Adding to list
                exerciseList.add(elem);
            } while (cursor.moveToNext());
        }

        // return  list
        return exerciseList;
    }



    // Exercise Table Columns names
    //"CREATE TABLE " + EXERCISE_TABLE + "(" +EXERCISE_ID + " INTEGER PRIMARY KEY,"
       //     + EXERCISE_SETS + " INTEGER, "+ EXERCISE_REPS +" INTEGER," + EXERCISE_NAME + " TEXT," + EXERCISE_DESC + " TEXT," + EXERCISE_IMAGE + " INTEGER)";

}

//this.exerciseID = exerciseID;
//this.exerciseName = ExerciseName;
//      this.description = description;
//      this.imageID = imageID;

//List<OperatoerDTO> list = new ArrayList<OperatoerDTO>();
//ResultSet rs;
//    try {
//        rs = connector.doQuery("SELECT * FROM operatoer");
//    } catch (SQLException e1) {
//        throw new DALException(e1);
//    }
//    try
//{
//        while (rs.next())
//        {
//            list.add(new OperatoerDTO(rs.getInt("opr_id"), rs.getString("opr_navn"), rs.getString("ini"), rs.getString("cpr"), rs.getString("password"), rs.getInt("status")));
//}
//    }
//    catch (SQLException e) { throw new DALException(e); }
//return list;
