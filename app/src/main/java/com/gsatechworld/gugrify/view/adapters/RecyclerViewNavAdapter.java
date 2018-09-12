package com.gsatechworld.gugrify.view.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gsatechworld.gugrify.R;
import com.gsatechworld.gugrify.model.NavItemModel;

import java.util.ArrayList;

public class RecyclerViewNavAdapter extends RecyclerView.Adapter<RecyclerViewNavAdapter.ItemRowHolder> {

    private ArrayList<NavItemModel> dataList;
    private Context mContext;
    private RecyclerView.RecycledViewPool recycledViewPool;

    public RecyclerViewNavAdapter(ArrayList<NavItemModel> dataList, Context mContext) {
        this.dataList = dataList;
        this.mContext = mContext;
        recycledViewPool = new RecyclerView.RecycledViewPool();
    }

    //Overriden so that I can display custom rows in the recyclerview
    @Override
    public int getItemViewType(int position) {
        int viewType = 1; //Default is 1
        if (position >= 2) viewType = 0; //if zero, it will be a header view
        return viewType;
    }

    @Override
    public ItemRowHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        ItemRowHolder rowHolder;
                v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.nav_item_row, parent, false);
                rowHolder = new ItemRowHolder(v);
                return  rowHolder;
    }

    @Override
    public void onBindViewHolder(ItemRowHolder holder, int position) {
        final String sectionName = dataList.get(position).getNavItemName();
        holder.navItemTitle.setText(sectionName);
        holder.navItemCount.setText(dataList.get(position).getNavItemCount());
        holder.navImageItem.setImageResource(dataList.get(position).getNavItemImage());
    }

    @Override
    public int getItemCount() {
        return (null != dataList ? dataList.size() : 0);
    }

    public class ItemRowHolder extends RecyclerView.ViewHolder {
        protected TextView navItemTitle;
        protected ImageView navImageItem;
        protected TextView navItemCount;

        public ItemRowHolder(View itemView) {
            super(itemView);
            this.navItemTitle = itemView.findViewById(R.id.navItemTitle);
            this.navImageItem = itemView.findViewById(R.id.navImageItem);
            this.navItemCount = itemView.findViewById(R.id.navItemCount);
        }
    }
}
