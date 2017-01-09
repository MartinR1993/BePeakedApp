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

/**
 * Created by Martin on 22-11-2016.
 */

public class DatabaseCommunication extends SQLiteOpenHelper {
    private static final String HOST     = "nasie.diskstation.me"; //Lasses server
    private static final int    PORT     = 3306;
    private static final String DATABASE = "bepeakedwebserver_com_db";
    private static final String USERNAME = "DTU123";
    private static final String PASSWORD = "Gruppe9";

    // Contacts Table Columns names
    private static final String EXERCISE_TABLE = "Exercises";
    private static final String EXERCISES_ID = "ExercisesID";
    private static final String EXERCISE_NAME = "ExerciseName";
    private static final String EXERCISE_WORKOUT_ID = "WorkoutID";
    private static final String EXERCISE_TIME = "Datetime";

    public DatabaseCommunication(Context context) {
        super(context, HOST, null, 2);
    }

//CREATE TABLE operatoer(opr_id INT PRIMARY KEY, opr_navn TEXT, ini TEXT, cpr TEXT, password TEXT, aktiv BOOLEAN, type TEXT)
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + EXERCISE_TABLE + "("
                + EXERCISES_ID + " INTEGER PRIMARY KEY," + EXERCISE_NAME + " TEXT,"
                + EXERCISE_WORKOUT_ID + " TEXT" + "," + EXERCISE_TIME + " TEXT)";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //db.execSQL("drop table " + EXERCISE_TABLE);
        //this.onCreate(db);

    }

    public ArrayList<ExerciseElement> getAllContacts() {
        ArrayList<ExerciseElement> exerciseList = new ArrayList<ExerciseElement>();

        // Select All Query
        String selectQuery = "SELECT * FROM Exercises";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                //  public ExerciseElement(int exerciseID, String ExerciseName, String description, int imageID) {
                ExerciseElement elem = new ExerciseElement(1, "", "", 1);
                elem.setExerciseID(Integer.parseInt(cursor.getString(0)));
                elem.setName(cursor.getString(1));
                elem.setDescription("Dette er ikke en del af databasen :)");
                elem.setImageID(Integer.parseInt(cursor.getString(2)));
                // Adding to list
                exerciseList.add(elem);
            } while (cursor.moveToNext());
        }

        // return  list
        return exerciseList;
    }
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
