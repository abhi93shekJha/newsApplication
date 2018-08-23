package com.gsatechworld.gugrify;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.gsatechworld.gugrify.view.dashboard.DashboardActivity;
import com.gsatechworld.gugrify.view.adapters.CitiesGridViewAdapter;
import com.gsatechworld.gugrify.view.adapters.LanguageExpandableListAdapter;
import com.gsatechworld.gugrify.view.adapters.LanguageRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

//import LanguageRecyclerAdapter;

public class SelectLanguageAndCities extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    ExpandableListView expListView;
    boolean runOnce;
    public static List<String> list;
    FloatingActionButton selectCityButton;
    public static String selectedLanguage = "Kannada";
    LanguageExpandableListAdapter adapter;
    LanguageRecyclerAdapter lAdapter;
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

        Spinner spinner = (Spinner) findViewById(R.id.citiesSpinner);
        spinner.setOnItemSelectedListener(SelectLanguageAndCities.this);

        List<String> categories = new ArrayList<String>();
        categories.add("Kannada");
        categories.add("English");
        categories.add("Hindi");
        categories.add("Malayalam");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

        TextView tv1 = findViewById(R.id.select_language_textView);
        selectCityButton = findViewById(R.id.languageAndCityFloating);
        selectCityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelectLanguageAndCities.this, DashboardActivity.class);
                startActivity(intent);
            }
        });
        Typeface fontRegular = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Regular.ttf");
        tv1.setTypeface(fontRegular);
        //selectCityButton.setTypeface(fontRegular);

        // clear FLAG_TRANSLUCENT_STATUS flag:
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        // finally change the color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.lang_ststusbar_color));
        }


        GridView gridView = findViewById(R.id.select_language_grid_view);
        final CitiesGridViewAdapter c = new CitiesGridViewAdapter(SelectLanguageAndCities.this);
        gridView.setAdapter(c);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                //This logic has to be changed later
                if (!runOnce) {
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
                if (previous == view) {
                    if (!b) {
                        RelativeLayout r = previous.findViewById(R.id.grid_view_items_relativeLayout);
                        r.setBackgroundColor(SelectLanguageAndCities.this.getResources().getColor(R.color.colorWhite));
                        TextView tv = previous.findViewById(R.id.citiesGridView_text2);
                        tv.setVisibility(View.GONE);
                        b = true;
                    } else {
                        RelativeLayout r = previous.findViewById(R.id.grid_view_items_relativeLayout);
                        r.setBackgroundColor(SelectLanguageAndCities.this.getResources().getColor(R.color.brown));
                        TextView tv = previous.findViewById(R.id.citiesGridView_text2);
                        tv.setVisibility(View.VISIBLE);
                        b = false;
                    }
                } else {
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
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        // On selecting a spinner item
        String item = adapterView.getItemAtPosition(i).toString();
        Toast.makeText(this, "Selected: " + item, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
