package project.martin.bepeakedprojekt.Workout;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import project.martin.bepeakedprojekt.R;
import project.martin.bepeakedprojekt.SingletonApplications;
import project.martin.bepeakedprojekt.Workout.WorkoutExercises.Workout_Exercises_akt;

/**
 * Created by Martin on 14-11-2016.
 */
public class WorkoutListAdapter extends BaseAdapter {
    private static LayoutInflater inflater = null;
    private ArrayList<WorkoutElement> workoutList;
    private Activity akt;
    private TextView WorkoutTitle;
    private TextView Exercises;
    private ImageView image;

    protected WorkoutListAdapter(Activity activity, ArrayList<WorkoutElement> workoutList) {
        Context context = activity;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.workoutList = workoutList;

        this.akt = activity;
    }

    @Override
    public int getCount() {
        return workoutList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }




    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final View rowView;
        rowView = inflater.inflate(R.layout.list_workout_element, null);
        final WorkoutElement workoutelement = workoutList.get(position);

        WorkoutTitle = (TextView) rowView.findViewById(R.id.wle_WorkoutTitle);
        Exercises = (TextView) rowView.findViewById(R.id.wle_Exercises);
        image = (ImageView) rowView.findViewById(R.id.wle_Image);

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (SingletonApplications.changepic == true && !workoutelement.isFromServer()) {

                    for ( int i = 0 ; i < SingletonApplications.DBcom.getAllWorkouts().size() ; i++) {

                        if (workoutList.get(position).getWorkoutID() == SingletonApplications.DBcom.getAllWorkouts().get(i).getWorkoutID() && !workoutelement.isFromServer())

                        SingletonApplications.DBcom.removeWorkout(workoutList.get(position).getWorkoutID());

                        workoutList.remove(position);
                        i = SingletonApplications.DBcom.getAllWorkouts().size();
                    }

                            notifyDataSetChanged();
                }

            }
        });

        String workoutTitle = workoutelement.getName();

        WorkoutTitle.setText(workoutTitle);


        if (SingletonApplications.changepic == true && !workoutelement.isFromServer()){
            image.setImageResource(R.drawable.ic_delete);
        }

        else if(SingletonApplications.changepic == false){
            image.setImageResource(R.drawable.forward);
        }


        //StringBuilder sbExerciseList = new StringBuilder();
        //for (ExerciseElement exercise : workoutelement.getExercises())
        //    sbExerciseList.append(exercise.getName()).append(", ");
        //sbExerciseList.substring(0, sbExerciseList.length());

        //holder.Exercises.setText(sbExerciseList);

        Exercises.setText("Carl Mar var her");




        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SingletonApplications.changepic == false) {
                    Intent i = new Intent(akt, Workout_Exercises_akt.class);
                    i.putExtra("workout", workoutList.get(position));
                    akt.startActivity(i);
                }
            }
        });
        return rowView;
    }
}