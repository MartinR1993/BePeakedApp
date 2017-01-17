package project.martin.bepeakedprojekt.Exercises.Exercise;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.OnDataPointTapListener;
import com.jjoe64.graphview.series.Series;

import project.martin.bepeakedprojekt.Misc.NumberPickerFormatter;
import project.martin.bepeakedprojekt.R;
import project.martin.bepeakedprojekt.SingletonApplications;
import project.martin.bepeakedprojekt.User.Settings;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

/**
 * Created by Martin on 21-11-2016.
 */
public class Result_frag extends Fragment implements View.OnClickListener {
    private TableLayout table;
    private LineGraphSeries<DataPoint> series;
    private GraphView graphView;
    private int i = 0;
    Toast toast;
    int sets;
    int currentSet = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rod = inflater.inflate(R.layout.frag_exercise_results, container, false);

        graphView = (GraphView) rod.findViewById(R.id.res_graph);

        FloatingActionButton fab = (FloatingActionButton) rod.findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new AddListener(this.getContext()));

        table = (TableLayout) rod.findViewById(R.id.res_tableresult);

        Intent intent = getActivity().getIntent();

        sets = intent.getIntExtra("sets",1000);


        //TODO: This is just dummy data.
        TableRow row = (TableRow) LayoutInflater.from(Result_frag.this.getActivity()).inflate(R.layout.res_tablerow, null);
        TextView col1 = (TextView) row.findViewById(R.id.resta_col1);
        col1.setTextAppearance(getActivity(), R.style.resta_header);
        col1.setText(R.string.exerciseResult_tableWeightkg);
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
        series.setDrawDataPoints(true);
        graphView.getViewport().setScalable(true);
        graphView.getGridLabelRenderer().setHorizontalLabelsVisible(false);
        graphView.getGridLabelRenderer().setPadding(30);

        return rod;
    }

    private TableRow createRow(String valCol1, String valCol2, String valRM) {
        final TableRow row = (TableRow) LayoutInflater.from(Result_frag.this.getActivity()).inflate(R.layout.res_tablerow, null);

        ((TextView) row.findViewById(R.id.resta_col1)).setText(valCol1);
        ((TextView) row.findViewById(R.id.resta_col2)).setText(valCol2);
        ((TextView) row.findViewById(R.id.resta_col3)).setText(valRM);
        ImageView edit = (ImageView) row.findViewById(R.id.resta_button);
        edit.setOnClickListener(this);

        currentSet++;

        if (currentSet == sets){
            Toast.makeText(getActivity(), "Du har lavet " + currentSet +" sæt", Toast.LENGTH_LONG).show();

            for (int i = 0; i < SingletonApplications.data.size(); i++) {
                if (SingletonApplications.currentExerciseID == SingletonApplications.data.get(i).getExerciseID() && i != SingletonApplications.data.size()-1) {


                    System.out.println(SingletonApplications.data.get(i).getName());

                    System.out.println(SingletonApplications.data.get(i+1).getName());

                    SingletonApplications.currentExerciseID = SingletonApplications.data.get(i+1).getExerciseID();

                    Intent j = new Intent(getContext(), Exercise_akt.class);
                    j.putExtra("exercise", SingletonApplications.data.get(i+1));
                    j.putExtra("sets", SingletonApplications.data.get(i+1).getSets());
                    getContext().startActivity(j);

                    i = SingletonApplications.data.size();
                }
            }
        }


        return row;
    }

    @Override
    public void onClick(View v) {
        //Implementere at redigere resultater
        Toast.makeText(getActivity(), "Her skal resultater redigeres", Toast.LENGTH_LONG).show();
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

            final int increment = 250;

            ((TextView) popupContent.findViewById(R.id.popres_weightkgTitle)).setText(R.string.exerciseResult_tableWeightkg);
            final NumberPicker npWeightkg = (NumberPicker) popupContent.findViewById(R.id.popres_weightkgSpin);
            npWeightkg.setMinValue(0);
            npWeightkg.setMaxValue(250);

            ((TextView) popupContent.findViewById(R.id.popres_weightgrTitle)).setText(R.string.exerciseResult_tableWeightgr);
            final NumberPicker npWeightgr = (NumberPicker) popupContent.findViewById(R.id.popres_weightgrSpin);
            npWeightgr.setMinValue(0);
            npWeightgr.setMaxValue(750/increment);
            npWeightgr.setFormatter(new NumberPickerFormatter(increment));



            ((TextView) popupContent.findViewById(R.id.popres_repsTitle)).setText(R.string.exerciseResult_tableReps);
            final NumberPicker npReps = (NumberPicker) popupContent.findViewById(R.id.popres_repsSpin);
            npReps.setMinValue(1);
            npReps.setMaxValue(30);

            layout.addView(popupContent);

            final Button saveButton = new Button(context);
            saveButton.setText(R.string.exerciseResult_addButtonExecute);
            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String unit = Settings.getUnit(Settings.USTAG_WEIGHT);

                    double weight = npWeightkg.getValue()+(npWeightgr.getValue()*increment/1000.0);
                    int reps = npReps.getValue();
                    //Lombardi
                    double oneRM = weight * Math.pow(reps, 0.1);
                    //Brzycki
                    //double oneRM = weight*36/(37-reps);
                    String oneRMString = "" + oneRM;
                    oneRMString = oneRMString.substring(0, oneRMString.indexOf('.') + 2);

                    i++;
                    series.appendData(new DataPoint(i-1, oneRM), true, i);

                    graphView.addSeries(series);
                    graphView.getViewport().setMinX(0);
                    graphView.getViewport().setMaxX(i);


                    series.setOnDataPointTapListener(new OnDataPointTapListener() {
                        @Override
                        public void onTap(Series series, DataPointInterface dataPoint) {
                            if (toast != null)
                            toast.cancel();
                            double afrunding =  ((int) (100 * dataPoint.getY() + 0.5)) / 100.0;
                            System.out.println(afrunding);
                            toast = Toast.makeText(getActivity(), afrunding + " - 1-RM", Toast.LENGTH_LONG);

                            toast.show();
                        }
                    });

                    table.addView(createRow(weight + " " + unit, reps + "", oneRMString + " " + unit),1);

                    if (table.getChildCount() > 11)
                        table.getChildAt(11).setVisibility(View.GONE);

                    popup.cancel();
                }
            });
            layout.addView(saveButton);

            popup.setView(layout);
            popup.show();
        }
    }
}