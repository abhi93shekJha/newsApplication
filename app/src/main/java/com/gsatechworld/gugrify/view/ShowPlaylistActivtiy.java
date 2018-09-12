package com.gsatechworld.gugrify.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.gsatechworld.gugrify.R;
import com.gsatechworld.gugrify.view.genericadapter.PlayListRecyclerAdapter;

public class ShowPlaylistActivtiy extends AppCompatActivity {
    RecyclerView recyclerView;
    Toolbar toolbar;
    protected void onCreate(Bundle savedInstaceState){
        super.onCreate(savedInstaceState);
        setContentView(R.layout.activity_show_playlist);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.playListRecycler);
        recyclerView.setHasFixedSize(true);
        PlayListRecyclerAdapter adapter = new PlayListRecyclerAdapter(this);
//        adapter.addMoreContacts(allSampleData);
        LinearLayoutManager l = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(l);

        recyclerView.setAdapter(adapter);
    }
}
