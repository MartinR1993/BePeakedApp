package project.martin.bepeakedprojekt.Workout.WorkoutExercises;

import android.app.Activity;
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
import android.widget.Toast;

import com.nhaarman.listviewanimations.appearance.simple.AlphaInAnimationAdapter;
import com.nhaarman.listviewanimations.itemmanipulation.DynamicListView;
import com.nhaarman.listviewanimations.itemmanipulation.dragdrop.GripView;
import com.nhaarman.listviewanimations.itemmanipulation.dragdrop.OnItemMovedListener;
import com.nhaarman.listviewanimations.itemmanipulation.dragdrop.TouchViewDraggableManager;

import java.util.ArrayList;

import project.martin.bepeakedprojekt.Backend.BackendData;
import project.martin.bepeakedprojekt.Backend.DatabaseCommunication;
import project.martin.bepeakedprojekt.Backend.ServerComm;
import project.martin.bepeakedprojekt.Exercises.ExerciseElement;
import project.martin.bepeakedprojekt.Misc.DummyData;
import project.martin.bepeakedprojekt.R;
import project.martin.bepeakedprojekt.SingletonApplications;
import project.martin.bepeakedprojekt.User.User;
import project.martin.bepeakedprojekt.Workout.WorkoutElement;

public class Workout_Exercises_akt extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private AlertDialog popup;
    private ListView listOfExercises;
    private ArrayList<String> exerciseNames = new ArrayList<>();
    private ArrayList<String> missingExerciseNames = new ArrayList<>();
    private ArrayList<ExerciseElement> exerciseList = new ArrayList<>();
    public ArrayList<ExerciseElement> allExercises;
    private EditText searchText;
    private DynamicListView dlv;
    private DynamicListView listView;
    //    private ListView lv;
    private ArrayAdapter adapter;
    private DatabaseCommunication DBCom;
    private AlphaInAnimationAdapter animAdapter;
    private GripView gripView;
    private Menu menu;
    private MenuItem item;

    private static final int INITIAL_DELAY_MILLIS = 300;
    WorkoutElement workout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_exercises_menu);
        workout = (WorkoutElement) getIntent().getSerializableExtra("workout");
        SingletonApplications.workout = workout;

        setTitle(workout.getName());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if(workout.isFromServer()) {
            ServerComm server = new ServerComm(BackendData.SERVER_ADRESS, BackendData.SERVER_PORT);
            server.getExercisesByWorkoutID(this, workout.getWorkoutID(), User.getSessionID());
        }
        DBCom = SingletonApplications.DBcom;


        ArrayList<Integer> exerciseArray = DBCom.getWorkoutExercises(workout.getWorkoutID());

        for (int id:exerciseArray) {
            exerciseList.add(DummyData.getExercise(id));
            exerciseNames.add(DummyData.getExercise(id).getName());
        }

        SingletonApplications.data = exerciseList;
        SingletonApplications.dataNames = exerciseNames;

        listView = (DynamicListView) findViewById(R.id.dynamiclistview);
        gripView = (GripView) findViewById(R.id.gripView);


        /* Setup the adapter */
        com.nhaarman.listviewanimations.ArrayAdapter<String> adapter = new WorkoutExercisesListAdapter(this, SingletonApplications.dataNames, SingletonApplications.data);
        animAdapter = new AlphaInAnimationAdapter(adapter);
        animAdapter.setAbsListView(listView);
        //  assert animAdapter.getViewAnimator() != null;
        animAdapter.getViewAnimator().setInitialDelayMillis(INITIAL_DELAY_MILLIS);
        listView.setAdapter(animAdapter);

        listView.setOnItemMovedListener(new MyOnItemMovedListener(adapter));
        listView.setOnItemLongClickListener(new MyOnItemLongClickListener(this,listView,adapter,exerciseNames,exerciseList));
    }

    public void addExercises(ArrayList<ExerciseElement> exerciseList) {
        //TODO: Tilføj øvelser hentet fra serveren til listen af øvelser.
//        throw new UnsupportedOperationException("Method not implemented yet!");
    }

    public boolean onOptionsItemSelected(final MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            onBackPressed();

        else if(item.getItemId() == R.id.edit){

            SingletonApplications.changepic = true;

            listView.setAdapter(new WorkoutExercisesListAdapter(this, SingletonApplications.dataNames, SingletonApplications.data));

            if (menu.findItem(R.id.OK) == null)
                getMenuInflater().inflate(R.menu.okmenu, menu);
            else {
                MenuItem ok = menu.findItem(R.id.OK);
                ok.setVisible(true);
            }
            MenuItem edit = menu.findItem(R.id.edit);
            edit.setVisible(false);
            MenuItem add = menu.findItem(R.id.add);
            add.setVisible(false);


            listView.enableDragAndDrop();
            listView.setDraggableManager(new TouchViewDraggableManager(R.id.gripView));

        }
        else if (item.getItemId() == R.id.OK){
            SingletonApplications.changepic = false;
            listView.setAdapter(new WorkoutExercisesListAdapter(this, SingletonApplications.dataNames, SingletonApplications.data));


            DBCom.removeAllWorkoutExercises(workout.getWorkoutID());

            for (ExerciseElement element: SingletonApplications.data) {
                DBCom.addWorkoutExercise(workout.getWorkoutID(),element.getExerciseID());
            }


            MenuItem ok = menu.findItem(R.id.OK);
            ok.setVisible(false);
            MenuItem edit = menu.findItem(R.id.edit);
            edit.setVisible(true);
            MenuItem add = menu.findItem(R.id.add);
            add.setVisible(true);
            listView.disableDragAndDrop();
        }
        else if (item.getItemId() == R.id.add) {
            popup = new AlertDialog.Builder(Workout_Exercises_akt.this).create();
            View addExercise = View.inflate(this, R.layout.popup_addexercises, null);
            searchText = (EditText) addExercise.findViewById(R.id.searchText);

            missingExerciseNames.clear();
            allExercises = new ArrayList<>();
            listOfExercises = (ListView) addExercise.findViewById(R.id.listViewExer);

            for(int i = 1 ; i <= DummyData.exerciseList.length ; i++)
                allExercises.add(DummyData.getExercise(i));

            for(int i = 0 ; i < exerciseList.size() ; i++)
                allExercises.remove(exerciseList.get(i));

            for (int q = 0; q< allExercises.size(); q++)
                missingExerciseNames.add(allExercises.get(q).getName());

            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, missingExerciseNames);
            listOfExercises.setAdapter(adapter);
            listOfExercises.setOnItemClickListener(this);

            searchText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    allExercises.clear();
                    missingExerciseNames.clear();

                    for(int i = 1 ; i <= DummyData.exerciseList.length ; i++)
                        allExercises.add(DummyData.getExercise(i));

                    for(int i = 0 ; i < exerciseList.size() ; i++)
                        allExercises.remove(exerciseList.get(i));

                    for (int q = 0; q< allExercises.size(); q++)
                        missingExerciseNames.add(allExercises.get(q).getName());

                    searchItem();
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });

            final Button addExerBotton = (Button) addExercise.findViewById(R.id.addExerBotton);
            addExerBotton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popup.cancel();
                }
            });
            popup.setTitle(R.string.add_exercise);
            popup.setView(addExercise);
            popup.show();
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.add_edit_menu, menu);
        return true;
    }

    public void searchItem(){

        for (int j = 0 ; j < missingExerciseNames.size(); j++) {
            String exerciseName = missingExerciseNames.get(j).toLowerCase();

            if(!exerciseName.contains(searchText.getText().toString())){
                missingExerciseNames.remove(j);
                j=j-1;
            }
            listOfExercises.setAdapter(adapter);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        for (int t = 0 ; t <allExercises.size(); t++) {
            if (allExercises.get(t).getName().equalsIgnoreCase(missingExerciseNames.get(i))) {

                DBCom.addWorkoutExercise(workout.getWorkoutID(),allExercises.get(t).getExerciseID());
                exerciseList.add(allExercises.get(t));
                exerciseNames.add(missingExerciseNames.get(i));
            }
        }

        System.out.println(missingExerciseNames.size()+" + "+allExercises.size()+" + "+ exerciseNames.size()+" + "+exerciseList.size() );

        listView.setAdapter(new WorkoutExercisesListAdapter(this, exerciseNames, exerciseList));
        listOfExercises.setAdapter(adapter);
        popup.cancel();
    }

    private static class MyOnItemLongClickListener implements AdapterView.OnItemLongClickListener {

        private  ArrayList<String> exerciseListNames;
        private ArrayList<ExerciseElement> exerciseElements;
        private DynamicListView mListView;
        com.nhaarman.listviewanimations.ArrayAdapter<String> adapter;
        Activity akt;

        MyOnItemLongClickListener(Activity activity, DynamicListView listView, com.nhaarman.listviewanimations.ArrayAdapter<String> adapter, ArrayList<String>exerciseListNames, ArrayList<ExerciseElement> exerciseElements) {
            mListView = listView;
            this.exerciseListNames = exerciseListNames;
            this.exerciseElements = exerciseElements;
            this.adapter = adapter;
            this.akt = activity;
        }

        @Override
        public boolean onItemLongClick(final AdapterView<?> parent, final View view, final int position, final long id) {

            // denne kode virker men har design problemer
//            TextView test =(TextView) view.findViewById(R.id.ele_ExerciseTitle);
//            String tesa = (String) test.getText();
//
//            for (int i = 0; i < exerciseListNames.size(); i++) {
//                if (tesa == exerciseListNames.get(i)) {
//                    SingletonApplications.DBcom.removeWorkoutExercise(SingletonApplications.workout.getWorkoutID(),exerciseElements.get(i).getExerciseID());
//                    exerciseElements.remove(i);
//                    exerciseListNames.remove(i);
//                    mListView.setAdapter( new WorkoutExercisesListAdapter(akt, exerciseListNames, exerciseElements));
//                }
//            }
            return true;
        }
    }

    private class MyOnItemMovedListener implements OnItemMovedListener {
        private final com.nhaarman.listviewanimations.ArrayAdapter<String> mAdapter;
        private Toast mToast;

        MyOnItemMovedListener(final com.nhaarman.listviewanimations.ArrayAdapter<String> adapter) {
            mAdapter = adapter;
        }

        @Override
        public void onItemMoved(final int originalPosition, final int newPosition) {
            if (mToast != null)
                mToast.cancel();

            mToast = Toast.makeText(getApplicationContext(), originalPosition + "til" + newPosition, Toast.LENGTH_SHORT);
            mToast.show();
        }
    }
}