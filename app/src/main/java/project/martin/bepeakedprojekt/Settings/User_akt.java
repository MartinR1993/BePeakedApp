package project.martin.bepeakedprojekt.Settings;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import project.martin.bepeakedprojekt.R;

public class User_akt extends AppCompatActivity implements View.OnClickListener{

    private TextView skiftKode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_user_akt);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(getString(R.string.settings_profile));


        skiftKode = (TextView) findViewById(R.id.changepassword);
        skiftKode.setOnClickListener(this);

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

     public void onClick(View v) {
        if (v == skiftKode){
            //TODO
            //Databasekald
            // implementere en metode der skifter adganskode, og åbner et popup vindue hvori alt dette kommer til at foregå
            Toast.makeText(getApplicationContext(), "Mangler kode hertil", Toast.LENGTH_LONG).show();
        }
    }
}