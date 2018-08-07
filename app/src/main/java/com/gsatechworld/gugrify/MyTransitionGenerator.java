package com.gsatechworld.gugrify;

import android.content.Context;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.flaviofaria.kenburnsview.Transition;
import com.flaviofaria.kenburnsview.TransitionGenerator;

public class MyTransitionGenerator implements TransitionGenerator {
    Context context;
    public MyTransitionGenerator(Context context){
        this.context = context;
    }
    @Override
    public Transition generateNextTransition(RectF drawableBounds, RectF viewport) {
        AccelerateDecelerateInterpolator ACCELERATE_DECELERATE = new AccelerateDecelerateInterpolator();
        Drawable drawable = context.getResources().getDrawable(R.drawable.news);
        float width = (drawableBounds.height()/viewport.height()) * viewport.width();
        float height = drawableBounds.height();

        RectF src = new RectF(0,0, width/1.1f, height/1.1f);
        RectF dest = new RectF(0, 0, width, height);
        Transition transition = new Transition(src, dest, 8000, ACCELERATE_DECELERATE);
        return transition;
    }
}
