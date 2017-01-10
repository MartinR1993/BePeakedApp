package project.martin.bepeakedprojekt.Exercises.Exercise;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import project.martin.bepeakedprojekt.Exercises.ExerciseElement;
import project.martin.bepeakedprojekt.R;

public class Exercise_akt extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;
    private ExerciseElement exercise;
    private int sets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_akt);

        exercise = (ExerciseElement) getIntent().getSerializableExtra("exercise");
        sets = (int) getIntent().getSerializableExtra("sets");
        setTitle(exercise.getName());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        viewPager = (ViewPager) findViewById(R.id.viewPager2);

        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        final TabLayout.Tab description = tabLayout.newTab();
        final TabLayout.Tab results = tabLayout.newTab();

        description.setText(R.string.exerciseDescription_banner);
        results.setText(R.string.exerciseResult_banner);

        tabLayout.addTab(results, 0);
        tabLayout.addTab(description, 1);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

    }
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    //Inner Class
    private class ViewPagerAdapter extends FragmentPagerAdapter {

        public ViewPagerAdapter (FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if (position == 0)
                return getString(R.string.exerciseResult_banner);
            else
                return getString(R.string.exerciseDescription_banner);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment f;

            Bundle bundle = new Bundle();
            bundle.putSerializable("exercise", exercise);

            if (position == 0) {
                f = new Result_frag();
                bundle.putSerializable("sets", sets);
            }else {
                f = new Description_frag();
            }
            f.setArguments(bundle);

            return f;
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}