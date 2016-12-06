package project.martin.bepeakedprojekt.Workout.WorkoutExercises;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import project.martin.bepeakedprojekt.Exercises.ExerciseElement;
import project.martin.bepeakedprojekt.R;
import project.martin.bepeakedprojekt.Workout.WorkoutElement;

public class Workout_Exercises_akt extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_menu);
        WorkoutElement workout = (WorkoutElement) getIntent().getSerializableExtra("workout");
        setTitle(workout.getName());

        ArrayList<ExerciseElement> exerciseList = workout.getExercises();

        ListView lv = (ListView) findViewById(R.id.exerciseList);
        lv.setAdapter(new WorkoutExercisesListAdapter(this, exerciseList));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == android.R.id.home){
            onBackPressed();
        }

        else if (item.getItemId() == R.id.add) {
            Toast.makeText(this, "Her skal tilføjes en exercise", Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.addmenu, menu);
        return true;
    }
}