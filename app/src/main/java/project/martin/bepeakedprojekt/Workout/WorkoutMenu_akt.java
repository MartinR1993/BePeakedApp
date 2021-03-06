package project.martin.bepeakedprojekt.Workout;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
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
import android.widget.Toast;

import java.util.ArrayList;

import project.martin.bepeakedprojekt.Backend.BackendData;
import project.martin.bepeakedprojekt.Backend.DatabaseCommunication;
import project.martin.bepeakedprojekt.Backend.ServerComm;
import project.martin.bepeakedprojekt.R;
import project.martin.bepeakedprojekt.SingletonApplications;
import project.martin.bepeakedprojekt.User.User;

public class WorkoutMenu_akt extends AppCompatActivity {
    private ArrayList<WorkoutElement> workoutList;
    private ListView lv;
    private EditText saveWorkoutName;
    private AlertDialog popup;
    private WorkoutListAdapter listAdapter;
    private DatabaseCommunication DBCom;
    private SharedPreferences prefs;
    ServerComm server;
    private Menu menu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_workout_menu);
        setTitle(R.string.workoutMenu_banner);
        SingletonApplications.changepic = false;
        server = new ServerComm(BackendData.SERVER_ADRESS, BackendData.SERVER_PORT);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        DBCom = new DatabaseCommunication(this);

        server.getWorkoutlist(this, User.getUserID(), User.getSessionID());
        workoutList = DBCom.getAllWorkouts();

        SingletonApplications.allWorkouts = workoutList;


        lv = (ListView) findViewById(R.id.listWorkoutMenu);
        listAdapter = new WorkoutListAdapter(this, SingletonApplications.allWorkouts);
        lv.setAdapter(listAdapter);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {
            case android.R.id.home: {
                SingletonApplications.changepic = false;
                onBackPressed();
                break;

            }
            case R.id.edit: {

                SingletonApplications.changepic = true;

                if (menu.findItem(R.id.OK) == null)
                    getMenuInflater().inflate(R.menu.okmenu, menu);
                else {
                    MenuItem ok = menu.findItem(R.id.OK);
                    ok.setVisible(true);
                }

                MenuItem edit = menu.findItem(R.id.edit);
                edit.setVisible(false);
                MenuItem add = menu.findItem(R.id.add);
                add.setVisible(false);

                lv.setAdapter(new WorkoutListAdapter(WorkoutMenu_akt.this,SingletonApplications.allWorkouts));
                break;
            }
            case  R.id.OK : {
                SingletonApplications.changepic = false;

                MenuItem ok = menu.findItem(R.id.OK);
                ok.setVisible(false);
                MenuItem edit = menu.findItem(R.id.edit);
                edit.setVisible(true);
                MenuItem add = menu.findItem(R.id.add);
                add.setVisible(true);

                lv.setAdapter(new WorkoutListAdapter(WorkoutMenu_akt.this, SingletonApplications.allWorkouts));
                break;
            }
            case R.id.add: {
                popup = new AlertDialog.Builder(WorkoutMenu_akt.this).create();
                View createWorkout = View.inflate(this, R.layout.popup_createworkout, null);

                saveWorkoutName = (EditText) createWorkout.findViewById(R.id.giveNameWorkout);
                final Button saveWorkoutButton = (Button) createWorkout.findViewById(R.id.saveWorkout);
                saveWorkoutButton.setOnClickListener(new clickListener(this));


                popup.setTitle(R.string.createWorkout_title);
                popup.setView(createWorkout);
                popup.show();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.add_edit_menu, menu);
        return true;
    }

    public void addWorkouts(ArrayList<WorkoutElement> workoutList) {
        System.out.println("Workout received=" + workoutList.toString());
        this.workoutList.addAll(0, workoutList);
        listAdapter.notifyDataSetChanged();
    }

    private class clickListener implements View.OnClickListener{

        private final Activity act;

        private clickListener(Activity act) {
            this.act = act;
        }

        @Override
        public void onClick(View v) {
            if(!saveWorkoutName.getText().toString().isEmpty()) {

                prefs.edit().putInt("idafworkoutet", prefs.getInt("idafworkoutet", 1) + 1).commit();
                DBCom.addWorkout(prefs.getInt("idafworkoutet", 1), saveWorkoutName.getText().toString());

                SingletonApplications.allWorkouts.add(DBCom.getAllWorkouts().get(DBCom.getAllWorkouts().size() - 1));


                lv.setAdapter(new WorkoutListAdapter(WorkoutMenu_akt.this, SingletonApplications.allWorkouts));
            }
            else {
                Toast.makeText(act, R.string.createWorkout_name, Toast.LENGTH_LONG).show();
            }
            popup.cancel();
        }
    }
}