package com.gsatechworld.gugrify.view.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class BreakingNewsViewPagerAdapter extends PagerAdapter {

    Context context;
    public BreakingNewsViewPagerAdapter(Context context){
        this.context = context;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {
        ModelObject modelObject = ModelObject.values()[position];
        LayoutInflater inflater = LayoutInflater.from(context);
        ViewGroup layout = (ViewGroup) inflater.inflate(modelObject.getLayoutResId(), collection, false);
        collection.addView(layout);
        return layout;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return false;
    }
}
