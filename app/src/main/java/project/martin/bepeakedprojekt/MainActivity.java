package project.martin.bepeakedprojekt;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;
import project.martin.bepeakedprojekt.User.Settings;

public class MainActivity extends AppCompatActivity {

    SharedPreferences prefs;
    Settings setting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        firstStart();


        if (savedInstanceState == null) {
            Fragment fragment = new Welcome_frag();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragmentindhold, fragment)
                    .commit();
        }

        if (prefs.getInt("Unitsystem", 0) == 0) {
            setting.setUnitSystem(Settings.UnitSystem.METRIC);
        } else if (prefs.getInt("Unitsystem", 0) == 1) {
            setting.setUnitSystem(Settings.UnitSystem.IMPERIAL);
        }

        if (prefs.getInt("Language", 0) == 0) {
            setting.setLanguage(this.getBaseContext().getResources(), Settings.Language.DANISH);
        } else if (prefs.getInt("Language", 0) == 1) {
            setting.setLanguage(this.getBaseContext().getResources(), Settings.Language.ENGLISH);
        } else if (prefs.getInt("Language", 0) == 2) {
            //Her mangler vietnamesisk
        }
    }

    public void firstStart() {
        if (prefs.getBoolean("firstStartOfApp", true)) {
            prefs.edit().putInt("Language", 1).commit();
            prefs.edit().putBoolean("firstStartOfApp", false).commit();

        }

        //Sprog kan ændres vha. hardcode her
//        Settings.setLanguage(this.getBaseContext().getResources(), Settings.Language.DANISH);
    }
}