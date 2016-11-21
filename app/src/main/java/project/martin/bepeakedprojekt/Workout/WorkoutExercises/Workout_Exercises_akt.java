package project.martin.bepeakedprojekt.Workout.WorkoutExercises;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import project.martin.bepeakedprojekt.Exercises.ExerciseElement;
import project.martin.bepeakedprojekt.Exercises.ExerciseListAdapter;
import project.martin.bepeakedprojekt.Login_akt;
import project.martin.bepeakedprojekt.MainMenu_akt;
import project.martin.bepeakedprojekt.R;

import static project.martin.bepeakedprojekt.R.id.exerciseList;

public class Workout_Exercises_akt extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_menu);
        String workout = getIntent().getStringExtra("workout");
        setTitle(workout);

        ArrayList<ExerciseElement> list = new ArrayList<>();
        list.add(new ExerciseElement("Benchpress", R.drawable.forward));
        list.add(new ExerciseElement("Push Ups", R.drawable.forward));

        ListView lv = (ListView) findViewById(exerciseList);
        lv.setAdapter(new WorkoutExercisesListAdapter(this,list));
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
