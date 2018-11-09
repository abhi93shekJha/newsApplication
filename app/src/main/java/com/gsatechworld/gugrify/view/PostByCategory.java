package com.gsatechworld.gugrify.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.gsatechworld.gugrify.R;
import com.gsatechworld.gugrify.model.PostsByCategory;
import com.gsatechworld.gugrify.model.retrofit.ApiClient;
import com.gsatechworld.gugrify.model.retrofit.ApiInterface;
import com.gsatechworld.gugrify.model.retrofit.CategoryPosts;
import com.gsatechworld.gugrify.model.retrofit.GetMainAdvertisement;
import com.gsatechworld.gugrify.view.adapters.PostsByCategoryAdapter;
import com.gsatechworld.gugrify.view.dashboard.DashboardActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostByCategory extends AppCompatActivity {

    RecyclerView post_by_category_recycler;
    Toolbar toolbar;
    ApiInterface apiService;
    String category;
    ProgressBar progressBar;
    TextView noNewsTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_by_category);

        progressBar = findViewById(R.id. progressBar);
        noNewsTextView = findViewById(R.id.noNewsTextView);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Gugrify");
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));
        setSupportActionBar(toolbar);

        category = getIntent().getStringExtra("category");

        post_by_category_recycler = findViewById(R.id.post_by_category_recycler);
        makeGetRequest();

    }

    void makeGetRequest(){

        post_by_category_recycler.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);

        apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<CategoryPosts> call = apiService.getPostByCategory(category);

        call.enqueue(new Callback<CategoryPosts>() {
            @Override
            public void onResponse(Call<CategoryPosts> call, Response<CategoryPosts> response) {
                CategoryPosts posts = null;
                if (response.isSuccessful()) {

                    Log.d("Reached here", "to getting postsByCategory");
                    posts = response.body();
                    PostsByCategoryAdapter adapter = new PostsByCategoryAdapter(PostByCategory.this, posts, category);
                    post_by_category_recycler.setLayoutManager(new LinearLayoutManager(PostByCategory.this));
                    post_by_category_recycler.setAdapter(adapter);

                    post_by_category_recycler.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);

                    if(posts.getResult() == null){
                        noNewsTextView.setVisibility(View.VISIBLE);
                    }

                } else {
                    Toast.makeText(PostByCategory.this, "Server error!!", Toast.LENGTH_LONG).show();
                    post_by_category_recycler.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<CategoryPosts> call, Throwable t) {
                // Log error here since request failed
                Toast.makeText(PostByCategory.this, "Server error!!", Toast.LENGTH_LONG).show();
                post_by_category_recycler.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}
