package project.martin.bepeakedprojekt.Exercises.Exercise;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;


import project.martin.bepeakedprojekt.R;

public class Exercise_akt extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_akt);
        setTitle("Benchpress");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        viewPager = (ViewPager) findViewById(R.id.viewPager2);

        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        final TabLayout.Tab description=tabLayout.newTab();
        final TabLayout.Tab results=tabLayout.newTab();

        description.setText("Description");
        results.setText("Results");


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
                return "Results";
            else
                return "Description";
        }

        @Override
        public Fragment getItem(int position) {
            Fragment f;
            if (position == 0)
                f = new Result_frag();
            else
                f = new Description_frag();

            return f;
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}
