package project.martin.bepeakedprojekt.Settings;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import project.martin.bepeakedprojekt.Logind_akt;
import project.martin.bepeakedprojekt.R;

public class Settings_akt extends AppCompatActivity implements AdapterView.OnItemClickListener {

    SharedPreferences prefs;
    String language;
    ArrayList<String> indstillinger;
    String profil;
    String activationKey;
    String changeLanguage;
    String unitSystem;
    String about;
    String credits;
    String logOut;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ListView listView;
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_settings_akt);
        setTitle(getString(R.string.settingstitle));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        if(prefs.getInt("Language",0) == 0){
            language = getString(R.string.settings_danish);
        }
        else if(prefs.getInt("Language",0) == 1){
            language = getString(R.string.settings_english);
        }
        else if(prefs.getInt("Language",0) == 2){
            language = getString(R.string.settings_vietnamese);
        }
        else{
            language = getString(R.string.settings_default);
        }

//        if(prefs.getInt("Unitsystem",0) == 0){
//            unitsystem = "Metric";
//        }
//        else if(prefs.getInt("Unitsystem",0) == 1){
//            unitsystem = "Imperial";
//        }

        profil = getString(R.string.settings_profile);
        activationKey = "ActivationKey";
        changeLanguage = getString(R.string.settings_language) + "  -  " + language;
        unitSystem = getString(R.string.settings_unitsystem);
        about = getString(R.string.settings_about);
        credits = getString(R.string.credits_title);
        logOut = getString(R.string.settings_logout);

        String[] indstillinger = {
                getString(R.string.settings_profile),
                getString(R.string.settings_language) + "  -  " + language,
                getString(R.string.settings_unitsystem),
                getString(R.string.settings_about),
                getString(R.string.credits_title),
                getString(R.string.settings_logout)
        };

        if(prefs.getInt("usertype",0) == 0)
            indstillinger = new String[]{
                    getString(R.string.settings_profile),
                    "ActivationKey",
                    getString(R.string.settings_language) + "  -  " + language,
                    getString(R.string.settings_unitsystem),
                    getString(R.string.settings_about),
                    getString(R.string.credits_title),
                    getString(R.string.settings_logout)
            };

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_selectable_list_item, android.R.id.text1, indstillinger);

        listView = (ListView) findViewById(R.id.listView);
        listView.setOnItemClickListener(this);
        listView.setAdapter(adapter);

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

        String data= (String)parent.getItemAtPosition(position);

        if (data.equalsIgnoreCase(profil)){
            Intent i = new Intent(this, User_akt.class);
            startActivity(i);

        }else if (data.equalsIgnoreCase(activationKey)){
            System.out.println("lol");
            Intent i = new Intent(this, ActivationKey_akt.class);
            startActivity(i);

        }else if (data.equalsIgnoreCase(changeLanguage)){
            Intent i = new Intent(this, Language_akt.class);
            startActivity(i);

        }else if (data.equalsIgnoreCase(unitSystem)){
            Intent i = new Intent(this, UnitSystem_akt.class);
            startActivity(i);

        }else if (data.equalsIgnoreCase(about)){
            Intent i = new Intent(this, About_akt.class);
            startActivity(i);

        }else if (data.equalsIgnoreCase(credits)){
            Intent i = new Intent(this, Credits_akt.class);
            startActivity(i);

        }else if (data.equalsIgnoreCase(logOut)){
            Intent i = new Intent(this, Logind_akt.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        }
    }
}