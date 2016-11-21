package project.martin.bepeakedprojekt.Exercises.Exercise;

import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import project.martin.bepeakedprojekt.R;

public class Exercise_akt extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_akt);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.ex_fragment, new Description_frag())
                    .commit();
        }

        setTitle("Øvelsens navn");
    }
}
