package com.gsatechworld.gugrify.view.dashboard;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.gsatechworld.gugrify.NewsSharedPreferences;
import com.gsatechworld.gugrify.R;
import com.gsatechworld.gugrify.SelectLanguageAndCities;
import com.gsatechworld.gugrify.model.LatestNewItemModel;
import com.gsatechworld.gugrify.model.NavItemModel;
import com.gsatechworld.gugrify.model.OtherNewsItemModel;
import com.gsatechworld.gugrify.model.PlayListItemModel;
import com.gsatechworld.gugrify.model.SectionDataModel;
import com.gsatechworld.gugrify.model.retrofit.ApiClient;
import com.gsatechworld.gugrify.model.retrofit.ApiInterface;
import com.gsatechworld.gugrify.model.retrofit.City;
import com.gsatechworld.gugrify.model.retrofit.CityResponse;
import com.gsatechworld.gugrify.model.retrofit.GetMainAdvertisement;
import com.gsatechworld.gugrify.model.retrofit.NewsCategories;
import com.gsatechworld.gugrify.model.retrofit.ReporterLogin;
import com.gsatechworld.gugrify.utils.Utility;
import com.gsatechworld.gugrify.view.ActivityShowWebView;
import com.gsatechworld.gugrify.view.DisplayBreakingNewsActivity;
import com.gsatechworld.gugrify.view.ReporterPostActivity;
import com.gsatechworld.gugrify.view.ReporterProfile;
import com.gsatechworld.gugrify.view.adapters.RecyclerViewDataAdapter;
import com.gsatechworld.gugrify.view.adapters.RecyclerViewNavAdapter;
import com.gsatechworld.gugrify.view.adapters.ViewPagerAdapter;
import com.gsatechworld.gugrify.view.authentication.LoginActivity;
import com.gsatechworld.gugrify.view.authentication.ReporterLoginActivity;
import com.gsatechworld.gugrify.view.genericadapter.OnRecyclerItemClickListener;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardActivity extends AppCompatActivity implements OnRecyclerItemClickListener,
        NavigationView.OnNavigationItemSelectedListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnBufferingUpdateListener {

    private AutoScrollViewPager viewPager;
    private EndlessScrollListener scrollListener;
    private ViewPagerAdapter mAdapter;
    private LinearLayoutManager l;
    RecyclerView recyclerView;
    private boolean isFinished = false;
    public static GetMainAdvertisement result;
    private DrawerLayout mainLayout;
    int count = 0;
    private ImageView[] dots;
    private RecyclerViewDataAdapter adapter;
    private ImageView iv_place;
    Dialog dialog;
    public int length;
    ProgressBar progressBar;
    Dialog cancelDialog;
    File file;
    NewsSharedPreferences sharedPreferences;
    ApiInterface apiService;
    public static NewsCategories newsCategories;
    RecyclerViewNavAdapter navAdapter;
    RecyclerView my_recycler_view;

    private MediaPlayer mediaPlayer;

    private MediaController mc;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    private LinearLayout pager_indicator;
    private ArrayList<SectionDataModel> allSampleData;
    private SectionDataModel sectionModel;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        newsCategories = new NewsCategories();

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            getSupportActionBar().setDisplayShowCustomEnabled(true);
            getSupportActionBar().setCustomView(R.layout.custom_actionbar);
            View view = getSupportActionBar().getCustomView();
        }

        progressBar = findViewById(R.id.progressBar);
        sharedPreferences = NewsSharedPreferences.getInstance(this);
        my_recycler_view = findViewById(R.id.my_recycler_view);

        //filling navigation bar
        if(newsCategories.getCategory() == null){
            getReporterCategories();
        }


        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        //After instantiating your ActionBarDrawerToggle
        toggle.setDrawerIndicatorEnabled(true);
        //Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.mipmap.logo1, getTheme());
        //toggle.setHomeAsUpIndicator(drawable);


        iv_place = (ImageView) findViewById(R.id.iv_place);
        iv_place.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(DashboardActivity.this, "Place", Toast.LENGTH_SHORT).show();
            }
        });

        toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawer.isDrawerVisible(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                } else {
                    Log.d("Yes it is", "toggeling");
                    drawer.openDrawer(GravityCompat.START);
                }
            }
        });

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // clear FLAG_TRANSLUCENT_STATUS flag:
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        // finally change the color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.ststusbar_color));
        }

        allSampleData = new ArrayList<>();
        createDummyData();

        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        adapter = new RecyclerViewDataAdapter(allSampleData, this);
        recyclerView.addItemDecoration(new DividerItemDecoration(DashboardActivity.this, 0));
//        adapter.addMoreContacts(allSampleData);
        l = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(l);

        recyclerView.setAdapter(adapter);
        showVideoDialog();
//        recyclerView.addItemDecoration(new DividerItemDecoration(DashboardActivity.this, 0));

        scrollListener = new EndlessScrollListener(l) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                Log.d("Scrolled position is", String.valueOf(view.getVerticalScrollbarPosition()));
//                if(page<3)
//                    loadNextDataFromApi(page);
            }
        };
        // Adds the scroll listener to RecyclerView
        recyclerView.addOnScrollListener(scrollListener);
//        ViewCompat.setNestedScrollingEnabled(recyclerView, false);
        //adapter.setItems(allSampleData);

        /*Ashish*/
    /*    mAdapterSearch = new ArrayAdapter<String>(DashboardActivity.this,
                android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.months_array));*/
        //mListView.setAdapter(mAdapterSearch);

        //check if signed in
        de.hdodenhof.circleimageview.CircleImageView profile_image= findViewById(R.id.profile_image);
        TextView nav_header_title= findViewById(R.id.nav_header_title);
        LinearLayout ll_location = findViewById(R.id.ll_location);

        if(sharedPreferences.getIsLoggedIn()){
            profile_image.setVisibility(View.VISIBLE);
            ll_location.setVisibility(View.VISIBLE);
        }
        else{
            profile_image.setVisibility(View.INVISIBLE);
            ll_location.setVisibility(View.INVISIBLE);
            nav_header_title.setText("Welcome");
        }

        // create nav item list
        ArrayList<NavItemModel> navItemModelArrayList = getNavItemList();

        recyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));

    }

    private ArrayList<NavItemModel> getNavItemList() {
        ArrayList<NavItemModel> navItemModelList = new ArrayList<>();
        navItemModelList.add(new NavItemModel(R.drawable.ic_menu_orange, "Breaking news 1",
                "8"));
        navItemModelList.add(new NavItemModel(R.drawable.ic_menu_orange, "Breaking news 1",
                "8"));
        navItemModelList.add(new NavItemModel(R.drawable.ic_menu_orange, "Breaking news 1",
                "8"));
        navItemModelList.add(new NavItemModel(R.drawable.ic_menu_orange, "Breaking news 1",
                "8"));
        navItemModelList.add(new NavItemModel(R.drawable.ic_menu_orange, "Breaking news 1",
                "8"));
        navItemModelList.add(new NavItemModel(R.drawable.ic_menu_orange, "Breaking news 1",
                "8"));
        navItemModelList.add(new NavItemModel(R.drawable.ic_menu_orange, "Breaking news 1",
                "8"));
        navItemModelList.add(new NavItemModel(R.drawable.ic_menu_orange, "Breaking news 1",
                "8"));
        return navItemModelList;
    }

    private ArrayList<PlayListItemModel> createPlaylistData() {
        ArrayList<PlayListItemModel> playListItemModelArrayList = new ArrayList<>();
        playListItemModelArrayList.add(new PlayListItemModel("99 +", "", "aaaaa", R.drawable.road1));
//        playListItemModelArrayList.add(new PlayListItemModel("25", "", "bbbbb", R.drawable.food2));
//        playListItemModelArrayList.add(new PlayListItemModel("10", "", "ccccc", R.drawable.food7));
        playListItemModelArrayList.add(new PlayListItemModel(null, null, null, R.drawable.ic_add));

        return playListItemModelArrayList;
    }

    private ArrayList<LatestNewItemModel> createLatestNews() {
        ArrayList<LatestNewItemModel> latestNewItemModelArrayList = new ArrayList<>();
        latestNewItemModelArrayList.add(new LatestNewItemModel("Usa", "", "aaaaa", R.drawable.country1));
        latestNewItemModelArrayList.add(new LatestNewItemModel("India", "", "bbbbb", R.drawable.country6));
        latestNewItemModelArrayList.add(new LatestNewItemModel("Canada", "", "ccccc", R.drawable.country7));
        latestNewItemModelArrayList.add(new LatestNewItemModel("Japan", "", "ddddd", R.drawable.country4));
        latestNewItemModelArrayList.add(new LatestNewItemModel("China", "", "eeeee", R.drawable.contry8));

        return latestNewItemModelArrayList;
    }

    private ArrayList<OtherNewsItemModel> createOtherNews() {
        ArrayList<OtherNewsItemModel> otherNewsItemModelArrayList = new ArrayList<>();
        otherNewsItemModelArrayList.add(new OtherNewsItemModel("AAAA", "", "AAAA", R.drawable.road1));
        otherNewsItemModelArrayList.add(new OtherNewsItemModel("BB", "", "BBBB", R.drawable.road2));
        otherNewsItemModelArrayList.add(new OtherNewsItemModel("CC", "", "CCCC", R.drawable.road3));
        otherNewsItemModelArrayList.add(new OtherNewsItemModel("DD", "", "DDDD", R.drawable.road4));
        otherNewsItemModelArrayList.add(new OtherNewsItemModel("EE", "", "EEEE", R.drawable.road5));

        return otherNewsItemModelArrayList;
    }

    /* For Video*/
    public void showVideoDialog() {

        dialog = new Dialog(DashboardActivity.this, R.style.NewDialog);//android.R.style.Theme_Dialog //android.R.style.Theme_Black_NoTitleBar_Fullscreen
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.video_dialog);
        // dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        //dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);

        // dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
//        ImageView iv_close = (ImageView)dialog.findViewById(R.id.iv_close);
        ImageView dialogImage = dialog.findViewById(R.id.iv_ad);
        TextView dialogText1 = dialog.findViewById(R.id.dialogText1);
        TextView dialogText2 = dialog.findViewById(R.id.dialogText2);
        Button dialogUrlButton = dialog.findViewById(R.id.redirectButton);

        dialog.show();

        if(result != null) {
            Glide.with(DashboardActivity.this).load(result.getImage()).into(dialogImage);
            dialogUrlButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(DashboardActivity.this, ActivityShowWebView.class);
                    intent.putExtra("url", result.getUrl());
                    startActivity(intent);
                }
            });
            dialogText1.setText(result.getText1());
            dialogText2.setText(result.getText2());
        }
        else{
            dialog.cancel();
        }

        if (result != null) {
            if (result.getAudio().trim().isEmpty()) {
                dialog.setCancelable(true);
                dialog.setCanceledOnTouchOutside(true);
            } else {
                if (dialog.isShowing()) {
                    mediaPlayer = new MediaPlayer();
                    new PlayMainAdAsync().execute(result.getAudio());
                    mediaPlayer.setOnBufferingUpdateListener(DashboardActivity.this);
                    mediaPlayer.setOnCompletionListener(DashboardActivity.this);
                }
            }
        } else {
            dialog.cancel();
        }
    }

    private void createDummyData() {

        SectionDataModel sectionModel = new SectionDataModel();
        allSampleData.add(sectionModel);

        SectionDataModel sectionModel2 = new SectionDataModel("LATEST NEWS");
        sectionModel2.setLatestNewItemModelArrayList(createLatestNews());
        allSampleData.add(sectionModel2);

        SectionDataModel sectionModel3 = new SectionDataModel("PLAY LIST");
        sectionModel3.setPlayListItemModelArrayList(createPlaylistData());
        allSampleData.add(sectionModel3);

        SectionDataModel sectionModel4 = new SectionDataModel("AAP leader Ashish Khetan leaves active politics citing plans to pursue law; sources say LS seat could have triggered move", R.drawable.road1);
        allSampleData.add(sectionModel4);
        SectionDataModel sectionModel5 = new SectionDataModel("India unlikely to accept foreign donations for flood relief efforts in Kerala, will rely on domestic assistance", R.drawable.road2);
        allSampleData.add(sectionModel5);
        SectionDataModel sectionModel6 = new SectionDataModel("CC", R.drawable.road3);
        allSampleData.add(sectionModel6);
        SectionDataModel sectionModel7 = new SectionDataModel("DD", R.drawable.road4);
        allSampleData.add(sectionModel7);
        SectionDataModel sectionModel8 = new SectionDataModel("EE", R.drawable.road5);
        allSampleData.add(sectionModel8);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_dashboard, menu);

     /*   MenuItem mSearch = menu.findItem(R.id.action_search);

        SearchView mSearchView = (SearchView) mSearch.getActionView();
        mSearchView.setQueryHint("Search");

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mAdapterSearch.getFilter().filter(newText);
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }
*/
        /*SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.menu.menu_dashboard).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));*/

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_search) {

           /* TextView tvTitle = (TextView)findViewById(R.id.tvTitle);
            tvTitle.setVisibility(View.GONE);
            EditText et_search = (EditText)findViewById(R.id.et_search);
            et_search.setVisibility(View.VISIBLE);*/

           /* TextView tvTitle = (TextView)toolbar.findViewById(R.id.tvTitle);
            tvTitle.setVisibility(View.GONE);*/

            // Toast.makeText(this, "Ashish", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(DashboardActivity.this, SearchActivity.class));
            return true;
        }
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == R.id.action_avatar) {
            Intent intent = new Intent(DashboardActivity.this, ReporterLoginActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!isFinished) {
            if(!dialog.isShowing()) {
                dialog.show();
                mediaPlayer.seekTo(length);
                mediaPlayer.start();
            }
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Intent homeIntent = new Intent(Intent.ACTION_MAIN);
            homeIntent.addCategory(Intent.CATEGORY_HOME);
            homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(homeIntent);
            finish();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onItemClick(int position) {

    }

    public void loadNextDataFromApi(int page) {
        Log.d("Load number", String.valueOf(count++));
        l.scrollToPosition(3);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onBufferingUpdate(MediaPlayer mediaPlayer, int i) {

    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        Log.d("Media completed", "True");
        dialog.cancel();
        isFinished = true;
    }

    @Override
    public void onStop() {
        super.onStop();
        dialog.cancel();
        Log.d("Entered", "OnStop");
        length = mediaPlayer.getCurrentPosition();
        mediaPlayer.pause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dialog.cancel();
        mediaPlayer.stop();
    }

    class PlayMainAdAsync extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            cancelDialog = Utility.showWaitDialog(DashboardActivity.this);
            cancelDialog.show();
        }

        @Override
        protected String doInBackground(String... aurl) {
            int count;
            try {
                mediaPlayer.setDataSource(aurl[0]);
                mediaPlayer.prepare();
            } catch (Exception e) {
                Log.d("Exception is", e.toString());
                dialog.setCanceledOnTouchOutside(true);
                dialog.setCancelable(true);
            }
            return null;
        }

        protected void onProgressUpdate(String... progress) {
            Log.d("ANDRO_ASYNC", progress[0]);
        }

        @Override
        protected void onPostExecute(String unused) {
            mediaPlayer.start();
            cancelDialog.cancel();
        }
    }

    public void getReporterCategories(){

        my_recycler_view.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);

        apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<NewsCategories> call = apiService.getCategoryList();

        call.enqueue(new Callback<NewsCategories>() {
            @Override
            public void onResponse(Call<NewsCategories> call, Response<NewsCategories> response) {

                if (response.isSuccessful()) {
                    Log.d("Reached here", "to dashboard");
                    newsCategories = response.body();
                    RecyclerView recycler_nav_item = (RecyclerView) findViewById(R.id.recycler_nav_item);
                    recycler_nav_item.setHasFixedSize(true);
                    navAdapter = new RecyclerViewNavAdapter(newsCategories, DashboardActivity.this);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(DashboardActivity.this, LinearLayoutManager.VERTICAL, false);
                    recycler_nav_item.setLayoutManager(layoutManager);
                    recycler_nav_item.setAdapter(navAdapter);
                    ViewCompat.setNestedScrollingEnabled(recycler_nav_item, false);

                    my_recycler_view.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                } else {
                    Toast.makeText(DashboardActivity.this, "Server error!!", Toast.LENGTH_SHORT).show();
                    my_recycler_view.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<NewsCategories> call, Throwable t) {
                // Log error here since request failed
                Toast.makeText(DashboardActivity.this, "Server error!!", Toast.LENGTH_SHORT).show();
                my_recycler_view.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }
        });
    }

}
