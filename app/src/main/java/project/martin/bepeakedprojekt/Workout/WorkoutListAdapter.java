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
import android.widget.Toast;

import java.util.ArrayList;

import project.martin.bepeakedprojekt.Exercises.Exercise.Exercise_akt;
import project.martin.bepeakedprojekt.R;

/**
 * Created by Martin on 14-11-2016.
 */

public class WorkoutListAdapter extends BaseAdapter {
    private final Context context;
    private static LayoutInflater inflater = null;
    private ArrayList<WorkoutElement> workoutList;
    private Activity akt;

    public WorkoutListAdapter(Activity activity, ArrayList<WorkoutElement> workoutList) {
        context = activity;
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

        String workoutTitle = workoutelement.getWorkoutName();
        ArrayList<String> exercises = workoutelement.getExercises();

        holder.WorkoutTitle.setText(workoutTitle);
        holder.Exercises.setText(exercises.toString());
        holder.image.setImageResource(workoutelement.getImage());

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(akt, Exercise_akt.class);
                akt.startActivity(i);
            }
        });
        return rowView;
    }


}
