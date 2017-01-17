package project.martin.bepeakedprojekt.Exercises;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

import project.martin.bepeakedprojekt.Misc.DummyData;
import project.martin.bepeakedprojekt.R;

public class ExerciseMenu_akt extends AppCompatActivity {

    ArrayList<ExerciseElement> list;
    EditText searchWord;
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_exercise_menu);
        setTitle(R.string.exerciseMenu_banner);

        list = new ArrayList<>(Arrays.asList(DummyData.exerciseList));

        lv = (ListView) findViewById(R.id.exerciseList);
        lv.setAdapter(new ExerciseListAdapter(this,list));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        searchWord = (EditText) findViewById(R.id.editText);

        searchWord.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                list.clear();
                list = new ArrayList<>(Arrays.asList(DummyData.exerciseList));

                searchItem();

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        else if(item.getItemId() == R.id.add) {
            Toast.makeText(this, "Denne funktion tilføjer en øvelse", Toast.LENGTH_LONG).show();
        }
            return super.onOptionsItemSelected(item);
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.addmenu, menu);
        return true;
    }


    public void searchItem(){

        for (int j = 0 ; j < list.size(); j++) {

            String exerciseName = list.get(j).getName().toLowerCase();
            System.out.println(exerciseName);

            if(!exerciseName.contains(searchWord.getText())){
                list.remove(j);
                j=j-1;

            }
        }
        lv.setAdapter(new ExerciseListAdapter(this,list));
    }
}