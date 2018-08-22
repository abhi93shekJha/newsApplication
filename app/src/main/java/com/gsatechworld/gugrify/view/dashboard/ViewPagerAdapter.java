package com.gsatechworld.gugrify.view.dashboard;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;

import com.flaviofaria.kenburnsview.KenBurnsView;
import com.flaviofaria.kenburnsview.TransitionGenerator;
import com.gsatechworld.gugrify.MyTransitionGenerator;
import com.gsatechworld.gugrify.R;
import com.gsatechworld.gugrify.view.ViewPagerTransitionGenerator;

/**
 * Created by Vithin on 02/08/2018.
 */

public class ViewPagerAdapter extends PagerAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private Integer[] images = {  R.mipmap.road1,
            R.mipmap.road2,
            R.mipmap.road6,
            R.mipmap.road1,
            R.mipmap.road2};

    public ViewPagerAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.custom_layout, null);
        KenBurnsView imageView = (KenBurnsView) view.findViewById(R.id.imageView);
        imageView.setImageResource(images[position]);

//        AccelerateDecelerateInterpolator ACCELERATE_DECELERATE = new AccelerateDecelerateInterpolator();
//
//        TransitionGenerator generator = new ViewPagerTransitionGenerator(context);
//        //duration = 10000ms = 10s and interpolator = ACCELERATE_DECELERATE
//        imageView.setTransitionGenerator(generator); //set new transition on kbv

        ViewPager vp = (ViewPager) container;
        vp.addView(view, 0);
        return view;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ViewPager vp = (ViewPager) container;
        View view = (View) object;
        vp.removeView(view);
    }
}