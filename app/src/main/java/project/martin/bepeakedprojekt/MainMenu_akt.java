package project.martin.bepeakedprojekt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import project.martin.bepeakedprojekt.Diet_Plan.DietPlanMenu_akt;
import project.martin.bepeakedprojekt.Exercises.ExerciseMenu_akt;
import project.martin.bepeakedprojekt.Workout.WorkoutMenu_akt;

public class MainMenu_akt extends AppCompatActivity implements View.OnClickListener {

    private Button exerciseButton, workoutButton, dietplanButton ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        setTitle("Menu");
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

    }

    @Override
    public void onClick(View v) {
        if(v == workoutButton){
            Intent i = new Intent(this, WorkoutMenu_akt.class);
            startActivity(i);
        }
        else if(v == exerciseButton){
            Intent i = new Intent(this, ExerciseMenu_akt.class);
            startActivity(i);
        }
        else if(v == dietplanButton){
            Intent i = new Intent(this, DietPlanMenu_akt.class);
            startActivity(i);
        }
    }
    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        if (item.getItemId() == R.id.about){
            System.out.println("Du har valgt Om appen");
            Toast.makeText(this, "Ikke implementeret endnu...", Toast.LENGTH_LONG).show();
        }
        else if (item.getItemId() == R.id.logout) {
            System.out.println("Du er nu logget ud");
            Intent i = new Intent(this, login_akt.class);
            MainMenu_akt.this.finish();
            startActivity(i);
        }
        else if(item.getItemId() == R.id.settings){
            System.out.println("Du har valgt indstillinger");
            Toast.makeText(this, "Ikke implementeret endnu...", Toast.LENGTH_LONG).show();
            //Intent i = new Intent(this, Settings.class);
            //startActivity(i);
        }
        return super.onOptionsItemSelected(item);

    }

}
