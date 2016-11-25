package project.martin.bepeakedprojekt;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //LoadingScreen title.
        setTitle(" ");
        getSupportActionBar().hide();
        
        if (savedInstanceState == null){
            Fragment fragment = new Welcome_frag();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragmentindhold, fragment)
                    .commit();
        }
    }
}
