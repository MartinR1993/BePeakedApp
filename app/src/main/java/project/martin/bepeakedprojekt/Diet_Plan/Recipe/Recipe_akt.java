package project.martin.bepeakedprojekt.Diet_Plan.Recipe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import project.martin.bepeakedprojekt.R;

public class Recipe_akt extends AppCompatActivity {
    ImageView image;
    TextView descriptionView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_akt);

        //Hente data fra forrige aktivitet
        String description = getIntent().getStringExtra("description");

        image = (ImageView) findViewById(R.id.rc_image);
        //TODO
        //image.setImageResource();
        descriptionView = (TextView) findViewById(R.id.rc_description);
        descriptionView.setText(description);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
