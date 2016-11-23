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
        before.setImageResource(R.drawable.benchpressbefore);
        after = (ImageView) rod.findViewById(R.id.ex_imageafter);
        after.setImageResource(R.drawable.benchpressafter);

        description = (TextView) rod.findViewById(R.id.ex_descriptiontext);
        description.setText("1. Lie on a flat bench with your feet flat on the floor, keep your back flat on the bench.\n" +
                "2. Grasp the bar a little wider than shoulder width apart.\n" +
                "3. Raise the barbell above your body and move it over the middle of your chest, this is your starting position.\n" +
                "4. Lower the bar down so it just touches your chest.\n" +
                "5. Raise the bar till your arms are fully extended and your elbows are locked.\n" +
                "6. Return to starting position.");

        return rod;
    }

    @Override
    public void onClick(View v) {
        if(v == descriptionbutton){
            getActivity().setTitle("Benchpress Results");
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.ex_fragment, new Result_frag())
                    .commit();
        }
    }
}
