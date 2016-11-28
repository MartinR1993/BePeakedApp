package project.martin.bepeakedprojekt.Exercises.Exercise;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import project.martin.bepeakedprojekt.Misc.NumberPickerFormatter;
import project.martin.bepeakedprojekt.R;
import project.martin.bepeakedprojekt.User.Settings;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

/**
 * Created by Martin on 21-11-2016.
 */
public class Result_frag extends Fragment
{
    private TableLayout table;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rod = inflater.inflate(R.layout.frag_exercise_results, container, false);

        GraphView graphView = (GraphView) rod.findViewById(R.id.res_graph);

        FloatingActionButton fab = (FloatingActionButton) rod.findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new AddListener(this.getContext()));

        table = (TableLayout) rod.findViewById(R.id.res_tableresult);

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

        double y;
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>();
        for (double x = 5.0; x < 8; x += 0.1){
            y = 1 / (5 + Math.exp(-x)) + 2;
            series.appendData(new DataPoint(x, y), true, 500);
        }
        graphView.addSeries(series);

        return rod;
    }

    private TableRow createRow(String valCol1, String valCol2, String valRM) {
        final TableRow row = (TableRow) LayoutInflater.from(Result_frag.this.getActivity()).inflate(R.layout.res_tablerow, null);

        ((TextView) row.findViewById(R.id.resta_col1)).setText(valCol1);
        ((TextView) row.findViewById(R.id.resta_col2)).setText(valCol2);
        ((TextView) row.findViewById(R.id.resta_col3)).setText(valRM);
        Button editButton = (Button) row.findViewById(R.id.resta_button);
        editButton.setText("Edit");

        return row;
    }

    private class AddListener implements View.OnClickListener
    {
        final Context context;

        private AddListener(Context context) {
            this.context = context;
        }

        @Override
        public void onClick(View v) {
            final AlertDialog popup = new AlertDialog.Builder(context).create();
            popup.setTitle("Add exercise result");
            popup.setMessage("Write your result for this set");

            final LinearLayout layout = new LinearLayout(context);
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.setLayoutParams(new ViewGroup.LayoutParams(MATCH_PARENT, MATCH_PARENT));

            final LinearLayout popupContent = (LinearLayout) LayoutInflater.from(Result_frag.this.getActivity()).inflate(R.layout.popup_result, null);

            ((TextView) popupContent.findViewById(R.id.popres_weightTitle)).setText("Weight");
            final NumberPicker npWeight = (NumberPicker) popupContent.findViewById(R.id.popres_weightSpin);

            final int increment = 5;
            npWeight.setMinValue(1);
            npWeight.setFormatter(new NumberPickerFormatter(increment));
            npWeight.setMaxValue(250 / increment);
            npWeight.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

            ((TextView) popupContent.findViewById(R.id.popres_repsTitle)).setText("Reps");
            final NumberPicker npReps = (NumberPicker) popupContent.findViewById(R.id.popres_repsSpin);
            npReps.setMinValue(1);
            npReps.setMaxValue(100);
            npReps.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

            layout.addView(popupContent);

            final Button saveButton = new Button(context);
            saveButton.setText("Save");
            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String unit = Settings.getUnit(Settings.USTAG_WEIGHT);

                    //TODO: Kender ikke algoritmen for 1-RM (x)
                    table.addView(createRow(npWeight.getValue() + " " + unit, npReps.getValue() + "", "x " + unit));
                    popup.cancel();
                }
            });
            layout.addView(saveButton);

            popup.setView(layout);
            popup.show();
        }
    }
}
