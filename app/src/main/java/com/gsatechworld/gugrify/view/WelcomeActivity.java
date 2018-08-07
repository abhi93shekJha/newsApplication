package com.gsatechworld.gugrify.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gsatechworld.gugrify.NewsSharedPreferences;
import com.gsatechworld.gugrify.R;
import com.gsatechworld.gugrify.SelectLanguageAndCities;

public class WelcomeActivity extends AppCompatActivity {

    ViewPager viewPager;
    LinearLayout dotsLayout;
    boolean once = true;
    private MyViewPagerAdapter myViewPagerAdapter;
    Button btnPrevious;
    Button btnDone, btnNext;
    ImageView leftArrow, rightArrow;
    NewsSharedPreferences prefManager;
    int[] colorsActive, colorsInactive;
    private int[] layouts;
    private ImageView[] dots;
    TextView tv1, tv2;

    // Checking for first time launch - before calling setContentView()
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        prefManager = NewsSharedPreferences.getInstance(this);
        if (!prefManager.getIsFirstTime()) {
            launchHomeScreen();
            finish();
        }

        // Making notification bar transparent
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        setContentView(R.layout.activity_welcome);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        dotsLayout = (LinearLayout) findViewById(R.id.layoutDots);
        btnPrevious = (Button) findViewById(R.id.btn_skip);
        rightArrow = (ImageView) findViewById(R.id.right_arrow);
        leftArrow = (ImageView) findViewById(R.id.left_arrow);
        btnDone = (Button) findViewById(R.id.btn_done);
        btnNext = (Button) findViewById(R.id.btn_next);

        colorsActive = getResources().getIntArray(R.array.array_dot_active);
        colorsInactive = getResources().getIntArray(R.array.array_dot_inactive);

        Typeface fontMedium = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Medium.ttf");
        Typeface fontBold = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Bold.ttf");
        Typeface fontRegular = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Regular.ttf");

        btnPrevious.setTypeface(fontRegular);
        btnDone.setTypeface(fontRegular);
        btnNext.setTypeface(fontRegular);

        // layouts of all welcome sliders
        // add few more layouts if you want
        layouts = new int[]{
                R.layout.welcome_file1,
                R.layout.welcome_file2,
                R.layout.welcome_file3,
                R.layout.welcome_file4};


        // making notification bar transparent
        changeStatusBarColor();

        myViewPagerAdapter = new MyViewPagerAdapter();
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);

        // adding bottom dots
        addBottomDots(0);

        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // checking for last page
                // if last page home screen will be launched
                int current = getItem(-1);
                if (current < layouts.length) {
                    // move to next screen
                    viewPager.setCurrentItem(current);
                }
            }
        });

        rightArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // checking for last page
                // if last page home screen will be launched
                int current = getItem(+1);
                if (current < layouts.length) {
                    // move to next screen
                    viewPager.setCurrentItem(current);
                } else {
                    launchHomeScreen();
                }
            }
        });

        leftArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int current = getItem(-1);
                if (current < layouts.length) {
                    // move to next screen
                    viewPager.setCurrentItem(current);
                }
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // checking for last page
                // if last page home screen will be launched
                int current = getItem(+1);
                if (current < layouts.length) {
                    // move to next screen
                    viewPager.setCurrentItem(current);
                }
            }
        });

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchHomeScreen();
            }
        });
    }

    public void launchHomeScreen(){
        prefManager.setIsFirstTime(false);
        startActivity(new Intent(WelcomeActivity.this, SelectLanguageAndCities.class));
        finish();
    }

    private void addBottomDots(int currentPage) {

        dots = new ImageView[layouts.length];
        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(getResources().getDrawable(R.drawable.nonselecteditem_dot));
            dots[i].setColorFilter(colorsInactive[currentPage]);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );

            params.setMargins(10, 0, 10, 0);
            dotsLayout.addView(dots[i], params);
        }

        //dots[0].setImageDrawable(getResources().getDrawable(R.drawable.selecteditem_dot));

        if (dots.length > 0)
            dots[currentPage].setColorFilter(colorsActive[currentPage]);
    }

    /**
     * Making notification bar transparent
     */
    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    private int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }


    //  viewpager change listener
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            addBottomDots(position);

            Animation a1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_right);
            Animation a2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_left);

            tv1.startAnimation(a1);
            tv2.startAnimation(a2);

            if(position == 0){
                btnPrevious.setVisibility(View.GONE);
                leftArrow.setVisibility(View.GONE);
                rightArrow.setVisibility(View.VISIBLE);
                btnNext.setVisibility(View.VISIBLE);
                btnDone.setVisibility(View.GONE);
            }
            // changing the next button text 'NEXT' / 'GOT IT'
            else if (position == layouts.length - 1) {
                // last page. make button text to GOT IT
                rightArrow.setVisibility(View.GONE);
                leftArrow.setVisibility(View.VISIBLE);
                btnNext.setVisibility(View.GONE);
                btnDone.setVisibility(View.VISIBLE);
                btnPrevious.setVisibility(View.VISIBLE);
                btnPrevious.setTextColor(colorsInactive[position]);
                btnDone.setTextColor(colorsInactive[position]);
            } else {
                // still pages are left
                btnDone.setVisibility(View.GONE);
                btnNext.setVisibility(View.VISIBLE);
                btnNext.setTextColor(colorsInactive[position]);
                leftArrow.setVisibility(View.VISIBLE);
                rightArrow.setVisibility(View.VISIBLE);
                btnPrevious.setVisibility(View.VISIBLE);
                btnPrevious.setTextColor(colorsInactive[position]);
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };
    /**
     * View pager adapter
     */
    public class MyViewPagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;

        public MyViewPagerAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(layouts[position], container, false);
            container.addView(view);

            Typeface fontMedium = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Medium.ttf");
            Typeface fontBold = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Bold.ttf");
            Typeface fontRegular = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Regular.ttf");

            tv1 = view.findViewById(R.id.welcome_file1_text1);
            tv2 = view.findViewById(R.id.welcome_file1_text2);
            TextView tv3 = view.findViewById(R.id.welcome_file1_text3);

            tv1.setTypeface(fontMedium);
            tv2.setTypeface(fontRegular);
            tv3.setTypeface(fontRegular);

            if(once){
                once = false;
                Animation a1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_right);
                Animation a2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_left);
                tv1.startAnimation(a1);
                tv2.startAnimation(a2);
            }


            Animation a3 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);

            //imageView.startAnimation(a3);

            return view;
        }

        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }

}
