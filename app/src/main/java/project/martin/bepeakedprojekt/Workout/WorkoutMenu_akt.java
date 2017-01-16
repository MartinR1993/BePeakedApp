package project.martin.bepeakedprojekt.Workout;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

import project.martin.bepeakedprojekt.Backend.BackendData;
import project.martin.bepeakedprojekt.Backend.DatabaseCommunication;
import project.martin.bepeakedprojekt.Backend.ServerComm;
import project.martin.bepeakedprojekt.R;
import project.martin.bepeakedprojekt.User.User;

public class WorkoutMenu_akt extends AppCompatActivity {
    private ArrayList<WorkoutElement> workoutList;
    private ListView lv;
    private EditText saveWorkoutName;
    private AlertDialog popup;
    private WorkoutListAdapter listAdapter;
    private DatabaseCommunication DBCom;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_menu);
        setTitle(R.string.workoutMenu_banner);
        ServerComm server = new ServerComm(BackendData.SERVER_ADRESS, BackendData.SERVER_PORT);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        DBCom = new DatabaseCommunication(this);
        server.getWorkoutlist(this, User.getSessionID());
        workoutList = DBCom.getAllWorkouts();

        lv = (ListView) findViewById(R.id.listWorkoutMenu);
        listAdapter = new WorkoutListAdapter(this, workoutList);
        lv.setAdapter(listAdapter);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                onBackPressed();
                break;
            }
            case R.id.edit: {
                break;
            }
            case R.id.add: {
                popup = new AlertDialog.Builder(WorkoutMenu_akt.this).create();
                View createWorkout = View.inflate(this, R.layout.popup_createworkout, null);

                saveWorkoutName = (EditText) createWorkout.findViewById(R.id.giveNameWorkout);
                final Button saveWorkoutButton = (Button) createWorkout.findViewById(R.id.saveWorkout);
                saveWorkoutButton.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        prefs.edit().putInt("idafworkoutet", prefs.getInt("idafworkoutet", 1) + 1).commit();
                        DBCom.addWorkout(prefs.getInt("idafworkoutet", 1), saveWorkoutName.getText().toString());

                        workoutList = DBCom.getAllWorkouts();
                        lv.setAdapter(new WorkoutListAdapter(WorkoutMenu_akt.this, workoutList));

                        popup.cancel();
                    }
                });

                popup.setTitle(R.string.createWorkout_title);
                popup.setView(createWorkout);
                popup.show();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.add_edit_menu, menu);
        return true;
    }

    public void addWorkouts(ArrayList<WorkoutElement> workoutList) {
        System.out.println("Workout received=" + workoutList.toString());
        this.workoutList.addAll(workoutList);
        listAdapter.notifyDataSetChanged();
    }


}