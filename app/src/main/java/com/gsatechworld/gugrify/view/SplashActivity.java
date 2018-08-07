package com.gsatechworld.gugrify.view;
/**
 * Created by Karthik's on 27-02-2016.
 */
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.flaviofaria.kenburnsview.KenBurnsView;
import com.flaviofaria.kenburnsview.TransitionGenerator;
import com.gsatechworld.gugrify.MyTransitionGenerator;
import com.gsatechworld.gugrify.R;

public class SplashActivity extends Activity implements Animation.AnimationListener{

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 8000;
    private ImageView imageView;
    private TextView textView;
    Animation slide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        imageView = findViewById(R.id.imagelogo);
        textView = findViewById(R.id.welcome_text);
        textView.setVisibility(View.INVISIBLE);
        slide = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down);
        imageView.startAnimation(slide);
        slide.setAnimationListener(this);

        KenBurnsView kbv = (KenBurnsView) findViewById(R.id.image);

        AccelerateDecelerateInterpolator ACCELERATE_DECELERATE = new AccelerateDecelerateInterpolator();

        TransitionGenerator generator = new MyTransitionGenerator(this);
        //duration = 10000ms = 10s and interpolator = ACCELERATE_DECELERATE
        kbv.setTransitionGenerator(generator); //set new transition on kbv

        Typeface fontMedium = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Medium.ttf");
        textView.setTypeface(fontMedium);

//        if (Build.VERSION.SDK_INT >= 21) {
//            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
//        }

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

//        mKenBurns = (KenBurnsView) findViewById(R.id.ken_burns_images);
//        mKenBurns.setImageResource(R.mipmap.news);

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                Intent i = new Intent(SplashActivity.this, WelcomeActivity.class);
                startActivity(i);

                finish();
            }
        }, SPLASH_TIME_OUT);
    }

    public void onAnimationEnd(Animation animation) {
        // Take any action after completing the animation

        // check for fade in animation
        if (animation == slide) {
            textView.setVisibility(View.VISIBLE);
            Animation animation1 = AnimationUtils.loadAnimation(SplashActivity.this, R.anim.fade_in);
            textView.startAnimation(animation1);
        }

    }

    @Override
    public void onAnimationRepeat(Animation animation) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onAnimationStart(Animation animation) {
        // TODO Auto-generated method stub

    }


//    private void setAnimation() {
//        ObjectAnimator scaleXAnimation = ObjectAnimator.ofFloat(findViewById(R.id.welcome_text), "scaleX", 5.0F, 1.0F);
//        scaleXAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
//        scaleXAnimation.setDuration(1200);
//        ObjectAnimator scaleYAnimation = ObjectAnimator.ofFloat(findViewById(R.id.welcome_text), "scaleY", 5.0F, 1.0F);
//        scaleYAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
//        scaleYAnimation.setDuration(1200);
//        ObjectAnimator alphaAnimation = ObjectAnimator.ofFloat(findViewById(R.id.welcome_text), "alpha", 0.0F, 1.0F);
//        alphaAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
//        alphaAnimation.setDuration(1200);
//        AnimatorSet animatorSet = new AnimatorSet();
//        animatorSet.play(scaleXAnimation).with(scaleYAnimation).with(alphaAnimation);
//        animatorSet.setStartDelay(500);
//        animatorSet.start();
//
//        findViewById(R.id.imagelogo).setAlpha(1.0F);
//        Animation anim = AnimationUtils.loadAnimation(this, R.anim.translate_top_to_center);
//        findViewById(R.id.imagelogo).startAnimation(anim);
//    }
}
