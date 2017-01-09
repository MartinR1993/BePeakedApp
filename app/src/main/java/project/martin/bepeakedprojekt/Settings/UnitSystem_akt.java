package project.martin.bepeakedprojekt.Settings;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import project.martin.bepeakedprojekt.R;
import project.martin.bepeakedprojekt.User.Settings;

public class UnitSystem_akt extends AppCompatActivity implements AdapterView.OnItemClickListener {

    SharedPreferences prefs;
    Settings setting;
    ListView listView;
    String[] unitsystem = {
            "Metric",
            "Imperial"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language_akt);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(getString(R.string.unitsystem_title));
        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_checked, android.R.id.text1, unitsystem);

        listView = (ListView) findViewById(R.id.listView);
        listView.setChoiceMode(listView.CHOICE_MODE_SINGLE);
        listView.setOnItemClickListener(this);
        listView.setAdapter(adapter);
        listView.setItemChecked(prefs.getInt("Unitsystem", 0),true);
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
            setting.setUnitSystem(Settings.UnitSystem.METRIC);
            prefs.edit().putInt("Unitsystem", 0).commit();
        }
        else if(position == 1){
            setting.setUnitSystem(Settings.UnitSystem.IMPERIAL);
            prefs.edit().putInt("Unitsystem", 1).commit();
        }

    }
}
