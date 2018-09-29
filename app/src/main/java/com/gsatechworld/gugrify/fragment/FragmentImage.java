package com.gsatechworld.gugrify.fragment;


import android.app.Fragment;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gsatechworld.gugrify.NewsSharedPreferences;
import com.gsatechworld.gugrify.R;
import com.gsatechworld.gugrify.view.DisplayBreakingNewsActivity;
import com.gsatechworld.gugrify.view.adapters.BreakingNewsViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class FragmentImage extends Fragment {
    TextView animatedTextView;
    String post_id;
    Animation zoomIn, animFadeIn, animFadeOut;
    Handler mHandler, animateHandler;
    ViewPager viewPager;
    private int dotscount;
    private LinearLayout linearLayout, pausePlayLayout;
    FrameLayout frameLayoutTextAnimation, frameLayoutViewPager;
    ImageView previous, pause, play, next;
    NewsSharedPreferences sharedPreferences;
    RelativeLayout postDetailFragmentImage;
    private ImageView dots[];
    boolean first = true;
    int i=0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
// Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.display_breaking_news_image_fragment, container, false);
        post_id = getArguments().getString("post_id");
        final ArrayList<String> texts = (ArrayList<String>) getPostDetailsFromServer(post_id);
        animateHandler = new Handler();

        postDetailFragmentImage = view.findViewById(R.id.fragment_image_layout);
        animatedTextView = view.findViewById(R.id.breakingNewstextWithAnimation);
        animFadeOut = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.fade_out);

        //this block of code is for pausing and play the visible animations
        pausePlayLayout = view.findViewById(R.id.pausePlayNextPreviousLayout);
        animFadeIn = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.pause_play_fade_in);
        postDetailFragmentImage.setOnClickListener(new View.OnClickListener() {
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
        }); //this is the end of showing play and pause buttons


        sharedPreferences = NewsSharedPreferences.getInstance(getActivity().getApplicationContext());
        mHandler = new Handler();
        frameLayoutTextAnimation = view.findViewById(R.id.animated_text_frame);
        frameLayoutViewPager = view.findViewById(R.id.view_pager_frame_layout);

        //setting animation for text
        Typeface fontBold = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-Bold.ttf");

        if(animatedTextView != null) {

            animatedTextView.setTypeface(fontBold);

            zoomIn = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.zoom_in);
            animatedTextView.setText(texts.get(0));
            animatedTextView.setAnimation(zoomIn);

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
                            if(i >= 2)
                                i=-1;
                            animatedTextView.setText(texts.get(++i));
                            animatedTextView.startAnimation(zoomIn);
//                            animatedTextView.setText(texts.get(i++));
                            //start your activity here
                        }

                    }, 3000L);

                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                    Log.d("Reached on repeat ", "true");
                }
            });

        } // till here I have set the text animation

        //pausing and playing the animation
        previous = view.findViewById(R.id.previousButton);
        pause = view.findViewById(R.id.pauseButton);
        play = view.findViewById(R.id.playButton);
        next = view.findViewById(R.id.nextButton);

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mHandler.removeCallbacksAndMessages(null);
                animatedTextView.setVisibility(View.GONE);

                view.setVisibility(View.GONE);
                play.setVisibility(View.VISIBLE);

                animateHandler.removeCallbacksAndMessages(null);
                pausePlayLayout.setVisibility(View.VISIBLE);

            }
        });

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                animatedTextView.setVisibility(View.VISIBLE);
                if(i == 2)
                    i = -1;
                animatedTextView.setText(texts.get(++i));
                animatedTextView.startAnimation(zoomIn);

                view.setVisibility(View.GONE);
                pause.setVisibility(View.VISIBLE);

                pausePlayLayout.startAnimation(animFadeOut);
                pausePlayLayout.setVisibility(View.GONE);
            }
        }); // pausing and playing stops here


        //here is the code for setting viewpager
        viewPager = view.findViewById(R.id.image_view_pager);

        //here I am setting the visibility of viewPage and textAnimations according to item selected
        if(sharedPreferences.getClickedPosition()%2 == 0){
            frameLayoutTextAnimation.setVisibility(View.VISIBLE);
            frameLayoutViewPager.setVisibility(View.GONE);
        }
        else {
            frameLayoutTextAnimation.setVisibility(View.GONE);
            frameLayoutViewPager.setVisibility(View.VISIBLE);
        }

        BreakingNewsViewPagerAdapter b = new BreakingNewsViewPagerAdapter(getActivity().getApplicationContext());
        viewPager.setAdapter(b);
        dotscount = b.getCount();
        dots = new ImageView[dotscount];

        linearLayout = view.findViewById(R.id.viewPagerDots);

        for (int i = 0; i < dotscount; i++) {

            dots[i] = new ImageView(getActivity().getApplicationContext());
            dots[i].setImageDrawable(ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.non_active_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(8, 0, 8, 0);

            linearLayout.addView(dots[i], params);

        }

        dots[0].setImageDrawable(ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.active_dot));

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                for (int i = 0; i < dotscount; i++) {
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.non_active_dot));
                }

                dots[position].setImageDrawable(ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.active_dot));

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        }); //till here I have set the viewpager

        //code for changing to landscape mode
        ImageView image = view.findViewById(R.id.enlarge);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            }
        }); //changes the activity to landscape mode

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    List<String> getPostDetailsFromServer(String post_id){
        DisplayBreakingNewsActivity activity = new DisplayBreakingNewsActivity();

        ArrayList<ArrayList<String>> texts = new ArrayList<>();

        ArrayList<String> text1 = new ArrayList<>();
        ArrayList<String> text2 = new ArrayList<>();
        ArrayList<String> text3 = new ArrayList<>();

        texts.add(text1);
        texts.add(text2);
        texts.add(text3);

        text1.add("Breaking News1");
        text1.add("ಒಟಿ ತಂತ್ರಜ್ಞರ ಬೇಡಿಕೆಗೆ ತಕ್ಕಂತೆ ಹುದ್ದೆ ಭರ್ತಿ ಮಾಡಿ - ಯೂನಿಯನ್ ಆಗ್ರಹ");
        text1.add("ಬೆಂಗಳೂರು - ಆಪರೇಷನ್ ಥಿಯೇಟರ್ ತಂತ್ರಜ್ಞರ ಕೋರ್ಸ್ ಮುಗಿಸಿ ಹೊರ ಬರುವವರಿಗೆ ಉದ್ಯೋಗ ಸೃಷ್ಟಿಯಾಗದಿರುವ ಬಗ್ಗೆ ಅಭ್ಯರ್ಥಿಗಳಲ್ಲಿ ");
        text1.add("ವಸಂತನಗರದ ಗುರುನಾನಕ್ ಭವನದಲ್ಲಿ ಹಮ್ಮಿಕೊಂಡಿದ್ದ ಪದಾಧಿಕಾರಿಗಳ ಸಭೆಯಲ್ಲಿ ಮಾತನಾಡಿದರು. ");

        text2.add("Breaking News2");
        text2.add("ಒಟಿ ತಂತ್ರಜ್ಞರ ಬೇಡಿಕೆಗೆ ತಕ್ಕಂತೆ ಹುದ್ದೆ ಭರ್ತಿ ಮಾಡಿ - ಯೂನಿಯನ್ ಆಗ್ರಹ");
        text2.add("ಬೆಂಗಳೂರು - ಆಪರೇಷನ್ ಥಿಯೇಟರ್ ತಂತ್ರಜ್ಞರ ಕೋರ್ಸ್ ಮುಗಿಸಿ ಹೊರ ಬರುವವರಿಗೆ ಉದ್ಯೋಗ ಸೃಷ್ಟಿಯಾಗದಿರುವ ಬಗ್ಗೆ ಅಭ್ಯರ್ಥಿಗಳಲ್ಲಿ ");
        text2.add("ವಸಂತನಗರದ ಗುರುನಾನಕ್ ಭವನದಲ್ಲಿ ಹಮ್ಮಿಕೊಂಡಿದ್ದ ಪದಾಧಿಕಾರಿಗಳ ಸಭೆಯಲ್ಲಿ ಮಾತನಾಡಿದರು. ");

        text3.add("Breaking News3");
        text3.add("ಒಟಿ ತಂತ್ರಜ್ಞರ ಬೇಡಿಕೆಗೆ ತಕ್ಕಂತೆ ಹುದ್ದೆ ಭರ್ತಿ ಮಾಡಿ - ಯೂನಿಯನ್ ಆಗ್ರಹ");
        text3.add("ಬೆಂಗಳೂರು - ಆಪರೇಷನ್ ಥಿಯೇಟರ್ ತಂತ್ರಜ್ಞರ ಕೋರ್ಸ್ ಮುಗಿಸಿ ಹೊರ ಬರುವವರಿಗೆ ಉದ್ಯೋಗ ಸೃಷ್ಟಿಯಾಗದಿರುವ ಬಗ್ಗೆ ಅಭ್ಯರ್ಥಿಗಳಲ್ಲಿ ");
        text3.add("ವಸಂತನಗರದ ಗುರುನಾನಕ್ ಭವನದಲ್ಲಿ ಹಮ್ಮಿಕೊಂಡಿದ್ದ ಪದಾಧಿಕಾರಿಗಳ ಸಭೆಯಲ್ಲಿ ಮಾತನಾಡಿದರು. ");

        return texts.get(Integer.parseInt(post_id));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((DisplayBreakingNewsActivity)getActivity()).goToNext();
            }
        });

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((DisplayBreakingNewsActivity)getActivity()).goToPrevious();
            }
        });
    }
}
