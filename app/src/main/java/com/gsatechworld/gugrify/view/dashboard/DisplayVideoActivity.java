package com.gsatechworld.gugrify.view.dashboard;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;

import com.gsatechworld.gugrify.R;
import com.gsatechworld.gugrify.SelectLanguageAndCities;
import com.gsatechworld.gugrify.model.retrofit.ApiClient;
import com.gsatechworld.gugrify.model.retrofit.ApiInterface;
import com.gsatechworld.gugrify.model.retrofit.LanguageResponse;
import com.gsatechworld.gugrify.model.retrofit.YoutubeResponsePojo;
import com.gsatechworld.gugrify.view.adapters.YoutubeVideoAdapter;
import com.gsatechworld.gugrify.utils.Constants;
import com.gsatechworld.gugrify.utils.RecyclerViewOnClickListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DisplayVideoActivity extends AppCompatActivity {

    private static final String TAG = DisplayVideoActivity.class.getSimpleName();
    private RecyclerView recyclerView;
    //youtube player fragment
    private YouTubePlayerSupportFragment youTubePlayerFragment;
    private ArrayList<String> youtubeVideoArrayList;
    YoutubeResponsePojo responsePojo = new YoutubeResponsePojo();
    //youtube player to play video when new video selected
    private YouTubePlayer youTubePlayer;
    ArrayList<String> newsHeadline, newsDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_video_youtube);
        newsHeadline = new ArrayList<>();
        newsDescription = new ArrayList<>();
        generateDummyVideoList();

        // clear FLAG_TRANSLUCENT_STATUS flag:
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        // finally change the color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.ststusbar_color2));
        }

        /*For Expandable View*//*
        expandableButton = findViewById(R.id.expandableButton);
        expandableButton.setCallbackListener(new ExpandableView.ExpandableButtonListener() {
            @Override
            public void onViewExpanded() {
               // Toast.makeText(MainActivity.this, "Expanded details", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onViewCollapsed() {
               // Toast.makeText(MainActivity.this, "Collapsed Details", Toast.LENGTH_SHORT).show();
            }
        });*/
    }


    public void childClicked(View view) {

        //((TextView) view).setText("Task Completed (Expandable Button color changed)");
        Toast.makeText(DisplayVideoActivity.this, "Change The Text And Click On Child Data", Toast.LENGTH_SHORT).show();
        // expandableButton.setBarColor(Color.parseColor("#297e55"));
        // expandableButton.childView.setBackgroundColor(getResources().getColor(R.color.cardview_dark_background));

    }

    /**
     * initialize youtube player via Fragment and get instance of YoutubePlayer
     */
    private void initializeYoutubePlayer() {

        youTubePlayerFragment = (YouTubePlayerSupportFragment) getSupportFragmentManager()
                .findFragmentById(R.id.youtube_player_fragment);

        if (youTubePlayerFragment == null)
            return;

        youTubePlayerFragment.initialize(Constants.DEVELOPER_KEY, new YouTubePlayer.OnInitializedListener() {

            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player,
                                                boolean wasRestored) {
                if (!wasRestored) {
                    youTubePlayer = player;

                    //set the player style default
                    youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);

                    //cue the 1st video by default
                    youTubePlayer.cueVideo(youtubeVideoArrayList.get(0));
                }
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider arg0, YouTubeInitializationResult arg1) {

                //print or show error if initialization failed
                Log.e(TAG, "Youtube Player View initialization failed");
            }
        });
    }

    /**
     * setup the recycler view here
     */
    private void setUpRecyclerView() {
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        //Horizontal direction recycler view
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    /**
     * populate the recycler view and implement the click event here
     */
    private void populateRecyclerView() {
        final YoutubeVideoAdapter adapter = new YoutubeVideoAdapter(this, youtubeVideoArrayList, newsHeadline, newsDescription);
        recyclerView.setAdapter(adapter);

        ViewCompat.setNestedScrollingEnabled(recyclerView, false);

        //set click event
        recyclerView.addOnItemTouchListener(new RecyclerViewOnClickListener(this, new RecyclerViewOnClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                if (youTubePlayerFragment != null && youTubePlayer != null) {
                    //update selected position
                    adapter.setSelectedPosition(position);

                    //load selected video
                    youTubePlayer.cueVideo(youtubeVideoArrayList.get(position));
                }

            }
        }));
    }


    /**
     * method to generate dummy array list of videos
     */
    private void generateDummyVideoList() {

        makeYoutubeVideoRequest();


    }

    public void makeYoutubeVideoRequest() {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<YoutubeResponsePojo> call = apiService.makeYoutubeVideoRequest();
        call.enqueue(new Callback<YoutubeResponsePojo>() {
            @Override
            public void onResponse(Call<YoutubeResponsePojo> call, Response<YoutubeResponsePojo> response) {
                if (response.isSuccessful()) {
                    responsePojo = response.body();
                    youtubeVideoArrayList = new ArrayList<>();
                    for (int i = 0; i < responsePojo.getResult().size(); i++) {
                        if (responsePojo.getResult().get(i).getYoutubeLink().trim().equals("")) {

                        } else {
                            youtubeVideoArrayList.add(responsePojo.getResult().get(i).getYoutubeLink());
                            newsHeadline.add(responsePojo.getResult().get(i).getNewsTitle());
                            newsDescription.add(responsePojo.getResult().get(i).getNews_description());
                        }
                    }

                    initializeYoutubePlayer();
                    setUpRecyclerView();
                    populateRecyclerView();
                } else {
                    Toast.makeText(DisplayVideoActivity.this, "Server error!!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<YoutubeResponsePojo> call, Throwable t) {
                // Log error here since request failed
                Toast.makeText(DisplayVideoActivity.this, "Server error!!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
