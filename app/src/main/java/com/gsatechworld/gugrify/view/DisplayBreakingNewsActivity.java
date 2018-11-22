package com.gsatechworld.gugrify.view;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.gsatechworld.gugrify.NewsSharedPreferences;
import com.gsatechworld.gugrify.R;
import com.gsatechworld.gugrify.SelectLanguageAndCities;
import com.gsatechworld.gugrify.fragment.FragmentImage;
import com.gsatechworld.gugrify.fragment.FragmentLayout;
import com.gsatechworld.gugrify.model.PostsByCategory;
import com.gsatechworld.gugrify.model.PostsForLandscape;
import com.gsatechworld.gugrify.model.ScrollNewsPojo;
import com.gsatechworld.gugrify.model.TopNewsPojo;
import com.gsatechworld.gugrify.model.retrofit.ApiClient;
import com.gsatechworld.gugrify.model.retrofit.ApiInterface;
import com.gsatechworld.gugrify.model.retrofit.City;
import com.gsatechworld.gugrify.model.retrofit.CityResponse;
import com.gsatechworld.gugrify.model.retrofit.CityWiseAdvertisement;
import com.gsatechworld.gugrify.model.retrofit.GetMainAdvertisement;
import com.gsatechworld.gugrify.model.retrofit.GetScrollNewsAndBNPojo;
import com.gsatechworld.gugrify.model.retrofit.PostDetailPojo;
import com.gsatechworld.gugrify.model.retrofit.TwentyPostsByCategory;
import com.gsatechworld.gugrify.model.retrofit.ViewPojo;
import com.gsatechworld.gugrify.utils.Utility;
import com.gsatechworld.gugrify.view.adapters.BreakingNewsRecyclerAdapter;
import com.gsatechworld.gugrify.view.adapters.BreakingNewsViewPagerAdapter;
import com.gsatechworld.gugrify.view.dashboard.AutoScrollViewPager;
import com.gsatechworld.gugrify.view.dashboard.DashboardActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DisplayBreakingNewsActivity extends AppCompatActivity implements MediaPlayer.OnCompletionListener, MediaPlayer.OnBufferingUpdateListener {
    public ArrayList<PostsByCategory> posts = new ArrayList<>();
    Animation zoomIn, animFadeIn, animFadeOut, animFadeIn1;
    AutoScrollViewPager viewPager;
    BreakingNewsRecyclerAdapter adapter;
    RecyclerView recycler, comments_recycler;
    FrameLayout frameLayoutTextAnimation, frameLayoutViewPager;
    ImageView pause, play, pauseView, playView;
    public static LinearLayout pausePlayLayout;
    NewsSharedPreferences sharedPreferences;
    List<CityWiseAdvertisement.Result> results;
    TextView textView;
    ApiInterface apiService;
    ArrayList<String> s;
    private int dotscount;
    BreakingNewsViewPagerAdapter b;
    private LinearLayout linearLayout, pausePlayLayout1, breaking_ll1;
    private ImageView dots[];
    int i = 0, adCounter = 0;
    Handler mHandler, animateHandler, scrollHandler;
    AdView mAdView;
    ProgressBar progressBar, fragmentprogressBar;
    Dialog dialog, cancelDialog;
    int adsCount = 0, random = 0;
    MediaPlayer mediaPlayer;
    ImageView dialogImage;
    TextView dialogText1, dialogText2, tv_comments;
    Button dialogUrlButton;
    public boolean active = true;
    TextView tv_date, tv_time;
    String postId = "", category = "";
    public static PostDetailPojo postDetails = null;
    PostsForLandscape landscapePosts;
    static String selection;
    ScrollNewsPojo scrollNews;
    TopNewsPojo topNews;
    int flag = 0;
    List<String> topNewsString;
    TextView tv_top_news, scroll_line;
    Animation anim;
    RelativeLayout main_layout;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //  if the screen rotates to landscape (bigger) mode, hide the status bar
        if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

        setContentView(R.layout.activity_display_breaking_news);
        scrollHandler = new Handler();
        scroll_line = findViewById(R.id.scrolling_line);

        //if the screen is in portrait mode, make status bar black
        // clear FLAG_TRANSLUCENT_STATUS flag:
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        active = true;

        //if run in landscape mode.
        if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            if (scrollNews == null) {
                getLandscapeItems();
            }
            tv_top_news = findViewById(R.id.tv_top_news);


            if (topNews == null) {
                getTopNews();
            }

            //setting post date and tiem in landscape mode
            Typeface fontRegular = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Regular.ttf");

            tv_date = findViewById(R.id.tv_date);
            tv_time = findViewById(R.id.tv_time);
            tv_date.setTypeface(fontRegular);
            tv_time.setTypeface(fontRegular);

            Date todayDate = Calendar.getInstance().getTime();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String todayString = formatter.format(todayDate);

            tv_date.setText(todayString);

            Calendar calendar = Calendar.getInstance();
            int hours = calendar.get(Calendar.HOUR_OF_DAY);
            int minutes = calendar.get(Calendar.MINUTE);
            tv_time.setText(""+hours+":"+minutes);

            new CountDownTimer(6000000, 60000) {

                public void onTick(long millisUntilFinished) {
                    Calendar calendar = Calendar.getInstance();
                    int hours = calendar.get(Calendar.HOUR_OF_DAY);
                    int minutes = calendar.get(Calendar.MINUTE);
                    tv_time.setText(""+hours+":"+minutes);
                }

                public void onFinish() {

                }

            }.start();

        }

        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        // finally change the color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.color_black));
        }

        animateHandler = new Handler();
        animFadeOut = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_out);
        animFadeOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                pausePlayLayout1.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        pausePlayLayout1 = findViewById(R.id.pausePlayNextPreviousLayout1);

        //implementing AdMob here
        MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713");
        mAdView = findViewById(R.id.news_details_adMob);
        AdRequest adRequest = new AdRequest.Builder().build();
        if (mAdView != null)
            mAdView.loadAd(adRequest);
        //end of AdMob

        //getting postCategory and postId from previous activity
        category = getIntent().getStringExtra("category");
        postId = getIntent().getStringExtra("postId");

        //request for increasing view
        if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            makeAViewIncreaseRequest();
        }

        mHandler = new Handler();

        recycler = findViewById(R.id.posts_recycler);
        frameLayoutTextAnimation = findViewById(R.id.animated_text_frame);
        frameLayoutViewPager = findViewById(R.id.view_pager_frame_layout);

        //load data for the first time
        FragmentImage fragmentImage = new FragmentImage();
        FragmentLayout frameLayout = new FragmentLayout();
        if (getResources().getConfiguration().orientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            loadData(fragmentImage, frameLayout, postId);
        } else
            active = false;

//        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        sharedPreferences = NewsSharedPreferences.getInstance(DisplayBreakingNewsActivity.this);

        //setting autoscroll viewpager for the landscape mode
        viewPager = findViewById(R.id.image_view_pager);
        if (viewPager != null && postDetails.getResult() != null && postDetails.getResult().get(0).getSelection().equalsIgnoreCase("image_arrays")) {

            viewPager = findViewById(R.id.image_view_pager);
            viewPager.startAutoScroll();
            viewPager.setInterval(3000);
            viewPager.setCycle(true);
            viewPager.setStopScrollWhenTouch(true);

            //here I am setting the visibility of viewPager and textAnimations according to item selected
            if (!selection.equalsIgnoreCase("image_arrays")) {
                frameLayoutTextAnimation.setVisibility(View.VISIBLE);
                frameLayoutViewPager.setVisibility(View.GONE);
            } else {
                frameLayoutTextAnimation.setVisibility(View.GONE);
                frameLayoutViewPager.setVisibility(View.VISIBLE);
            }
            b = new BreakingNewsViewPagerAdapter(DisplayBreakingNewsActivity.this, postDetails.getResult().get(0).getImageArray());
            viewPager.setAdapter(b);
            dotscount = b.getCount();
            dots = new ImageView[dotscount];

            linearLayout = findViewById(R.id.viewPagerDots);

            for (int i = 0; i < dotscount; i++) {

                dots[i] = new ImageView(DisplayBreakingNewsActivity.this);
                dots[i].setImageDrawable(ContextCompat.getDrawable(DisplayBreakingNewsActivity.this, R.drawable.non_active_dot));

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);

                params.setMargins(8, 0, 8, 0);

                linearLayout.addView(dots[i], params);

            }

            dots[0].setImageDrawable(ContextCompat.getDrawable(DisplayBreakingNewsActivity.this, R.drawable.active_dot));

            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {

                    for (int i = 0; i < dotscount; i++) {
                        dots[i].setImageDrawable(ContextCompat.getDrawable(DisplayBreakingNewsActivity.this, R.drawable.non_active_dot));
                    }

                    dots[position].setImageDrawable(ContextCompat.getDrawable(DisplayBreakingNewsActivity.this, R.drawable.active_dot));

                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        }// end of code for setting autoscroll viewpager

        //this block of code is for pausing and play the visible animations (for text animations)
        pausePlayLayout = findViewById(R.id.pausePlayNextPreviousLayout);
        animFadeIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.pause_play_fade_in);
        animFadeIn1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.pause_play_fade_in);

        if (frameLayoutTextAnimation != null)
            frameLayoutTextAnimation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    pausePlayLayout.setVisibility(View.VISIBLE);
                    pausePlayLayout.startAnimation(animFadeIn);
                    animFadeIn.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {

                            animateHandler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    pausePlayLayout.setVisibility(View.VISIBLE);
                                    pausePlayLayout.startAnimation(animFadeOut);
                                    pausePlayLayout.setVisibility(View.GONE);
                                }
                            }, 1000L);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                }
            }); //this is the end of showing play and pause buttons (for text animations)

        Typeface fontBold = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Bold.ttf");
        textView = findViewById(R.id.breakingNewstext);

        if (textView != null && postDetails.getResult() != null && !postDetails.getResult().get(0).getSelection().equalsIgnoreCase("image_arrays")) {

            textView.setTypeface(fontBold);

            //here I am setting the visibility of viewPager and textAnimations according to item selected
            if (!selection.equalsIgnoreCase("image_arrays")) {
                frameLayoutTextAnimation.setVisibility(View.VISIBLE);
                frameLayoutViewPager.setVisibility(View.GONE);
            } else {
                frameLayoutTextAnimation.setVisibility(View.GONE);
                frameLayoutViewPager.setVisibility(View.VISIBLE);
            }

            zoomIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_in);
            textView.setText(postDetails.getResult().get(0).getImageArray().get(i++));
//            zoomIn.setRepeatCount(Animation.INFINITE);
            textView.setAnimation(zoomIn);

            zoomIn.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    zoomIn = animation;
                    Log.d("Reached on end ", "true");
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Log.d("Inside", "postDelayed" + String.valueOf(i));
                            if (i == 12)
                                i = 0;
                            textView.startAnimation(zoomIn);
                            textView.setText(postDetails.getResult().get(0).getImageArray().get(i++));
                            //start your activity here
                        }

                    }, 3000L);

                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                    Log.d("Reached on repeat ", "true");
                }
            });

        }

        //pausing and playing the animation (for textviews)
        pause = findViewById(R.id.pauseButton);
        play = findViewById(R.id.playButton);

        if (pause != null)
            pause.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    mHandler.removeCallbacksAndMessages(null);
                    textView.setVisibility(View.GONE);

                    view.setVisibility(View.GONE);
                    play.setVisibility(View.VISIBLE);

                    animateHandler.removeCallbacksAndMessages(null);
                    pausePlayLayout.setVisibility(View.VISIBLE);

                }
            });

        if (play != null)
            play.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    textView.setVisibility(View.VISIBLE);
                    if (i == 12)
                        i = 0;
                    textView.setText(postDetails.getResult().get(0).getImageArray().get(i++));
                    textView.startAnimation(zoomIn);

                    view.setVisibility(View.GONE);
                    pause.setVisibility(View.VISIBLE);

                    pausePlayLayout.startAnimation(animFadeOut);
                    pausePlayLayout.setVisibility(View.GONE);
                }
            }); // pausing and playing stops here

        //pausing and playing the animation (for viewpager)
        pauseView = findViewById(R.id.pauseButton1);
        playView = findViewById(R.id.playButton1);
        if (pauseView != null)
            pauseView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    view.setVisibility(View.GONE);
                    playView.setVisibility(View.VISIBLE);

                    linearLayout.setVisibility(View.GONE);
                    viewPager.stopAutoScroll();

                    b.animateHandler.removeCallbacksAndMessages(null);
                    pausePlayLayout1.setVisibility(View.VISIBLE);

                }
            });

        if (playView != null)
            playView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    view.setVisibility(View.GONE);
                    pauseView.setVisibility(View.VISIBLE);

                    linearLayout.setVisibility(View.VISIBLE);
                    viewPager.startAutoScroll();

                    pausePlayLayout1.startAnimation(animFadeOut);
                    pausePlayLayout1.setVisibility(View.GONE);
                }
            }); // playing and pausing animation for viewpager ends here

        //getting Ads of Reporter if not present
        final FrameLayout frame1 = findViewById(R.id.frame1);
        final FrameLayout frame2 = findViewById(R.id.frame2);
        breaking_ll1 = findViewById(R.id.breaking_ll1);
        progressBar = findViewById(R.id.progressBar);

        if (frame1 != null) {
            if (results == null) {
                frame1.setVisibility(View.GONE);
                frame2.setVisibility(View.GONE);
                breaking_ll1.setVisibility(View.GONE);
                recycler.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);

                apiService = ApiClient.getClient().create(ApiInterface.class);
                Log.d("selected city is", sharedPreferences.getCitySelected());
                Call<CityWiseAdvertisement> call = apiService.getReporterAdvertisement(sharedPreferences.getCitySelected().toLowerCase());

                call.enqueue(new Callback<CityWiseAdvertisement>() {
                    @Override
                    public void onResponse(Call<CityWiseAdvertisement> call, Response<CityWiseAdvertisement> response) {
                        CityWiseAdvertisement advertisement = null;
                        if (response.isSuccessful()) {

                            Log.d("Reached here", "true");
                            advertisement = response.body();
                            results = advertisement.getResult();

                            if (results != null) {

                                if (results.size() > 7)
                                    adsCount = 7;
                                else
                                    adsCount = results.size();

                                showAds();
                            }
                            Log.d("Result is", results.get(0).getCity());
                            Log.d("Result is", results.get(0).getImage());

                            frame1.setVisibility(View.VISIBLE);
                            frame2.setVisibility(View.VISIBLE);
                            breaking_ll1.setVisibility(View.VISIBLE);
                            recycler.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);
                        } else {
                            Toast.makeText(DisplayBreakingNewsActivity.this, "Server error!!", Toast.LENGTH_SHORT);

                            frame1.setVisibility(View.VISIBLE);
                            frame2.setVisibility(View.VISIBLE);
                            breaking_ll1.setVisibility(View.VISIBLE);
                            recycler.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onFailure(Call<CityWiseAdvertisement> call, Throwable t) {
                        // Log error here since request failed
                        frame1.setVisibility(View.VISIBLE);
                        frame2.setVisibility(View.VISIBLE);
                        breaking_ll1.setVisibility(View.VISIBLE);
                        recycler.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);

                        Toast.makeText(DisplayBreakingNewsActivity.this, "Server error!!", Toast.LENGTH_SHORT);
                    }
                });
            }//end of getting ads
        }

    }

    public void loadFragment(Fragment fragment1, Fragment fragment2, String postId) {

        loadData(fragment1, fragment2, postId);

    }

    public void loadData(final Fragment fragment1, final Fragment fragment2, String postId) {

        final FrameLayout frame1 = findViewById(R.id.frame1);
        final FrameLayout frame2 = findViewById(R.id.frame2);
        breaking_ll1 = findViewById(R.id.breaking_ll1);
        progressBar = findViewById(R.id.progressBar);
        fragmentprogressBar = findViewById(R.id.fragmentprogressBar);

        if (posts.size() == 0) {
            frame1.setVisibility(View.GONE);
            frame2.setVisibility(View.GONE);
            breaking_ll1.setVisibility(View.GONE);
            recycler.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
        } else {
            fragmentprogressBar.setVisibility(View.VISIBLE);
        }

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<PostDetailPojo> call = apiService.getPostDetails(postId);

        call.enqueue(new Callback<PostDetailPojo>() {
            @Override
            public void onResponse(Call<PostDetailPojo> call, Response<PostDetailPojo> response) {

                if (response.isSuccessful()) {

                    Log.d("Reached to", "loadFragment");
                    postDetails = response.body();

                    if (posts.size() == 0) {
                        frame1.setVisibility(View.VISIBLE);
                        frame2.setVisibility(View.VISIBLE);
                        breaking_ll1.setVisibility(View.VISIBLE);
                        recycler.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                    } else
                        fragmentprogressBar.setVisibility(View.VISIBLE);

                    PostsByCategory post = null;

                    if (postDetails.getResult() != null) {
                        selection = postDetails.getResult().get(0).getSelection();
                        String id = postDetails.getResult().get(0).getPostId();
                        String image = postDetails.getResult().get(0).getImage();
                        String headlines = postDetails.getResult().get(0).getNewsHeadline();
                        String description = postDetails.getResult().get(0).getNewsDescription();
                        String views = postDetails.getResult().get(0).getViews();
                        String likes = "" + postDetails.getResult().get(0).getLikes();
                        List<PostDetailPojo.Comment> comments = postDetails.getResult().get(0).getComments();
                        String selection = postDetails.getResult().get(0).getSelection();
                        ArrayList<String> array = (ArrayList<String>) postDetails.getResult().get(0).getImageArray();
                        String newsBrief = postDetails.getResult().get(0).getNewsBrief();
                        String postTime = postDetails.getResult().get(0).getTimeOfPost();

                        if (posts.size() == 0) {
                            PostsByCategory category = new PostsByCategory(id, image, headlines, description, views, likes);
                            posts.add(category);
                        }

                        landscapePosts = new PostsForLandscape();
                        landscapePosts.setArray(array);
                        landscapePosts.setSelection(selection);
                        landscapePosts.setHeadlines(headlines);
                        landscapePosts.setImage(image);
                        landscapePosts.setId(id);
                        landscapePosts.setDescription(description);
                        landscapePosts.setLikes(likes);
                        landscapePosts.setBrief(newsBrief);
                        landscapePosts.setPostTime(postTime);

                        FragmentManager fm = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fm.beginTransaction();
                        Bundle bundle = new Bundle();
                        fragment1.setArguments(bundle);

                        ArrayList<String> list = new ArrayList<>();
                        list.add(views);
                        list.add(likes);

                        bundle.putStringArrayList("forLinearLayout", list);
                        fragment2.setArguments(bundle);

                        fragmentTransaction.replace(R.id.frame2, fragment2);
                        fragmentTransaction.replace(R.id.frame1, fragment1);
                        fragmentTransaction.commit(); // save the changes

                        //this will fetch 20 posts by category from database
                        if (posts.size() == 1)
                            lodaDataByCategory();
                        if (posts.size() > 1)
                            adapter.notifyDataSetChanged();
                    }

                } else {
                    Toast.makeText(DisplayBreakingNewsActivity.this, "Server error!!", Toast.LENGTH_SHORT).show();

                    frame1.setVisibility(View.VISIBLE);
                    frame2.setVisibility(View.VISIBLE);
                    breaking_ll1.setVisibility(View.VISIBLE);
                    recycler.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                    fragmentprogressBar.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<PostDetailPojo> call, Throwable t) {
                // Log error here since request failed
                frame1.setVisibility(View.VISIBLE);
                frame2.setVisibility(View.VISIBLE);
                breaking_ll1.setVisibility(View.VISIBLE);
                recycler.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                fragmentprogressBar.setVisibility(View.VISIBLE);

                Toast.makeText(DisplayBreakingNewsActivity.this, "Server error!!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        // code here to show dialog
        if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        else {
            Intent intent = new Intent(DisplayBreakingNewsActivity.this, DashboardActivity.class);
            startActivity(intent);
            finish();
        }
    }

    //changing the adapter to go to next item
    public void goToNext() {

        FragmentImage fragment1 = new FragmentImage();
        FragmentLayout fragment2 = new FragmentLayout();

        if (adapter.previous == (adapter.getItemCount() - 1) - 2) {
            adapter.clicked[0] = true;
            //loading both the fragments with clicked position
            loadFragment(fragment1, fragment2, posts.get(0).getPostId());
            sharedPreferences.setClickedPosition(0);

            adapter.clicked[(adapter.getItemCount() - 1) - 2] = false;
            recycler.smoothScrollToPosition(0);
            adapter.previous = 0;
        } else {
            adapter.clicked[adapter.previous] = false;
            adapter.clicked[adapter.previous + 1] = true;
            //loading both the fragments with clicked position
            loadFragment(fragment1, fragment2, posts.get(adapter.previous + 1).getPostId());
            sharedPreferences.setClickedPosition(adapter.previous + 1);

            recycler.smoothScrollToPosition((adapter.previous + 1) + 1); //added this extra 1 to scroll the recycler to correct position
            adapter.previous = adapter.previous + 1;
        }
        adapter.notifyDataSetChanged();
    }

    //changing the adapter to go to previous item
    public void goToPrevious() {

        FragmentImage fragment1 = new FragmentImage();
        FragmentLayout fragment2 = new FragmentLayout();

        if (adapter.previous == 0) {
            adapter.clicked[(adapter.getItemCount() - 1) - 2] = true;
            //loading both the fragments with clicked position
            loadFragment(fragment1, fragment2, posts.get((adapter.getItemCount() - 1) - 2).getPostId());
            sharedPreferences.setClickedPosition((adapter.getItemCount() - 1) - 2);

            adapter.clicked[adapter.previous] = false;
            recycler.smoothScrollToPosition(((adapter.getItemCount() - 1) - 2) + 1); //added this extra 1 to scroll the recycler to correct position
            adapter.previous = (adapter.getItemCount() - 1) - 2;
        } else {
            adapter.clicked[adapter.previous - 1] = true;
            //loading both the fragments with clicked position
            loadFragment(fragment1, fragment2, posts.get((adapter.previous - 1)).getPostId());
            sharedPreferences.setClickedPosition((adapter.previous - 1));

            adapter.clicked[adapter.previous] = false;
            recycler.smoothScrollToPosition(adapter.previous);
            adapter.previous = adapter.previous - 1;
        }
        adapter.notifyDataSetChanged();
    }

    //for showing ads
    public void showAds() {

        dialog = new Dialog(DisplayBreakingNewsActivity.this, R.style.NewDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.video_dialog);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);

        dialogImage = dialog.findViewById(R.id.iv_ad);
        dialogText1 = dialog.findViewById(R.id.dialogText1);
        dialogText2 = dialog.findViewById(R.id.dialogText2);
        dialogUrlButton = dialog.findViewById(R.id.redirectButton);

        //implementing ads on every 30 seconds
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

                if (active) {
                    dialog.show();
                    Glide.with(DisplayBreakingNewsActivity.this).load(results.get(random).getImage()).into(dialogImage);
                    dialogUrlButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(DisplayBreakingNewsActivity.this, ActivityShowWebView.class);
                            intent.putExtra("url", results.get(random).getUrl());
                            startActivity(intent);
                        }
                    });
                    dialogText1.setText(results.get(random).getText1());
                    dialogText2.setText(results.get(random).getText2());

                    if (results.get(random).getAudio().trim().isEmpty()) {
                        dialog.setCancelable(true);
                        dialog.setCanceledOnTouchOutside(true);
                    } else {
                        if (dialog.isShowing()) {
                            mediaPlayer = new MediaPlayer();
                            new DisplayBreakingNewsActivity.PlayMainAdAsync().execute(results.get(random).getAudio());
                            mediaPlayer.setOnBufferingUpdateListener(DisplayBreakingNewsActivity.this);
                            mediaPlayer.setOnCompletionListener(DisplayBreakingNewsActivity.this);
                        }
                    }
                }
            }
        }, 28000);

        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                if (mediaPlayer != null)
                    mediaPlayer.stop();
                random = (int) (Math.random() * adsCount + 1);
                random = random - 1;

                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {

                        if (active) {
                            dialog.show();

                            Glide.with(DisplayBreakingNewsActivity.this).load(results.get(random).getImage()).into(dialogImage);
                            dialogUrlButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent intent = new Intent(DisplayBreakingNewsActivity.this, ActivityShowWebView.class);
                                    intent.putExtra("url", results.get(random).getUrl());
                                    startActivity(intent);
                                }
                            });
                            dialogText1.setText(results.get(random).getText1());
                            dialogText2.setText(results.get(random).getText2());

                            if (results.get(random).getAudio().trim().isEmpty()) {
                                dialog.setCancelable(true);
                                dialog.setCanceledOnTouchOutside(true);
                            } else {
                                if (dialog.isShowing()) {
                                    new DisplayBreakingNewsActivity.PlayMainAdAsync().execute(results.get(random).getAudio());
                                    mediaPlayer.setOnBufferingUpdateListener(DisplayBreakingNewsActivity.this);
                                    mediaPlayer.setOnCompletionListener(DisplayBreakingNewsActivity.this);
                                }
                            }

                        }
                    }
                }, 30000);
            }
        }); // end of ads dialog in every 30 seconds
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mediaPlayer, int i) {

    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        dialog.cancel();
    }

    @Override
    protected void onResume() {
        super.onResume();
        showAds();
    }

    @Override
    protected void onStop() {
        super.onStop();
        active = false;
        if (mediaPlayer != null)
            mediaPlayer.stop();
        dialog.cancel();
    }

    class PlayMainAdAsync extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            /*cancelDialog = Utility.showWaitDialog(DisplayBreakingNewsActivity.this);
            cancelDialog.show();*/
        }

        @Override
        protected String doInBackground(String... aurl) {

            try {
                mediaPlayer.setDataSource(aurl[0]);
                mediaPlayer.prepare();
            } catch (Exception e) {
                Log.d("Exception is", e.toString());
            }
            return null;
        }

        protected void onProgressUpdate(String... progress) {
            Log.d("ANDRO_ASYNC", progress[0]);
        }

        @Override
        protected void onPostExecute(String unused) {
            mediaPlayer.start();
//            cancelDialog.cancel();
        }
    }

    public void lodaDataByCategory() {

        final FrameLayout frame1 = findViewById(R.id.frame1);
        final FrameLayout frame2 = findViewById(R.id.frame2);
        breaking_ll1 = findViewById(R.id.breaking_ll1);
        progressBar = findViewById(R.id.progressBar);

        apiService = ApiClient.getClient().create(ApiInterface.class);
        category = postDetails.getResult().get(0).getCategory();
        Call<TwentyPostsByCategory> call = apiService.getTwentyPostsByCategory(category);

        call.enqueue(new Callback<TwentyPostsByCategory>() {

            @Override
            public void onResponse(Call<TwentyPostsByCategory> call, Response<TwentyPostsByCategory> response) {

                TwentyPostsByCategory postsByCat = new TwentyPostsByCategory();
                if (response.isSuccessful()) {

                    Log.d("Reached to", "getTwentyPostsByCategory");
                    postsByCat = response.body();

                    frame1.setVisibility(View.VISIBLE);
                    frame2.setVisibility(View.VISIBLE);
                    breaking_ll1.setVisibility(View.VISIBLE);
                    recycler.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);

                    PostsByCategory post = null;
                    if (postsByCat != null) {
                        if (postsByCat.getResult().size() > 1)
                            for (int i = 1; i < postsByCat.getResult().size(); i++) {
                                String id = postsByCat.getResult().get(i).getPostId();
                                String image = postsByCat.getResult().get(i).getImage();
                                String headlines = postsByCat.getResult().get(i).getNewsHeadline();
                                String description = postsByCat.getResult().get(i).getNewsDescription();
                                String views = postsByCat.getResult().get(i).getViews();
                                String likes = postsByCat.getResult().get(i).getLikes();

                                post = new PostsByCategory(id, image, headlines, description, views, likes);
                                posts.add(post);
                            }
                    }

                    if (recycler != null) {
                        adapter = new BreakingNewsRecyclerAdapter(DisplayBreakingNewsActivity.this, posts);
                        recycler.setLayoutManager(new LinearLayoutManager(DisplayBreakingNewsActivity.this, LinearLayoutManager.VERTICAL, false));
                        recycler.setAdapter(adapter);
                    }

                } else {
                    Toast.makeText(DisplayBreakingNewsActivity.this, "Server error!!", Toast.LENGTH_LONG).show();

                    frame1.setVisibility(View.VISIBLE);
                    frame2.setVisibility(View.VISIBLE);
                    breaking_ll1.setVisibility(View.VISIBLE);
                    recycler.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<TwentyPostsByCategory> call, Throwable t) {
                // Log error here since request failed
                frame1.setVisibility(View.VISIBLE);
                frame2.setVisibility(View.VISIBLE);
                breaking_ll1.setVisibility(View.VISIBLE);
                recycler.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);

                Toast.makeText(DisplayBreakingNewsActivity.this, "Server error!!", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void getLandscapeItems() {
        main_layout = findViewById(R.id.main_layout);
        progressBar = findViewById(R.id.progressBar);

        main_layout.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ScrollNewsPojo> call = apiService.getscrollingNews();

        call.enqueue(new Callback<ScrollNewsPojo>() {
            @Override
            public void onResponse(Call<ScrollNewsPojo> call, Response<ScrollNewsPojo> response) {

                if (response.isSuccessful()) {
                    scrollNews = response.body();
                    String s = "";
                    if (scrollNews.getResult().getScrollNews() != null) {
                        for (int i = 0; i < scrollNews.getResult().getScrollNews().size(); i++) {
                            s = s + scrollNews.getResult().getScrollNews().get(i).getText1();
                            s = s + scrollNews.getResult().getScrollNews().get(i).getText2();
                            s = s + scrollNews.getResult().getScrollNews().get(i).getText3();
                            s = s + scrollNews.getResult().getScrollNews().get(i).getText4();
                            s = s + scrollNews.getResult().getScrollNews().get(i).getText5();
                            s = s + scrollNews.getResult().getScrollNews().get(i).getText6();
                            s = s + scrollNews.getResult().getScrollNews().get(i).getText7();
                            s = s + scrollNews.getResult().getScrollNews().get(i).getText8();
                            s = s + scrollNews.getResult().getScrollNews().get(i).getText9();
                            s = s + scrollNews.getResult().getScrollNews().get(i).getText10();
                            s = s + scrollNews.getResult().getScrollNews().get(i).getText11();
                            s = s + scrollNews.getResult().getScrollNews().get(i).getText12();
                            s = s + scrollNews.getResult().getScrollNews().get(i).getText13();
                            s = s + scrollNews.getResult().getScrollNews().get(i).getText14();
                            s = s + scrollNews.getResult().getScrollNews().get(i).getText15();
                            s = s + scrollNews.getResult().getScrollNews().get(i).getText16();
                            s = s + scrollNews.getResult().getScrollNews().get(i).getText17();
                            s = s + scrollNews.getResult().getScrollNews().get(i).getText18();
                            s = s + scrollNews.getResult().getScrollNews().get(i).getText19();
                            s = s + scrollNews.getResult().getScrollNews().get(i).getText20();
                            s = s + scrollNews.getResult().getScrollNews().get(i).getText21();
                            s = s + scrollNews.getResult().getScrollNews().get(i).getText22();
                            s = s + scrollNews.getResult().getScrollNews().get(i).getText23();
                            s = s + scrollNews.getResult().getScrollNews().get(i).getText24();
                            s = s + scrollNews.getResult().getScrollNews().get(i).getText25();
                            s = s + scrollNews.getResult().getScrollNews().get(i).getText26();
                            s = s + scrollNews.getResult().getScrollNews().get(i).getText27();
                            s = s + scrollNews.getResult().getScrollNews().get(i).getText28();
                            s = s + scrollNews.getResult().getScrollNews().get(i).getText29();
                            s = s + scrollNews.getResult().getScrollNews().get(i).getText30();
                        }
                    }
                    main_layout.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);

                    TextView scrolling_line = findViewById(R.id.scrolling_line);
                    scrolling_line.setText(s);
                    scrolling_line.setEllipsize(TextUtils.TruncateAt.MARQUEE);
                    scrolling_line.setSelected(true);
                    scrolling_line.setSingleLine(true);

                } else {
                    main_layout.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(DisplayBreakingNewsActivity.this, "Server error!!", Toast.LENGTH_SHORT).show();
                }
//                Log.d(TAG, "Number of movies received: " + movies.size());
            }

            @Override
            public void onFailure(Call<ScrollNewsPojo> call, Throwable t) {
                // Log error here since request failed
                main_layout.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                Toast.makeText(DisplayBreakingNewsActivity.this, "Server error!!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void makeAViewIncreaseRequest() {

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ViewPojo> call = apiService.makeAView(postId);

        call.enqueue(new Callback<ViewPojo>() {

            @Override
            public void onResponse(Call<ViewPojo> call, Response<ViewPojo> response) {

                ViewPojo postsByCat = null;
                if (response.isSuccessful()) {

                    Log.d("Reached to", "makeAview");
                    postsByCat = response.body();


                } else {

                }
            }

            @Override
            public void onFailure(Call<ViewPojo> call, Throwable t) {
                // Log error here since request failed
            }
        });
    }

    public void getTopNews() {
        main_layout = findViewById(R.id.main_layout);
        progressBar = findViewById(R.id.progressBar);

        main_layout.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<TopNewsPojo> call = apiService.getTopNews();

        call.enqueue(new Callback<TopNewsPojo>() {
            @Override
            public void onResponse(Call<TopNewsPojo> call, Response<TopNewsPojo> response) {

                if (response.isSuccessful()) {
                    topNews = response.body();
                    s = new ArrayList<>();
                    if (topNews.getResult().getTopNews() != null) {
                        for (int i = 0; i < topNews.getResult().getTopNews().size(); i++) {
                            s.add("Top News");
                            s.add(topNews.getResult().getTopNews().get(i).getText1());
                            s.add(topNews.getResult().getTopNews().get(i).getText2());
                            s.add(topNews.getResult().getTopNews().get(i).getText3());
                            s.add("Top News");
                            s.add(topNews.getResult().getTopNews().get(i).getText4());
                            s.add(topNews.getResult().getTopNews().get(i).getText5());
                            s.add(topNews.getResult().getTopNews().get(i).getText6());
                            s.add("Top News");
                            s.add(topNews.getResult().getTopNews().get(i).getText7());
                            s.add(topNews.getResult().getTopNews().get(i).getText8());
                            s.add(topNews.getResult().getTopNews().get(i).getText9());
                            s.add("Top News");
                            s.add(topNews.getResult().getTopNews().get(i).getText10());
                            s.add(topNews.getResult().getTopNews().get(i).getText11());
                            s.add(topNews.getResult().getTopNews().get(i).getText12());
                            s.add("Top News");
                            s.add(topNews.getResult().getTopNews().get(i).getText13());
                            s.add(topNews.getResult().getTopNews().get(i).getText14());
                            s.add(topNews.getResult().getTopNews().get(i).getText15());
                            s.add("Top News");
                            s.add(topNews.getResult().getTopNews().get(i).getText16());
                            s.add(topNews.getResult().getTopNews().get(i).getText17());
                            s.add(topNews.getResult().getTopNews().get(i).getText18());
                            s.add("Top News");
                            s.add(topNews.getResult().getTopNews().get(i).getText19());
                            s.add(topNews.getResult().getTopNews().get(i).getText20());
                            s.add(topNews.getResult().getTopNews().get(i).getText21());
                            s.add("Top News");
                            s.add(topNews.getResult().getTopNews().get(i).getText22());
                            s.add(topNews.getResult().getTopNews().get(i).getText23());
                            s.add(topNews.getResult().getTopNews().get(i).getText24());
                            s.add("Top News");
                            s.add(topNews.getResult().getTopNews().get(i).getText25());
                            s.add(topNews.getResult().getTopNews().get(i).getText26());
                            s.add(topNews.getResult().getTopNews().get(i).getText27());
                            s.add("Top News");
                            s.add(topNews.getResult().getTopNews().get(i).getText28());
                            s.add(topNews.getResult().getTopNews().get(i).getText29());
                            s.add(topNews.getResult().getTopNews().get(i).getText30());
                        }
                    }
                    main_layout.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);

                    tv_top_news = findViewById(R.id.tv_top_news);
                    anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);

                    tv_top_news.setText(s.get(flag++));
                    tv_top_news.startAnimation(anim);

                    anim.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            anim = animation;
                            scrollHandler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    if (flag == s.size())
                                        flag = 0;
                                    tv_top_news.setText(s.get(flag++));
                                    tv_top_news.startAnimation(anim);
                                }
                            }, 2000L);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });

                    ImageView place_holder_image = findViewById(R.id.place_holder_image);
                    TextView place = findViewById(R.id.place);

                    Glide.with(DisplayBreakingNewsActivity.this).load(postDetails.getResult().get(0).getReporterPic()).into(place_holder_image);
                    place.setText(postDetails.getResult().get(0).getReporterLocation());

                } else {
                    main_layout.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(DisplayBreakingNewsActivity.this, "Server error!!", Toast.LENGTH_SHORT).show();
                }
//                Log.d(TAG, "Number of movies received: " + movies.size());
            }

            @Override
            public void onFailure(Call<TopNewsPojo> call, Throwable t) {
                // Log error here since request failed
                main_layout.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                Toast.makeText(DisplayBreakingNewsActivity.this, "Server error!!", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
