package com.gsatechworld.gugrify.view;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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
import com.gsatechworld.gugrify.fragment.FragmentImage;
import com.gsatechworld.gugrify.fragment.FragmentLayout;
import com.gsatechworld.gugrify.model.PostsByCategory;
import com.gsatechworld.gugrify.model.retrofit.ApiClient;
import com.gsatechworld.gugrify.model.retrofit.ApiInterface;
import com.gsatechworld.gugrify.model.retrofit.CityWiseAdvertisement;
import com.gsatechworld.gugrify.model.retrofit.GetMainAdvertisement;
import com.gsatechworld.gugrify.view.adapters.BreakingNewsRecyclerAdapter;
import com.gsatechworld.gugrify.view.adapters.BreakingNewsViewPagerAdapter;
import com.gsatechworld.gugrify.view.dashboard.AutoScrollViewPager;
import com.gsatechworld.gugrify.view.dashboard.DashboardActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DisplayBreakingNewsActivity extends AppCompatActivity {
    public ArrayList<PostsByCategory> posts = new ArrayList<>();
    Animation zoomIn, animFadeIn, animFadeOut, animFadeIn1;
    AutoScrollViewPager viewPager;
    BreakingNewsRecyclerAdapter adapter;
    RecyclerView recycler;
    FrameLayout frameLayoutTextAnimation, frameLayoutViewPager;
    ImageView pause, play, pauseView, playView;
    public static LinearLayout pausePlayLayout;
    NewsSharedPreferences sharedPreferences;
    List<CityWiseAdvertisement.Result> results;
    TextView textView;
    ApiInterface apiService;
    private int dotscount;
    InterstitialAd mInterstitialAd;
    BreakingNewsViewPagerAdapter b;
    private LinearLayout linearLayout, pausePlayLayout1, breaking_ll1;
    private ImageView dots[];
    int i = 0, adCounter = 0;
    Handler mHandler, animateHandler;
    AdView mAdView;
    ProgressBar progressBar;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //  if the screen rotates to landscape (bigger) mode, hide the status bar
        if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

        setContentView(R.layout.activity_display_breaking_news);

        //if the screen is in portrait mode, make status bar black
        // clear FLAG_TRANSLUCENT_STATUS flag:
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

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

        mHandler = new Handler();

//        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        sharedPreferences = NewsSharedPreferences.getInstance(DisplayBreakingNewsActivity.this);

        recycler = findViewById(R.id.posts_recycler);
        frameLayoutTextAnimation = findViewById(R.id.animated_text_frame);
        frameLayoutViewPager = findViewById(R.id.view_pager_frame_layout);

        //setting autoscroll viewpager for the landscape mode
        viewPager = findViewById(R.id.image_view_pager);
        if (viewPager != null) {

            viewPager = findViewById(R.id.image_view_pager);
            viewPager.startAutoScroll();
            viewPager.setInterval(3000);
            viewPager.setCycle(true);
            viewPager.setStopScrollWhenTouch(true);

            //chaning the landscape mode to either animation or viewpager mode.
            if (sharedPreferences.getClickedPosition() % 2 == 0) {
                frameLayoutTextAnimation.setVisibility(View.VISIBLE);
                frameLayoutViewPager.setVisibility(View.GONE);
            } else {
                frameLayoutTextAnimation.setVisibility(View.GONE);
                frameLayoutViewPager.setVisibility(View.VISIBLE);
            }
            b = new BreakingNewsViewPagerAdapter(DisplayBreakingNewsActivity.this);
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

        TextView scroll_line = findViewById(R.id.scrolling_line);
        if (scroll_line != null) {
            Typeface fontBold = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Bold.ttf");
            scroll_line.setTypeface(fontBold);
            scroll_line.setSelected(true);
        }


        //this will fetch the data from server.
        loadData();


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

        if (textView != null) {

            textView.setTypeface(fontBold);

            zoomIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_in);
            textView.setText(posts.get(0).getTexts().get(i++));
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
                            if (i == 3)
                                i = 0;
                            textView.startAnimation(zoomIn);
                            textView.setText(posts.get(sharedPreferences.getClickedPosition()).getTexts().get(i++));
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


        if (recycler != null) {
            FragmentImage fragment1 = new FragmentImage();
            FragmentLayout fragment2 = new FragmentLayout();
            loadFragment(fragment1, fragment2, sharedPreferences.getClickedPosition());

            adapter = new BreakingNewsRecyclerAdapter(DisplayBreakingNewsActivity.this, posts);
            recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            recycler.setAdapter(adapter);
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
                    if (i == 3)
                        i = 0;
                    textView.setText(posts.get(sharedPreferences.getClickedPosition()).getTexts().get(i++));
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
        if(results == null){
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

    public void loadFragment(Fragment fragment1, Fragment fragment2, int position) {
        // create a FragmentManager
        FragmentManager fm = getFragmentManager();
// create a FragmentTransaction to begin the transaction and replace the Fragment
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
// replace the FragmentLayout with new Fragment

        Bundle bundle = new Bundle();
        bundle.putString("post_id", posts.get(position).getPostId());
        fragment1.setArguments(bundle);

        ArrayList<String> list = new ArrayList<>();
        list.add(posts.get(position).getViews());
        list.add(posts.get(position).getLikes());
        list.add(String.valueOf(posts.get(position).getComments().size()));

        bundle.putStringArrayList("forLinearLayout", list);
        fragment2.setArguments(bundle);

        fragmentTransaction.replace(R.id.frame2, fragment2);
//        fragmentTransaction.commit();
        fragmentTransaction.replace(R.id.frame1, fragment1);
        fragmentTransaction.commit(); // save the changes
    }

    public void loadData() {
        sharedPreferences.setClickedPosition(0);

        String image1 = "https://i2-prod.manchestereveningnews.co.uk/incoming/article13699191.ece/ALTERNATES/s615/unnamed-2.jpg";
        String image2 = "http://blog.typeathought.com/wp-content/uploads/2018/06/football.jpg";
        String image3 = "https://cdn-triplem.scadigital.io/media/46306/cricket-australia.jpg?preset=MainImage";

        String head1 = "Football news 1";
        String head2 = "Football news 2";
        String head3 = "Cricket news 3";

        String desc1 = "Kane played 573 minutes at the World Cup this summer, picking up six goals and the Golden Boot along the way.";
        String desc2 = "Southgate said: \"Harry falls in the category in which we have several players where we have to watch how much they play.\"";
        String desc3 = "After the two-and-a-half-day disaster at The Lord's last month, Virat Kohli's boys are now staring at another humiliation at the other end of London, The Oval.";

        String view1 = "15";
        String view2 = "19";
        String view3 = "25";

        String likes1 = "10";
        String likes2 = "15";
        String likes3 = "20";

        ArrayList<String> comments1 = new ArrayList<>();
        ArrayList<String> comments2 = new ArrayList<>();
        ArrayList<String> comments3 = new ArrayList<>();

        comments1.add("Good post!! Keep up the good work.");
        comments1.add("Nice post!!");
        comments1.add("Keep it up");

        comments1.add("Good post!! Keep up the good work.");
        comments1.add("Nice post!!");
        comments1.add("Keep it up!!");

        comments1.add("Good post!! Keep up the good work.");
        comments1.add("Nice post!!");
        comments1.add("Keep it up!!");

        ArrayList<String> text1 = new ArrayList<>();
        ArrayList<String> text2 = new ArrayList<>();
        ArrayList<String> text3 = new ArrayList<>();

        text1.add("Breaking News");
        text1.add("ಒಟಿ ತಂತ್ರಜ್ಞರ ಬೇಡಿಕೆಗೆ ತಕ್ಕಂತೆ ಹುದ್ದೆ ಭರ್ತಿ ಮಾಡಿ - ಯೂನಿಯನ್ ಆಗ್ರಹ");
        text1.add("ಬೆಂಗಳೂರು - ಆಪರೇಷನ್ ಥಿಯೇಟರ್ ತಂತ್ರಜ್ಞರ ಕೋರ್ಸ್ ಮುಗಿಸಿ ಹೊರ ಬರುವವರಿಗೆ ಉದ್ಯೋಗ ಸೃಷ್ಟಿಯಾಗದಿರುವ ಬಗ್ಗೆ ಅಭ್ಯರ್ಥಿಗಳಲ್ಲಿ ");
        text1.add("ವಸಂತನಗರದ ಗುರುನಾನಕ್ ಭವನದಲ್ಲಿ ಹಮ್ಮಿಕೊಂಡಿದ್ದ ಪದಾಧಿಕಾರಿಗಳ ಸಭೆಯಲ್ಲಿ ಮಾತನಾಡಿದರು. ");

        text2.add("Breaking News");
        text2.add("ಒಟಿ ತಂತ್ರಜ್ಞರ ಬೇಡಿಕೆಗೆ ತಕ್ಕಂತೆ ಹುದ್ದೆ ಭರ್ತಿ ಮಾಡಿ - ಯೂನಿಯನ್ ಆಗ್ರಹ");
        text2.add("ಬೆಂಗಳೂರು - ಆಪರೇಷನ್ ಥಿಯೇಟರ್ ತಂತ್ರಜ್ಞರ ಕೋರ್ಸ್ ಮುಗಿಸಿ ಹೊರ ಬರುವವರಿಗೆ ಉದ್ಯೋಗ ಸೃಷ್ಟಿಯಾಗದಿರುವ ಬಗ್ಗೆ ಅಭ್ಯರ್ಥಿಗಳಲ್ಲಿ ");
        text2.add("ವಸಂತನಗರದ ಗುರುನಾನಕ್ ಭವನದಲ್ಲಿ ಹಮ್ಮಿಕೊಂಡಿದ್ದ ಪದಾಧಿಕಾರಿಗಳ ಸಭೆಯಲ್ಲಿ ಮಾತನಾಡಿದರು. ");

        text3.add("Breaking News");
        text3.add("ಒಟಿ ತಂತ್ರಜ್ಞರ ಬೇಡಿಕೆಗೆ ತಕ್ಕಂತೆ ಹುದ್ದೆ ಭರ್ತಿ ಮಾಡಿ - ಯೂನಿಯನ್ ಆಗ್ರಹ");
        text3.add("ಬೆಂಗಳೂರು - ಆಪರೇಷನ್ ಥಿಯೇಟರ್ ತಂತ್ರಜ್ಞರ ಕೋರ್ಸ್ ಮುಗಿಸಿ ಹೊರ ಬರುವವರಿಗೆ ಉದ್ಯೋಗ ಸೃಷ್ಟಿಯಾಗದಿರುವ ಬಗ್ಗೆ ಅಭ್ಯರ್ಥಿಗಳಲ್ಲಿ ");
        text3.add("ವಸಂತನಗರದ ಗುರುನಾನಕ್ ಭವನದಲ್ಲಿ ಹಮ್ಮಿಕೊಂಡಿದ್ದ ಪದಾಧಿಕಾರಿಗಳ ಸಭೆಯಲ್ಲಿ ಮಾತನಾಡಿದರು. ");

        PostsByCategory p1 = new PostsByCategory("0", image1, head1, desc1, view1, likes1, comments1, text1);
        PostsByCategory p2 = new PostsByCategory("1", image2, head2, desc2, view2, likes2, comments2, text2);
        PostsByCategory p3 = new PostsByCategory("2", image3, head3, desc3, view3, likes3, comments3, text3);

        posts.add(p1);
        posts.add(p2);
        posts.add(p3);

        posts.add(p1);
        posts.add(p2);
        posts.add(p3);

        posts.add(p1);
        posts.add(p2);
        posts.add(p3);
    }


    @Override
    public void onBackPressed() {
        // code here to show dialog
        if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        else
            finish();
    }

    //changing the adapter to go to next item
    public void goToNext() {

        FragmentImage fragment1 = new FragmentImage();
        FragmentLayout fragment2 = new FragmentLayout();

        if (adapter.previous == (adapter.getItemCount() - 1) - 1) {
            adapter.clicked[0] = true;
            //loading both the fragments with clicked position
            loadFragment(fragment1, fragment2, 0);
            sharedPreferences.setClickedPosition(0);

            adapter.clicked[(adapter.getItemCount() - 1) - 1] = false;
            recycler.smoothScrollToPosition(0);
            adapter.previous = 0;
        } else {
            adapter.clicked[adapter.previous] = false;
            adapter.clicked[adapter.previous + 1] = true;
            //loading both the fragments with clicked position
            loadFragment(fragment1, fragment2, adapter.previous + 1);
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
            adapter.clicked[(adapter.getItemCount() - 1) - 1] = true;
            //loading both the fragments with clicked position
            loadFragment(fragment1, fragment2, (adapter.getItemCount() - 1) - 1);
            sharedPreferences.setClickedPosition((adapter.getItemCount() - 1) - 1);

            adapter.clicked[adapter.previous] = false;
            recycler.smoothScrollToPosition(((adapter.getItemCount() - 1) - 1) + 1); //added this extra 1 to scroll the recycler to correct position
            adapter.previous = (adapter.getItemCount() - 1) - 1;
        } else {
            adapter.clicked[adapter.previous - 1] = true;
            //loading both the fragments with clicked position
            loadFragment(fragment1, fragment2, (adapter.previous - 1));
            sharedPreferences.setClickedPosition((adapter.previous - 1));

            adapter.clicked[adapter.previous] = false;
            recycler.smoothScrollToPosition(adapter.previous);
            adapter.previous = adapter.previous - 1;
        }
        adapter.notifyDataSetChanged();
    }
}
