package com.gsatechworld.gugrify.view.dashboard;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.gsatechworld.gugrify.R;
import com.gsatechworld.gugrify.view.genericadapter.OnRecyclerItemClickListener;

import java.util.ArrayList;

import butterknife.ButterKnife;

public class DashboardActivity extends AppCompatActivity implements OnRecyclerItemClickListener {

    private AutoScrollViewPager viewPager;
    private ViewPagerAdapter mAdapter;
    private int dotscount;
    private ImageView[] dots;

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }
    private LinearLayout pager_indicator;
    private ArrayList<SectionDataModel> allSampleData;
    private SectionDataModel sectionModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        ButterKnife.bind(this);

        pager_indicator = (LinearLayout) findViewById(R.id.viewPagerCountDots);
        viewPager = (AutoScrollViewPager) findViewById(R.id.viewPager);
        viewPager.startAutoScroll();
        viewPager.setInterval(3000);
        viewPager.setCycle(true);
        viewPager.setStopScrollWhenTouch(true);


        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this);

        viewPager.setAdapter(viewPagerAdapter);

        dotscount = viewPagerAdapter.getCount();
        dots = new ImageView[dotscount];

        for(int i = 0; i < dotscount; i++){

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

                for(int i = 0; i< dotscount; i++){
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
    }

    private ArrayList<PlayListItemModel> createPlaylistData(){
        ArrayList<PlayListItemModel> playListItemModelArrayList = new ArrayList<>();
        playListItemModelArrayList.add(new PlayListItemModel("A", "", "aaaaa", R.drawable.food1));
        playListItemModelArrayList.add(new PlayListItemModel("B", "", "bbbbb", R.drawable.food2));
        playListItemModelArrayList.add(new PlayListItemModel("C", "", "ccccc", R.drawable.food3));
        playListItemModelArrayList.add(new PlayListItemModel("D", "", "ddddd", R.drawable.food4));

        return playListItemModelArrayList;
    }

    private ArrayList<LatestNewItemModel> createLatestNews(){
        ArrayList<LatestNewItemModel> latestNewItemModelArrayList = new ArrayList<>();
        latestNewItemModelArrayList.add(new LatestNewItemModel("1", "", "aaaaa", R.drawable.fruit1));
        latestNewItemModelArrayList.add(new LatestNewItemModel("2", "", "bbbbb", R.drawable.fruit2));
        latestNewItemModelArrayList.add(new LatestNewItemModel("3", "", "ccccc", R.drawable.fruit3));
        latestNewItemModelArrayList.add(new LatestNewItemModel("4", "", "ddddd", R.drawable.fruit4));
        latestNewItemModelArrayList.add(new LatestNewItemModel("5", "", "eeeee", R.drawable.fruit5));

        return latestNewItemModelArrayList;
    }

    private ArrayList<OtherNewsItemModel> createOtherNews(){
        ArrayList<OtherNewsItemModel> otherNewsItemModelArrayList = new ArrayList<>();
        otherNewsItemModelArrayList.add(new OtherNewsItemModel("AA", "", "AAAA", R.drawable.road1));
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
            sectionModel = new SectionDataModel("AA", R.drawable.road1);
            allSampleData.add(sectionModel);
            sectionModel = new SectionDataModel("BB", R.drawable.road2);
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
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(int position) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
