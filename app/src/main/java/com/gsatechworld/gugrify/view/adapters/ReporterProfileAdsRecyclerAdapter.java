package com.gsatechworld.gugrify.view.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

public class ReporterProfileAdsRecyclerAdapter extends RecyclerView.Adapter<ReporterProfileAdsRecyclerAdapter.AdsViewHolder> {

    @NonNull
    @Override
    public AdsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull AdsViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class AdsViewHolder extends RecyclerView.ViewHolder{

        public AdsViewHolder(View itemView) {
            super(itemView);
        }
    }
}
