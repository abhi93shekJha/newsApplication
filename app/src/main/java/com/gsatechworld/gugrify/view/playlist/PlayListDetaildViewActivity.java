package com.gsatechworld.gugrify.view.playlist;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gsatechworld.gugrify.NewsSharedPreferences;
import com.gsatechworld.gugrify.R;
import com.gsatechworld.gugrify.fragment.FragmentImage;

import java.util.ArrayList;
import java.util.Random;

public class PlayListDetaildViewActivity extends AppCompatActivity {

    ArrayList<PlayListModel> posts;
    ArrayList<Integer> listPlaylistItemPositions;
    private LinearLayout ll_share;
    private TextView textView;
    NewsSharedPreferences sharedPreferences;
    Animation zoomIn;
    int i=0;
    Handler mHandler;
    private ViewPager viewPager;
    private FrameLayout frameLayoutTextAnimation;
    private FrameLayout frameLayoutViewPager, frameShowAdd;
    private RecyclerView recycler;
    private LinearLayout ll_rewind, ll_play, ll_forward, ll_suffle, ll_frame2, ll_repeat;
    private PlayListViewRecyclerAdapter adapter;
    private ImageView iv_suffle, iv_rewind, iv_play, iv_forward, iv_repeat;
    private boolean isSuffleEnabled = false;
    private boolean isPlayEnabled = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //  if the screen rotates to landscape (bigger) mode, hide the status bar
        if(getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        setContentView(R.layout.activity_play_list_detaild_view);

        //if the screen is in portrait mode, make status bar black
        // clear FLAG_TRANSLUCENT_STATUS flag:
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        // finally change the color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.color_black));
        }

        sharedPreferences = NewsSharedPreferences.getInstance(this);

        ll_frame2 = (LinearLayout)findViewById(R.id.frame2);
        frameShowAdd = (FrameLayout)findViewById(R.id.frameShowAdd);
        ll_rewind = (LinearLayout)findViewById(R.id.ll_rewind);
        ll_play = (LinearLayout)findViewById(R.id.ll_play);
        ll_forward = (LinearLayout)findViewById(R.id.ll_forward);
        ll_repeat = (LinearLayout)findViewById(R.id.ll_repeat);
        iv_suffle = (ImageView)findViewById(R.id.iv_suffle);
        ll_suffle = (LinearLayout)findViewById(R.id.ll_suffle);
        recycler = (RecyclerView) findViewById(R.id.playlist_recycler);

        iv_rewind = (ImageView)findViewById(R.id.iv_rewind);
        iv_play = (ImageView)findViewById(R.id.iv_play);
        iv_forward = (ImageView)findViewById(R.id.iv_forward);
        iv_repeat = (ImageView)findViewById(R.id.iv_repeat);

        if(ll_frame2 != null){
            iv_suffle.setColorFilter(getResources().getColor(R.color.md_grey_400));
            iv_rewind.setColorFilter(getResources().getColor(R.color.color_black));
            iv_play.setColorFilter(getResources().getColor(R.color.color_black));
            iv_forward.setColorFilter(getResources().getColor(R.color.color_black));
            iv_repeat.setColorFilter(getResources().getColor(R.color.color_black));

            isPlayEnabled = true;
            iv_play.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.ic_pause));

        }

        ll_share = (LinearLayout)findViewById(R.id.ll_share);

        mHandler = new Handler();

        loadData();

        if(recycler != null) {
            adapter = new PlayListViewRecyclerAdapter(this, posts);
            recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            recycler.setAdapter(adapter);

            loadAdFragment(new ShowAdFragment());
        }


        if(ll_share != null)
        ll_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Share to Social Media

                ImageView imageView = (ImageView) findViewById(R.id.iv_share);
                Drawable mDrawable = null;
                Bitmap mBitmap  = null;

//                String path = MediaStore.Images.Media.insertImage(getContentResolver(),
//                        mBitmap, "Design", null);

                //Uri uri = Uri.parse(path);

                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("image/*");
                //share.putExtra(Intent.EXTRA_STREAM, uri);
                share.putExtra(Intent.EXTRA_TEXT, "I found something cool!");
                startActivity(Intent.createChooser(share, "Share Your Design!"));
            }
        });

        viewPager = (ViewPager) findViewById(R.id.image_view_pager);
        frameLayoutTextAnimation = findViewById(R.id.animated_text_frame);
        frameLayoutViewPager = findViewById(R.id.view_pager_frame_layout);
        if(viewPager != null) {

            //chaning the landscape mode to either animation or viewpager mode.
                frameLayoutTextAnimation.setVisibility(View.VISIBLE);
                frameLayoutViewPager.setVisibility(View.GONE);

        }

        ImageView imageView = findViewById(R.id.advertisementImage);
        if(imageView != null) {
            Glide.with(PlayListDetaildViewActivity.this).load("http://www.bloggs74.com/wp-content/uploads/resadv251.jpg?39a0e9").into(imageView);
        }

        TextView scroll_line = findViewById(R.id.scrolling_line);
        if(scroll_line != null){
            Typeface fontBold = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Bold.ttf");
            scroll_line.setTypeface(fontBold);
            scroll_line.setSelected(true);
        }

        TextView tv = findViewById(R.id.advertisementText);
        if(tv != null) {
            Typeface fontRegular = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Regular.ttf");
            tv.setTypeface(fontRegular);
        }

        Typeface fontBold = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Bold.ttf");
        textView = findViewById(R.id.breakingNewstext);

        //this is for landscape textviews
        if(textView != null) {

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
                            Log.d("Inside", "postDelayed"+String.valueOf(i));
                            if(i==3)
                                i=0;
                            textView.startAnimation(zoomIn);
                            textView.setText(posts.get(1).getTexts().get(i++));
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

        // show or hide Ad Fragment in portrait mode
        if(frameShowAdd != null) {
            FragmentManager fm = getFragmentManager();
            addShowHideListener(fm.findFragmentById(R.id.frameShowAdd), true);

            // default first item selected
            int currentPosition = adapter.getCurrentItemSelectedPosition();
            adapter.itemClicked(currentPosition);
        }


        if(ll_forward != null && ll_rewind != null){
            ll_forward.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!isSuffleEnabled){
                        int currentPosition = adapter.getCurrentItemSelectedPosition();
                        adapter.itemClicked(currentPosition+1);
                        recycler.smoothScrollToPosition(currentPosition+1);
                    } else {
                        // get random positions
                        Random rand = new Random();
                        int currentPositionAdapter = adapter.getCurrentItemSelectedPosition();
                        int currentPosition = listPlaylistItemPositions.get(rand.nextInt(listPlaylistItemPositions.size()));
                        if(currentPositionAdapter == currentPosition){
                            currentPosition = listPlaylistItemPositions.get(rand.nextInt(listPlaylistItemPositions.size()));
                        }
                        adapter.itemClicked(currentPosition);
                        recycler.smoothScrollToPosition(currentPosition);
                    }
                }
            });

            ll_rewind.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!isSuffleEnabled){
                        int currentPosition = adapter.getCurrentItemSelectedPosition();
                        adapter.itemClicked(currentPosition-1);
                        if(currentPosition != 0){
                            recycler.smoothScrollToPosition(currentPosition-1);
                        }
                    } else {
                        // get random positions
                        Random rand = new Random();
                        int currentPositionAdapter = adapter.getCurrentItemSelectedPosition();
                        int currentPosition = listPlaylistItemPositions.get(rand.nextInt(listPlaylistItemPositions.size()));
                        if(currentPositionAdapter == currentPosition){
                            currentPosition = listPlaylistItemPositions.get(rand.nextInt(listPlaylistItemPositions.size()));
                        }
                        adapter.itemClicked(currentPosition);
                        recycler.smoothScrollToPosition(currentPosition);
                    }
                }
            });
        }

        if(ll_suffle != null){
            ll_suffle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(isSuffleEnabled){
                        iv_suffle.setColorFilter(getResources().getColor(R.color.md_grey_400));
                        isSuffleEnabled = false;
                    } else {
                        iv_suffle.setColorFilter(getResources().getColor(R.color.color_black));
                        isSuffleEnabled = true;
                    }

                }
            });
        }

        if(ll_play != null){
            ll_play.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!isPlayEnabled) {
                        isPlayEnabled = true;
                        iv_play.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.ic_pause));
                        if(FragmentImage.frameLayoutViewPager.getVisibility() == View.VISIBLE)
                            FragmentImage.playView.performClick();
                        else
                            FragmentImage.play.performClick();

                    } else {
                        isPlayEnabled = false;
                        iv_play.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.ic_play));
                        if(FragmentImage.frameLayoutViewPager.getVisibility() == View.VISIBLE)
                            FragmentImage.pauseView.performClick();
                        else
                            FragmentImage.pause.performClick();
                    }
                }
            });
        }

        if(ll_repeat != null){
            ll_repeat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FragmentVideoORImageView fragment = (FragmentVideoORImageView)
                            getFragmentManager().findFragmentByTag("videoFragment");
                    fragment.repeatPlay();
                }
            });
        }
    }

    private void addShowHideListener(Fragment fragment, boolean isShowAdd) {

        if(isShowAdd){
            frameShowAdd.setVisibility(View.VISIBLE);
            // dynamically set weight for recycler view , if add is not their
            LinearLayout.LayoutParams lay = (LinearLayout.LayoutParams) recycler.getLayoutParams();
            lay.weight = 0.45f;
            recycler.setLayoutParams(lay);
        } else {
            // dynamically set weight for recycler view , if add is not their
            LinearLayout.LayoutParams lay = (LinearLayout.LayoutParams) recycler.getLayoutParams();
            lay.weight = 0.53f;
            recycler.setLayoutParams(lay);
        }
    }

    private void loadData() {
        posts = new ArrayList<>();
        listPlaylistItemPositions = new ArrayList<>();
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

        PlayListModel p1 = new PlayListModel("0",image1, head1, view1, likes1, comments1, text1, false);
        PlayListModel p2 = new PlayListModel("1",image2, head2, view2, likes2, comments2, text2, false);
        PlayListModel p3 = new PlayListModel("2",image3, head3, view3, likes3, comments3, text3, false);

        PlayListModel p4 = new PlayListModel("0",image1, head1, view1, likes1, comments1, text1, false);
        PlayListModel p5 = new PlayListModel("1",image2, head2, view2, likes2, comments2, text2, false);
        PlayListModel p6 = new PlayListModel("2",image3, head3, view3, likes3, comments3, text3, false);

        PlayListModel p7 = new PlayListModel("0",image1, head1, view1, likes1, comments1, text1, false);
        PlayListModel p8 = new PlayListModel("1",image2, head2, view2, likes2, comments2, text2, false);
        PlayListModel p9 = new PlayListModel("2",image3, head3, view3, likes3, comments3, text3, false);

        posts.add(p1);
        listPlaylistItemPositions.add(0);
        posts.add(p2);
        listPlaylistItemPositions.add(1);
        posts.add(p3);
        listPlaylistItemPositions.add(2);

        posts.add(p4);
        listPlaylistItemPositions.add(3);
        posts.add(p5);
        listPlaylistItemPositions.add(4);
        posts.add(p6);
        listPlaylistItemPositions.add(5);

        posts.add(p7);
        listPlaylistItemPositions.add(6);
        posts.add(p8);
        listPlaylistItemPositions.add(7);
        posts.add(p9);
        listPlaylistItemPositions.add(8);
    }

    private void loadAdFragment(ShowAdFragment showAdFragment) {
        Fragment imgFragment = new FragmentImage();
        Bundle bundle = new Bundle();
        bundle.putString("image", posts.get(0).getImage());
        bundle.putString("post_id","0");
        imgFragment.setArguments(bundle);
        // create a FragmentManager
        //FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameShowAdd, showAdFragment, "showAdd");
        fragmentTransaction.add(R.id.frame1, imgFragment, "imgFragment");
        fragmentTransaction.commit();
    }

    public void loadFragment(Fragment fragment1, int position){
        // create a FragmentManager
        FragmentManager fm = getFragmentManager();
        // create a FragmentTransaction to begin the transaction and replace the Fragment
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        // replace the FragmentLayout with new Fragment

        Bundle bundle = new Bundle();
        bundle.putString("post_id", posts.get(position).getPost_id());
        fragment1.setArguments(bundle);

        fragmentTransaction.replace(R.id.frame1, fragment1, "videoFragment");
        fragmentTransaction.commit(); // save the changes
    }

    @Override
    public void onBackPressed()
    {
        // code here to show dialog
        if(getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        else
            finish();
    }

    public void goToNext(){
        if(!isSuffleEnabled){
            int currentPosition = adapter.getCurrentItemSelectedPosition();
            adapter.itemClicked(currentPosition+1);
            recycler.smoothScrollToPosition(currentPosition+1);
        } else {
            // get random positions
            Random rand = new Random();
            int currentPositionAdapter = adapter.getCurrentItemSelectedPosition();
            int currentPosition = listPlaylistItemPositions.get(rand.nextInt(listPlaylistItemPositions.size()));
            if(currentPositionAdapter == currentPosition){
                currentPosition = listPlaylistItemPositions.get(rand.nextInt(listPlaylistItemPositions.size()));
            }
            adapter.itemClicked(currentPosition);
            recycler.smoothScrollToPosition(currentPosition);
        }
    }

    public void goToPrevious(){
        if(!isSuffleEnabled){
            int currentPosition = adapter.getCurrentItemSelectedPosition();
            adapter.itemClicked(currentPosition-1);
            if(currentPosition != 0){
                recycler.smoothScrollToPosition(currentPosition-1);
            }
        } else {
            // get random positions
            Random rand = new Random();
            int currentPositionAdapter = adapter.getCurrentItemSelectedPosition();
            int currentPosition = listPlaylistItemPositions.get(rand.nextInt(listPlaylistItemPositions.size()));
            if(currentPositionAdapter == currentPosition){
                currentPosition = listPlaylistItemPositions.get(rand.nextInt(listPlaylistItemPositions.size()));

            }
            adapter.itemClicked(currentPosition);
            recycler.smoothScrollToPosition(currentPosition);
        }
    }

}

