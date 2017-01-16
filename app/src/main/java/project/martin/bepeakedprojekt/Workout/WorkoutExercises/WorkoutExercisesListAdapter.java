package project.martin.bepeakedprojekt.Workout.WorkoutExercises;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nhaarman.listviewanimations.ArrayAdapter;
import com.nhaarman.listviewanimations.itemmanipulation.dragdrop.GripView;

import java.util.ArrayList;

import project.martin.bepeakedprojekt.Exercises.Exercise.Exercise_akt;
import project.martin.bepeakedprojekt.Exercises.ExerciseElement;
import project.martin.bepeakedprojekt.R;
import project.martin.bepeakedprojekt.SingletonApplications;

/**
 * Created by Martin on 14-11-2016.
 */
public class WorkoutExercisesListAdapter extends ArrayAdapter<String> {

    private final Context mContext;
    private ArrayList<String> exerciseListNames;
    private static LayoutInflater inflater = null;
    ArrayList<ExerciseElement> exerciseElements = new ArrayList<>();
    private TextView ExerciseTitle, Sets,Reps;
    private ImageView image;

    WorkoutExercisesListAdapter(final Context context, ArrayList<String> exerciseListNames, ArrayList<ExerciseElement> exerciseElements) {
        this.exerciseListNames = exerciseListNames;
        this.exerciseElements = exerciseElements;
        mContext = context;
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        for (int i = 0; i < exerciseListNames.size(); i++) {
            add(exerciseListNames.get(i));
        }
    }

    @Override
    public long getItemId(final int position) {
        return getItem(position).hashCode();
    }

    @Override
    public void swapItems(int positionOne, int positionTwo) {
        ExerciseElement e = SingletonApplications.data.get(positionOne);
        String name = SingletonApplications.dataNames.get(positionOne);
        SingletonApplications.data.remove(positionOne);
        SingletonApplications.data.add(positionTwo, e);
        SingletonApplications.dataNames.remove(positionOne);
        SingletonApplications.dataNames.add(positionTwo, name);

        super.swapItems(positionOne, positionTwo);
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.list_workout_exercise_element, null);
        }


        image = (ImageView) view.findViewById(R.id.ele_image);


        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                System.out.println(SingletonApplications.dataNames.get(position) +"tester " + position);

                if (SingletonApplications.changepic == true) {


                    for (int i = 0; i < exerciseListNames.size(); i++) {

                        if (SingletonApplications.dataNames.get(position) == exerciseListNames.get(i)) {

                            SingletonApplications.DBcom.removeWorkoutExercise(SingletonApplications.workout.getWorkoutID(),exerciseElements.get(i).getExerciseID());

                            exerciseElements.remove(i);

                            exerciseListNames.remove(i);

                            remove(position);
                        }
                    }
                }

            }
        });
        GripView grip = (GripView) view.findViewById(R.id.gripView);

        if(SingletonApplications.changepic == true){
            image.setImageResource(R.drawable.ic_delete);
            grip.setVisibility(View.VISIBLE);
            notifyDataSetChanged();
        }
        else{
            image.setImageResource(R.drawable.forward);
            grip.setVisibility(View.GONE);
            notifyDataSetChanged();
        }
        ExerciseTitle = (TextView) view.findViewById(R.id.ele_ExerciseTitle);
        ExerciseTitle.setText(getItem(position));



        Sets = (TextView) view.findViewById(R.id.textViewSets);
        Reps = (TextView) view.findViewById(R.id.textViewReps);

        for (int i = 0 ; i < exerciseElements.size();i++) {
            if (exerciseElements.get(i).getName() == ExerciseTitle.getText()) {
                int sets = exerciseElements.get(i).getSets();
                String reps = exerciseElements.get(i).getReps();
                Sets.setText("Sets: " + sets);
                Reps.setText("Reps: " + reps);
            }
        }



        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TextView test =(TextView) v.findViewById(R.id.ele_ExerciseTitle);

                String tesa = (String) test.getText();

                for (int i = 0 ; i < exerciseElements.size();i++){
                    if (exerciseElements.get(i).getName().equals(tesa)){
                        Intent j = new Intent(mContext, Exercise_akt.class);
                        j.putExtra("exercise", exerciseElements.get(i));
                        j.putExtra("sets", exerciseElements.get(i).getSets());
                        mContext.startActivity(j);
                        SingletonApplications.currentExerciseID = exerciseElements.get(i).getExerciseID();
                    }
                }
            }
        });


        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
// denne kode virker ikke på sidste element men har ellers ikke design problemer
//                TextView test =(TextView) view.findViewById(R.id.ele_ExerciseTitle);
//
//                String tesa = (String) test.getText();
//
//                for (int i = 0; i < exerciseListNames.size(); i++) {
//
//                    if (tesa == exerciseListNames.get(i)) {
//                        exerciseElements.remove(i);
//
//                        exerciseListNames.remove(i);
//
//                        remove(position);
//                    }
//                }
//
                return false;
            }
        });

        return view;
    }
}