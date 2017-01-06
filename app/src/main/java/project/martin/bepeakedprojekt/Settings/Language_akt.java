package project.martin.bepeakedprojekt.Settings;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import project.martin.bepeakedprojekt.Login_akt;
import project.martin.bepeakedprojekt.MainMenu_akt;
import project.martin.bepeakedprojekt.R;
import project.martin.bepeakedprojekt.User.Settings;
import project.martin.bepeakedprojekt.Workout.WorkoutExercises.Workout_Exercises_akt;

import static project.martin.bepeakedprojekt.User.Settings.Language.DANISH;
import static project.martin.bepeakedprojekt.User.Settings.Language.ENGLISH;

public class Language_akt extends AppCompatActivity  implements AdapterView.OnItemClickListener, Runnable {

    private Handler handler = new Handler();
    AlertDialog popup;
    SharedPreferences prefs;
    Settings setting;
    ListView listView;
    String[] language = {
            //Kan ikke oversættes?
            "Danish",
            "English",
            "Vietnamese"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language_akt);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(getString(R.string.language_title));
        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_checked, android.R.id.text1, language);

        listView = (ListView) findViewById(R.id.listView);
        listView.setChoiceMode(listView.CHOICE_MODE_SINGLE);
        listView.setOnItemClickListener(this);
        listView.setAdapter(adapter);
        listView.setItemChecked(prefs.getInt("Language", 1),true);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(position == 0){
            setting.setLanguage(this.getBaseContext().getResources() ,DANISH);
            prefs.edit().putInt("Language", 0).commit();

            popup = new AlertDialog.Builder(Language_akt.this).create();
            View change = View.inflate(this, R.layout.popup_loading_language, null);

            popup.setView(change);
            popup.show();

            handler.postDelayed(this, 1000);
        }

        else if(position == 1){
            setting.setLanguage(this.getBaseContext().getResources() ,ENGLISH);
            prefs.edit().putInt("Language", 1).commit();


            popup = new AlertDialog.Builder(Language_akt.this).create();
            View change = View.inflate(this, R.layout.popup_loading_language, null);

            popup.setView(change);
            popup.show();

            handler.postDelayed(this, 1000);
        }
        else if(position == 2){
            //Mangler vietnamesisk.
            prefs.edit().putInt("Language", 2).commit();

        }
    }

    @Override
    public void run() {
        Intent i = new Intent(this, MainMenu_akt.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }
}
