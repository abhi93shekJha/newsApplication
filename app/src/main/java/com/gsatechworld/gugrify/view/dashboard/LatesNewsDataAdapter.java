package com.gsatechworld.gugrify.view.dashboard;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.gsatechworld.gugrify.R;
import com.gsatechworld.gugrify.model.LatestNewItemModel;
import com.gsatechworld.gugrify.model.OtherNewsItemModel;
import com.gsatechworld.gugrify.model.PlayListItemModel;
import com.gsatechworld.gugrify.model.retrofit.LatestNewsByCity;
import com.gsatechworld.gugrify.view.DisplayBreakingNewsActivity;
import com.gsatechworld.gugrify.view.playlist.PlayListDetaildViewActivity;

import java.util.ArrayList;
import java.util.List;

public class LatesNewsDataAdapter extends RecyclerView.Adapter<LatesNewsDataAdapter.SingleItemRowHolder> {

    private List<LatestNewsByCity.Result> lists;
    private Context mContext;

    public LatesNewsDataAdapter(List<LatestNewsByCity.Result> lists, Context mContext) {
        this.lists = lists;
        this.mContext = mContext;
    }


    @Override
    public SingleItemRowHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_single_card, null);
        return new SingleItemRowHolder(v);

    }

    @Override
    public void onBindViewHolder(SingleItemRowHolder holder, final int position) {

        holder.tvTitle.setText(lists.get(position).getNews_headline());
        Glide.with(mContext).load(lists.get(position).getImage()).into(holder.itemImage);

        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, DisplayBreakingNewsActivity.class);
                intent.putExtra("category", lists.get(position).getCategory());
                intent.putExtra("postId", lists.get(position).getPost_id());
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    public class SingleItemRowHolder extends RecyclerView.ViewHolder {

        protected TextView tvTitle;
        protected ImageView itemImage;
        protected LinearLayout mainLayout;


        public SingleItemRowHolder(View itemView) {
            super(itemView);
            this.tvTitle = itemView.findViewById(R.id.tvTitle);
            this.itemImage = itemView.findViewById(R.id.itemImage);
            this.mainLayout = itemView.findViewById(R.id.mainLayout);

        }
    }
}
