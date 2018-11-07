package com.gsatechworld.gugrify.view.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gsatechworld.gugrify.R;
import com.gsatechworld.gugrify.fragment.FragmentImage;
import com.gsatechworld.gugrify.fragment.FragmentLayout;
import com.gsatechworld.gugrify.model.retrofit.CategoryPosts;
import com.gsatechworld.gugrify.model.retrofit.ReporterPostById;
import com.gsatechworld.gugrify.view.DisplayBreakingNewsActivity;
import com.gsatechworld.gugrify.view.PostByCategory;

import java.util.Calendar;
import java.util.List;

public class PostsByCategoryAdapter extends RecyclerView.Adapter<PostsByCategoryAdapter.ReporterViewHolder> {

    Context context;
    CategoryPosts posts;

    public PostsByCategoryAdapter(Context context, CategoryPosts posts) {
        this.posts = posts;
        this.context = context;
    }

    public class ReporterViewHolder extends RecyclerView.ViewHolder {

        private TextView headline, description, views, likes;
        private CardView cardView;
        private ImageView image;

        public ReporterViewHolder(View view) {
            super(view);
            cardView = view.findViewById(R.id.youtube_row_card_view);
            image = view.findViewById(R.id.video_thumbnail_image_view);
            headline = view.findViewById(R.id.textViewname);
            description = view.findViewById(R.id.textViewdescription);
            views = view.findViewById(R.id.text1);
            likes = view.findViewById(R.id.text2);
        }
    }

    @NonNull
    @Override
    public ReporterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.activity_playlist, parent, false);

        return new ReporterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReporterViewHolder holder, final int position) {
        Glide.with(context).load(posts.getResult().get(position).getImage()).into(holder.image);
        holder.headline.setText(posts.getResult().get(position).getNewsHeadline());
        holder.description.setText(posts.getResult().get(position).getNewsDescription());
        holder.likes.setText(posts.getResult().get(position).getLikes());
        holder.views.setText(posts.getResult().get(position).getViews());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DisplayBreakingNewsActivity.class);
                intent.putExtra("postId", posts.getResult().get(position).getPostId());
                context.startActivity(intent);
                if (context instanceof PostByCategory)
                    ((PostByCategory) context).finish();
            }
        });
    }


    @Override
    public int getItemCount() {
        if (posts.getResult() == null)
            return 0;
        else
            return posts.getResult().size();
    }
}
