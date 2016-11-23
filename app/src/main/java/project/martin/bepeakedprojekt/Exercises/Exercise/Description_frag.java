package project.martin.bepeakedprojekt.Exercises.Exercise;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import project.martin.bepeakedprojekt.Backend.API;
import project.martin.bepeakedprojekt.R;

/**
 * Created by Martin on 21-11-2016.
 */

public class Description_frag extends Fragment {
    private ImageView before, after;
    private TextView description;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rod = inflater.inflate(R.layout.frag_exercise_description, container, false);

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

        API api = new API();
        api.getImages();

        return rod;
    }
}
