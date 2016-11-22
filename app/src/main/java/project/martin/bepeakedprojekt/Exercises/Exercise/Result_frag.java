package project.martin.bepeakedprojekt.Exercises.Exercise;

import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import project.martin.bepeakedprojekt.R;

/**
 * Created by Martin on 21-11-2016.
 */

public class Result_frag extends Fragment implements View.OnClickListener {
    private ImageView graph;
    private GridView view;
    private Button resultbutton;
    private FloatingActionButton fab;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rod = inflater.inflate(R.layout.frag_exercise_results, container, false);

        resultbutton = (Button) rod.findViewById(R.id.ex_results_button);
        resultbutton.setOnClickListener(this);

        graph = (ImageView) rod.findViewById(R.id.ex_graph);
//        view = (GridView) rod.findViewById(R.id.ex_gridview);

        fab = (FloatingActionButton) rod.findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO
                Snackbar.make(view, "Skal tilføje et ekstra resultat", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        TableLayout table = (TableLayout) rod.findViewById(R.id.res_tableresult);

        TableRow row = (TableRow) LayoutInflater.from(Result_frag.this.getActivity()).inflate(R.layout.res_tablerow, null);
//        TextView col1 = (TextView) row.findViewById(R.id.resta_col1);
        ((TextView) row.findViewById(R.id.resta_col1)).setText("R0C1");
        ((TextView) row.findViewById(R.id.resta_col2)).setText("R0C2");
        ((TextView) row.findViewById(R.id.resta_col3)).setText("R0C3");
        ((Button) row.findViewById(R.id.resta_button)).setText("R0B1");
        table.addView(row);

        row = (TableRow) LayoutInflater.from(Result_frag.this.getActivity()).inflate(R.layout.res_tablerow, null);
        ((TextView) row.findViewById(R.id.resta_col1)).setText("R1C1");
        ((TextView) row.findViewById(R.id.resta_col2)).setText("R1C2");
        ((TextView) row.findViewById(R.id.resta_col3)).setText("R1C3");
        ((Button) row.findViewById(R.id.resta_button)).setText("R1B1");
        table.addView(row);

        row = (TableRow) LayoutInflater.from(Result_frag.this.getActivity()).inflate(R.layout.res_tablerow, null);
        ((TextView) row.findViewById(R.id.resta_col1)).setText("R2C1");
        ((TextView) row.findViewById(R.id.resta_col2)).setText("R2C2");
        ((TextView) row.findViewById(R.id.resta_col3)).setText("R2C3");
        ((Button) row.findViewById(R.id.resta_button)).setText("R2B1");
        table.addView(row);

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
