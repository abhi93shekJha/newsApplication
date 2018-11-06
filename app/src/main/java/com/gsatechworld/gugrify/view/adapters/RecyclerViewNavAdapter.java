package com.gsatechworld.gugrify.view.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gsatechworld.gugrify.NewsSharedPreferences;
import com.gsatechworld.gugrify.R;
import com.gsatechworld.gugrify.model.NavItemModel;
import com.gsatechworld.gugrify.model.retrofit.NewsCategories;
import com.gsatechworld.gugrify.view.dashboard.DisplayVideoActivity;

import java.util.ArrayList;

public class RecyclerViewNavAdapter extends RecyclerView.Adapter<RecyclerViewNavAdapter.ItemRowHolder> {

    private NewsCategories newsCategories;
    private Context mContext;
    private RecyclerView.RecycledViewPool recycledViewPool;
    NewsSharedPreferences sharedPreferences;

    public RecyclerViewNavAdapter(NewsCategories dataList, Context mContext) {
        this.newsCategories = dataList;
        this.mContext = mContext;
        recycledViewPool = new RecyclerView.RecycledViewPool();
        sharedPreferences = NewsSharedPreferences.getInstance(mContext);
    }

    //Overriden so that I can display custom rows in the recyclerview
    @Override
    public int getItemViewType(int position) {
        int viewType = 0; //Default is 1
        if (position == newsCategories.getCategory().size()) viewType = 1; //if zero, it will be a header view
        if (position == newsCategories.getCategory().size()+1) viewType = 2;
        if (position == newsCategories.getCategory().size()+2) viewType = 3;
        if (position == newsCategories.getCategory().size()+3) viewType = 4;
        return viewType;
    }

    @Override
    public ItemRowHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        ItemRowHolder rowHolder;
        switch (viewType) {
            case 0:
                v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.nav_item_row, parent, false);
                rowHolder = new ItemRowHolder(v);
                return rowHolder;
            case 1:
                v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.nav_other_item, parent, false);
                rowHolder = new ItemRowHolder(v);
                return rowHolder;
            case  2:
                v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.nav_sign_out, parent, false);
                rowHolder = new ItemRowHolder(v);
                return rowHolder;
            case  3:
                v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.nav_about_us, parent, false);
                rowHolder = new ItemRowHolder(v);
                return rowHolder;
            default:
                v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.nav_contact_us, parent, false);
                rowHolder = new ItemRowHolder(v);
                return rowHolder;
        }
    }

    @Override
    public void onBindViewHolder(ItemRowHolder holder, int position) {
        if (position < newsCategories.getCategory().size()) {
            final String sectionName = newsCategories.getCategory().get(position);
            holder.navItemTitle.setText(sectionName);
        } else if (position == newsCategories.getCategory().size()) {

        } else {
            if(sharedPreferences.getIsLoggedIn()){
                holder.navItemTitle.setText("Sign Out");
            }
            else {
                holder.navItemTitle.setText("Sign In");
            }
        }
    }

    @Override
    public int getItemCount() {
        return (null != newsCategories.getCategory() ? newsCategories.getCategory().size() + 4 : 0);
    }

    public class ItemRowHolder extends RecyclerView.ViewHolder {
        protected TextView navItemTitle;

        public ItemRowHolder(View itemView) {
            super(itemView);
            this.navItemTitle = itemView.findViewById(R.id.navItemTitle);

            navItemTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(navItemTitle.getText().toString().equalsIgnoreCase("news videos")){
                        Intent intent = new Intent(mContext, DisplayVideoActivity.class);
                        mContext.startActivity(intent);
                    }
                }
            });
        }
    }
}
