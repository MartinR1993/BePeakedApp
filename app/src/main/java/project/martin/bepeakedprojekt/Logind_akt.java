package project.martin.bepeakedprojekt;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import project.martin.bepeakedprojekt.Backend.BackendData;
import project.martin.bepeakedprojekt.Backend.ServerComm;
import project.martin.bepeakedprojekt.User.CreateUser_akt;

public class Logind_akt extends AppCompatActivity implements View.OnClickListener
{
    private ServerComm server = new ServerComm(BackendData.SERVER_ADRESS, BackendData.SERVER_PORT);
    private TextView textUsername, textPassword;
    private EditText editUsername, editPassword;
    private Button buttonLogin, opretBruger;
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
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

        opretBruger = (Button) findViewById(R.id.opretBruger);
        opretBruger.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == buttonLogin){
            server.getSalt(this, editUsername.getText().toString());
        }
        else if (v == opretBruger) {
            Intent i = new Intent(this, CreateUser_akt.class);
            startActivity(i);
        }
    }

    public void login(String salt) {
        System.out.println("SALT=" + salt);
        server.login(this, editUsername.getText().toString(), editPassword.getText().toString(), salt);
    }

    public void gotoMenu() {
        Intent i = new Intent(this, MainMenu_akt.class);
        Logind_akt.this.finish();
        startActivity(i);
    }
}
