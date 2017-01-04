package project.martin.bepeakedprojekt.Exercises;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

import project.martin.bepeakedprojekt.Misc.DummyData;
import project.martin.bepeakedprojekt.R;

public class ExerciseMenu_akt extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_menu);
        setTitle(R.string.exerciseMenu_banner);

        ArrayList<ExerciseElement> list = new ArrayList<>(Arrays.asList(DummyData.exerciseList));

        ListView lv = (ListView) findViewById(R.id.exerciseList);
        lv.setAdapter(new ExerciseListAdapter(this,list));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        else if(item.getItemId() == R.id.add) {
            Toast.makeText(this, "Denne funktion tilføjer en øvelse", Toast.LENGTH_LONG).show();
        }
            return super.onOptionsItemSelected(item);
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.addmenu, menu);
        return true;
    }
}