package project.martin.bepeakedprojekt.Workout;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;

import project.martin.bepeakedprojekt.Backend.DatabaseCommunication;
import project.martin.bepeakedprojekt.Exercises.ExerciseElement;
import project.martin.bepeakedprojekt.Misc.DummyData;
import project.martin.bepeakedprojekt.R;

public class WorkoutMenu_akt extends AppCompatActivity  {
    private ArrayList<WorkoutElement> workoutList;
    private ListView lv;
    private EditText saveWorkoutName;
    private AlertDialog popup;
    private WorkoutListAdapter listAdapter;


    DatabaseCommunication DBCom;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_menu);
        setTitle(R.string.workoutMenu_banner);


        DBCom = new DatabaseCommunication(this);

        //workoutList = new ArrayList<>(Arrays.asList(DummyData.workoutList));

        lv = (ListView) findViewById(R.id.listWorkoutMenu);
        listAdapter = new WorkoutListAdapter(this, DBCom.getAllWorkouts());
        lv.setAdapter(listAdapter);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        else if(item.getItemId() == R.id.edit){

        }
        else if (item.getItemId() == R.id.add) {
            popup = new AlertDialog.Builder(WorkoutMenu_akt.this).create();
            View createWorkout = View.inflate(this, R.layout.popup_createworkout, null);

            saveWorkoutName = (EditText) createWorkout.findViewById(R.id.giveNameWorkout);
            final Button saveWorkoutButton = (Button) createWorkout.findViewById(R.id.saveWorkout);
            saveWorkoutButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    DBCom.addWorkout(saveWorkoutName.getText().toString());
                    //workoutList.add(new WorkoutElement(-1, saveWorkoutName.getText().toString(), new ArrayList<ExerciseElement>()));
                    listAdapter.notifyDataSetChanged();
                    popup.cancel();
                }
            });

            popup.setTitle(R.string.createWorkout_title);
            popup.setView(createWorkout);
            popup.show();
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.add_edit_menu, menu);
        return true;
    }
}