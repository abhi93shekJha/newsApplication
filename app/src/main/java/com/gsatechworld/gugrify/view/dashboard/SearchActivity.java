package com.gsatechworld.gugrify.view.dashboard;

import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.gsatechworld.gugrify.R;
import com.gsatechworld.gugrify.model.NewsListHolder;
import com.gsatechworld.gugrify.view.adapters.SearchRecyclerAdapter;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {
    Toolbar mToolbar;
    ArrayList<NewsListHolder> arrayList = new ArrayList<>();
    SearchRecyclerAdapter adapter;
    RecyclerView rView;
    String reporterId = "";
//    ImageView iv_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        rView = (RecyclerView) findViewById(R.id.searchNewsList);
        setData();
        adapter = new SearchRecyclerAdapter(arrayList, SearchActivity.this);
        LinearLayoutManager l = new LinearLayoutManager(this);
        rView.setLayoutManager(l);
        rView.setAdapter(adapter);
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

        SearchView mSearchView = (SearchView) mSearch.getActionView();
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
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                adapter.getFilter().filter(newText);
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

    private void setData(){
        NewsListHolder holder = new NewsListHolder();
        holder.setImage("https://i.ytimg.com/vi/icqDxNab3Do/maxresdefault.jpg");
        holder.setPostHeading("News Post Headlines goes here");
        holder.setDescription("In broadcasting, News Reporters are filmed or recorded presenting the news. They may be recorded in a studio or in the field, depending on the story. They usually read off of a script, but also must be knowledgeable about the topic that they are reporting on.");
        holder.setLikes("29");
        holder.setViews("18 views");
        arrayList.add(holder);
        arrayList.add(holder);
        arrayList.add(holder);
        arrayList.add(holder);
        arrayList.add(holder);
        arrayList.add(holder);
        arrayList.add(holder);
        arrayList.add(holder);
    }
}
