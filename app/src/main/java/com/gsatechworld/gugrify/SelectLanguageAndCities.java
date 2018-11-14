package com.gsatechworld.gugrify;

import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.gsatechworld.gugrify.model.retrofit.ApiClient;
import com.gsatechworld.gugrify.model.retrofit.ApiInterface;
import com.gsatechworld.gugrify.model.retrofit.City;
import com.gsatechworld.gugrify.model.retrofit.CityResponse;
import com.gsatechworld.gugrify.model.retrofit.Language;
import com.gsatechworld.gugrify.model.retrofit.LanguageResponse;
import com.gsatechworld.gugrify.view.dashboard.DashboardActivity;
import com.gsatechworld.gugrify.view.adapters.LanguageRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SelectLanguageAndCities extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    boolean runOnce;
    public static ArrayList<City> list;
    FloatingActionButton selectCityButton;
    public static String selectedLanguage = "Kannada";
    RelativeLayout rLayout;
    Spinner spinner;
    TextView textView;
    boolean b = false;
    ApiInterface apiService;
    RelativeLayout mainLayout;
    ProgressBar progressBar;
    List<CityResponse.city> lists;
    List<String> images;
    List<String> languages;
    NewsSharedPreferences sharedPreferences;
    LanguageResponse language;
    LanguageRecyclerAdapter l;
    RecyclerView grid;
    boolean fromSettings;

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_language_cities);
        sharedPreferences = NewsSharedPreferences.getInstance(SelectLanguageAndCities.this);

        fromSettings = getIntent().getBooleanExtra("fromSettings",false);

        //if city has already been selected

        if(sharedPreferences.getCitySelected().length() != 0){
            if(fromSettings){

            }else {
                Intent intent = new Intent(SelectLanguageAndCities.this, DashboardActivity.class);
                startActivity(intent);
                finish();
            }
        }

        mainLayout = findViewById(R.id.selectCityMain);
        progressBar = findViewById(R.id.progressBar1);
        list = new ArrayList<>();
        lists = new ArrayList<>();
        images = new ArrayList<>();
        languages = new ArrayList<>();
        language = new LanguageResponse();

        rLayout = findViewById(R.id.selectCityMain);
        runOnce=false;

        spinner = (Spinner) findViewById(R.id.citiesSpinner);
        spinner.setOnItemSelectedListener(SelectLanguageAndCities.this);

        final List<String> categories = new ArrayList<String>();
        getLanguages();

        TextView tv1 = findViewById(R.id.select_language_textView);
        selectCityButton = findViewById(R.id.languageAndCityFloating);
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

        grid = findViewById(R.id.select_language_grid_view);
        l = new LanguageRecyclerAdapter(SelectLanguageAndCities.this, images, list, (FloatingActionButton) findViewById(R.id.languageAndCityFloating), rLayout);
        grid.setLayoutManager(new GridLayoutManager(SelectLanguageAndCities.this, 3));
        grid.setAdapter(l);
//        l.setClickListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        // On selecting a spinner item
        String languageId = "0";
        if(i != language.getResult().size()) {
            languageId = language.getResult().get(i).getLanguageId();
            getCities(languageId);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void getCities(String languageId){

        list.clear();
        mainLayout.setAlpha(0.5f);
        progressBar.setVisibility(View.VISIBLE);

        apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<CityResponse> call = apiService.getAllCities(languageId);

        call.enqueue(new Callback<CityResponse>() {
            @Override
            public void onResponse(Call<CityResponse> call, Response<CityResponse> response) {
                CityResponse city = new CityResponse();
                    if (response.isSuccessful()) {
                        city = response.body();
                        lists = city.getResult();

                        for(int i=0; i<lists.size(); i++){
                            City c = new City(lists.get(i).getCities());
//                            Log.d("city is", lists.get(i).getCities());
                            String image = lists.get(i).getImages();
                            list.add(c);
                            images.add(image);
                        }
                        Log.d("List size is", String.valueOf(list.size()));
                        l.notifyDataSetChanged();
                        mainLayout.setAlpha(1.0f);
                        progressBar.setVisibility(View.GONE);
                    }
                    else {
                        Toast.makeText(SelectLanguageAndCities.this, "Server error!!", Toast.LENGTH_SHORT).show();
                    }
//                Log.d(TAG, "Number of movies received: " + movies.size());
            }

            @Override
            public void onFailure(Call<CityResponse>call, Throwable t) {
                // Log error here since request failed
                Toast.makeText(SelectLanguageAndCities.this, "Server error!!", Toast.LENGTH_SHORT).show();
                Log.e(SelectLanguageAndCities.class.getSimpleName(), t.toString());
            }
        });
    }

    public void getLanguages(){
       /* languages.add("Kannada");
        languages.add("English");
        languages.add("Hindi");
        languages.add("Malayalam");
        languages.add("Select");*/
        mainLayout.setAlpha(0.5f);
        progressBar.setVisibility(View.VISIBLE);

        apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<LanguageResponse> call = apiService.getAllLanguages();
        call.enqueue(new Callback<LanguageResponse>() {
            @Override
            public void onResponse(Call<LanguageResponse> call, Response<LanguageResponse> response) {
                if (response.isSuccessful()) {
                    language = response.body();
                    Log.d("Response for language", language.getResult().get(0).getLanguage());
                    fillSpinner();
                }
                else {
                    Toast.makeText(SelectLanguageAndCities.this, "Server error!!", Toast.LENGTH_SHORT).show();
                }
//                Log.d(TAG, "Number of movies received: " + movies.size());
            }

            @Override
            public void onFailure(Call<LanguageResponse>call, Throwable t) {
                // Log error here since request failed
                Toast.makeText(SelectLanguageAndCities.this, "Server error!!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void fillSpinner(){
        for(int i=0; i<language.getResult().size(); i++) {
            languages.add(language.getResult().get(i).getLanguage());
        }
        languages.add("Select");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, languages){

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
                return (languages.size() - 1); // you dont display last item. It is used as hint.
            }
        };
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
        spinner.setSelection(dataAdapter.getCount());

        mainLayout.setAlpha(1.0f);
        progressBar.setVisibility(View.GONE);
    }

}
