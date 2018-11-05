package com.gsatechworld.gugrify.view.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.gsatechworld.gugrify.R;
import com.gsatechworld.gugrify.view.DisplayBreakingNewsActivity;

import java.util.logging.Handler;

import static com.gsatechworld.gugrify.fragment.FragmentImage.pausePlayLayout1;

public class BreakingNewsViewPagerAdapter extends PagerAdapter {

    Animation animFadeIn1, animFadeOut1;
    public android.os.Handler animateHandler;
    private Integer[] images = {  R.mipmap.road1,
            R.mipmap.road2,
            R.mipmap.road6,
            R.mipmap.road1,
            R.mipmap.road2};

    Context context;
    public BreakingNewsViewPagerAdapter(Context context){
        this.context = context;
        animateHandler = new android.os.Handler();
    }

    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.breaking_news_view_pager_item, null);
        ImageView image = view.findViewById(R.id.view_pager_image_item);
        image.setImageResource(images[position]);

        animFadeOut1 = AnimationUtils.loadAnimation(context, R.anim.fade_out);
        animFadeIn1 = AnimationUtils.loadAnimation(context, R.anim.pause_play_fade_in);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Log.d("inside viewPager", "onClick");
                    pausePlayLayout1.setVisibility(View.VISIBLE);
                    pausePlayLayout1.startAnimation(animFadeIn1);
                    animFadeIn1.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {

                            animateHandler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    pausePlayLayout1.setVisibility(View.VISIBLE);
                                    pausePlayLayout1.startAnimation(animFadeOut1);
                                    pausePlayLayout1.setVisibility(View.GONE);
                                }
                            }, 1000L);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
            }
        });
        collection.addView(view, 0);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
}
