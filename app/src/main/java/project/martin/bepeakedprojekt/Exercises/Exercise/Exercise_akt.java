package project.martin.bepeakedprojekt.Exercises.Exercise;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import project.martin.bepeakedprojekt.R;

public class Exercise_akt extends AppCompatActivity implements View.OnClickListener {

    Button switchbutton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_akt);

        if (savedInstanceState == null){
            Fragment fragment = new Description_frag();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.ex_fragment, fragment)
                    .commit();
        }

        setTitle("Øvelsens navn");

        switchbutton = (Button) findViewById(R.id.ex_button);
        switchbutton.setText("Results");
        switchbutton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == switchbutton){
            switchbutton.setText("Description");
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.ex_fragment, new Result_frag())
                    .commit();
        }
    }
}
