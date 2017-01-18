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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import project.martin.bepeakedprojekt.Backend.BackendData;
import project.martin.bepeakedprojekt.Backend.ServerComm;
import project.martin.bepeakedprojekt.MainMenu_akt;
import project.martin.bepeakedprojekt.R;
import project.martin.bepeakedprojekt.User.User;

public class ActivationKey_akt extends AppCompatActivity implements View.OnClickListener {

    private EditText insertKey;
    private Button activationButton;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_activationkey_akt);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(getString(R.string.settings_activation));

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        insertKey = (EditText) findViewById(R.id.insertactivationkey);
        activationButton = (Button) findViewById(R.id.activationButton);

        activationButton.setOnClickListener(this);

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
    public void onClick(View v) {
        if (v == activationButton){

            ActivationKeyCheck();

        }
    }

    public void ActivationKeyCheck(){
        new ServerComm(BackendData.SERVER_ADRESS, BackendData.SERVER_PORT).activateUser(this, User.getUserID(), insertKey.getText().toString(), User.getSessionID());
    }

    public void activateUser(boolean success) {
        System.out.println("USER ACTIVATED=" + success);
        if(success) {
            prefs.edit().putInt("usertype", 1).commit();
            Intent i = new Intent(this, MainMenu_akt.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        }
        else
            Toast.makeText(getApplicationContext(), R.string.activationkey_error, Toast.LENGTH_LONG).show();
    }
}
