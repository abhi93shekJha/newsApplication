package com.gsatechworld.gugrify;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gsatechworld.gugrify.view.dashboard.DashboardActivity;
import com.gsatechworld.gugrify.view.adapters.CitiesGridViewAdapter;
import com.gsatechworld.gugrify.view.adapters.LanguageExpandableListAdapter;
import com.gsatechworld.gugrify.view.adapters.LanguageRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

//import LanguageRecyclerAdapter;

public class SelectLanguageAndCities extends AppCompatActivity {

    ExpandableListView expListView;
    boolean runOnce;
    public static List<String> list;
    Button  invisibleButton;
    ImageView selectCityButton;
    public static String selectedLanguage = "Kannada";
    LanguageExpandableListAdapter adapter;
    LanguageRecyclerAdapter lAdapter;
    boolean runOnceForLanguage;
    public static String pickedLanguage = "";
    TextView textView;
    View[] views = new View[9];
    boolean b = false;
    View previous=null;

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_language_cities);

        TextView tv1 = findViewById(R.id.select_language_textView);
        selectCityButton = findViewById(R.id.selectOnceButton);
        invisibleButton = findViewById(R.id.button_above_recycler);
        Typeface fontRegular = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Regular.ttf");
        tv1.setTypeface(fontRegular);
        //selectCityButton.setTypeface(fontRegular);

        // clear FLAG_TRANSLUCENT_STATUS flag:
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        // finally change the color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.lang_ststusbar_color));
        }

//        expListView = findViewById(R.id.select_language_expandable_list);
//        list = new ArrayList<>();
//        list.add("Kannada");
//        list.add("Hindi");
//        list.add("English");
//        list.add("Tamil");
//        adapter = new LanguageExpandableListAdapter(SelectLanguageAndCities.this, list);
//        expListView.setAdapter(adapter);
//        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
//            @Override
//            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
//                Log.d("Child clicked"," True");
//                expListView.collapseGroup(i);
//                selectedLanguage = list.get(i1);
//                adapter.notifyDataSetChanged();
//                return false;
//            }
//        });
//
//        ParentItems p = new ParentItems("Kannada");
//
//        //These are the list of languages to be added dynamically
//        List<Object> languages = new ArrayList<>();
//        languages.add("Kannada");
//        languages.add("English");
//        languages.add("Hindi");
//        languages.add("Malayalam");
//        p.setChildObjectList(languages);

//        List<ParentObject> list = new ArrayList<>();
//        list.add(p);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        list = new ArrayList<>();
        list.add("Select");
        lAdapter = new LanguageRecyclerAdapter(SelectLanguageAndCities.this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(lAdapter);

//        adapter.setCustomParentAnimationViewId(R.id.activity_header_down_image);
//        adapter.setParentClickableViewAnimationDefaultDuration();
//        adapter.setParentAndIconExpandOnClick(true);
        //recyclerView.setAdapter(adapter);

        GridView gridView = findViewById(R.id.select_language_grid_view);
        final CitiesGridViewAdapter c = new CitiesGridViewAdapter(SelectLanguageAndCities.this);
        gridView.setAdapter(c);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                //This logic has to be changed later
                if(!runOnce) {
                    selectCityButton.setVisibility(View.VISIBLE);
                    Animation animation2 = AnimationUtils.loadAnimation(SelectLanguageAndCities.this, R.anim.slide_left);
                    selectCityButton.startAnimation(animation2);
                    previous = view;
                    RelativeLayout r = view.findViewById(R.id.grid_view_items_relativeLayout);
                    r.setBackgroundColor(SelectLanguageAndCities.this.getResources().getColor(R.color.brown));
                    TextView tv = view.findViewById(R.id.citiesGridView_text2);
                    tv.setVisibility(View.VISIBLE);
                    runOnce = true;
                    return;
                }
//                else{
//                    runOnce = false;
//                }
                    if(previous == view){
                        if(!b) {
                            RelativeLayout r = previous.findViewById(R.id.grid_view_items_relativeLayout);
                            r.setBackgroundColor(SelectLanguageAndCities.this.getResources().getColor(R.color.colorWhite));
                            TextView tv = previous.findViewById(R.id.citiesGridView_text2);
                            tv.setVisibility(View.GONE);
                            b = true;
                        }
                        else{
                            RelativeLayout r = previous.findViewById(R.id.grid_view_items_relativeLayout);
                            r.setBackgroundColor(SelectLanguageAndCities.this.getResources().getColor(R.color.brown));
                            TextView tv = previous.findViewById(R.id.citiesGridView_text2);
                            tv.setVisibility(View.VISIBLE);
                            b = false;
                        }
                    }
                    else{
                        RelativeLayout r = previous.findViewById(R.id.grid_view_items_relativeLayout);
                        r.setBackgroundColor(SelectLanguageAndCities.this.getResources().getColor(R.color.colorWhite));
                        TextView tv = previous.findViewById(R.id.citiesGridView_text2);
                        tv.setVisibility(View.GONE);

                        RelativeLayout r1 = view.findViewById(R.id.grid_view_items_relativeLayout);
                        r1.setBackgroundColor(SelectLanguageAndCities.this.getResources().getColor(R.color.brown));
                        TextView tv1 = view.findViewById(R.id.citiesGridView_text2);
                        tv1.setVisibility(View.VISIBLE);

                        previous = view;
                    }

//                    RelativeLayout r = view.findViewById(R.id.grid_view_items_relativeLayout);
//                    r.setBackgroundColor(SelectLanguageAndCities.this.getResources().getColor(R.color.colorWhite));
//                    TextView tv = view.findViewById(R.id.citiesGridView_text2);
//                    tv.setVisibility(View.GONE);

            }
        });

        invisibleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(list.size() > 1){
                    String temp = list.get(0);
                    list.clear();
                    pickedLanguage = "Kannada";
                    list.add(temp);
                    lAdapter.notifyDataSetChanged();
                    return;
                }
                fillLanguageList();
                runOnceForLanguage = true;
                lAdapter.notifyItemRangeInserted(0, list.size() - 1);
            }
        });

        selectCityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SelectLanguageAndCities.this, DashboardActivity.class));
            }
        });
    }

//    @Override
//    public void onClick(View view) {
//        Log.d("Inside: ", "onClick");
//        ArrayList<String> temp = new ArrayList<>();
//        temp.add("Kannada");
//        temp.add("Hindi");
//        temp.add("Malayalam");
//        temp.add("English");
//        list.addAll(1, temp);
//        //lAdapter.notifyDataSetChanged();
//    }

    public void fillLanguageList() {
        if (!runOnceForLanguage) {
            getLanguageFromServer();
        } else {
            if (pickedLanguage.equals("Kannada") == false) {
                list.remove(pickedLanguage);
                list.add("Kannada");
                getLanguageFromServer();
            } else {
                getLanguageFromServer();
            }
        }
    }

        // Ignore the logic and just use this method to fill the language list got from server.
        public void getLanguageFromServer () {
            list.add("English");
            list.add("Hindi");
            list.add("Malayalam");
            list.add("Tamil");
        }
    }
