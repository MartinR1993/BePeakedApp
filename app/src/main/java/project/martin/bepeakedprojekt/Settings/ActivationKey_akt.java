package project.martin.bepeakedprojekt.Settings;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import project.martin.bepeakedprojekt.MainMenu_akt;
import project.martin.bepeakedprojekt.R;


public class ActivationKey_akt extends AppCompatActivity implements View.OnClickListener {

    TextView infoText;
    TextView helpText;
    EditText insertKey;
    Button activationButton;
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activationkey_akt);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Activation Key");

        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        infoText = (TextView) findViewById(R.id.insertInfo);
        helpText = (TextView) findViewById(R.id.helpText);
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


    // denne metode skal laves så den passer til noget db kald men havde jeg ikke lige nogen ide om pt
    public void ActivationKeyCheck(){

        if (insertKey.getText().toString().equals("abe")) {
            prefs.edit().putInt("usertype", 0).commit();

            Toast.makeText(getApplicationContext(), "Din activationkey er godkendt", Toast.LENGTH_LONG).show();

            Intent i = new Intent(this, MainMenu_akt.class);
            startActivity(i);
        }else{
            Toast.makeText(getApplicationContext(), "Din activationkey blev afvist prøv med \"abe\"", Toast.LENGTH_LONG).show();
        }
    }

}
