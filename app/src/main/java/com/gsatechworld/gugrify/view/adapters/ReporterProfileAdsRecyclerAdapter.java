package com.gsatechworld.gugrify.view.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gsatechworld.gugrify.R;
import com.gsatechworld.gugrify.model.retrofit.GetReporterAdsPojo;
import com.gsatechworld.gugrify.view.DisplayBreakingNewsActivity;

public class ReporterProfileAdsRecyclerAdapter extends RecyclerView.Adapter<ReporterProfileAdsRecyclerAdapter.AdsViewHolder> {

    Context context;
    GetReporterAdsPojo adsPojo;

    public ReporterProfileAdsRecyclerAdapter(Context context, GetReporterAdsPojo adsPojo) {
        this.context = context;
        this.adsPojo = adsPojo;
    }

    class AdsViewHolder extends RecyclerView.ViewHolder {
        private TextView text1, text2;
        private ImageView video_thumbnail_image_view;
        public AdsViewHolder(View itemView)
        {
            super(itemView);
            text1 = itemView.findViewById(R.id.ad_text1);
            text2 = itemView.findViewById(R.id.ad_text2);
            video_thumbnail_image_view = itemView.findViewById(R.id.video_thumbnail_image_view);
        }
    }

    @NonNull
    @Override
    public AdsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.reporter_ads_item, parent, false);
        return new ReporterProfileAdsRecyclerAdapter.AdsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdsViewHolder holder, int position) {
        Glide.with(context).load(adsPojo.getResult().get(position).getImage()).into(holder.video_thumbnail_image_view);
        holder.text1.setText(adsPojo.getResult().get(position).getText1());
        holder.text2.setText(adsPojo.getResult().get(position).getText2());
    }

    @Override
    public int getItemCount() {
        if (adsPojo.getResult() == null)
            return 0;
        else
            return adsPojo.getResult().size();
    }

}
