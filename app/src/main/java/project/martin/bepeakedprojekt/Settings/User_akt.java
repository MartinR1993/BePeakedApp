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
    private TextView skiftBrugernavn;
    private TextView skiftEmail;

    private TextView Username, Height, Weight, Age, Calories, Culhydrates, Protein, Fat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_user_akt);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(getString(R.string.settings_profile));


        skiftKode = (TextView) findViewById(R.id.changepassword);
        skiftKode.setOnClickListener(this);

        skiftBrugernavn = (TextView) findViewById(R.id.changeUsername);
        skiftBrugernavn.setOnClickListener(this);

        skiftEmail = (TextView) findViewById(R.id.changeEmail);
        skiftEmail.setOnClickListener(this);


        Username = (TextView) findViewById(R.id.textView5);

        Height = (TextView) findViewById(R.id.textView9);

        Weight = (TextView) findViewById(R.id.textView10);

        Age = (TextView) findViewById(R.id.textView11);

        Calories = (TextView) findViewById(R.id.textView13);

        Culhydrates = (TextView) findViewById(R.id.textView14);

        Protein = (TextView) findViewById(R.id.textView15);

        Fat = (TextView) findViewById(R.id.textView16);

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
        else if (v == skiftBrugernavn) {
            Toast.makeText(getApplicationContext(), "Mangler skift brugernavn kode", Toast.LENGTH_LONG).show();
        }
        else if (v == skiftEmail) {
            Toast.makeText(getApplicationContext(), "Mangler skift email kode", Toast.LENGTH_LONG).show();
        }
    }
}