package project.martin.bepeakedprojekt.Exercises;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import project.martin.bepeakedprojekt.R;
import project.martin.bepeakedprojekt.Workout.WorkoutElement;
import project.martin.bepeakedprojekt.Workout.WorkoutListAdapter;

public class ExerciseMenu_akt extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_menu);
        setTitle("Exercises");

        ArrayList<ExerciseElement> list = new ArrayList<>();
        list.add(new ExerciseElement("Exercise 1", R.drawable.forward));
        list.add(new ExerciseElement("Exercise 2", R.drawable.forward));

        ListView lv = (ListView) findViewById(R.id.exerciseList);
        lv.setAdapter(new ExerciseListAdapter(this,list));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        else if(item.getItemId() == R.id.search) {
            Toast.makeText(this, "Denne funktion søger i alle øvelser", Toast.LENGTH_LONG).show();
        }
            return super.onOptionsItemSelected(item);
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.searchmenu, menu);
        return true;
    }
}
