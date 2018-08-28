package com.gsatechworld.gugrify.view.dashboard;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.gsatechworld.gugrify.R;
import com.gsatechworld.gugrify.view.genericadapter.OnRecyclerItemClickListener;

import java.util.ArrayList;

import butterknife.ButterKnife;

public class DashboardActivity extends AppCompatActivity implements OnRecyclerItemClickListener,
        NavigationView.OnNavigationItemSelectedListener {

    private AutoScrollViewPager viewPager;
    private ViewPagerAdapter mAdapter;
    private int dotscount;
    private ImageView[] dots;
    private ImageView iv_place;

  /*  ArrayAdapter<String> mAdapterSearch;
    ListView mListView;*/

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

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            getSupportActionBar().setDisplayShowCustomEnabled(true);
            getSupportActionBar().setCustomView(R.layout.custom_actionbar);
            View view = getSupportActionBar().getCustomView();
        }

        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        //After instantiating your ActionBarDrawerToggle
        toggle.setDrawerIndicatorEnabled(false);
        Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.mipmap.logo1, getTheme());
        toggle.setHomeAsUpIndicator(drawable);

        iv_place = (ImageView)findViewById(R.id.iv_place);
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

        ButterKnife.bind(this);

        pager_indicator = (LinearLayout) findViewById(R.id.viewPagerCountDots);
        viewPager = (AutoScrollViewPager) findViewById(R.id.viewPager);
        viewPager.startAutoScroll();
//        viewPager.setAnimation();
        viewPager.setInterval(5000);
        viewPager.setCycle(true);
        viewPager.setStopScrollWhenTouch(true);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this);

        viewPager.setAdapter(viewPagerAdapter);

        dotscount = viewPagerAdapter.getCount();
        dots = new ImageView[dotscount];

        for (int i = 0; i < dotscount; i++) {

            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(8, 0, 8, 0);

            pager_indicator.addView(dots[i], params);

        }

        dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                for (int i = 0; i < dotscount; i++) {
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_dot));
                }

                dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        allSampleData = new ArrayList<>();

        createDummyData();

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerViewDataAdapterTest adapter = new RecyclerViewDataAdapterTest(allSampleData, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
        ViewCompat.setNestedScrollingEnabled(recyclerView, false);
        //adapter.setItems(allSampleData);

        /*Ashish*/
    /*    mAdapterSearch = new ArrayAdapter<String>(DashboardActivity.this,
                android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.months_array));*/
        //mListView.setAdapter(mAdapterSearch);


        // create nav item list
        ArrayList<NavItemModel> navItemModelArrayList = getNavItemList();

        RecyclerView recycler_nav_item = (RecyclerView) findViewById(R.id.recycler_nav_item);
        recycler_nav_item.setHasFixedSize(true);
        RecyclerViewNavAdapter navAdapter = new RecyclerViewNavAdapter(navItemModelArrayList, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recycler_nav_item.setLayoutManager(layoutManager);
        recycler_nav_item.setAdapter(navAdapter);
        ViewCompat.setNestedScrollingEnabled(recycler_nav_item, false);
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
        playListItemModelArrayList.add(new PlayListItemModel("99 +", "", "aaaaa", R.drawable.food6));
        playListItemModelArrayList.add(new PlayListItemModel("25", "", "bbbbb", R.drawable.food2));
        playListItemModelArrayList.add(new PlayListItemModel("10", "", "ccccc", R.drawable.food7));
        playListItemModelArrayList.add(new PlayListItemModel("70", "", "ddddd", R.drawable.food4));

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

    private void createDummyData() {
//            SectionDataModel dmSingle = new SectionDataModel();
//            ArrayList<SingleItemModel> singleItemModels = new ArrayList<>();
//            dmSingle.setAllItemInSection(singleItemModels);
//            allSampleData.add(dmSingle);
        sectionModel = new SectionDataModel("LATEST NEWS");
        sectionModel.setLatestNewItemModelArrayList(createLatestNews());
        allSampleData.add(sectionModel);

        sectionModel = new SectionDataModel("Play List");
        sectionModel.setPlayListItemModelArrayList(createPlaylistData());
        allSampleData.add(sectionModel);

        sectionModel = new SectionDataModel("AAP leader Ashish Khetan leaves active politics citing plans to pursue law; sources say LS seat could have triggered move", R.drawable.road1);
        allSampleData.add(sectionModel);
        sectionModel = new SectionDataModel("India unlikely to accept foreign donations for flood relief efforts in Kerala, will rely on domestic assistance", R.drawable.road2);
        allSampleData.add(sectionModel);
        sectionModel = new SectionDataModel("CC", R.drawable.road3);
        allSampleData.add(sectionModel);
        sectionModel = new SectionDataModel("DD", R.drawable.road4);
        allSampleData.add(sectionModel);
        sectionModel = new SectionDataModel("EE", R.drawable.road5);
        allSampleData.add(sectionModel);
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

        if (id == R.id.action_search){

           /* TextView tvTitle = (TextView)findViewById(R.id.tvTitle);
            tvTitle.setVisibility(View.GONE);
            EditText et_search = (EditText)findViewById(R.id.et_search);
            et_search.setVisibility(View.VISIBLE);*/

           /* TextView tvTitle = (TextView)toolbar.findViewById(R.id.tvTitle);
            tvTitle.setVisibility(View.GONE);*/

           // Toast.makeText(this, "Ashish", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(DashboardActivity.this,SearchActivity.class));
            return true;
        }
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

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

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
