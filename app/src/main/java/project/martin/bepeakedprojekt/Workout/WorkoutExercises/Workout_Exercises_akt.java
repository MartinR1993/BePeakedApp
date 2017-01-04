package project.martin.bepeakedprojekt.Workout.WorkoutExercises;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import project.martin.bepeakedprojekt.Exercises.ExerciseElement;
import project.martin.bepeakedprojekt.Misc.DummyData;
import project.martin.bepeakedprojekt.R;
import project.martin.bepeakedprojekt.Workout.WorkoutElement;

public class Workout_Exercises_akt extends AppCompatActivity implements AdapterView.OnItemClickListener  {

    AlertDialog popup;
    ListView listOfExercises;
    ArrayList<String> exerciseNames = new ArrayList<>();
    ArrayList<ExerciseElement> exerciseList;
    public ArrayList<ExerciseElement> allExercises;
    EditText searchText;
    ListView lv;
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_exercises_menu);
        WorkoutElement workout = (WorkoutElement) getIntent().getSerializableExtra("workout");
        setTitle(workout.getName());

        exerciseList = workout.getExercises();

        lv = (ListView) findViewById(R.id.exerciseList);
        lv.setAdapter(new WorkoutExercisesListAdapter(this, exerciseList));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
    public boolean onOptionsItemSelected(final MenuItem item){
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        else if (item.getItemId() == R.id.add) {
            popup = new AlertDialog.Builder(Workout_Exercises_akt.this).create();

            View addExercise = View.inflate(this, R.layout.popup_addexercise, null);

            searchText = (EditText) addExercise.findViewById(R.id.searchText);


            allExercises = new ArrayList<ExerciseElement>();

            listOfExercises = (ListView) addExercise.findViewById(R.id.listViewExer);


            for(int i = 1 ; i <= DummyData.exerciseList.length ; i++){
                allExercises.add(DummyData.getExercise(i));
           }
            for(int i = 0 ; i < exerciseList.size() ; i++){
                allExercises.remove(exerciseList.get(i));
            }


            for (int q = 0; q< allExercises.size(); q++)
                exerciseNames.add(allExercises.get(q).getName());

             adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, exerciseNames);

            listOfExercises.setAdapter(adapter);

            listOfExercises.setOnItemClickListener(this);


            searchText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                        allExercises.clear();
                        exerciseNames.clear();
                        for(int i = 1 ; i <= DummyData.exerciseList.length ; i++){
                                allExercises.add(DummyData.getExercise(i));
                        }

                    for(int i = 0 ; i < exerciseList.size() ; i++){
                        allExercises.remove(exerciseList.get(i));
                    }

                    for (int q = 0; q< allExercises.size(); q++)
                        exerciseNames.add(allExercises.get(q).getName());

                searchItem();

                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });


            final Button addExerBotton = (Button) addExercise.findViewById(R.id.addExerBotton);
            addExerBotton.setText("Cancel");
            addExerBotton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    popup.cancel();

                }
            });

            popup.setView(addExercise);
            popup.show();
        }
        return super.onOptionsItemSelected(item);

    }


    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.addmenu, menu);
        return true;
    }

    public void searchItem(){

            for (int j = 0 ; j < exerciseNames.size(); j++) {

                String exerciseName = exerciseNames.get(j).toLowerCase();

                if(!exerciseName.contains(searchText.getText().toString())){
                    exerciseNames.remove(j);
                    j=j-1;

                }
            listOfExercises.setAdapter(adapter);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        for (int t = 0 ; t <allExercises.size(); t++) {
            if (allExercises.get(t).getName().equalsIgnoreCase(exerciseNames.get(i)))
            exerciseList.add(allExercises.get(t));
        }

                lv.setAdapter(new WorkoutExercisesListAdapter(this, exerciseList));
                exerciseNames.clear();
                listOfExercises.setAdapter(adapter);
                popup.cancel();

    }
}

