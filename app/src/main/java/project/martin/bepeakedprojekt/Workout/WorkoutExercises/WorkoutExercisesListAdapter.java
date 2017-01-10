package project.martin.bepeakedprojekt.Workout.WorkoutExercises;

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

import project.martin.bepeakedprojekt.Exercises.Exercise.Exercise_akt;
import project.martin.bepeakedprojekt.Exercises.ExerciseElement;
import project.martin.bepeakedprojekt.R;

/**
 * Created by Martin on 14-11-2016.
 */
public class WorkoutExercisesListAdapter extends BaseAdapter {
    private static LayoutInflater inflater = null;
    private ArrayList<ExerciseElement> exerciseList;
    private Activity akt;


    protected WorkoutExercisesListAdapter(Activity activity, ArrayList<ExerciseElement> exerciseList) {
        Context context = activity;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.exerciseList = exerciseList;
        this.akt = activity;
    }

    @Override
    public int getCount() {
        return exerciseList.size();
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
        protected TextView ExerciseTitle;
        protected ImageView image;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Holder holder = new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.list_workout_exercise_element, null);
        ExerciseElement exerciseElement = exerciseList.get(position);

        holder.ExerciseTitle = (TextView) rowView.findViewById(R.id.ele_ExerciseTitle);
        holder.image = (ImageView) rowView.findViewById(R.id.ele_image);

        String exerciseTitle = exerciseElement.getName();

        holder.ExerciseTitle.setText(exerciseTitle);
        holder.image.setImageResource(R.drawable.forward);

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(akt, Exercise_akt.class);
                i.putExtra("exercise", exerciseList.get(position));
                akt.startActivity(i);
            }
        });
        return rowView;
    }
}