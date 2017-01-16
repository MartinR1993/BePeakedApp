package project.martin.bepeakedprojekt.Diet_Plan;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;

import project.martin.bepeakedprojekt.Diet_Plan.Adapter.ChildInfo;
import project.martin.bepeakedprojekt.Diet_Plan.Adapter.DietPlanAdapter;
import project.martin.bepeakedprojekt.Diet_Plan.Adapter.GroupInfo;
import project.martin.bepeakedprojekt.Diet_Plan.Recipe.Recipe_akt;
import project.martin.bepeakedprojekt.R;

public class DietPlanMenu_akt extends AppCompatActivity {
    private LinkedHashMap<String, GroupInfo> subjects = new LinkedHashMap<String, GroupInfo>();
    private ArrayList<GroupInfo> deptList = new ArrayList<GroupInfo>();
    private DietPlanAdapter listAdapter;
    private ExpandableListView simpleExpandableListView;
    Calendar c = Calendar.getInstance();
    int hour = c.get(Calendar.HOUR);
    int ampm = c.get(Calendar.AM_PM);
    private TextView username, calories, culhydrates, fat, protein;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet_plan_menu);
        setTitle(R.string.dietPlanMenu_banner);
        // add data for displaying in expandable list view
        loadData();

//        new ServerComm(BackendData.SERVER_ADRESS, BackendData.SERVER_PORT).getDieatplanProfile(this, User.getUserID(), User.getSessionID());

        username = (TextView) findViewById(R.id.user);
        calories = (TextView) findViewById(R.id.calories);
        culhydrates = (TextView) findViewById(R.id.culhydrates);
        fat = (TextView) findViewById(R.id.fat);
        protein = (TextView) findViewById(R.id.protein);

        //get reference of the ExpandableListView
        simpleExpandableListView = (ExpandableListView) findViewById(R.id.simpleExpandableListView);
        // create the adapter by passing your ArrayList data
        listAdapter = new DietPlanAdapter(DietPlanMenu_akt.this, deptList);
        // attach the adapter to the expandable list view
        simpleExpandableListView.setAdapter(listAdapter);

        if (hour > 5 && hour < 12 && ampm == 0)
            simpleExpandableListView.expandGroup(0);
        else if(hour < 5 && ampm == 1)
            simpleExpandableListView.expandGroup(1);
        else if(hour < 10 && ampm == 1)
            simpleExpandableListView.expandGroup(2);

        // setOnChildClickListener listener for child row click
        simpleExpandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                //get the group header
                GroupInfo headerInfo = deptList.get(groupPosition);
                //get the child info
                ChildInfo detailInfo =  headerInfo.getProductList().get(childPosition);
                //display it or do something with it
                if(groupPosition == 0 && childPosition == 0) {
                    Intent intent = new Intent(DietPlanMenu_akt.this, Recipe_akt.class);
                    intent.putExtra("description", getString(R.string.DietPlanMenu_Proteinsmoothie_and_egg));
                    startActivity(intent);
                }
                else {
                    Toast.makeText(getBaseContext(), "Clicked on : " + headerInfo.getName()
                            + "/" + detailInfo.getName(), Toast.LENGTH_LONG).show();
                }
                return false;
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    //method to expand all groups
    private void expandAll() {
        int count = listAdapter.getGroupCount();
        for (int i = 0; i < count; i++){
            simpleExpandableListView.expandGroup(i);
        }
    }

    //method to collapse all groups
    private void collapseAll() {
        int count = listAdapter.getGroupCount();
        for (int i = 0; i < count; i++){
            simpleExpandableListView.collapseGroup(i);
        }
    }

    //load some initial data into our list
    private void loadData(){
        String breakfast = getString(R.string.dietPlanMenu_breakfast);
        String lunch = getString(R.string.dietPlanMenu_lunch);
        String dinner = getString(R.string.dietPlanMenu_dinner);
        String snacks = getString(R.string.dietPlanMenu_snacks);

        addProduct(breakfast,getString(R.string.DietPlanMenu_Proteinsmoothie_and_egg));
        addProduct(breakfast,getString(R.string.DietPlanMenu_shun));
        addProduct(breakfast,getString(R.string.DietPlanMenu_Banana_Pancakes));

        addProduct(lunch,getString(R.string.DietPlanMenu_Chili_con_carne));
        addProduct(lunch,getString(R.string.DietPlanMenu_Sandwiches));

        addProduct(dinner, getString(R.string.DietPlanMenu_Omelette));
        addProduct(dinner, getString(R.string.DietPlanMenu_Student_Caviar));

        addProduct(snacks, getString(R.string.DietPlanMenu_Nuts));
    }

    //here we maintain our products in various food
    private int addProduct(String food, String product){

        int groupPosition = 0;

        //check the hash map if the group already exists
        GroupInfo headerInfo = subjects.get(food);
        //add the group if doesn't exists
        if(headerInfo == null){
            headerInfo = new GroupInfo();
            headerInfo.setName(food);
            subjects.put(food, headerInfo);
            deptList.add(headerInfo);
        }

        //get the children for the group
        ArrayList<ChildInfo> productList = headerInfo.getProductList();
        //size of the children list
        int listSize = productList.size();
        //add to the counter
        listSize++;

        //create a new child and add that to the group
        ChildInfo detailInfo = new ChildInfo();
        // detailInfo.setSequence(String.valueOf(listSize));
        detailInfo.setName(product);
        productList.add(detailInfo);
        headerInfo.setProductList(productList);

        //find the group position inside the list
        groupPosition = deptList.indexOf(headerInfo);
        return groupPosition;
    }

    public void setUserData(double prot, double cal, double col, double fat) {
        TextView protView = (TextView) findViewById(R.id.protein);
        TextView calView = (TextView) findViewById(R.id.calories);
        TextView colView = (TextView) findViewById(R.id.culhydrates);
        TextView fatView = (TextView) findViewById(R.id.fat);

        protView.setText("Protein: " + prot);
        calView.setText("Calories: " + cal);
        colView.setText("Culhydrates: " + col);
        fatView.setText("Fat: " + fat);
    }
}
