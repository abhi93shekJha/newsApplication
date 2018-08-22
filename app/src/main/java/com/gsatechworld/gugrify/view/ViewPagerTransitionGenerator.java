package com.gsatechworld.gugrify.view;

import android.content.Context;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.flaviofaria.kenburnsview.Transition;
import com.flaviofaria.kenburnsview.TransitionGenerator;
import com.gsatechworld.gugrify.R;

public class ViewPagerTransitionGenerator implements TransitionGenerator {
    Context context;
    public ViewPagerTransitionGenerator(Context context){
        this.context = context;
    }
    @Override
    public Transition generateNextTransition(RectF drawableBounds, RectF viewport) {
        AccelerateDecelerateInterpolator ACCELERATE_DECELERATE = new AccelerateDecelerateInterpolator();
        Drawable drawable = context.getResources().getDrawable(R.drawable.news);
        float width = drawableBounds.width();
        float height = drawableBounds.height();

        RectF src = new RectF(0,0, width, height);
        RectF dest = new RectF(0, 0, width/1.1f, height/1.1f);
        Transition transition = new Transition(src, dest, 5000, ACCELERATE_DECELERATE);
        return transition;
    }
}
