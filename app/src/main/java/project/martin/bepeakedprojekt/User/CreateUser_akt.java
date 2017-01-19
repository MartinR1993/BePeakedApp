package project.martin.bepeakedprojekt.User;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import project.martin.bepeakedprojekt.Backend.BackendData;
import project.martin.bepeakedprojekt.Backend.ServerComm;
import project.martin.bepeakedprojekt.Logind_akt;
import project.martin.bepeakedprojekt.R;
import scSecurity.RandomGen;
import scSecurity.hashing.MD5Hashing;

public class CreateUser_akt extends AppCompatActivity implements View.OnClickListener {
    private TextView firstname, lastname, username, email, password, rpassword;
    private EditText efirstname, elastname, eusername, eemail, epassword, erpassword;
    private Button create;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_create_user_akt);
        setTitle(getString(R.string.create_user_title));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firstname = (TextView) findViewById(R.id.cua_textfirstname);
        efirstname = (EditText) findViewById(R.id.cua_editfirstname);

        lastname = (TextView) findViewById(R.id.cua_textlastname);
        elastname = (EditText) findViewById(R.id.cua_editlastname);

        username = (TextView) findViewById(R.id.cua_textusername);
        eusername = (EditText) findViewById(R.id.cua_editusername);

        email = (TextView) findViewById(R.id.cua_textemail);
        eemail = (EditText) findViewById(R.id.cua_editemail);

        password = (TextView) findViewById(R.id.cua_password1);
        epassword = (EditText) findViewById(R.id.cua_editpassword1);

        rpassword = (TextView) findViewById(R.id.cua_password2);
        erpassword = (EditText) findViewById(R.id.cua_editpassword2);

        create = (Button) findViewById(R.id.cua_opretbtn);
        create.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String password = epassword.getText().toString();
        if(password.equals(erpassword.getText().toString()) && (!eemail.getText().toString().isEmpty()) && (!efirstname.getText().toString().isEmpty()) && (!elastname.getText().toString().isEmpty()) && (!eusername.getText().toString().isEmpty()) && (epassword.toString().length() >= 6)) {
            String firstName = efirstname.getText().toString();
            String lastName = elastname.getText().toString();
            String email = eemail.getText().toString();
            String nickName = eusername.getText().toString();

            String salt = RandomGen.getSalt(16);
            MD5Hashing md5 = new MD5Hashing();
            String passwordHashed = md5.decryptHash(md5.encryptHash(password, salt));

            new ServerComm(BackendData.SERVER_ADRESS, BackendData.SERVER_PORT).createUser(this, firstName, lastName,  nickName, passwordHashed, salt, email);
        }
        else
            Toast.makeText(getApplicationContext(), R.string.create_user_error, Toast.LENGTH_LONG).show();
    }

    public void userCreated() {
        Toast.makeText(getApplicationContext(), R.string.create_user_created, Toast.LENGTH_LONG).show();
        Intent i = new Intent(this, Logind_akt.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return true;
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        return true;
    }
}