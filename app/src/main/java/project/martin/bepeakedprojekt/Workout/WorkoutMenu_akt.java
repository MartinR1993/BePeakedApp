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

import java.util.ArrayList;

import project.martin.bepeakedprojekt.R;

public class WorkoutMenu_akt extends AppCompatActivity  {

    ArrayList<WorkoutElement> list;
    ArrayList<String> exercises;
    ListView lv;
    EditText saveWorkoutName;
    AlertDialog popup;
    private WorkoutListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_menu);
        setTitle("Workouts");
        exercises = new ArrayList<>();
        exercises.add("Benchpress");
        exercises.add("Push Ups");

        list = new ArrayList<>();
        list.add(new WorkoutElement("Workout 1", exercises, R.drawable.forward));
        list.add(new WorkoutElement("Workout 2", exercises, R.drawable.forward));

        lv = (ListView) findViewById(R.id.listWorkoutMenu);
        listAdapter = new WorkoutListAdapter(this, list);
        lv.setAdapter(listAdapter);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        } else if (item.getItemId() == R.id.add) {

            popup = new AlertDialog.Builder(WorkoutMenu_akt.this).create();
            View createWorkout = View.inflate(this, R.layout.popup_createworkout, null);


            saveWorkoutName = (EditText) createWorkout.findViewById(R.id.giveNameWorkout);
            final Button saveWorkoutButton = (Button) createWorkout.findViewById(R.id.saveWorkout);
            saveWorkoutButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    list.add(new WorkoutElement(saveWorkoutName.getText().toString(), exercises, R.drawable.forward));
                    System.out.println("hej"+ list.size());
                    listAdapter.notifyDataSetChanged();
                    popup.cancel();

                }
            });

            popup.setTitle("Create Workout");
            popup.setView(createWorkout);
            popup.show();


        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.addmenu, menu);
        return true;
    }
}
