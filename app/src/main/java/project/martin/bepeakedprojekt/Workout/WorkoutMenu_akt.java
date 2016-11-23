package project.martin.bepeakedprojekt.Workout;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import project.martin.bepeakedprojekt.R;

public class WorkoutMenu_akt extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_menu);
        setTitle("Workouts");
        ArrayList<String> exercises = new ArrayList<>();
        exercises.add("Benchpress");
        exercises.add("Push Ups");

        ArrayList<WorkoutElement> list = new ArrayList<>();
        list.add(new WorkoutElement("Workout 1", exercises, R.drawable.forward));
        list.add(new WorkoutElement("Workout 2", exercises, R.drawable.forward));

        ListView lv = (ListView) findViewById(R.id.listWorkoutMenu);
        lv.setAdapter(new WorkoutListAdapter(this,list));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        else if(item.getItemId() == R.id.add){
            Toast.makeText(this, "Her skal tilføjes et workout", Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.addmenu, menu);
        return true;
    }
}
