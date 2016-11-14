package project.martin.bepeakedprojekt.Workout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

import project.martin.bepeakedprojekt.R;

public class WorkoutMenu_akt extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_menu);
        setTitle("Workouts");
        ArrayList<String> exercises = new ArrayList<>();
        exercises.add("ryg");
        exercises.add("mave");

        ArrayList<WorkoutElement> list = new ArrayList<>();
        list.add(new WorkoutElement("Workout 1", exercises));
        list.add(new WorkoutElement("Workout 2", exercises));

        ListView lv = (ListView) findViewById(R.id.listWorkoutMenu);
        lv.setAdapter(new WorkoutListAdapter(this,list));

    }
}
