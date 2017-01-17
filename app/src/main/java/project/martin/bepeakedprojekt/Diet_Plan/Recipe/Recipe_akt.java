package project.martin.bepeakedprojekt.Diet_Plan.Recipe;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import project.martin.bepeakedprojekt.R;

public class Recipe_akt extends AppCompatActivity {
    private ImageView image;
    private TextView descriptionView, ingridients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_recipe_akt);

        //Hente data fra forrige aktivitet
        String description = getIntent().getStringExtra("description");
        setTitle(description);

        ingridients = (TextView) findViewById(R.id.ingridients);

        image = (ImageView) findViewById(R.id.rc_image);
        //TODO
        //Lav det for diverse opskrifter
        image.setImageResource(R.drawable.proteinsmothie);
        descriptionView = (TextView) findViewById(R.id.rc_description);
        descriptionView.setText(getString(R.string.recipe_protiensmoothie));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
