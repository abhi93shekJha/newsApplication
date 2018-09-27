package com.gsatechworld.gugrify.view.genericadapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gsatechworld.gugrify.R;
import com.gsatechworld.gugrify.view.DisplayBreakingNewsActivity;
import com.gsatechworld.gugrify.view.dashboard.DisplayVideoActivity;

public class PlayListRecyclerAdapter extends RecyclerView.Adapter<PlayListRecyclerAdapter.MyViewHolder>{
    Context context;
    public PlayListRecyclerAdapter(Context context){
        this.context = context;
    }
    @NonNull
    @Override
    public PlayListRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_playlist, parent, false);
        return new PlayListRecyclerAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlayListRecyclerAdapter.MyViewHolder holder, int position) {
        Glide.with(context)
                .load("https://www.siliconweek.com/wp-content/uploads/2017/07/Noticias-y-Publicaciones_home_left.png")
                .into(holder.imageView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DisplayBreakingNewsActivity.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 8;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        public MyViewHolder(View view) {
            super(view);
            imageView = view.findViewById(R.id.video_thumbnail_image_view);
        }
    }
}
