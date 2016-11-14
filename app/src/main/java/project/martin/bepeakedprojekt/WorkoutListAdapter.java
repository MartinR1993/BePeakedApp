package project.martin.bepeakedprojekt;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by Martin on 14-11-2016.
 */

public class WorkoutListAdapter extends BaseAdapter {
    private final Context context;
    private static LayoutInflater inflater = null;
    private ArrayList<Score> scoreList;

    public WorkoutListAdapter(Activity activity, ArrayList<Score> scoreList) {
        context = activity;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.scoreList = scoreList;
    }

    @Override
    public int getCount() {
        return scoreList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class Holder
    {
        protected TextView hiddenWordView;
        protected TextView shownWordView;
        protected ImageView img;
        protected TextView date;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Holder holder = new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.listelement_score, null);
        Score score = scoreList.get(position);

        holder.hiddenWordView = (TextView) rowView.findViewById(R.id.scorelistEl_hiddenWord);
        holder.shownWordView = (TextView) rowView.findViewById(R.id.scorelistEl_shownWord);
        holder.img = (ImageView) rowView.findViewById(R.id.scorelistEl_image);
        holder.date = (TextView) rowView.findViewById(R.id.scorelistEl_date);

        int state = score.getState();
        String hiddenWord = score.getHiddenWord();
        hiddenWord = hiddenWord.substring(0, 1).toUpperCase() + hiddenWord.substring(1);
        String shownWord = score.getShownWord();
        shownWord = shownWord.substring(0, 1).toUpperCase() + shownWord.substring(1);
        String date = new SimpleDateFormat("dd/MM-yy HH:mm").format(score.getDate());

        holder.hiddenWordView.setText(hiddenWord);
        holder.shownWordView.setText(shownWord);
        holder.img.setImageResource(getImage(state));
        holder.date.setText(date);

        if(state == 7)
            holder.img.setBackgroundColor(Color.parseColor("#ff7090"));
        else
            holder.img.setBackgroundColor(Color.parseColor("#70ff90"));

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, scoreList.get(position).getShownWord(), Toast.LENGTH_LONG).show();
            }
        });
        return rowView;
    }

    private int getImage(int state) {
        int imageID;

        switch (state) {
            default:
            case 0: {
                imageID = R.mipmap.galge; break;
            }
            case 1: {
                imageID = R.mipmap.forkert1; break;
            }
            case 2: {
                imageID = R.mipmap.forkert2; break;
            }
            case 3: {
                imageID = R.mipmap.forkert3; break;
            }
            case 4: {
                imageID = R.mipmap.forkert4; break;
            }
            case 5: {
                imageID = R.mipmap.forkert5; break;
            }
            case 6:
            case 7: {
                imageID = R.mipmap.forkert6; break;
            }
        }
        return imageID;
    }
}
