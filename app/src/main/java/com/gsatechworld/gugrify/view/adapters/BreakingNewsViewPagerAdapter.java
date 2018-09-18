package com.gsatechworld.gugrify.view.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.gsatechworld.gugrify.R;

public class BreakingNewsViewPagerAdapter extends PagerAdapter {

    private Integer[] images = {  R.mipmap.road1,
            R.mipmap.road2,
            R.mipmap.road6,
            R.mipmap.road1,
            R.mipmap.road2};

    Context context;
    public BreakingNewsViewPagerAdapter(Context context){
        this.context = context;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.breaking_news_view_pager_item, null);
        ImageView image = view.findViewById(R.id.view_pager_image_item);
        image.setImageResource(images[position]);
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
