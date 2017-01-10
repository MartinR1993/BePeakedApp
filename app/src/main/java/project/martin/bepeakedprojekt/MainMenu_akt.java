package project.martin.bepeakedprojekt;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import project.martin.bepeakedprojekt.Diet_Plan.DietPlanMenu_akt;
import project.martin.bepeakedprojekt.Exercises.ExerciseMenu_akt;
import project.martin.bepeakedprojekt.Settings.Settings_akt;
import project.martin.bepeakedprojekt.Workout.WorkoutMenu_akt;

public class MainMenu_akt extends AppCompatActivity implements View.OnClickListener {
    private Button exerciseButton, workoutButton, dietplanButton ;
    SharedPreferences prefs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        setTitle(R.string.mainMenu_banner);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        prefs.getInt("usertype",0);



        /*//HER KAN DER SÆTTES ET LOGO TIL HOVEDMENUBAREN
        ActionBar actionBar = getSupportActionBar();
        actionBar.setLogo(R.drawable.bepeakedlogo);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);*/
        workoutButton = (Button) findViewById(R.id.WorkoutButton);
        workoutButton.setOnClickListener(this);

        exerciseButton = (Button) findViewById(R.id.ExerciseButton);
        exerciseButton.setOnClickListener(this);

        dietplanButton = (Button) findViewById(R.id.DietPlanButton);
        dietplanButton.setOnClickListener(this);

        // fjerner muligheden for dietplan hvis man ikke er kunde ( usertype = 1)
        if (prefs.getInt("usertype",0) == 1) {
            dietplanButton.setEnabled(false);
            dietplanButton.setBackgroundColor(Color.BLUE);
        }


    }

    @Override
    public void onClick(View v) {
        if (v == workoutButton) {
            Intent i = new Intent(this, WorkoutMenu_akt.class);
            startActivity(i);
        }
        else if (v == exerciseButton) {
            Intent i = new Intent(this, ExerciseMenu_akt.class);
            startActivity(i);
        }
        else if (v == dietplanButton) {
            Intent i = new Intent(this, DietPlanMenu_akt.class);
            startActivity(i);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        if(item.getItemId() == R.id.settings) {
            System.out.println("Du har valgt indstillinger");
            Intent i = new Intent(this, Settings_akt.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }
}