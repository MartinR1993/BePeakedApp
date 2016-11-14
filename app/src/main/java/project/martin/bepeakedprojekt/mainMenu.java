package project.martin.bepeakedprojekt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class mainMenu extends AppCompatActivity implements View.OnClickListener {

    Button exerciseButton, workoutButton, dietplanButton ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        workoutButton = (Button) findViewById(R.id.WorkoutButton);
        workoutButton.setOnClickListener(this);

        exerciseButton = (Button) findViewById(R.id.ExerciseButton);
        exerciseButton.setOnClickListener(this);

        dietplanButton = (Button) findViewById(R.id.DietPlanButton);
        dietplanButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v == workoutButton){
            Intent i = new Intent(this, WorkoutMenu.class);
            startActivity(i);
        }
        else if(v == exerciseButton){
            Intent i = new Intent(this, ExerciseMenu.class);
            startActivity(i);
        }
        else if(v == dietplanButton){
            Intent i = new Intent(this, DietPlanMenu.class);
            startActivity(i);
        }
    }
}
