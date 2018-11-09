package com.gsatechworld.gugrify.view;
/**
 * Created by Karthik's on 27-02-2016.
 */

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.flaviofaria.kenburnsview.KenBurnsView;
import com.flaviofaria.kenburnsview.TransitionGenerator;
import com.gsatechworld.gugrify.MyTransitionGenerator;
import com.gsatechworld.gugrify.R;
import com.gsatechworld.gugrify.model.retrofit.ApiClient;
import com.gsatechworld.gugrify.model.retrofit.ApiInterface;
import com.gsatechworld.gugrify.model.retrofit.GetMainAdvertisement;
import com.gsatechworld.gugrify.utils.Utility;
import com.gsatechworld.gugrify.view.authentication.LoginActivity;
import com.gsatechworld.gugrify.view.dashboard.DashboardActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends Activity implements Animation.AnimationListener {

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 6000;
    private ImageView imageView;
    private TextView textView;
    private ApiInterface apiService;
    Animation slide;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //running in background
        new AsyncCaller().execute();

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
               /* boolean b = Utility.checkInternet(SplashActivity.this);
                if(!b){
                    dialog = new Dialog(SplashActivity.this, R.style.NewDialog);//android.R.style.Theme_Dialog //android.R.style.Theme_Black_NoTitleBar_Fullscreen
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.dialog_no_internet);
                    // dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                    //dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    dialog.setCancelable(false);
                    dialog.setCanceledOnTouchOutside(false);

                    // dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
                    dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
//        ImageView iv_close = (ImageView)dialog.findViewById(R.id.iv_close);
                    dialog.show();

                }*/

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

    private class AsyncCaller extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {

            //this method will be running on background thread so don't update UI frome here
            //do your long running http tasks here,you dont want to pass argument and u can access the parent class' variable url over here

            apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<GetMainAdvertisement> call = apiService.getMainAdvertisement();

            call.enqueue(new Callback<GetMainAdvertisement>() {
                @Override
                public void onResponse(Call<GetMainAdvertisement> call, Response<GetMainAdvertisement> response) {
                    GetMainAdvertisement advertisement = null;
                    if (response.isSuccessful()) {
                        Log.d("Reached here", "true");
                        advertisement = response.body();
                        DashboardActivity.result = advertisement;

                    } else {
                        Toast.makeText(SplashActivity.this, "Server error!!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<GetMainAdvertisement> call, Throwable t) {
                    // Log error here since request failed
                    Toast.makeText(SplashActivity.this, "Server error!!", Toast.LENGTH_SHORT).show();
                }
            });

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
        }

    }

    @Override
    public void onBackPressed() {
        dialog.cancel();
        finish();
    }
}
