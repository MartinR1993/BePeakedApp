package project.martin.bepeakedprojekt.Exercises.Exercise;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import project.martin.bepeakedprojekt.R;

/**
 * Created by Martin on 21-11-2016.
 */

public class Description_frag extends Fragment implements View.OnClickListener {
    ImageView before, after;
    TextView description;
    Button descriptionbutton;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rod = inflater.inflate(R.layout.frag_exercise_description, container, false);

        descriptionbutton = (Button) rod.findViewById(R.id.ex_description_button);
        descriptionbutton.setOnClickListener(this);

        before = (ImageView) rod.findViewById(R.id.ex_imagebefore);
        before.setImageResource(R.drawable.bepeaked);
        after = (ImageView) rod.findViewById(R.id.ex_imageafter);
        after.setImageResource(R.drawable.bepeaked_logo);

        description = (TextView) rod.findViewById(R.id.ex_descriptiontext);
        description.setText("Her skal beskrivelsen af øvelsen stå");

        return rod;
    }

    @Override
    public void onClick(View v) {
        if(v == descriptionbutton){
            getActivity().setTitle("Øvelsens navn Results");
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.ex_fragment, new Result_frag())
                    .commit();
        }
    }
}
