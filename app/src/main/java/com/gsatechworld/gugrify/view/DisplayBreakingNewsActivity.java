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
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gsatechworld.gugrify.NewsSharedPreferences;
import com.gsatechworld.gugrify.R;
import com.gsatechworld.gugrify.fragment.FragmentImage;
import com.gsatechworld.gugrify.fragment.FragmentLayout;
import com.gsatechworld.gugrify.model.PostsByCategory;
import com.gsatechworld.gugrify.view.adapters.BreakingNewsRecyclerAdapter;
import com.gsatechworld.gugrify.view.adapters.BreakingNewsViewPagerAdapter;

import java.util.ArrayList;

public class DisplayBreakingNewsActivity extends AppCompatActivity {
    public ArrayList<PostsByCategory> posts = new ArrayList<>();
    Animation zoomIn;
    ViewPager viewPager;
    BreakingNewsRecyclerAdapter adapter;
    RecyclerView recycler;
    FrameLayout frameLayoutTextAnimation, frameLayoutViewPager;
    NewsSharedPreferences sharedPreferences;
    TextView textView;
    private int dotscount;
    private LinearLayout linearLayout;
    private ImageView dots[];
    int i=0;
    Handler mHandler;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        //  if the screen rotates to landscape (bigger) mode, hide the status bar
        if(getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
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

        mHandler = new Handler();
//        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        sharedPreferences = NewsSharedPreferences.getInstance(DisplayBreakingNewsActivity.this);

        recycler = findViewById(R.id.posts_recycler);
        frameLayoutTextAnimation = findViewById(R.id.animated_text_frame);
        frameLayoutViewPager = findViewById(R.id.view_pager_frame_layout);
        viewPager = findViewById(R.id.image_view_pager);
        if(viewPager != null) {

            //chaning the landscape mode to either animation or viewpager mode.
            if(sharedPreferences.getClickedPosition()%2 == 0){
                frameLayoutTextAnimation.setVisibility(View.VISIBLE);
                frameLayoutViewPager.setVisibility(View.GONE);
            }
            else {
                frameLayoutTextAnimation.setVisibility(View.GONE);
                frameLayoutViewPager.setVisibility(View.VISIBLE);
            }
            BreakingNewsViewPagerAdapter b = new BreakingNewsViewPagerAdapter(DisplayBreakingNewsActivity.this);
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
        }

        ImageView imageView = findViewById(R.id.advertisementImage);
        if(imageView != null) {
            Glide.with(DisplayBreakingNewsActivity.this).load("http://www.bloggs74.com/wp-content/uploads/resadv251.jpg?39a0e9").into(imageView);
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

        //this will fetch the data from server.
        loadData();

        /*ImageView reduce = findViewById(R.id.reduceImage);
        if(reduce != null) {
            reduce.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                }
            });
        }*/
        Typeface fontBold = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Bold.ttf");
        textView = findViewById(R.id.breakingNewstext);

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
                            textView.setText(posts.get(sharedPreferences.getClickedPosition()).getTexts().get(i++));
                            //start your activity here
                        }

                    }, 2000L);

                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                    Log.d("Reached on repeat ", "true");
                }
            });

        }


        if(recycler != null) {
            FragmentImage fragment1 = new FragmentImage();
            FragmentLayout fragment2 = new FragmentLayout();
            loadFragment(fragment1, fragment2, sharedPreferences.getClickedPosition());

            adapter = new BreakingNewsRecyclerAdapter(DisplayBreakingNewsActivity.this, posts);
            recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            recycler.setAdapter(adapter);
        }

    }

    public void loadFragment(Fragment fragment1, Fragment fragment2, int position){
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

    public void loadData(){
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
    public void onBackPressed()
    {
        // code here to show dialog
        if(getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        else
            finish();
    }

    //changing the adapter to go to next item
    public void goToNext(){

        FragmentImage fragment1 = new FragmentImage();
        FragmentLayout fragment2 = new FragmentLayout();

        if(adapter.previous == (adapter.getItemCount()-1) - 1){
            adapter.clicked[0] = true;
            //loading both the fragments with clicked position
            loadFragment(fragment1, fragment2, 0);

            adapter.clicked[(adapter.getItemCount()-1) - 1] = false;
            recycler.smoothScrollToPosition(0);
            adapter.previous = 0;
        }
        else {
            adapter.clicked[adapter.previous] = false;
            adapter.clicked[adapter.previous + 1] = true;
            //loading both the fragments with clicked position
            loadFragment(fragment1, fragment2, adapter.previous + 1);

            recycler.smoothScrollToPosition((adapter.previous + 1) + 1); //added this extra 1 to scroll the recycler to correct position
            adapter.previous = adapter.previous + 1;
        }
        adapter.notifyDataSetChanged();
    }

    //changing the adapter to go to previous item
    public void goToPrevious(){

        FragmentImage fragment1 = new FragmentImage();
        FragmentLayout fragment2 = new FragmentLayout();

        if(adapter.previous == 0){
            adapter.clicked[(adapter.getItemCount()-1)-1] = true;
            //loading both the fragments with clicked position
            loadFragment(fragment1, fragment2, (adapter.getItemCount()-1)-1);

            adapter.clicked[adapter.previous] = false;
            recycler.smoothScrollToPosition(((adapter.getItemCount()-1)-1)+1); //added this extra 1 to scroll the recycler to correct position
            adapter.previous = (adapter.getItemCount()-1)-1;
        }
        else {
            adapter.clicked[adapter.previous - 1] = true;
            //loading both the fragments with clicked position
            loadFragment(fragment1, fragment2, adapter.previous - 1);

            adapter.clicked[adapter.previous] = false;
            recycler.smoothScrollToPosition(adapter.previous);
            adapter.previous = adapter.previous - 1;
        }
        adapter.notifyDataSetChanged();
    }
}
