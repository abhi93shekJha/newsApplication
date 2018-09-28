package com.gsatechworld.gugrify.view.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.aakira.expandablelayout.ExpandableLinearLayout;
import com.gsatechworld.gugrify.NewsSharedPreferences;
import com.gsatechworld.gugrify.R;
import com.gsatechworld.gugrify.fragment.FragmentImage;
import com.gsatechworld.gugrify.fragment.FragmentLayout;
import com.gsatechworld.gugrify.model.PostsByCategory;
import com.gsatechworld.gugrify.view.DisplayBreakingNewsActivity;

import java.util.ArrayList;

public class BreakingNewsRecyclerAdapter extends RecyclerView.Adapter<BreakingNewsRecyclerAdapter.MyViewHolder>{

    Context context;
    boolean b = false;
    ArrayList<PostsByCategory> posts;
    NewsSharedPreferences sharedPreferences;
    Boolean[] clicked;
    int previous=0;

    public BreakingNewsRecyclerAdapter(Context context, ArrayList<PostsByCategory> posts){
        this.context = context;
        this.posts = posts;
        sharedPreferences = NewsSharedPreferences.getInstance(context);
        clicked = new Boolean[posts.size()];
        for(int i=0; i<posts.size(); i++){
            clicked[i] = false;
        }
        clicked[0] = true;
    }

    public BreakingNewsRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if(viewType == 0) {
            view = LayoutInflater.from(context).inflate(R.layout.reporter_detail_item, null, false);
            return new MyViewHolder(view);
        }
        else {
            view = view = LayoutInflater.from(context).inflate(R.layout.activity_playlist, null, false);
            return new MyViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final BreakingNewsRecyclerAdapter.MyViewHolder holder, final int position) {

//        holder.setIsRecyclable(false);
//        holder.expandableLinearLayout.setInRecyclerView(true);

        if(position > 0){
            Glide.with(context).load(posts.get(position-1).getImage()).into(holder.image);
            holder.headline.setText(posts.get(position-1).getNews_headlines());
            holder.description.setText(posts.get(position-1).getNews_description());
            holder.likes.setText(posts.get(position-1).getLikes());
            holder.views.setText(posts.get(position-1).getViews());

            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FragmentImage fragment1 = new FragmentImage();
                    FragmentLayout fragment2 = new FragmentLayout();

                    if(previous != position-1) {
                        clicked[previous] = false;
                        notifyDataSetChanged();
                    }

                    clicked[position-1] = true;
                    previous = position-1;

                    if(context instanceof DisplayBreakingNewsActivity){
                        sharedPreferences.setClickedPosition(position-1);
                        notifyItemChanged(position);
                        ((DisplayBreakingNewsActivity) context).loadFragment(fragment1, fragment2, position-1);
                    }
                }
            });

            if(clicked[position-1])
                holder.cardView.setBackgroundColor(context.getResources().getColor(R.color.ststusbar_color));
            else
                holder.cardView.setBackgroundColor(context.getResources().getColor(R.color.colorWhite));
        }

        else {
            if (holder.linearLayout != null)
                holder.linearLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (b == true) {
                            holder.l.setVisibility(View.GONE);
                            RotateAnimation rotateAnimation = new RotateAnimation(180.0f, 0.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                            rotateAnimation.setInterpolator(new DecelerateInterpolator());
                            rotateAnimation.setRepeatCount(0);
                            rotateAnimation.setDuration(500);
                            rotateAnimation.setFillAfter(true);
                            holder.arrow.startAnimation(rotateAnimation);
                            b = false;
                        } else {
                            holder.l.setVisibility(View.VISIBLE);
                            Animation animation = AnimationUtils.loadAnimation(context,
                                    R.anim.news_slide_down);
                            holder.l.startAnimation(animation);
                            RotateAnimation rotateAnimation = new RotateAnimation(0.0f, 180.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                            rotateAnimation.setInterpolator(new DecelerateInterpolator());
                            rotateAnimation.setRepeatCount(0);
                            rotateAnimation.setDuration(500);
                            rotateAnimation.setFillAfter(true);
                            holder.arrow.startAnimation(rotateAnimation);
                            b = true;
                        }
                    }
                });
        }
    }

    @Override
    public int getItemViewType(int position) {
        int viewType=0;
        if(position == 0)
            viewType = 0;
        if (position > 0)
            viewType = 1; //if zero, it will be a header view
        return viewType;
    }

    @Override
    public int getItemCount() {
        return (posts.size()+1);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView textView, headline, description, views, likes;
        private LinearLayout linearLayout, l;
        private com.github.aakira.expandablelayout.ExpandableLinearLayout expandableLinearLayout;
        private de.hdodenhof.circleimageview.CircleImageView imageView;
        private CardView cardView;
        private ImageView image, arrow;
        MyViewHolder(View view){
            super(view);
            expandableLinearLayout = view.findViewById(R.id.expandableLayout);
            linearLayout = view.findViewById(R.id.expandable_ll);
            textView = view.findViewById(R.id.news_headline_text);
            imageView = view.findViewById(R.id.reporter_circular_image);
            l = view.findViewById(R.id.hiddenLinearLayout);
            cardView = view.findViewById(R.id.youtube_row_card_view);
            image = view.findViewById(R.id.video_thumbnail_image_view);
            headline = view.findViewById(R.id.textViewname);
            description = view.findViewById(R.id.textViewdescription);
            views = view.findViewById(R.id.text1);
            likes = view.findViewById(R.id.text2);
            arrow = view.findViewById(R.id.dropImage);
        }
    }
}
