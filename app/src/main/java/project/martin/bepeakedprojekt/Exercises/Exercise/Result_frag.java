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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
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
    private LineGraphSeries<DataPoint> series;
    private GraphView graphView;
    private int i = 0;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rod = inflater.inflate(R.layout.frag_exercise_results, container, false);





        graphView = (GraphView) rod.findViewById(R.id.res_graph);

        FloatingActionButton fab = (FloatingActionButton) rod.findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new AddListener(this.getContext()));

        table = (TableLayout) rod.findViewById(R.id.res_tableresult);

        //TODO: This is just dummy data.
        TableRow row = (TableRow) LayoutInflater.from(Result_frag.this.getActivity()).inflate(R.layout.res_tablerow, null);
        TextView col1 = (TextView) row.findViewById(R.id.resta_col1);
        col1.setTextAppearance(getActivity(), R.style.resta_header);
        col1.setText(R.string.exerciseResult_tableWeight);
        TextView col2 = (TextView) row.findViewById(R.id.resta_col2);
        col2.setTextAppearance(getActivity(), R.style.resta_header);
        col2.setText(R.string.exerciseResult_tableReps);
        TextView col3 = (TextView) row.findViewById(R.id.resta_col3);
        col3.setTextAppearance(getActivity(), R.style.resta_header);
        col3.setText(R.string.exerciseResult_table1RM);
        row.findViewById(R.id.resta_div1).setVisibility(View.INVISIBLE);
        row.findViewById(R.id.resta_div2).setVisibility(View.INVISIBLE);
        row.findViewById(R.id.resta_div3).setVisibility(View.INVISIBLE);
        row.findViewById(R.id.resta_button).setVisibility(View.INVISIBLE);
        table.addView(row);

        series = new LineGraphSeries<>();
        graphView.getGridLabelRenderer().setVerticalLabelsVisible(false);
        graphView.getViewport().setScalable(true);

        return rod;
    }



    private TableRow createRow(String valCol1, String valCol2, String valRM) {
        final TableRow row = (TableRow) LayoutInflater.from(Result_frag.this.getActivity()).inflate(R.layout.res_tablerow, null);

        ((TextView) row.findViewById(R.id.resta_col1)).setText(valCol1);
        ((TextView) row.findViewById(R.id.resta_col2)).setText(valCol2);
        ((TextView) row.findViewById(R.id.resta_col3)).setText(valRM);
        Button editButton = (Button) row.findViewById(R.id.resta_button);
        editButton.setText(R.string.exerciseResult_tableButtonEdit);

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
            popup.setTitle(R.string.exerciseResult_addBanner);
            popup.setMessage(getString(R.string.exerciseResult_addDescription));

            final LinearLayout layout = new LinearLayout(context);
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.setLayoutParams(new ViewGroup.LayoutParams(MATCH_PARENT, MATCH_PARENT));

            final LinearLayout popupContent = (LinearLayout) LayoutInflater.from(Result_frag.this.getActivity()).inflate(R.layout.popup_result, null);

            ((TextView) popupContent.findViewById(R.id.popres_weightTitle)).setText(R.string.exerciseResult_tableWeight);
            final NumberPicker npWeight = (NumberPicker) popupContent.findViewById(R.id.popres_weightSpin);

            final int increment = 5;
            npWeight.setMinValue(1);
            npWeight.setFormatter(new NumberPickerFormatter(increment));
            npWeight.setMaxValue(250 / increment);
            npWeight.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

            ((TextView) popupContent.findViewById(R.id.popres_repsTitle)).setText(R.string.exerciseResult_tableReps);
            final NumberPicker npReps = (NumberPicker) popupContent.findViewById(R.id.popres_repsSpin);
            npReps.setMinValue(1);
            npReps.setMaxValue(100);
            npReps.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

            layout.addView(popupContent);

            final Button saveButton = new Button(context);
            saveButton.setText(R.string.exerciseResult_addButtonExecute);
            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String unit = Settings.getUnit(Settings.USTAG_WEIGHT);

                    double weight = npWeight.getValue() * increment;
                    int reps = npReps.getValue();
                    //Lombardi
                    double oneRM = weight * Math.pow(reps, 0.1);
                    //Brzycki
                    //double oneRM = weight*36/(37-reps);
                    String oneRMString = "" + oneRM;
                    oneRMString = oneRMString.substring(0, oneRMString.indexOf('.') + 2);

                    i++;
                    series.appendData(new DataPoint(i, oneRM), true, i);

                    graphView.addSeries(series);
                    graphView.getViewport().setMinX(0);
                    graphView.getViewport().setMaxX(i);

                    table.addView(createRow(weight + " " + unit, reps + "", oneRMString + " " + unit));
                    popup.cancel();
                }
            });
            layout.addView(saveButton);

            popup.setView(layout);
            popup.show();
        }
    }
}
