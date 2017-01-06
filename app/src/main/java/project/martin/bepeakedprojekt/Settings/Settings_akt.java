package project.martin.bepeakedprojekt.Settings;

import android.content.Intent;
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

public class Settings_akt extends AppCompatActivity implements AdapterView.OnItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ListView listView;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_akt);
        setTitle(getString(R.string.settingstitle));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String[] indstillinger = {getString(R.string.settings_profile), getString(R.string.settings_language), getString(R.string.settings_unitsystem), getString(R.string.settings_about), getString(R.string.settings_logout)};
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
        if(position == 0){
            //her skal der oprettes en profil side
        }

        else if(position == 1){
            Intent i = new Intent(this, Language_akt.class);
            startActivity(i);        }

        else if(position == 4){
            Intent i = new Intent(this, Login_akt.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        }
    }
}
