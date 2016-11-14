package project.martin.bepeakedprojekt.Exercises;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

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
        list.add(new ExerciseElement("Exercise 1", R.mipmap.ic_launcher));
        list.add(new ExerciseElement("Exercise 2", R.mipmap.ic_launcher));

        ListView lv = (ListView) findViewById(R.id.exerciseList);
        lv.setAdapter(new ExerciseListAdapter(this,list));
    }
}
