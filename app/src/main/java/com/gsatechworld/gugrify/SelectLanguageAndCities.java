package com.gsatechworld.gugrify;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.gsatechworld.gugrify.model.retrofit.ApiClient;
import com.gsatechworld.gugrify.model.retrofit.ApiInterface;
import com.gsatechworld.gugrify.model.retrofit.City;
import com.gsatechworld.gugrify.view.dashboard.DashboardActivity;
import com.gsatechworld.gugrify.view.adapters.LanguageRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SelectLanguageAndCities extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    boolean runOnce;
    public static List<String> list;
    FloatingActionButton selectCityButton;
    public static String selectedLanguage = "Kannada";
    RelativeLayout rLayout;
    TextView textView;
    boolean b = false;

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_language_cities);

        rLayout = findViewById(R.id.selectCityMain);
        runOnce=false;

        Spinner spinner = (Spinner) findViewById(R.id.citiesSpinner);
        spinner.setOnItemSelectedListener(SelectLanguageAndCities.this);

        final List<String> categories = new ArrayList<String>();

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item){

            public View getView(int position, View convertView, ViewGroup parent) {

                View v = super.getView(position, convertView, parent);
                if (position == getCount()) {
                    ((TextView) v.findViewById(android.R.id.text1)).setText("");
                    ((TextView) v.findViewById(android.R.id.text1)).setHint(getItem(getCount())); //"Hint to be displayed"
                }
                return v;
            }

            @Override
            public int getCount() {
                return super.getCount() - 1; // you dont display last item. It is used as hint.
            }
        };
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter.add("Kannada");
        dataAdapter.add("English");
        dataAdapter.add("Hindi");
        dataAdapter.add("Malayalam");
        dataAdapter.add("Select");
        spinner.setAdapter(dataAdapter);
        spinner.setSelection(dataAdapter.getCount());

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

        RecyclerView grid = findViewById(R.id.select_language_grid_view);
        LanguageRecyclerAdapter l = new LanguageRecyclerAdapter(SelectLanguageAndCities.this, getCities(), (FloatingActionButton) findViewById(R.id.languageAndCityFloating), rLayout);
        grid.setLayoutManager(new GridLayoutManager(this, 3));
        grid.setAdapter(l);
//        l.setClickListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        // On selecting a spinner item
        String item = adapterView.getItemAtPosition(i).toString();
        if(!item.equals("Select"))
            Toast.makeText(this, "Selected: " + item, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public ArrayList<City> getCities(){
        ArrayList<City> cities = new ArrayList<>();

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<City> call = apiService.getAllCities();

        call.enqueue(new Callback<City>() {
            @Override
            public void onResponse(Call<City>call, Response<City> response) {
                List<City> movies = response.body().getResults();
//                Log.d(TAG, "Number of movies received: " + movies.size());
            }

            @Override
            public void onFailure(Call<City>call, Throwable t) {
                // Log error here since request failed
//                Log.e(TAG, t.toString());
            }
        });

        /*cities.add(new City("Bangalore"));
        cities.add(new City("Bangalore"));
        cities.add(new City("Bangalore"));
        cities.add(new City("Bangalore"));
        cities.add(new City("Bangalore"));
        cities.add(new City("Bangalore"));
        cities.add(new City("Bangalore"));
        cities.add(new City("Bangalore"));
        cities.add(new City("Bangalore"));
        cities.add(new City("Bangalore"));
        cities.add(new City("Bangalore"));
        cities.add(new City("Bangalore"));
        cities.add(new City("Bangalore"));
        cities.add(new City("Bangalore"));
        cities.add(new City("Bangalore"));
        cities.add(new City("Bangalore"));
        cities.add(new City("Bangalore"));
        cities.add(new City("Bangalore"));
        cities.add(new City("Bangalore"));
        cities.add(new City("Bangalore"));*/

        return cities;
    }
}
