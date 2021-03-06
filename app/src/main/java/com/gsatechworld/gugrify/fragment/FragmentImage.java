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
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.gsatechworld.gugrify.NewsSharedPreferences;
import com.gsatechworld.gugrify.R;
import com.gsatechworld.gugrify.view.DisplayBreakingNewsActivity;
import com.gsatechworld.gugrify.view.adapters.BreakingNewsViewPagerAdapter;
import com.gsatechworld.gugrify.view.dashboard.AutoScrollViewPager;
import com.gsatechworld.gugrify.view.playlist.PlayListDetaildViewActivity;

import java.util.ArrayList;
import java.util.List;

public class FragmentImage extends Fragment {
    TextView animatedTextView;
    String id, image, headlines, description, selection, array;
    Animation zoomIn, animFadeIn, animFadeOut, animFadeIn1, animFadeOut1;
    Handler mHandler, animateHandler;
    AutoScrollViewPager viewPager;
    private int dotscount;
    private LinearLayout linearLayout, pausePlayLayout;
    public static LinearLayout pausePlayLayout1;
    BreakingNewsViewPagerAdapter b;
    public static FrameLayout frameLayoutTextAnimation, frameLayoutViewPager;
    public static ImageView previous, pause, play, next, previousView, pauseView, playView, nextView;
    NewsSharedPreferences sharedPreferences;
    RelativeLayout postDetailFragmentImage;
    private ImageView dots[];
    int i = 0;
    List<String> texts;
    boolean fromPlaylistDetail;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
// Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.display_breaking_news_image_fragment, container, false);
        animateHandler = new Handler();

        //getting the texts from postDetails
        selection = DisplayBreakingNewsActivity.postDetails.getResult().get(0).getSelection();
        if (!selection.equalsIgnoreCase("image_arrays")) {
            texts = new ArrayList<>();
            texts.add("Big breaking");
            texts.addAll(DisplayBreakingNewsActivity.postDetails.getResult().get(0).getImageArray());
        } else {
            texts = DisplayBreakingNewsActivity.postDetails.getResult().get(0).getImageArray();
        }

        postDetailFragmentImage = view.findViewById(R.id.fragment_image_layout);
        animatedTextView = view.findViewById(R.id.breakingNewstextWithAnimation);
        if (getActivity() != null) {
            animFadeOut = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.fade_out);
            animFadeOut.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    Log.d("Yes", "animation ended");
                    pausePlayLayout.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            animFadeOut1 = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.fade_out);
        }


        frameLayoutTextAnimation = view.findViewById(R.id.animated_text_frame);
        frameLayoutViewPager = view.findViewById(R.id.view_pager_frame_layout);


        //checking if this activity comes from PlaylistDetailsActivity and hence hide the rotating button
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            fromPlaylistDetail = bundle.getBoolean("key", false);
        }
        ImageView enlarge = view.findViewById(R.id.enlarge);
        if (fromPlaylistDetail) {
            enlarge.setVisibility(View.GONE);
        } else
            enlarge.setVisibility(View.VISIBLE); // end for code here



        //this block of code is for pausing and play the visible animations (for text animations)
        pausePlayLayout = view.findViewById(R.id.pausePlayNextPreviousLayout);
        pausePlayLayout1 = view.findViewById(R.id.pausePlayNextPreviousLayout1);
        if (getActivity() != null) {
            animFadeIn = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.pause_play_fade_in);
            animFadeIn1 = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.pause_play_fade_in);
        }

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


        sharedPreferences = NewsSharedPreferences.getInstance(getActivity().getApplicationContext());
        mHandler = new Handler();

        //here I am setting the visibility of viewPager and textAnimations according to item selected
        if (!selection.equalsIgnoreCase("image_arrays")) {
            frameLayoutTextAnimation.setVisibility(View.VISIBLE);
            frameLayoutViewPager.setVisibility(View.GONE);
        } else {
            frameLayoutTextAnimation.setVisibility(View.GONE);
            frameLayoutViewPager.setVisibility(View.VISIBLE);
        }


        //setting animation for text
        Typeface fontBold = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-Bold.ttf");

        if (animatedTextView != null) {

            if (!selection.equalsIgnoreCase("image_arrays")) {

                animatedTextView.setTypeface(fontBold);

                zoomIn = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.zoom_in);
                animatedTextView.setText(texts.get(i));
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
                                Log.d("Inside", "postDelayed" + String.valueOf(i));
                                if (i >= 12)
                                    i = -1;
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
            }

        } // till here I have set the text animation


        //pausing and playing the animation (for textviews)
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
                if (i == 12)
                    i = -1;
                animatedTextView.setText(texts.get(++i));
                animatedTextView.startAnimation(zoomIn);

                view.setVisibility(View.GONE);
                pause.setVisibility(View.VISIBLE);

                pausePlayLayout.startAnimation(animFadeOut);
            }
        }); // pausing and playing stops here


        //here is the code for setting viewpager
        if (selection.equalsIgnoreCase("image_arrays")) {
            viewPager = view.findViewById(R.id.image_view_pager);
            viewPager.startAutoScroll();
            viewPager.setInterval(3000);
            viewPager.setCycle(true);
            viewPager.setStopScrollWhenTouch(true);
        }

        if (selection.equalsIgnoreCase("image_arrays")) {
            b = new BreakingNewsViewPagerAdapter(getActivity().getApplicationContext(), DisplayBreakingNewsActivity.postDetails.getResult().get(0).getImageArray());
            viewPager.setAdapter(b);
            dotscount = b.getCount();
            dots = new ImageView[dotscount];

            linearLayout = view.findViewById(R.id.viewPagerDots);

            for (int i = 0; i < dotscount; i++) {

                if (getActivity() != null) {
                    dots[i] = new ImageView(getActivity().getApplicationContext());
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.non_active_dot));
                }

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);

                params.setMargins(8, 0, 8, 0);

                linearLayout.addView(dots[i], params);

            }

            if (getActivity() != null)
                dots[0].setImageDrawable(ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.active_dot));

        }

        if (viewPager != null)
            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {

                    for (int i = 0; i < dotscount; i++) {
                        if (getActivity() != null)
                            dots[i].setImageDrawable(ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.non_active_dot));
                    }
                    if (getActivity() != null)
                        dots[position].setImageDrawable(ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.active_dot));

                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            }); //till here I have set the viewpager


        //this block of code pauses and plays the viewpager images
        if (getActivity() != null)
            animFadeIn1 = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.pause_play_fade_in);
        postDetailFragmentImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        }); //this is the end of showing play and pause buttons (for image viewpager)


        //pausing and playing the animation (for viewpager)
        previousView = view.findViewById(R.id.previousButton1);
        pauseView = view.findViewById(R.id.pauseButton1);
        playView = view.findViewById(R.id.playButton1);
        nextView = view.findViewById(R.id.nextButton1);
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

    List<String> getPostDetailsFromServer(String post_id) {

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
                if (getActivity() instanceof DisplayBreakingNewsActivity)
                    ((DisplayBreakingNewsActivity) getActivity()).goToNext();
                else
                    ((PlayListDetaildViewActivity) getActivity()).goToNext();
            }
        });

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getActivity() instanceof DisplayBreakingNewsActivity)
                    ((DisplayBreakingNewsActivity) getActivity()).goToPrevious();
                else
                    ((PlayListDetaildViewActivity) getActivity()).goToPrevious();
            }
        });

        nextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getActivity() instanceof DisplayBreakingNewsActivity)
                    ((DisplayBreakingNewsActivity) getActivity()).goToNext();
                else
                    ((PlayListDetaildViewActivity) getActivity()).goToNext();
            }
        });

        previousView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getActivity() instanceof DisplayBreakingNewsActivity)
                    ((DisplayBreakingNewsActivity) getActivity()).goToPrevious();
                else
                    ((PlayListDetaildViewActivity) getActivity()).goToPrevious();
            }
        });
    }
}
