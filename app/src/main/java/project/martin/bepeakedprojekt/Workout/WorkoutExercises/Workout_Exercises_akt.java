package project.martin.bepeakedprojekt.Workout.WorkoutExercises;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.nhaarman.listviewanimations.appearance.simple.AlphaInAnimationAdapter;
import com.nhaarman.listviewanimations.itemmanipulation.DynamicListView;
import com.nhaarman.listviewanimations.itemmanipulation.dragdrop.OnItemMovedListener;
import com.nhaarman.listviewanimations.itemmanipulation.dragdrop.TouchViewDraggableManager;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.OnDismissCallback;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.undo.SimpleSwipeUndoAdapter;

import java.util.ArrayList;
import java.util.Arrays;

import project.martin.bepeakedprojekt.Backend.DatabaseCommunication;
import project.martin.bepeakedprojekt.Exercises.ExerciseElement;
import project.martin.bepeakedprojekt.Misc.DummyData;
import project.martin.bepeakedprojekt.R;
import project.martin.bepeakedprojekt.Workout.WorkoutElement;

import static android.R.id.edit;
import static project.martin.bepeakedprojekt.R.id.listView;

public class Workout_Exercises_akt extends AppCompatActivity implements AdapterView.OnItemClickListener  {

    AlertDialog popup;
    ListView listOfExercises;
    ArrayList<String> exerciseNames = new ArrayList<>();
    ArrayList<String> missingExerciseNames = new ArrayList<>();
    ArrayList<ExerciseElement> exerciseList = new ArrayList<>();
    public ArrayList<ExerciseElement> allExercises;
    EditText searchText;
    DynamicListView dlv;
    DynamicListView listView;
//    ListView lv;
    ArrayAdapter adapter;
    DatabaseCommunication DBCom;
    AlphaInAnimationAdapter animAdapter;

    private static final int INITIAL_DELAY_MILLIS = 300;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_exercises_menu);
        WorkoutElement workout = (WorkoutElement) getIntent().getSerializableExtra("workout");
        setTitle(workout.getName());

        DBCom = new DatabaseCommunication(this);

        exerciseList.add(DummyData.getExercise(1));
        exerciseList.add(DummyData.getExercise(2));
        exerciseList.add(DummyData.getExercise(3));
        exerciseList.add(DummyData.getExercise(4));

        exerciseNames.add(DummyData.getExercise(1).getName());
        exerciseNames.add(DummyData.getExercise(2).getName());
        exerciseNames.add(DummyData.getExercise(3).getName());
        exerciseNames.add(DummyData.getExercise(4).getName());

        //exerciseList = workout.getExercises();

        listView = (DynamicListView) findViewById(R.id.dynamiclistview);

        /* Setup the adapter */
        com.nhaarman.listviewanimations.ArrayAdapter<String> adapter = new WorkoutExercisesListAdapter(this, exerciseNames, exerciseList);
        SimpleSwipeUndoAdapter simpleSwipeUndoAdapter = new SimpleSwipeUndoAdapter(adapter, this, new MyOnDismissCallback(adapter));
        animAdapter = new AlphaInAnimationAdapter(simpleSwipeUndoAdapter);
        animAdapter.setAbsListView(listView);
        assert animAdapter.getViewAnimator() != null;
        animAdapter.getViewAnimator().setInitialDelayMillis(INITIAL_DELAY_MILLIS);
        listView.setAdapter(animAdapter);

        /* Enable drag and drop functionality */
        listView.enableDragAndDrop();
        listView.setDraggableManager(new TouchViewDraggableManager(R.id.gripView));
        listView.setOnItemMovedListener(new MyOnItemMovedListener(adapter));
        listView.setOnItemLongClickListener(new MyOnItemLongClickListener(listView));

        /* Enable swipe to dismiss */
        listView.enableSimpleSwipeUndo();
//        dlv = (DynamicListView) findViewById(R.id.dynamiclistview);
//        dlv.setAdapter(new WorkoutExercisesListAdapter(this, DBCom.getAllWorkoutExercises(workout.getWorkoutID())));
        //dlv.setAdapter(new WorkoutExercisesListAdapter(this, exerciseList));
//        dlv.enableDragAndDrop();
//        lv = (ListView) findViewById(R.id.exerciseList);
//        lv.setAdapter(new WorkoutExercisesListAdapter(this, exerciseList));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
    public boolean onOptionsItemSelected(final MenuItem item){
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        else if(item.getItemId() == R.id.edit){
        }
        else if (item.getItemId() == R.id.add) {
            popup = new AlertDialog.Builder(Workout_Exercises_akt.this).create();

            View addExercise = View.inflate(this, R.layout.popup_addexercises, null);

            searchText = (EditText) addExercise.findViewById(R.id.searchText);


            missingExerciseNames.clear();

            allExercises = new ArrayList<ExerciseElement>();

            listOfExercises = (ListView) addExercise.findViewById(R.id.listViewExer);

            for(int i = 1 ; i <= DummyData.exerciseList.length ; i++){
                allExercises.add(DummyData.getExercise(i));
           }
            for(int i = 0 ; i < exerciseList.size() ; i++){
                allExercises.remove(exerciseList.get(i));
            }
            for (int q = 0; q< allExercises.size(); q++){
                missingExerciseNames.add(allExercises.get(q).getName());
            }

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
                        for(int i = 1 ; i <= DummyData.exerciseList.length ; i++){
                                allExercises.add(DummyData.getExercise(i));
                        }

                    for(int i = 0 ; i < exerciseList.size() ; i++){
                        allExercises.remove(exerciseList.get(i));
                    }

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
                exerciseList.add(allExercises.get(t));
                exerciseNames.add(missingExerciseNames.get(i));
            }
        }

        System.out.println(missingExerciseNames.size()+" + "+allExercises.size()+" + "+ exerciseNames.size()+" + "+exerciseList.size() );



//        com.nhaarman.listviewanimations.ArrayAdapter<String> adapter = new WorkoutExercisesListAdapter(this, exerciseNames, exerciseList);
//        SimpleSwipeUndoAdapter simpleSwipeUndoAdapter = new SimpleSwipeUndoAdapter(adapter, this, new MyOnDismissCallback(adapter));
//        animAdapter = new AlphaInAnimationAdapter(simpleSwipeUndoAdapter);
//        animAdapter.setAbsListView(listView);
//        assert animAdapter.getViewAnimator() != null;
//        animAdapter.getViewAnimator().setInitialDelayMillis(INITIAL_DELAY_MILLIS);

        listView.setAdapter(new WorkoutExercisesListAdapter(this, exerciseNames, exerciseList));



                listOfExercises.setAdapter(adapter);
                popup.cancel();

    }

    private static class MyOnItemLongClickListener implements AdapterView.OnItemLongClickListener {

        private final DynamicListView mListView;

        MyOnItemLongClickListener(final DynamicListView listView) {
            mListView = listView;
        }

        @Override
        public boolean onItemLongClick(final AdapterView<?> parent, final View view, final int position, final long id) {
            if (mListView != null) {
                mListView.startDragging(position - mListView.getHeaderViewsCount());
            }
            return true;
        }
    }

    private class MyOnDismissCallback implements OnDismissCallback {

        private final com.nhaarman.listviewanimations.ArrayAdapter<String> mAdapter;

        @Nullable
        private Toast mToast;

        MyOnDismissCallback(final com.nhaarman.listviewanimations.ArrayAdapter<String> adapter) {
            mAdapter = adapter;
        }

        @Override
        public void onDismiss(@NonNull final ViewGroup listView, @NonNull final int[] reverseSortedPositions) {
            for (int position : reverseSortedPositions) {
                mAdapter.remove(position);
            }

            if (mToast != null) {
                mToast.cancel();
            }
            mToast = Toast.makeText(
                    Workout_Exercises_akt.this,
                    getString(R.string.removed_positions, Arrays.toString(reverseSortedPositions)),
                    Toast.LENGTH_LONG
            );
            mToast.show();
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
            if (mToast != null) {
                mToast.cancel();
            }

            mToast = Toast.makeText(getApplicationContext(), originalPosition + "til" + newPosition, Toast.LENGTH_SHORT);
            mToast.show();
        }
    }
}

