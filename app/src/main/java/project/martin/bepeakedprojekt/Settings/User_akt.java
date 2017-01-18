package project.martin.bepeakedprojekt.Settings;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import project.martin.bepeakedprojekt.Backend.BackendData;
import project.martin.bepeakedprojekt.Backend.ServerComm;
import project.martin.bepeakedprojekt.R;
import project.martin.bepeakedprojekt.User.User;

public class User_akt extends AppCompatActivity implements View.OnClickListener{

    private Button skiftKode;
    private Button skiftEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new ServerComm(BackendData.SERVER_ADRESS, BackendData.SERVER_PORT).getUserProfile(this, User.getUserID(), User.getSessionID());
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_user_akt);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(getString(R.string.settings_profile));


        skiftKode = (Button) findViewById(R.id.changepassword);
        skiftKode.setOnClickListener(this);

        skiftEmail = (Button) findViewById(R.id.changeEmail);
        skiftEmail.setOnClickListener(this);

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
            Toast.makeText(getApplicationContext(), "Mangler skift kode kode", Toast.LENGTH_LONG).show();
        }

        else if (v == skiftEmail) {
            Toast.makeText(getApplicationContext(), "Mangler skift email kode", Toast.LENGTH_LONG).show();
        }
    }

    public void setUserData(String firstName, String lastName, int age, double height, double weight, double prot, double cal, double col, double fat, int dpid) {
        TextView eName = (TextView) findViewById(R.id.up_name);
        TextView eHeight = (TextView) findViewById(R.id.up_height);
        TextView eWeight = (TextView) findViewById(R.id.up_weight);
        TextView eAge = (TextView) findViewById(R.id.up_age);
        TextView eDPID = (TextView) findViewById(R.id.up_id);
        TextView eCal = (TextView) findViewById(R.id.up_cal);
        TextView eCol = (TextView) findViewById(R.id.up_col);
        TextView eProt = (TextView) findViewById(R.id.up_prot);
        TextView eFat = (TextView) findViewById(R.id.up_fat);

        eName.setText(firstName + ' ' + lastName);
        eHeight.setText(getString(R.string.settings_user_height) + ' ' + height);
        eWeight.setText(getString(R.string.settings_user_weight) + ' ' + weight);
        eDPID.setText(getString(R.string.settings_user_kostplanID) + ' ' + dpid);
        eAge.setText(getString(R.string.settings_user_age) + ' ' + age);
        eCal.setText(getString(R.string.settings_user_calories) + ' ' + cal);
        eCol.setText(getString(R.string.settings_user_culhydrates) + ' ' + col);
        eProt.setText(getString(R.string.settings_user_protein) + ' ' + prot);
        eFat.setText(getString(R.string.settings_user_fat) + ' ' + fat);
    }
}