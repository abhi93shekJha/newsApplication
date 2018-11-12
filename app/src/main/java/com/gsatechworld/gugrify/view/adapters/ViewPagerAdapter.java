package com.gsatechworld.gugrify.view.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.flaviofaria.kenburnsview.TransitionGenerator;
import com.gsatechworld.gugrify.MyTransitionGenerator;
import com.gsatechworld.gugrify.R;
import com.gsatechworld.gugrify.model.retrofit.LatestNewsByCity;
import com.gsatechworld.gugrify.view.ActivityShowWebView;
import com.gsatechworld.gugrify.view.DisplayBreakingNewsActivity;
import com.gsatechworld.gugrify.view.ViewPagerTransitionGenerator;
import com.gsatechworld.gugrify.view.dashboard.DashboardActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vithin on 02/08/2018.
 */

public class ViewPagerAdapter extends PagerAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    String frontImage, frontUrl;
    List<LatestNewsByCity.Result> lists = new ArrayList<>();

    public ViewPagerAdapter(Context context, List<LatestNewsByCity.Result> lists, String image, String url) {
        this.lists = lists;
        this.frontImage = image;
        this.frontUrl = url;
        this.context = context;
    }

    @Override
    public int getCount() {
        return lists.size() + 1;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        View view = null;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(position == 0){
            view = layoutInflater.inflate(R.layout.custom_layout, null);
            ImageView imageView = view.findViewById(R.id.imageView);
            Glide.with(context).load(frontImage).into(imageView);

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ActivityShowWebView.class);
                    intent.putExtra("url", frontUrl);
                    context.startActivity(intent);
                }
            });
        }
        else {
            view = layoutInflater.inflate(R.layout.custom_layout, null);
            ImageView imageView = view.findViewById(R.id.imageView);
            Glide.with(context).load(lists.get(position-1).getImage()).into(imageView);

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, DisplayBreakingNewsActivity.class);
                    intent.putExtra("category", lists.get(position-1).getCategory());
                    intent.putExtra("postId", lists.get(position-1).getPost_id());
                    context.startActivity(intent);
                }
            });
        }

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