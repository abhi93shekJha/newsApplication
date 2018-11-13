package com.gsatechworld.gugrify.view.dashboard;

import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.gsatechworld.gugrify.NewsSharedPreferences;
import com.gsatechworld.gugrify.R;
import com.gsatechworld.gugrify.SelectLanguageAndCities;
import com.gsatechworld.gugrify.model.NewsListHolder;
import com.gsatechworld.gugrify.model.retrofit.ApiClient;
import com.gsatechworld.gugrify.model.retrofit.ApiInterface;
import com.gsatechworld.gugrify.model.retrofit.City;
import com.gsatechworld.gugrify.model.retrofit.CityResponse;
import com.gsatechworld.gugrify.model.retrofit.HeadlineSearchPojo;
import com.gsatechworld.gugrify.view.adapters.SearchRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {
    Toolbar mToolbar;
    SearchRecyclerAdapter adapter;
    RecyclerView rView;
    HeadlineSearchPojo headlines;
    ProgressBar progressBar;
    TextView noPostsTitle;
    SearchView mSearchView;
    NewsSharedPreferences sharedPreferences;
    Set<String> stringList, postLists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        noPostsTitle = findViewById(R.id.noPostsTitle);
        progressBar = findViewById(R.id.progressBar);
        sharedPreferences = NewsSharedPreferences.getInstance(SearchActivity.this);

        headlines = new HeadlineSearchPojo();
        stringList = sharedPreferences.getSearchedStringList();
        postLists = sharedPreferences.getPostIdsSet();

        rView = (RecyclerView) findViewById(R.id.searchNewsList);
        adapter = new SearchRecyclerAdapter(stringList, postLists, headlines, SearchActivity.this);
        LinearLayoutManager l = new LinearLayoutManager(SearchActivity.this);
        rView.setLayoutManager(l);
        rView.setAdapter(adapter);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
//
//        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Toast.makeText(SearchActivity.this, adapterView.getItemAtPosition(i).toString(), Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        mListView.setEmptyView(mEmptyView);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_dashboard, menu);

        MenuItem mSearch = menu.findItem(R.id.action_search1);

        menu.findItem(R.id.action_search1).expandActionView();

        mSearchView = (SearchView) mSearch.getActionView();
        mSearchView.setQueryHint("Search");

        MenuItemCompat.setOnActionExpandListener(mSearch, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                finish();
                return true;
            }
        });

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                getSearchedResult(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d("came here", "to query");
                if(newText.trim().length() >= 3){
                    getSearchedResult(newText.trim());
                }
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onPrepareOptionsMenu (Menu menu) {
            menu.findItem(R.id.action_search).setEnabled(false);
            menu.findItem(R.id.action_search).setVisible(false);
            menu.findItem(R.id.action_search1).setEnabled(true);
            menu.findItem(R.id.action_search1).setVisible(true);
            // You can also use something like:
            // menu.findItem(R.id.example_foobar).setEnabled(false);
        return true;
    }

    public void getSearchedResult(String query){

        rView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        mSearchView.setFocusable(false);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<HeadlineSearchPojo> call = apiService.getSearchedHeadlines(query);

        call.enqueue(new Callback<HeadlineSearchPojo>() {
            @Override
            public void onResponse(Call<HeadlineSearchPojo> call, Response<HeadlineSearchPojo> response) {
                if (response.isSuccessful()) {
                    headlines = response.body();
                    if(headlines.getResult() == null){
                        noPostsTitle.setVisibility(View.VISIBLE);
                        rView.setVisibility(View.GONE);
                        progressBar.setVisibility(View.GONE);
                    }
                    else {
                        noPostsTitle.setVisibility(View.GONE);
                        rView.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                        adapter = new SearchRecyclerAdapter(stringList, postLists, headlines, SearchActivity.this);
                        LinearLayoutManager l = new LinearLayoutManager(SearchActivity.this);
                        rView.setLayoutManager(l);
                        rView.setAdapter(adapter);
                    }
                    mSearchView.setFocusable(true);
                }
                else {
                    noPostsTitle.setVisibility(View.GONE);
                    rView.setVisibility(View.GONE);
                    progressBar.setVisibility(View.GONE);
                    mSearchView.setFocusable(true);
                }
//                Log.d(TAG, "Number of movies received: " + movies.size());
            }

            @Override
            public void onFailure(Call<HeadlineSearchPojo> call, Throwable t) {
                noPostsTitle.setVisibility(View.GONE);
                rView.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
                mSearchView.setFocusable(true);
            }
        });
    }
}
