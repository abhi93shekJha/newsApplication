package com.gsatechworld.gugrify.view.dashboard;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.gsatechworld.gugrify.R;

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
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_single_card, null);
            singleItemRowHolder = new SingleItemRowHolder(v);
        } else {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_single_card_paly_list, null);
            singleItemRowHolder = new SingleItemRowHolder(v);
        }

        return singleItemRowHolder;
    }

    @Override
    public void onBindViewHolder(SingleItemRowHolder holder, int position) {
        if(layoutAllignment == 1){
            LatestNewItemModel latestNewItemModel = (LatestNewItemModel) itemModels.get(position);
            holder.tvTitle.setText(latestNewItemModel.getName());
            Glide.with(mContext)
                    .load(latestNewItemModel.getImage())
                    .into(holder.itemImage);
            //holder.itemImage.setImageResource(latestNewItemModel.getImage());
        } else if(layoutAllignment == 2) {
            PlayListItemModel playListItemModel = (PlayListItemModel) itemModels.get(position);
            holder.tvTitle.setText(playListItemModel.getName());
            Glide.with(mContext)
                    .load(playListItemModel.getImage())
                    .into(holder.itemImage);
            //holder.itemImage.setImageResource(playListItemModel.getImage());
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

        protected TextView tvTitle;
        protected ImageView itemImage;

        public SingleItemRowHolder(View itemView) {
            super(itemView);
            this.tvTitle = itemView.findViewById(R.id.tvTitle);
            this.itemImage = itemView.findViewById(R.id.itemImage);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(view.getContext(), tvTitle.getText(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
