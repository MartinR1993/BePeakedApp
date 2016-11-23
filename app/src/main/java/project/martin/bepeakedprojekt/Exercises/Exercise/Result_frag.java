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

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import project.martin.bepeakedprojekt.R;

/**
 * Created by Martin on 21-11-2016.
 */

public class Result_frag extends Fragment implements View.OnClickListener {
    private GridView view;
    private Button resultbutton;
    private GraphView graphView;
    private FloatingActionButton fab;
    private double y,x;
    LineGraphSeries<DataPoint> series;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rod = inflater.inflate(R.layout.frag_exercise_results, container, false);

        resultbutton = (Button) rod.findViewById(R.id.ex_results_button);
        resultbutton.setOnClickListener(this);

        graphView = (GraphView) rod.findViewById(R.id.graph);
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

        //TODO: This is just dummy data.
        TableRow row = (TableRow) LayoutInflater.from(Result_frag.this.getActivity()).inflate(R.layout.res_tablerow, null);
        TextView col1 = (TextView) row.findViewById(R.id.resta_col1);
        col1.setTextAppearance(getActivity(), R.style.resta_header);
        col1.setText("Weight");
        TextView col2 = (TextView) row.findViewById(R.id.resta_col2);
        col2.setTextAppearance(getActivity(), R.style.resta_header);
        col2.setText("Reps");
        TextView col3 = (TextView) row.findViewById(R.id.resta_col3);
        col3.setTextAppearance(getActivity(), R.style.resta_header);
        col3.setText("1-RM");
        row.findViewById(R.id.resta_div1).setVisibility(View.INVISIBLE);
        row.findViewById(R.id.resta_div2).setVisibility(View.INVISIBLE);
        row.findViewById(R.id.resta_div3).setVisibility(View.INVISIBLE);
        row.findViewById(R.id.resta_button).setVisibility(View.INVISIBLE);
        table.addView(row);

        for (int i = 1; i < 15; i++) {
            row = (TableRow) LayoutInflater.from(Result_frag.this.getActivity()).inflate(R.layout.res_tablerow, null);
            ((TextView) row.findViewById(R.id.resta_col1)).setText("R" + i + "C1");
            ((TextView) row.findViewById(R.id.resta_col2)).setText("R" + i + "C2");
            ((TextView) row.findViewById(R.id.resta_col3)).setText("R" + i + "C3");
            ((Button) row.findViewById(R.id.resta_button)).setText("Edit"/*"R" + i + "B1"*/);
            table.addView(row);
        }

        x = 5.0;
        series = new LineGraphSeries<DataPoint>();
        for (int i = 0; i<100; i++){
            x = x + 0.1;
            y = 2*x+2;
            series.appendData(new DataPoint(x,y), true, 500);
        }
        graphView.addSeries(series);

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
