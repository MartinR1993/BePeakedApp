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
        setTitle(description);

        image = (ImageView) findViewById(R.id.rc_image);
        //TODO
        //Lav det for diverse opskrifter
        image.setImageResource(R.drawable.proteinsmothie);
        descriptionView = (TextView) findViewById(R.id.rc_description);
        descriptionView.setText("1. Bland skyr, isterninger, havregryn, blåbær og proteinpulver i den beskrevne mængde og blend det i 2-3 minutter. " +
                "\n2. Ægget kan du spise hårdkogt eller som spejlæg. Spejlæg: Slå æggene ud på en opvarmet pande og lad dem stege 3-5 minutter, " +
                "afhængig af hvordan blommen foretrækkes. Hold blusset på middel varme.");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
