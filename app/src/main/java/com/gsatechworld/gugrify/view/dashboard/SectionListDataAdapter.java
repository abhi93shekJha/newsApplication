package com.gsatechworld.gugrify.view.dashboard;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
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
import com.gsatechworld.gugrify.view.DisplayBreakingNewsActivity;
import com.gsatechworld.gugrify.view.playlist.PlayListDetaildViewActivity;

import java.util.ArrayList;

public class SectionListDataAdapter extends RecyclerView.Adapter<SectionListDataAdapter.SingleItemRowHolder>{

    private ArrayList<?> itemModels;
    private Context mContext;
    private int layoutAllignment;
    private int outerAdapterPosition;

    public SectionListDataAdapter(ArrayList<?> itemModels, Context mContext, int layoutAllignment, int outerAdapterPosition) {
        this.itemModels = itemModels;
        this.mContext = mContext;
        this.layoutAllignment = layoutAllignment;
        this.outerAdapterPosition = outerAdapterPosition;
    }

    @Override
    public int getItemViewType(int position) {
        int viewType = 1; //Default is 1
        if (position >= 2) viewType = 0; //if zero, it will be a header view
        return viewType;
    }

    @Override
    public SingleItemRowHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        SingleItemRowHolder singleItemRowHolder = null;
        if(outerAdapterPosition == 0){
//            Log.d("Inflating latest", "items true");
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_single_card, null);
            singleItemRowHolder = new SingleItemRowHolder(v);
        } else {
//            Log.d("inflating playlist", "True");
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_single_card_paly_list, null);
            singleItemRowHolder = new SingleItemRowHolder(v);
        }

        return singleItemRowHolder;
    }

    @Override
    public void onBindViewHolder(SingleItemRowHolder holder, int position) {
        if(layoutAllignment == 1){
//            Log.d("Entering items", "to latestNews");
            LatestNewItemModel latestNewItemModel = (LatestNewItemModel) itemModels.get(position);
            holder.tvTitle.setText(latestNewItemModel.getName());
            Glide.with(mContext)
                    .load(latestNewItemModel.getImage())
                    .into(holder.itemImage);

            holder.mainLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, DisplayBreakingNewsActivity.class);
                    mContext.startActivity(intent);
                }
            });
            //holder.itemImage.setImageResource(latestNewItemModel.getImage());
        }
        if(layoutAllignment == 2) {
//            Log.d("Entering items", "to playlist");
            PlayListItemModel playListItemModel = (PlayListItemModel) itemModels.get(position);
            holder.tvTitleCount.setText(playListItemModel.getName());
            Glide.with(mContext)
                    .load(playListItemModel.getImage())
                    .into(holder.itemImage);
            //holder.itemImage.setImageResource(playListItemModel.getImage());
            if(position == itemModels.size()-1 && itemModels.size() > 1){
                Glide.with(mContext)
                        .load(R.drawable.ic_add)
                        .into(holder.itemImage);
                if(holder.ll_img_bag != null){
                    holder.itemImage.setPadding(170, 80, 170, 80);
                    holder.ll_img_bag.setVisibility(View.GONE);
                    holder.tvTitle.setVisibility(View.GONE);
                    holder.tvTitleCount.setVisibility(View.GONE);
                    //holder.frl_main.setBackgroundColor(mContext.getResources().getColor(R.color.global_color_green_accent));
                    holder.frl_main.setBackgroundColor(mContext.getResources().getColor(R.color.black_overlay));
                }
            } else {
                holder.ll_img_bag.setVisibility(View.VISIBLE);
                holder.tvTitle.setVisibility(View.VISIBLE);
                holder.tvTitleCount.setVisibility(View.VISIBLE);
                holder.itemImage.setPadding(0, 0, 0, 0);
            }
            holder.mainLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, PlayListDetaildViewActivity.class);
                    mContext.startActivity(intent);
                }
            });
        }
        else if(layoutAllignment == 3){
            OtherNewsItemModel otherNewsItemModel = (OtherNewsItemModel) itemModels.get(position);
            holder.tvTitle.setText(otherNewsItemModel.getName());
            Glide.with(mContext)
                    .load(otherNewsItemModel.getImage())
                    .into(holder.itemImage);
            //holder.itemImage.setImageResource(otherNewsItemModel.getImage());
        }
    }

    @Override
    public int getItemCount() {
        return (null != itemModels ? itemModels.size() : 0);
    }

    public class SingleItemRowHolder extends RecyclerView.ViewHolder {

        protected TextView tvTitle, tvTitleCount;
        protected ImageView itemImage;
        protected LinearLayout mainLayout, ll_img_bag;
        FrameLayout frl_main;

        public SingleItemRowHolder(View itemView) {
            super(itemView);
            this.tvTitle = itemView.findViewById(R.id.tvTitle);
            this.tvTitleCount = itemView.findViewById(R.id.tvTitleCount);
            this.itemImage = itemView.findViewById(R.id.itemImage);
            this.ll_img_bag = itemView.findViewById(R.id.ll_img_bag);
            this.mainLayout =itemView.findViewById(R.id.mainLayout);
            frl_main = itemView.findViewById(R.id.frl_main);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(view.getContext(), tvTitle.getText(), Toast.LENGTH_SHORT).show();
                }
            });
            this.mainLayout = itemView.findViewById(R.id.mainLayout);
        }
    }
}
