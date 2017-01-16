package project.martin.bepeakedprojekt.User;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import project.martin.bepeakedprojekt.Backend.BackendData;
import project.martin.bepeakedprojekt.Backend.ServerComm;
import project.martin.bepeakedprojekt.R;
import scSecurity.RandomGen;
import scSecurity.hashing.MD5Hashing;

public class CreateUser_akt extends AppCompatActivity implements View.OnClickListener {

    TextView firstname, lastname, email, password, rpassword;
    EditText efirstname, elastname, eemail, epassword, erpassword;
    Button create;
    String passwordc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user_akt);
        setTitle(getString(R.string.create_user_title));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firstname = (TextView) findViewById(R.id.cua_textfirstname);
        efirstname = (EditText) findViewById(R.id.cua_editfirstname);

        lastname = (TextView) findViewById(R.id.cua_textlastname);
        elastname = (EditText) findViewById(R.id.cua_editlastname);

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
        if(password.equals(erpassword.getText().toString())) {
            String firstName = efirstname.getText().toString();
            String lastName = elastname.getText().toString();
            String email = eemail.getText().toString();
            String nickName = email;

            String salt = RandomGen.getSalt(16);
            MD5Hashing md5 = new MD5Hashing();
            String passwordHashed = md5.decryptHash(md5.encryptHash(password, salt));

            new ServerComm(BackendData.SERVER_ADRESS, BackendData.SERVER_PORT).createUser(this, firstName, lastName,  nickName, passwordHashed, salt, email);
        }
        else
            Toast.makeText(getApplicationContext(), "Dit nye password var ikke ens", Toast.LENGTH_LONG).show();
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

    public boolean setPassword(String password, String password2, String oldPassword) {
        int capitalLetter = 0;
        int smallLetter = 0;
        int number = 0;
        int passwordLength = 0;

        if(oldPassword.equals(this.passwordc)) {
            if(password.equals(password2)) {
                if(password.length() >= 6) {
                    for(int i = 0; i < password.length(); i++){
                        if(password.charAt(i) >= 'A' && password.charAt(i) <= 'Z') {
                            capitalLetter = 1;
                        }
                        if(password.charAt(i) >= 'a' && password.charAt(i) <= 'z') {
                            smallLetter = 1;
                        }
                        if(password.charAt(i) >= '0' && password.charAt(i) <= '9'){
                            number = 1;
                        }
                    }
                    if(capitalLetter+smallLetter+number+passwordLength >= 3) {
                        this.passwordc = password;
                        return true;
                    } else {
//						throw new DALException("Passwordet skal indeholde minimum et stort tegn, et lille tegn og et tal.");
                        Toast.makeText(getApplicationContext(), "Passwordet skal indeholde minimum et stort tegn, et lille tegn og et tal", Toast.LENGTH_LONG).show();
                    }
                } else {
//					throw new DALException("Passwordet skal minimum være 6 tegn langt.");
                    Toast.makeText(getApplicationContext(), "Passwordet skal minimum være 6 tegn langt", Toast.LENGTH_LONG).show();
                }
            } else {
//				throw new DALException("Dit nye password var ikke ens.");
                Toast.makeText(getApplicationContext(), "Dit nye password var ikke ens", Toast.LENGTH_LONG).show();
            }
        } else {
//			throw new DALException("Dit gamle password er ikke indtastet korrekt.");
            Toast.makeText(getApplicationContext(), "Dit gamle password er ikke indtastet korrekt", Toast.LENGTH_LONG).show();
        }
        return false;
    }
}
