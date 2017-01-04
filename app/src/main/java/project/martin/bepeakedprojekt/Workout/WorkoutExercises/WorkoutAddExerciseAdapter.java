package project.martin.bepeakedprojekt.Workout.WorkoutExercises;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import project.martin.bepeakedprojekt.Exercises.ExerciseElement;
import project.martin.bepeakedprojekt.R;

/**
 * Created by Martin on 14-11-2016.
 */
public class WorkoutAddExerciseAdapter extends BaseAdapter {
    private static LayoutInflater inflater = null;
    private ArrayList<ExerciseElement> exerciseList;
    private Activity akt;


    protected WorkoutAddExerciseAdapter(Activity activity, ArrayList<ExerciseElement> exerciseList) {
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

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Holder holder = new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.list_add_exercise, null);
        ExerciseElement exerciseElement = exerciseList.get(position);

        holder.ExerciseTitle = (TextView) rowView.findViewById(R.id.AddExerciseTitle);

        String exerciseTitle = exerciseElement.getName();

        holder.ExerciseTitle.setText(exerciseTitle);

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });
        return rowView;
    }
}