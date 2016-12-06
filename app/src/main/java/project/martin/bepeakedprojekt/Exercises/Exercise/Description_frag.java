package project.martin.bepeakedprojekt.Exercises.Exercise;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import project.martin.bepeakedprojekt.Backend.API;
import project.martin.bepeakedprojekt.Exercises.ExerciseElement;
import project.martin.bepeakedprojekt.R;

public class Description_frag extends Fragment {
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rod = inflater.inflate(R.layout.frag_exercise_description, container, false);
        ExerciseElement exercise = (ExerciseElement) getArguments().getSerializable("exercise");

        ImageView before = (ImageView) rod.findViewById(R.id.ex_imagebefore);
        ImageView after = (ImageView) rod.findViewById(R.id.ex_imageafter);

        API.getImages(exercise.getImageID(), after, before);

        TextView description = (TextView) rod.findViewById(R.id.ex_descriptiontext);
        description.setText(exercise.getDescription());

        return rod;
    }
}