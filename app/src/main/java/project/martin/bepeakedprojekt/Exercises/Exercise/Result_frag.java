package project.martin.bepeakedprojekt.Exercises.Exercise;

import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
    FloatingActionButton fab;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rod = inflater.inflate(R.layout.frag_exercise_results, container, false);

        resultbutton = (Button) rod.findViewById(R.id.ex_results_button);
        resultbutton.setOnClickListener(this);

        graph = (ImageView) rod.findViewById(R.id.ex_graph);
        view = (GridView) rod.findViewById(R.id.ex_gridview);

        fab = (FloatingActionButton) rod.findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO
                Snackbar.make(view, "Skal tilføje et ekstra resultat", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        return rod;
    }

    @Override
    public void onClick(View v) {
        if(v == resultbutton){
            getActivity().setTitle("Benchpress Description");
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.ex_fragment, new Description_frag())
                    .commit();
        }
    }
}
