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

import project.martin.bepeakedprojekt.Exercises.ExerciseElement;
import project.martin.bepeakedprojekt.R;
import project.martin.bepeakedprojekt.Workout.WorkoutExercises.Workout_Exercises_akt;

/**
 * Created by Martin on 14-11-2016.
 */
public class WorkoutListAdapter extends BaseAdapter {
    private static LayoutInflater inflater = null;
    private ArrayList<WorkoutElement> workoutList;
    private Activity akt;

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

    private class Holder
    {
        protected TextView WorkoutTitle;
        protected TextView Exercises;
        protected ImageView image;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Holder holder = new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.list_workout_element, null);
        WorkoutElement workoutelement = workoutList.get(position);

        holder.WorkoutTitle = (TextView) rowView.findViewById(R.id.wle_WorkoutTitle);
        holder.Exercises = (TextView) rowView.findViewById(R.id.wle_Exercises);
        holder.image = (ImageView) rowView.findViewById(R.id.wle_Image);

        String workoutTitle = workoutelement.getName();

        holder.WorkoutTitle.setText(workoutTitle);

        StringBuilder sbExerciseList = new StringBuilder();
        for (ExerciseElement exercise : workoutelement.getExercises())
            sbExerciseList.append(exercise.getName()).append(", ");
        sbExerciseList.substring(0, sbExerciseList.length() - 2);

        holder.Exercises.setText(sbExerciseList);
        holder.image.setImageResource(R.drawable.forward);

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(akt, Workout_Exercises_akt.class);
                i.putExtra("workout", workoutList.get(position));
                akt.startActivity(i);
            }
        });
        return rowView;
    }
}