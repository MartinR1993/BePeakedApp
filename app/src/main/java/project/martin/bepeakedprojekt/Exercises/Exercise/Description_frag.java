package project.martin.bepeakedprojekt.Exercises.Exercise;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import project.martin.bepeakedprojekt.R;

/**
 * Created by Martin on 21-11-2016.
 */

public class Description_frag extends Fragment {
    ImageView before, after;
    TextView description;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rod = inflater.inflate(R.layout.frag_exercise_description, container, false);

        before = (ImageView) rod.findViewById(R.id.ex_imagebefore);
        before.setImageResource(R.drawable.bepeaked);
        after = (ImageView) rod.findViewById(R.id.ex_imageafter);
        after.setImageResource(R.drawable.bepeaked_logo);

        description = (TextView) rod.findViewById(R.id.ex_descriptiontext);
        description.setText("Her skal beskrivelsen af øvelsen stå");

        return rod;
    }
}
