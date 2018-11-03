package com.gsatechworld.gugrify.view.playlist;


import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.app.Fragment;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gsatechworld.gugrify.R;

import java.util.ArrayList;

public class FragmentVideoORImageView extends Fragment {
    private ViewPager viewPager;
    private TextView textView;
    Animation zoomIn;
    int i=0;
    Handler mHandler;
    private FrameLayout frameLayoutTextAnimation;
    private FrameLayout frameLayoutViewPager;
    private ArrayList<PlayListModel> posts;
    private Runnable m_handlerTask;
    private Animator anim;
    private boolean isOnPauseCalled = false;
    private boolean isOnResumeCalled = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
// Inflate the layout for this fragment
       View view = inflater.inflate(R.layout.video_or_image_view_fragment, container, false);
        String imageString = getArguments().getString("image");
        posts=  getArguments().getParcelableArrayList("postArray");
        mHandler = new Handler();
        viewPager = (ViewPager)view.findViewById(R.id.image_view_pager);
        frameLayoutTextAnimation = view.findViewById(R.id.animated_text_frame);
        frameLayoutViewPager = view.findViewById(R.id.view_pager_frame_layout);
        if(viewPager != null) {

            //chaning the landscape mode to either animation or viewpager mode.
            frameLayoutTextAnimation.setVisibility(View.VISIBLE);
            frameLayoutViewPager.setVisibility(View.GONE);

//            BreakingNewsViewPagerAdapter b = new BreakingNewsViewPagerAdapter(DisplayBreakingNewsActivity.this);
//            viewPager.setAdapter(b);
//            dotscount = b.getCount();
//            dots = new ImageView[dotscount];
//
//            linearLayout = findViewById(R.id.viewPagerDots);
//
//            for (int i = 0; i < dotscount; i++) {
//
//                dots[i] = new ImageView(DisplayBreakingNewsActivity.this);
//                dots[i].setImageDrawable(ContextCompat.getDrawable(DisplayBreakingNewsActivity.this, R.drawable.non_active_dot));
//
//                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
//                        LinearLayout.LayoutParams.WRAP_CONTENT);
//
//                params.setMargins(8, 0, 8, 0);
//
//                linearLayout.addView(dots[i], params);
//
//            }
//
//            dots[0].setImageDrawable(ContextCompat.getDrawable(DisplayBreakingNewsActivity.this, R.drawable.active_dot));
//
//            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//                @Override
//                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//                }
//
//                @Override
//                public void onPageSelected(int position) {
//
//                    for (int i = 0; i < dotscount; i++) {
//                        dots[i].setImageDrawable(ContextCompat.getDrawable(DisplayBreakingNewsActivity.this, R.drawable.non_active_dot));
//                    }
//
//                    dots[position].setImageDrawable(ContextCompat.getDrawable(DisplayBreakingNewsActivity.this, R.drawable.active_dot));
//
//                }
//
//                @Override
//                public void onPageScrollStateChanged(int state) {
//
//                }
//            });
        }


        Typeface fontBold = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-Bold.ttf");

        textView = view.findViewById(R.id.breakingNewstext);
        textView.setTypeface(fontBold);

        anim = AnimatorInflater.loadAnimator(getActivity().getApplicationContext(), R.animator.zoom_in);
        anim.setTarget(textView);
        anim.start();
        anim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    animator.resume();
                    Log.d(FragmentVideoORImageView.class.getSimpleName(), "called --->  onAnimationStart");
                }
            }

            @Override
            public void onAnimationEnd(final Animator animator) {
                Log.d(FragmentVideoORImageView.class.getSimpleName(), "called --->  onAnimationEnd");
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Log.d("Inside", "postDelayed"+String.valueOf(i));
                            if(i==3) {
                                i = 0;
                            }
                                //      anim = animator;
                            if(isOnPauseCalled){
                                anim.setTarget(textView);
                                textView.setText(posts.get(1).getTexts().get(i++));
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                                    anim.pause();
                                }
                                //isOnPauseCalled = false;
                            } else if(isOnResumeCalled){
                                anim.setTarget(textView);
                                textView.setText(posts.get(1).getTexts().get(i++));
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                                    anim.resume();
                                }
                                anim.start();
                                //isOnResumeCalled = false;
                            } else {
                                anim.setTarget(textView);
                                textView.setText(posts.get(1).getTexts().get(i++));
                                anim.start();
                            }
                                //start your activity here
                        }

                    }, 2000L);
            }

            @Override
            public void onAnimationCancel(Animator animator) {
                Log.d(FragmentVideoORImageView.class.getSimpleName(), "called --->  onAnimationCancel");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                   // animator.pause();
                }
            }

            @Override
            public void onAnimationRepeat(Animator animator) {
                Log.d(FragmentVideoORImageView.class.getSimpleName(), "called --->  onAnimationRepeat");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                   // animator.pause();
                }
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            anim.addPauseListener(new Animator.AnimatorPauseListener() {
                @Override
                public void onAnimationPause(Animator animator) {
                    Log.d(FragmentVideoORImageView.class.getSimpleName(), "called --->  onAnimationPause");
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        animator.pause();
                    }
                }

                @Override
                public void onAnimationResume(Animator animator) {
                    Log.d(FragmentVideoORImageView.class.getSimpleName(), "called --->  onAnimationResume");
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        animator.resume();
                        //isOnPauseCalled = false;
                       // isOnResumeCalled = true;
                    }
                }
            });
        }

//        if(textView != null) {
//
//            textView.setTypeface(fontBold);
//
//            zoomIn = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.zoom_in);
//            textView.setText(posts.get(0).getTexts().get(i++));
////            zoomIn.setRepeatCount(Animation.INFINITE);
//            textView.setAnimation(zoomIn);
//
//
//
//            zoomIn.setAnimationListener(new Animation.AnimationListener() {
//                @Override
//                public void onAnimationStart(Animation animation) {
//
//                }
//
//                @Override
//                public void onAnimationEnd(Animation animation) {
//                    zoomIn = animation;
//                    Log.d("Reached on end ", "true");
//
//                    mHandler.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            Log.d("Inside", "postDelayed"+String.valueOf(i));
//                            if(i==3)
//                                i=0;
//                            textView.startAnimation(zoomIn);
//                            textView.setText(posts.get(1).getTexts().get(i++));
//                            //start your activity here
//                        }
//
//                    }, 2000L);
//                }
//
//                @Override
//                public void onAnimationRepeat(Animation animation) {
//
//                    Log.d("Reached on repeat ", "true");
//                }
//            });
//        }

        ImageView image = view.findViewById(R.id.enlarge);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            }
        });

        return view;
    }


    public void stopPlay() {
        if(textView != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                anim.pause();
                isOnPauseCalled = true;
                isOnResumeCalled = false;
            }
        }
    }


    public void resumePlay() {
        if(textView != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                anim.start();
                isOnResumeCalled = true;
                isOnPauseCalled = false;
            }
        }
    }

    public void repeatPlay() {
        if(textView != null){
            mHandler.removeCallbacks(null);
            anim.cancel();
            anim.start();
        }
    }

    @Override
    public void onDestroy () {
        mHandler.removeCallbacksAndMessages(null);
        super.onDestroy ();

    }

    @Override
    public void onStop () {
        mHandler.removeCallbacksAndMessages(null);
        //mHandler.removeCallbacks(null);
        super.onStop ();
    }
}
