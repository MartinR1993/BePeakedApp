package project.martin.bepeakedprojekt.Exercises.Exercise;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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
public class Result_frag extends Fragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rod = inflater.inflate(R.layout.frag_exercise_results, container, false);

        GraphView graphView = (GraphView) rod.findViewById(R.id.res_graph);

        FloatingActionButton fab = (FloatingActionButton) rod.findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new AddListener(this.getContext()));

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
            Button editButton = (Button) row.findViewById(R.id.resta_button);
            editButton.setText("Edit");
            table.addView(row);
        }

        double y;
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>();
        for (double x = 5.0; x < 8; x += 0.1){
            y = 1 / (5 + Math.exp(-x)) + 2;
            series.appendData(new DataPoint(x, y), true, 500);
        }
        graphView.addSeries(series);

        return rod;
    }

    private class AddListener implements View.OnClickListener
    {
        final Context context;

        private AddListener(Context context) {
            this.context = context;
        }

        @Override
        public void onClick(View v) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(context);
            dialog.setTitle("Add exercise result");
            dialog.setMessage("Write your result for this set");

            LinearLayout layout = new LinearLayout(context);
            layout.setOrientation(LinearLayout.VERTICAL);

            final EditText weightBox = new EditText(context);
            weightBox.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
            weightBox.setHint("Weight");
            layout.addView(weightBox);

            final EditText repsBox = new EditText(context);
            repsBox.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
            repsBox.setHint("Reps");
            layout.addView(repsBox);

            final Button saveButton = new Button(context);
            saveButton.setText("Save");
            layout.addView(saveButton);

            dialog.setView(layout);
            dialog.show();
        }
    }
}
