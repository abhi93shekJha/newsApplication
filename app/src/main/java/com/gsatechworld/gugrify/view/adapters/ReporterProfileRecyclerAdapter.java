package com.gsatechworld.gugrify.view.adapters;

import android.content.Context;
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
import com.gsatechworld.gugrify.model.PostsByCategory;

import java.util.ArrayList;
import java.util.List;

public class ReporterProfileRecyclerAdapter extends RecyclerView.Adapter<ReporterProfileRecyclerAdapter.ReporterViewHolder> {

    Context context;
    List<PostsByCategory> posts;
    public ReporterProfileRecyclerAdapter(Context context, ArrayList<PostsByCategory> postsByCategories){
        this.context = context;
        this.posts = postsByCategories;
    }

    public void updateList(List<PostsByCategory> newList) {
        posts = newList;
        notifyDataSetChanged();
    }

    public class ReporterViewHolder extends RecyclerView.ViewHolder  {
        private TextView textView, headline, views, likes;
        private LinearLayout linearLayout, l;
        private com.github.aakira.expandablelayout.ExpandableLinearLayout expandableLinearLayout;
        private de.hdodenhof.circleimageview.CircleImageView imageView;
        private CardView cardView;
        private ImageView image, arrow;
        public ReporterViewHolder(View view) {
            super(view);
            expandableLinearLayout = view.findViewById(R.id.expandableLayout);
            linearLayout = view.findViewById(R.id.expandable_ll);
            textView = view.findViewById(R.id.news_headline_text);
            imageView = view.findViewById(R.id.reporter_circular_image);
            l = view.findViewById(R.id.hiddenLinearLayout);
            cardView = view.findViewById(R.id.youtube_row_card_view);
            image = view.findViewById(R.id.video_thumbnail_image_view);
            headline = view.findViewById(R.id.textViewname);
            views = view.findViewById(R.id.text1);
            likes = view.findViewById(R.id.text2);
            arrow = view.findViewById(R.id.dropImage);
        }
    }

    @NonNull
    @Override
    public ReporterProfileRecyclerAdapter.ReporterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.reporter_profile_recycler_item, parent, false);
        return new ReporterProfileRecyclerAdapter.ReporterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReporterProfileRecyclerAdapter.ReporterViewHolder holder, int position) {
        Glide.with(context).load(posts.get(position).getImage()).into(holder.image);
        holder.headline.setText(posts.get(position).getNews_headlines());
        holder.likes.setText(posts.get(position).getLikes());
        holder.views.setText(posts.get(position).getViews());
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }
}
