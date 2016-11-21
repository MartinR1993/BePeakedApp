package project.martin.bepeakedprojekt.Exercises.Exercise;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;

import project.martin.bepeakedprojekt.R;

/**
 * Created by Martin on 21-11-2016.
 */

public class Result_frag extends Fragment implements View.OnClickListener {
    ImageView graph;
    GridView view;
    Button resultbutton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rod = inflater.inflate(R.layout.frag_exercise_results, container, false);

        resultbutton = (Button) rod.findViewById(R.id.ex_results_button);

        graph = (ImageView) rod.findViewById(R.id.ex_graph);
        view = (GridView) rod.findViewById(R.id.ex_gridview);

        //TODO
        //Mangler floating button, som kan tilføje et nyt resultat til gridviewet.

        return rod;
    }

    @Override
    public void onClick(View v) {

    }
}
