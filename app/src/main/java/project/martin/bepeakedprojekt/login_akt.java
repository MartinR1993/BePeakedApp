package project.martin.bepeakedprojekt;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class login_akt extends AppCompatActivity implements View.OnClickListener {

    TextView textUsername, textPassword, opretBruger;
    EditText editUsername, editPassword;
    Button buttonLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_akt);

        setTitle("Login Menu");

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
            /*Intent i = new Intent(this, mainMenu_akt.class);
            startActivity(i);*/
        }
        else if (v == opretBruger){
            System.out.println("Opret bruger");
        }
    }
}
