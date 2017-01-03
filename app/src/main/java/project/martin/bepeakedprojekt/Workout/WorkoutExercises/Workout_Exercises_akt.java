package project.martin.bepeakedprojekt.Workout.WorkoutExercises;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

import project.martin.bepeakedprojekt.Exercises.ExerciseElement;
import project.martin.bepeakedprojekt.Misc.DummyData;
import project.martin.bepeakedprojekt.R;
import project.martin.bepeakedprojekt.Workout.WorkoutElement;

public class Workout_Exercises_akt extends AppCompatActivity {

    private AlertDialog popup;
    ListView listOfExercises;
    ArrayList<ExerciseElement> exerciseList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_menu);
        WorkoutElement workout = (WorkoutElement) getIntent().getSerializableExtra("workout");
        setTitle(workout.getName());

        exerciseList = workout.getExercises();

        ListView lv = (ListView) findViewById(R.id.exerciseList);
        lv.setAdapter(new WorkoutExercisesListAdapter(this, exerciseList));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);





    }
    public boolean onOptionsItemSelected(MenuItem item){
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        else if (item.getItemId() == R.id.add) {
            popup = new AlertDialog.Builder(Workout_Exercises_akt.this).create();

            View addExercise = View.inflate(this, R.layout.popup_addexercise, null);

            EditText searchText = (EditText) addExercise.findViewById(R.id.searchText);
            searchText.setText("hej");


            ArrayList<ExerciseElement> allExercises = new ArrayList<ExerciseElement>();

            listOfExercises = (ListView) addExercise.findViewById(R.id.listViewExer);


            for(int i = 1 ; i <= DummyData.exerciseList.length ; i++){
                allExercises.add(DummyData.getExercise(i));
                System.out.println(DummyData.getExercise(i).getName());
           }

                System.out.println(allExercises.size());

            // VIRKER halvt
             listOfExercises.setAdapter(new WorkoutExercisesListAdapter(this,allExercises));


            final Button addExerBotton = (Button) addExercise.findViewById(R.id.addExerBotton);
            addExerBotton.setText("Add Exercise");
            addExerBotton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
               //     workoutList.add(new WorkoutElement(-1, saveExerciseName.getText().toString(), new ArrayList<ExerciseElement>()));
                //    listAdapter.notifyDataSetChanged();
                    popup.cancel();
                }
            });

           // popup.setTitle("Add Exercise");
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
}