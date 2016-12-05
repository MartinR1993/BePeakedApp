package project.martin.bepeakedprojekt.Diet_Plan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.ArrayList;
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet_plan_menu);
        setTitle(R.string.dietPlanMenu_banner);
        // add data for displaying in expandable list view
        loadData();

        //get reference of the ExpandableListView
        simpleExpandableListView = (ExpandableListView) findViewById(R.id.simpleExpandableListView);
        // create the adapter by passing your ArrayList data
        listAdapter = new DietPlanAdapter(DietPlanMenu_akt.this, deptList);
        // attach the adapter to the expandable list view
        simpleExpandableListView.setAdapter(listAdapter);

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
                    intent.putExtra("description", "Proteinsmoothie & Æg");
                    startActivity(intent);
                }
                else {
                    Toast.makeText(getBaseContext(), " Clicked on : " + headerInfo.getName()
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

        addProduct(breakfast,"Proteinsmoothie & Æg");
        addProduct(breakfast,"Skyr");
        addProduct(breakfast,"Banan Pandekager");

        addProduct(lunch,"Chili con Carne");
        addProduct(lunch,"Håndmadder");

        addProduct(dinner, "Æggekage");
        addProduct(dinner, "Studenter Kaviar");

        addProduct(snacks, "Nødder");
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
}
