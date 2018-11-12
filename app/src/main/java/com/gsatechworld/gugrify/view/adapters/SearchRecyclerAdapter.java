package com.gsatechworld.gugrify.view.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gsatechworld.gugrify.R;
import com.gsatechworld.gugrify.model.NewsListHolder;
import com.gsatechworld.gugrify.model.retrofit.HeadlineSearchPojo;
import com.gsatechworld.gugrify.view.DisplayBreakingNewsActivity;
import com.gsatechworld.gugrify.view.dashboard.DisplayVideoActivity;

import java.util.ArrayList;
import java.util.List;

public class SearchRecyclerAdapter extends RecyclerView.Adapter<SearchRecyclerAdapter.MyViewHolder> {

    HeadlineSearchPojo pojo;
    private Context context;

    public SearchRecyclerAdapter(HeadlineSearchPojo pojo, Context context) {
        this.pojo = pojo;
        this.context = context;
    }

    @Override
    public SearchRecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_list_item1, parent, false);
        return new SearchRecyclerAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final SearchRecyclerAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        if (pojo.getResult() != null) {
            holder.heading.setText(pojo.getResult().get(position).getNewsHeadline());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, DisplayBreakingNewsActivity.class);
                    intent.putExtra("postId", pojo.getResult().get(position).getPostId());
                    context.startActivity(intent);
                }
            });
        }
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube://" + list.get(position).getYoutubekey()));
//                context.startActivity(intent);
//            }
//        });
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView heading;

        MyViewHolder(View itemView) {
            super(itemView);
            heading = itemView.findViewById(R.id.textViewname);
        }
    }


    @Override
    public int getItemCount() {
        if (pojo.getResult() != null)
            return pojo.getResult().size();
        else
            return 0;
    }
}
