package project.martin.bepeakedprojekt.Workout.WorkoutExercises;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
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
    public ArrayList<ExerciseElement> allExercises;
    EditText searchText;
    ArrayList<String> list;
    ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_exercises_menu);
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

             searchText = (EditText) addExercise.findViewById(R.id.searchText);





            allExercises = new ArrayList<ExerciseElement>();

            listOfExercises = (ListView) addExercise.findViewById(R.id.listViewExer);


            for(int i = 1 ; i <= DummyData.exerciseList.length ; i++){
                allExercises.add(DummyData.getExercise(i));
           }

//             list = new ArrayList<>();

//            for (int h=0; h< allExercises.size(); h++)
//                list.add(allExercises.get(h).getName().toString());



            // VIRKER halvt
            listOfExercises.setAdapter(new WorkoutAddExerciseAdapter(this,allExercises));

          //   adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list);
         //   listOfExercises.setAdapter(adapter);

            searchText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (s.toString().equals("")) {
                        allExercises.clear();

                        for(int i = 1 ; i <= DummyData.exerciseList.length ; i++){
                                allExercises.add(DummyData.getExercise(i));
                        }
                    }

                    else{

                    }
                }

                @Override
                public void afterTextChanged(Editable s) {
                    searchItem(s.toString());
                }
            });


            final Button addExerBotton = (Button) addExercise.findViewById(R.id.addExerBotton);
            addExerBotton.setText("Add Exercise");
            addExerBotton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
               //     workoutList.add(new WorkoutElement(-1, saveExerciseName.getText().toString(), new ArrayList<ExerciseElement>()));
                //    listAdapter.notifyDataSetChanged();
                  //  popup.cancel();

                    for (int k=0 ; k<allExercises.size(); k++)
                        System.out.println(allExercises.get(k).getName().toString().toLowerCase());
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

    public void searchItem(String textToSearch){


        System.out.println(searchText.getText().toString());

            for (int j = 0 ; j < allExercises.size(); j++) {


               String exerciseName = allExercises.get(j).getName().toString().toLowerCase();

             //   String exerciseName = list.get(j).toLowerCase();

                System.out.println(exerciseName);



                if(!exerciseName.contains(searchText.getText().toString())){
                    System.out.println(allExercises.get(j).getName() +" blev fjernet");
                    allExercises.remove(j);

//                    System.out.println(exerciseName +" blev fjernet");
//                    list.remove(j);



                    //listOfExercises.deferNotifyDataSetChanged();






                    j=j-1;




                }
                listOfExercises.setAdapter(new WorkoutAddExerciseAdapter(this,allExercises));

//                listOfExercises.setAdapter(adapter);



        }

    }

}

