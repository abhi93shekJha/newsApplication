package com.gsatechworld.gugrify.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.gsatechworld.gugrify.NewsSharedPreferences;
import com.gsatechworld.gugrify.R;
import com.gsatechworld.gugrify.model.PostsByCategory;
import com.gsatechworld.gugrify.model.retrofit.ApiClient;
import com.gsatechworld.gugrify.model.retrofit.ApiInterface;
import com.gsatechworld.gugrify.model.retrofit.ReporterLogin;
import com.gsatechworld.gugrify.model.retrofit.ReporterPostById;
import com.gsatechworld.gugrify.view.adapters.ReporterProfileRecyclerAdapter;
import com.gsatechworld.gugrify.view.authentication.ReporterLoginActivity;
import com.gsatechworld.gugrify.view.dashboard.DashboardActivity;
import com.gsatechworld.gugrify.view.dashboard.SearchActivity;

import org.w3c.dom.Text;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReporterProfile extends AppCompatActivity {
    FloatingActionButton addNewPost;
    ImageView profileSearch, iv_contactUs;
    private Toolbar searchtollbar;
    private Toolbar toolbar;
    private Menu search_menu;
    private MenuItem item_search;
    private ArrayList<PostsByCategory> posts;
    private ReporterProfileRecyclerAdapter adapter;
    private LinearLayout linearLayoutProfile, linearLayoutPost;
    private CardView reporter_below_layout;
    private FloatingActionButton languageAndCityFloating;
    private SearchView searchView;
    NewsSharedPreferences sharedPreferences;
    ApiInterface apiService;
    List<ReporterPostById.Result> results;
    RelativeLayout main_layout;
    ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       // getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);

        setContentView(R.layout.activity_reporter_profile);
        results = new ArrayList<>();
        main_layout = findViewById(R.id.main_layout);
        progressBar = findViewById(R.id.progressBar);
        iv_contactUs = findViewById(R.id.iv_contactUs);

        iv_contactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ReporterProfile.this, ActivityContactUs.class);
                startActivity(intent);
            }
        });

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        sharedPreferences = NewsSharedPreferences.getInstance(ReporterProfile.this);

        setSearchtollbar();
//        getActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.actionbar_bg));

        linearLayoutProfile = (LinearLayout) findViewById(R.id.linearLayoutProfile);
        linearLayoutPost = (LinearLayout) findViewById(R.id.linearLayoutPost);
        reporter_below_layout = (CardView)findViewById(R.id.reporter_below_layout);
        languageAndCityFloating =(FloatingActionButton) findViewById(R.id.languageAndCityFloating);

        makePostRequest(sharedPreferences.getSharedPrefValue("reporterId"));

        CircleImageView reporterImage = findViewById(R.id.reporterImage);
        TextView reporterName = findViewById(R.id.reporterName);
        TextView tv_reporter_place = findViewById(R.id.tv_reporter_place);
        TextView tv_total_ads = findViewById(R.id.tv_total_ads);
        TextView tv_total_posts = findViewById(R.id.tv_total_posts);
        ImageView reporter_sign_out = findViewById(R.id.reporter_sign_out);
        ImageView contactUs = findViewById(R.id.iv_contactUs);
        ImageView iv_home = findViewById(R.id.iv_home);

        Glide.with(ReporterProfile.this).load(sharedPreferences.getSharedPrefValue("reporterPic")).into(reporterImage);
        reporterName.setText(sharedPreferences.getSharedPrefValue("reporterName"));
        tv_reporter_place.setText(sharedPreferences.getSharedPrefValue("reporterCity"));
        String totalAds = sharedPreferences.getSharedPrefValue("reporterAdsCount");
        String totalPosts = sharedPreferences.getSharedPrefValue("reporterPostsCount");
        if(totalAds.length() == 1)
            totalAds = "0"+totalAds;
        if(totalPosts.length() == 1)
            totalPosts = "0"+totalPosts;
        tv_total_ads.setText(totalAds);
        tv_total_posts.setText(totalPosts);

        reporter_sign_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(ReporterProfile.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Sign Out")
                        .setMessage("Are you sure you want to sign out?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                sharedPreferences.setSharedPrefValueBoolean("reporterLoggedIn", false);
                                finish();
                            }

                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        });

        iv_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ReporterProfile.this, DashboardActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // clear FLAG_TRANSLUCENT_STATUS flag:
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        // finally change the color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.profile_pic_background_dark));
        }

        addNewPost = findViewById(R.id.languageAndCityFloating);
        addNewPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ReporterProfile.this, ReporterPostActivity.class));
            }
        });
    }

    private void setSearchtollbar() {
        {
            searchtollbar = (Toolbar) findViewById(R.id.searchtoolbar);
            if (searchtollbar != null) {
                searchtollbar.inflateMenu(R.menu.menu_search);
                search_menu = searchtollbar.getMenu();

                searchtollbar.setNavigationOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        linearLayoutPost.setVisibility(View.GONE);
                        linearLayoutProfile.setVisibility(View.GONE);
                        languageAndCityFloating.setVisibility(View.GONE);
                        reporter_below_layout.setVisibility(View.GONE);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                            circleReveal(R.id.searchtoolbar, 1, true, false);
                        else
                            searchtollbar.setVisibility(View.GONE);
                    }
                });

                item_search = search_menu.findItem(R.id.action_filter_search);

                MenuItemCompat.setOnActionExpandListener(item_search, new MenuItemCompat.OnActionExpandListener() {
                    @Override
                    public boolean onMenuItemActionCollapse(MenuItem item) {
                        // Do something when collapsed
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            circleReveal(R.id.searchtoolbar, 1, true, false);
                            linearLayoutPost.setVisibility(View.VISIBLE);
                            linearLayoutProfile.setVisibility(View.VISIBLE);
                            languageAndCityFloating.setVisibility(View.VISIBLE);
                            reporter_below_layout.setVisibility(View.VISIBLE);
                        } else
                            searchtollbar.setVisibility(View.GONE);
                        return true;
                    }

                    @Override
                    public boolean onMenuItemActionExpand(MenuItem item) {
                        // Do something when expanded
                        linearLayoutPost.setVisibility(View.GONE);
                        linearLayoutProfile.setVisibility(View.GONE);
                        languageAndCityFloating.setVisibility(View.GONE);
                        reporter_below_layout.setVisibility(View.GONE);
                        return true;
                    }
                });

                initSearchView();


            } else
                Log.d("toolbar", "setSearchtollbar: NULL");
        }
    }

    public void initSearchView()
    {
        searchView =
                (SearchView) search_menu.findItem(R.id.action_filter_search).getActionView();

        // Enable/Disable Submit button in the keyboard

        searchView.setSubmitButtonEnabled(false);

        // Change search close button image

        ImageView closeButton = (ImageView) searchView.findViewById(R.id.search_close_btn);
        closeButton.setImageResource(R.drawable.ic_clear);


        // set hint and the text colors

        EditText txtSearch = ((EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text));
        txtSearch.setHint("Search..");
        txtSearch.setHintTextColor(Color.DKGRAY);
        txtSearch.setTextColor(getResources().getColor(R.color.colorPrimary));


        // set the cursor

        AutoCompleteTextView searchTextView = (AutoCompleteTextView) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        try {
            Field mCursorDrawableRes = TextView.class.getDeclaredField("mCursorDrawableRes");
            mCursorDrawableRes.setAccessible(true);
            mCursorDrawableRes.set(searchTextView, R.drawable.search_cursor); //This sets the cursor resource ID to 0 or @null which will make it visible on white background
        } catch (Exception e) {
            e.printStackTrace();
        }

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                callSearch(query);
                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                callSearch(newText);
                return true;
            }

            public void callSearch(String query) {
                //Do searching
                Log.i("query", "" + query);

                List<ReporterPostById.Result> temp = new ArrayList();
                for(ReporterPostById.Result d: results){
                    //or use .equal(text) with you want equal match
                    //use .toLowerCase() for better matches
                    if(d.getPostHeading().toLowerCase().contains(query.toLowerCase())){
                        temp.add(d);
                    }
                }
                //update recyclerview
                adapter.updateList(temp);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void circleReveal(int viewID, int posFromRight, boolean containsOverflow, final boolean isShow)
    {
        final View myView = findViewById(viewID);

        int width=myView.getWidth();

        if(posFromRight>0)
            width-=(posFromRight*getResources().getDimensionPixelSize(R.dimen.abc_action_button_min_width_material))-(getResources().getDimensionPixelSize(R.dimen.abc_action_button_min_width_material)/ 2);
        if(containsOverflow)
            width-=getResources().getDimensionPixelSize(R.dimen.abc_action_button_min_width_overflow_material);

        int cx=width;
        int cy=myView.getHeight()/2;

        Animator anim;
        if(isShow)
            anim = ViewAnimationUtils.createCircularReveal(myView, cx, cy, 0,(float)width);
        else
            anim = ViewAnimationUtils.createCircularReveal(myView, cx, cy, (float)width, 0);

        anim.setDuration((long)220);

        // make the view invisible when the animation is done
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if(!isShow)
                {
                    super.onAnimationEnd(animation);
                    myView.setVisibility(View.INVISIBLE);
                }
            }
        });

        // make the view visible and start the animation
        if(isShow)
            myView.setVisibility(View.VISIBLE);

        // start the animation
        anim.start();


    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_search:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                    circleReveal(R.id.searchtoolbar,1,true,true);
                else
                    searchtollbar.setVisibility(View.VISIBLE);

                item_search.expandActionView();
                return true;
            case R.id.action_settings:
                Toast.makeText(this, "Home Settings Click", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void makePostRequest(String reporterId){

        main_layout.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);

        apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ReporterPostById> call = apiService.getReporterPostById(reporterId);

        call.enqueue(new Callback<ReporterPostById>() {
            @Override
            public void onResponse(Call<ReporterPostById> call, Response<ReporterPostById> response) {
                ReporterPostById reporterProfile = null;
                if (response.isSuccessful()) {
                    reporterProfile = response.body();
                    results = reporterProfile.getResult();
                    Log.d("Reached here", "to getting posts");
                    RecyclerView recyclerView = findViewById(R.id.reporter_profile_recycler);
                    adapter = new ReporterProfileRecyclerAdapter(ReporterProfile.this, results);
                    recyclerView.setLayoutManager(new LinearLayoutManager(ReporterProfile.this, LinearLayoutManager.VERTICAL, false));
                    recyclerView.setAdapter(adapter);

                    main_layout.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);

                } else {
                    Toast.makeText(ReporterProfile.this, "Server error!! Try again.", Toast.LENGTH_LONG).show();
                    main_layout.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<ReporterPostById> call, Throwable t) {
                // Log error here since request failed
                Toast.makeText(ReporterProfile.this, "Server error!! Try again.", Toast.LENGTH_LONG).show();
                main_layout.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }
        });
    }

}
