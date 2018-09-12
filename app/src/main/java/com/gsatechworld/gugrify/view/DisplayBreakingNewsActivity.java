package com.gsatechworld.gugrify.view;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gsatechworld.gugrify.NewsSharedPreferences;
import com.gsatechworld.gugrify.R;
import com.gsatechworld.gugrify.fragment.FragmentImage;
import com.gsatechworld.gugrify.fragment.FragmentLayout;
import com.gsatechworld.gugrify.model.PostsByCategory;
import com.gsatechworld.gugrify.view.adapters.BreakingNewsRecyclerAdapter;

import java.util.ArrayList;

public class DisplayBreakingNewsActivity extends AppCompatActivity {
    ArrayList<PostsByCategory> posts = new ArrayList<>();
    Animation zoomIn;
    NewsSharedPreferences sharedPreferences;
    TextView textView;
    int i=0;
    Handler mHandler;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_breaking_news);
        mHandler = new Handler();
        sharedPreferences = NewsSharedPreferences.getInstance(DisplayBreakingNewsActivity.this);

        RecyclerView recycler = findViewById(R.id.posts_recycler);

        ImageView imageView = findViewById(R.id.advertisementImage);
        if(imageView != null) {
            Glide.with(DisplayBreakingNewsActivity.this).load("http://www.bloggs74.com/wp-content/uploads/resadv251.jpg?39a0e9").into(imageView);
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
            loadFragment(fragment1, fragment2, 0);

            BreakingNewsRecyclerAdapter adapter = new BreakingNewsRecyclerAdapter(DisplayBreakingNewsActivity.this, posts);
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
        bundle.putString("image", posts.get(position).getImage());
        fragment1.setArguments(bundle);

        ArrayList<String> list = new ArrayList<>();
        list.add(posts.get(position).getViews());
        list.add(posts.get(position).getLikes());

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

        PostsByCategory p1 = new PostsByCategory(image1, head1, desc1, view1, likes1, comments1, text1);
        PostsByCategory p2 = new PostsByCategory(image2, head2, desc2, view2, likes2, comments2, text2);
        PostsByCategory p3 = new PostsByCategory(image3, head3, desc3, view3, likes3, comments3, text3);

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

    /*@Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        if(newConfig.orientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT){
            onCreate(new Bundle());
            super.onConfigurationChanged(newConfig);
        }
        else {
            setContentView(R.layout.activity_display_breaking_news);
            ImageView image = findViewById(R.id.portraitImage);
            Glide.with(DisplayBreakingNewsActivity.this).load("http://www.bloggs74.com/wp-content/uploads/resadv251.jpg?39a0e9").into(image);
            ImageView reduce = findViewById(R.id.reduceImage);
            reduce.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                }
            });
            super.onConfigurationChanged(newConfig);
        }
    }*/
}
