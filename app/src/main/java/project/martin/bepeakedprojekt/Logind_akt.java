package project.martin.bepeakedprojekt;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Logind_akt extends AppCompatActivity implements View.OnClickListener {

    private TextView textUsername, textPassword, opretBruger;
    private EditText editUsername, editPassword;
    private Button buttonLogin;
    SharedPreferences prefs;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_akt);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        prefs.edit().putInt("usertype",0).commit();
        setTitle(R.string.login_banner);

        textUsername = (TextView) findViewById(R.id.textUsername);
        editUsername = (EditText) findViewById(R.id.editUsername);

        textPassword = (TextView) findViewById(R.id.textPassword);
        editPassword = (EditText) findViewById(R.id.editPassword);

        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        buttonLogin.setOnClickListener(this);

        opretBruger = (TextView) findViewById(R.id.opretBruger);
        opretBruger.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == buttonLogin){



            Intent i = new Intent(this, MainMenu_akt.class);
            Logind_akt.this.finish();
            startActivity(i);
        }
        else if (v == opretBruger){

            // bruges midlertidig til at sætte en bruger til ikke kunde
            prefs.edit().putInt("usertype",1).commit();

            System.out.println("Opret bruger");
        }
    }
}
