package project.martin.bepeakedprojekt.Backend;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Arrays;

import project.martin.bepeakedprojekt.Exercises.ExerciseElement;
import project.martin.bepeakedprojekt.Exercises.ResultElement;
import project.martin.bepeakedprojekt.SingletonApplications;
import project.martin.bepeakedprojekt.Workout.WorkoutElement;

/**
 * Created by Martin on 22-11-2016.
 */

public class DatabaseCommunication extends SQLiteOpenHelper {
    private static final String DATABASE = "database.db";

    // Workout Table Columns names
    private static final String WORKOUT_TABLE = "Workouts";
    private static final String WORKOUT_ID = "WorkoutID";
    private static final String WORKOUT_NAME = "WorkoutName";

    // WorkoutExercises Table Columns names
    private static final String WORKOUTEXERCISE_TABLE = "WorkoutExercises";
    //private static final String WORKOUT_ID = "WorkoutID";
    private static final String EXERCISE_ID = "ExerciseID";

    // ExerciseResults Table Columns names
//    private static final String EXERCISERESULTS_TABLE = "ExerciseResults";
//    private static final String RESULT_ID = "ResultID";
    //private static final String EXERCISE_ID = "ExerciseID";

    // Results Table Columns names
    private static final String RESULTS_TABLE = "Results";
    private static final String RESULT_ID = "ResultID";
    private static final String RESULT_WEIGHT = "ResultWeight";
    private static final String RESULT_REPS = "ResultReps";
    private static final String RESULT_1RM = "Result1RM";


    public DatabaseCommunication(Context context) {
        super(context, DATABASE, null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_WORKOUT_TABLE = "CREATE TABLE " + WORKOUT_TABLE + "(" + WORKOUT_ID + " INTEGER PRIMARY KEY," + WORKOUT_NAME + " TEXT)";
        db.execSQL(CREATE_WORKOUT_TABLE);

        String CREATE_WORKOUTEXERCISE_TABLE = "CREATE TABLE " + WORKOUTEXERCISE_TABLE + "(" + WORKOUT_ID + " INTEGER," + EXERCISE_ID + " INTEGER)";
        db.execSQL(CREATE_WORKOUTEXERCISE_TABLE);

        String CREATE_RESULTS_TABLE = "CREATE TABLE " + RESULTS_TABLE + "(" + RESULT_ID + " INTEGER PRIMARY KEY, " + EXERCISE_ID + " INTEGER, " + RESULT_WEIGHT + " DOUBLE," + RESULT_REPS + " INTEGER," + RESULT_1RM + " DOUBLE)";
        db.execSQL(CREATE_RESULTS_TABLE);

//        String CREATE_RESULTS_TABLE = "CREATE TABLE " + RESULTS_TABLE + "(" + RESULT_ID + " INTEGER PRIMARY KEY, " + RESULT_WEIGHT + " DOUBLE," + RESULT_REPS + " INTEGER," + RESULT_1RM + " DOUBLE)";
//        db.execSQL(CREATE_RESULTS_TABLE);
//
//        String CREATE_EXERCISERESULT_TABLE = "CREATE TABLE " + EXERCISERESULTS_TABLE + "(" + RESULT_ID + " INTEGER," + EXERCISE_ID + " INTEGER)";
//        db.execSQL(CREATE_EXERCISERESULT_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table " + WORKOUT_TABLE);
        db.execSQL("drop table " + WORKOUTEXERCISE_TABLE);
        db.execSQL("drop table " + RESULTS_TABLE);
//        db.execSQL("drop table " + EXERCISERESULTS_TABLE);
        this.onCreate(db);

    }

    public ArrayList<WorkoutElement> getAllWorkouts() {
        ArrayList<WorkoutElement> workoutElements = new ArrayList<WorkoutElement>();
        String selectQuery = "SELECT * FROM " + WORKOUT_TABLE;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                WorkoutElement elem = new WorkoutElement(1, "", new ArrayList<ExerciseElement>(), false);
                elem.setWorkoutID(cursor.getInt(0));
                elem.setName(cursor.getString(1));
                workoutElements.add(elem);
            } while (cursor.moveToNext());
        }
        return workoutElements;
    }


    //Test... giver et array af exercise ID'er vi kan bruge til at hente exercises fra Lasses database
    public ArrayList<Integer> getWorkoutExercises(int WorkoutID) {
        String selectQuery = "SELECT * FROM " + WORKOUTEXERCISE_TABLE + " WHERE " + WORKOUT_ID + "=" + WorkoutID + "";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        ArrayList<Integer> ExerciseIDs = new ArrayList<Integer>();
        if (cursor.moveToFirst()) {
            do {
                int uname = cursor.getInt(cursor.getColumnIndex("ExerciseID"));
                ExerciseIDs.add(uname);
            } while (cursor.moveToNext());
        }
        return ExerciseIDs;
    }


    public void addWorkout(int id, String name) {
        String insertQuery = "INSERT INTO " + WORKOUT_TABLE + "(" + WORKOUT_ID + ", " + WORKOUT_NAME + ") VALUES " + "(NULL, '" + name + "')";
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(insertQuery);
    }


    public void removeWorkout(int id) {
        String removeQuery = "DELETE FROM " + WORKOUT_TABLE + " WHERE " + WORKOUT_ID + " = " + id;
        String removeQuery2 = "DELETE FROM " + WORKOUTEXERCISE_TABLE + " WHERE " + WORKOUT_ID + " = " + id;
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(removeQuery);
        db.execSQL(removeQuery2);
    }


    public void editWorkout(String newName, int id) {
        String updateQuery = "UPDATE " + WORKOUT_TABLE + " SET " + WORKOUT_NAME + " = " + newName + " WHERE " + WORKOUT_ID + " = " + id;
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(updateQuery);
    }


    public void addWorkoutExercise(int workoutID, int exerciseID) {
        String insertQuery = "INSERT INTO " + WORKOUTEXERCISE_TABLE + "(" + WORKOUT_ID + ", " + EXERCISE_ID + ") VALUES " + "(" + workoutID + "," + exerciseID + ")";
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(insertQuery);
    }

    public void removeWorkoutExercise(int workoutID, int exerciseID) {
        String deleteQuery = "DELETE FROM " + WORKOUTEXERCISE_TABLE + " WHERE " + WORKOUT_ID + " = " + workoutID + " AND " + EXERCISE_ID + " = " + exerciseID;
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(deleteQuery);
    }

    public void removeAllWorkoutExercises(int workoutID) {
        String deleteQuery = "DELETE FROM " + WORKOUTEXERCISE_TABLE + " WHERE " + WORKOUT_ID + " = " + workoutID;
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(deleteQuery);
    }

    public ArrayList<Integer> getWorkouts() {
        String selectQuery = "SELECT " + WORKOUT_ID + " FROM " + WORKOUTEXERCISE_TABLE;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        ArrayList<Integer> WorkoutIDs = new ArrayList<Integer>();
        if (cursor.moveToFirst()) {
            do {
                int uname = cursor.getInt(cursor.getColumnIndex("WorkoutID"));
                WorkoutIDs.add(uname);
            } while (cursor.moveToNext());
        }
        System.out.println("Workouts : " + Arrays.toString(WorkoutIDs.toArray()));
        return WorkoutIDs;
    }


    //RESULTATERDELEN KOMMER HER SÅ VÆR KLAR PARAT TIL START

    public void addExerciseResult(int resultID, int exerciseID, double weight, int reps, double OneRM) {
        String insertQuery = "INSERT INTO " + RESULTS_TABLE + "( " + RESULT_ID + "," + EXERCISE_ID + ", " + RESULT_WEIGHT + ", " + RESULT_REPS + ", " + RESULT_1RM + ") VALUES " + "(" + resultID + "," + exerciseID + "," + weight + "," + reps + "," + OneRM + ")";
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(insertQuery);
    }

    public void editExerciseResult(int resultID, double weight, int reps, double OneRM) {
        String updateQuery = "UPDATE " + RESULTS_TABLE + " SET " + RESULT_WEIGHT + " = " + weight + ", " + RESULT_REPS + " = " + reps + ", " + RESULT_1RM + " = " + RESULT_1RM + " WHERE " + RESULT_ID + " = " +resultID;
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(updateQuery);
    }

    public ArrayList<ResultElement> getResults(int exerciseID) {
        String selectQuery = "SELECT * FROM " + RESULTS_TABLE + " WHERE " + EXERCISE_ID + "=" + exerciseID + "";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        ArrayList<ResultElement> resultList = new ArrayList<ResultElement>();

        if (cursor.moveToFirst()) {
            do {
                ResultElement elem = new ResultElement(0, 0, 0, 0, 0);
                elem.setResultID(cursor.getInt(0));
                elem.setExerciseID(cursor.getInt(1));
                elem.setWeight(cursor.getDouble(2));
                elem.setReps(cursor.getInt(3));
                elem.setOneRM(cursor.getDouble(4));
                resultList.add(elem);
            } while (cursor.moveToNext());
        }
        return resultList;
    }
}

//    public void editExerciseResult(int exerciseID, double weight, int reps, double OneRM) {
//        String updateQuery = "INSERT INTO " + RESULTS_TABLE + "(" + EXERCISE_ID + ", " + RESULT_WEIGHT + ", " + RESULT_REPS + ", " + RESULT_1RM + ") VALUES " + "(" + exerciseID + "," + weight + "," + reps + "," + OneRM + ")";
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.execSQL(updateQuery);
//    }
//

//
//
//    public ArrayList<ResultElement> getResults(int resultID) {
//        String selectQuery = "SELECT * FROM " + RESULTS_TABLE + " WHERE " + RESULT_ID + "=" + resultID + "";
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor cursor = db.rawQuery(selectQuery, null);
//        ArrayList<ResultElement> resultList = new ArrayList<ResultElement>();
//
//        if (cursor.moveToFirst()) {
//            do {
//                ResultElement elem = new ResultElement(0, 0, 0, 0);
//                elem.setExerciseID(cursor.getInt(0));
//                elem.setWeight(cursor.getDouble(1));
//                elem.setReps(cursor.getInt(2));
//                elem.setOneRM(cursor.getDouble(3));
//                resultList.add(elem);
//            } while (cursor.moveToNext());
//        }
//        return resultList;
//    }
//}

//
//    public ArrayList<ExerciseElement> getAllasdaExercises() {
//        ArrayList<ExerciseElement> exerciseList = new ArrayList<ExerciseElement>();
//
//        // Select All Query
//        String selectQuery = "SELECT * FROM Exercises";
//
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor cursor = db.rawQuery(selectQuery, null);
//
//        // looping through all rows and adding to list
//        if (cursor.moveToFirst()) {
//            do {
//                //public WorkoutElement(int workoutID, String workoutName, ArrayList<ExerciseElement> exercises)
//                ExerciseElement elem = new ExerciseElement(0, 0, "", "", "", 0);
//                //int exerciseID, int sets, String reps, String ExerciseName, String description, int imageID
//
//                //elem.setWorkoutID(Integer.parseInt(cursor.getString(0)));
//                elem.setExerciseID(cursor.getInt(0));
//                elem.setSets(cursor.getInt(1));
//                elem.setReps(cursor.getString(2));
//                elem.setName(cursor.getString(3));
//                elem.setDescription(cursor.getString(4));
//                elem.setImageID(cursor.getInt(5));
//                // Adding to list
//                exerciseList.add(elem);
//            } while (cursor.moveToNext());
//        }
//
//        // return  list
//        return exerciseList;
//    }






//    // ExerciseResults Table Columns names
//    private static final String EXERCISERESULTS_TABLE = "WorkoutExercises";
//    //private static final String EXERCISE_ID = "ExerciseID";
//    private static final String RESULT_WEIGHT = "ResultWeight";
//    private static final String RESULT_REPS = "ResultReps";
//    private static final String RESULT_1RM = "Result1RM";
//
//        String CREATE_EXERCISERESULTS_TABLE = "CREATE TABLE " + EXERCISERESULTS_TABLE + "(" + EXERCISE_ID + " INTEGER PRIMARY KEY," + RESULT_WEIGHT + " INTEGER," + RESULT_REPS + " INTEGER)";
//        db.execSQL(CREATE_EXERCISERESULTS_TABLE);







//    public ArrayList<Integer> getWorkoutExercises(int WorkoutID) {
//        String selectQuery = "SELECT * FROM "+ WORKOUTEXERCISE_TABLE +" WHERE "+ WORKOUT_ID +"="+WorkoutID+"";
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor cursor = db.rawQuery(selectQuery, null);
//        ArrayList<Integer> ExerciseIDs = new ArrayList<Integer>();
//        if (cursor.moveToFirst()) {
//            do {
//                int uname = cursor.getInt(cursor.getColumnIndex("ExerciseID"));
//                ExerciseIDs.add(uname);
//            } while (cursor.moveToNext());
//        }
//        System.out.println("SE MIG!!!!!: "+ Arrays.toString(ExerciseIDs.toArray()));
//        return ExerciseIDs;
//    }



//    public void addExercises(ExerciseElement[] exerciseList) {
//
//        for (int i=0; i<exerciseList.length; i++) {
//            String insertQuery = "INSERT INTO "+ EXERCISE_TABLE +"(ExerciseID, ExerciseSets, ExerciseReps, ExerciseName, ExerciseDesc, ExerciseImage) " +
//                    "VALUES ("+ exerciseList[i].getExerciseID() +","+ exerciseList[i].getSets() +","+ exerciseList[i].getReps() +",'"+ exerciseList[i].getName() +"','"+ exerciseList[i].getDescription() +"', "+ exerciseList[i].getImageID() +")";
//            SQLiteDatabase db = this.getWritableDatabase();
//            db.execSQL(insertQuery);
//        }
//    }

//    public ArrayList<ExerciseElement> getAllExercises() {
//        ArrayList<ExerciseElement> exerciseList = new ArrayList<ExerciseElement>();
//
//        // Select All Query
//        String selectQuery = "SELECT * FROM Exercises";
//
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor cursor = db.rawQuery(selectQuery, null);
//
//        // looping through all rows and adding to list
//        if (cursor.moveToFirst()) {
//            do {
//                //public WorkoutElement(int workoutID, String workoutName, ArrayList<ExerciseElement> exercises)
//                ExerciseElement elem = new ExerciseElement(0, 0, "", "", "", 0);
//                //int exerciseID, int sets, String reps, String ExerciseName, String description, int imageID
//
//                //elem.setWorkoutID(Integer.parseInt(cursor.getString(0)));
//                elem.setExerciseID(cursor.getInt(0));
//                elem.setSets(cursor.getInt(1));
//                elem.setReps(cursor.getString(2));
//                elem.setName(cursor.getString(3));
//                elem.setDescription(cursor.getString(4));
//                elem.setImageID(cursor.getInt(5));
//                // Adding to list
//                exerciseList.add(elem);
//            } while (cursor.moveToNext());
//        }
//
//        // return  list
//        return exerciseList;
//    }



// Exercise Table Columns names
//"CREATE TABLE " + EXERCISE_TABLE + "(" +EXERCISE_ID + " INTEGER PRIMARY KEY,"
//     + EXERCISE_SETS + " INTEGER, "+ EXERCISE_REPS +" INTEGER," + EXERCISE_NAME + " TEXT," + EXERCISE_DESC + " TEXT," + EXERCISE_IMAGE + " INTEGER)";


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


//    public ArrayList<ExerciseElement> getAllWorkoutExercises(int ID) {
//        ArrayList<ExerciseElement> exerciseList = new ArrayList<ExerciseElement>();
//
//        // Select All Query
//        String selectQuery = "SELECT * FROM WorkoutExercises NATURAL JOIN Exercises WHERE WorkoutID="+ID+"";
//
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor cursor = db.rawQuery(selectQuery, null);
//
//        // looping through all rows and adding to list
//        if (cursor.moveToFirst()) {
//            do {
//                //public WorkoutElement(int workoutID, String workoutName, ArrayList<ExerciseElement> exercises)
//                ExerciseElement elem = new ExerciseElement(0, 0, "", "", "", 0);
//                //int exerciseID, int sets, String reps, String ExerciseName, String description, int imageID
//
//                //elem.setWorkoutID(Integer.parseInt(cursor.getString(0)));
//                elem.setExerciseID(cursor.getInt(0));
//                elem.setSets(cursor.getInt(1));
//                elem.setReps(cursor.getString(2));
//                elem.setName(cursor.getString(3));
//                elem.setDescription(cursor.getString(4));
//                elem.setImageID(cursor.getInt(5));
//                // Adding to list
//                exerciseList.add(elem);
//            } while (cursor.moveToNext());
//        }
//
//        // return  list
//        return exerciseList;
//    }


// Exercise Table Columns names
//private static final String EXERCISE_TABLE = "Exercises";
//private static final String EXERCISE_ID = "ExerciseID";
//private static final String EXERCISE_SETS = "ExerciseSets";
//private static final String EXERCISE_REPS = "ExerciseReps";
//private static final String EXERCISE_NAME = "ExerciseName";
//private static final String EXERCISE_DESC = "ExerciseDesc";
//private static final String EXERCISE_IMAGE = "ExerciseImage";



//String CREATE_EXERCISE_TABLE = "CREATE TABLE " + EXERCISE_TABLE + "(" +EXERCISE_ID + " INTEGER PRIMARY KEY,"
//        + EXERCISE_SETS + " INTEGER, "+ EXERCISE_REPS +" INTEGER," + EXERCISE_NAME + " TEXT," + EXERCISE_DESC + " TEXT," + EXERCISE_IMAGE + " INTEGER)";
//db.execSQL(CREATE_EXERCISE_TABLE);

//db.execSQL("drop table " + EXERCISE_TABLE);