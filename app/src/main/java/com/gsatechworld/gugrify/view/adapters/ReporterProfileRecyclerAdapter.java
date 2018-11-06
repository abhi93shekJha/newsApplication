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
import com.gsatechworld.gugrify.model.retrofit.ReporterPostById;
import com.gsatechworld.gugrify.view.ReporterPostActivity;
import com.gsatechworld.gugrify.view.ReporterProfile;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ReporterProfileRecyclerAdapter extends RecyclerView.Adapter<ReporterProfileRecyclerAdapter.ReporterViewHolder> {

    Context context;

    Calendar rightNow = Calendar.getInstance();


    List<ReporterPostById.Result> posts;

    public ReporterProfileRecyclerAdapter(Context context, List<ReporterPostById.Result> result) {
        this.context = context;
        posts = result;
    }

    public void updateList(List<ReporterPostById.Result> newList) {
        posts = newList;
        notifyDataSetChanged();
    }

    public class ReporterViewHolder extends RecyclerView.ViewHolder {
        ImageView video_thumbnail_image_view;
        TextView textViewname, text1, textTime, textLike;

        public ReporterViewHolder(View view) {
            super(view);
            video_thumbnail_image_view = view.findViewById(R.id.video_thumbnail_image_view);
            textViewname = view.findViewById(R.id.textViewname);
            text1 = view.findViewById(R.id.text1);
            textTime = view.findViewById(R.id.text2);
            textLike = view.findViewById(R.id.text3);
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
        Glide.with(context).load(posts.get(position)).into(holder.video_thumbnail_image_view);
        holder.textViewname.setText(posts.get(position).getPostHeading());
        holder.textLike.setText(posts.get(position).getLikes());
        holder.text1.setText(posts.get(position).getViews() + " views");

        String day = "" + rightNow.get(Calendar.DATE);
        if (day.length() == 1) {
            day = "0" + day;
        }

        String month = "" + rightNow.get(Calendar.MONTH);
        if (month.length() == 1) {
            month = "0" + month;
        }
        String year = "" + rightNow.get(Calendar.YEAR);

        String date = year + "-" + month + "-" + day;

        if (date.equalsIgnoreCase(posts.get(position).getPostDate())) {

            int hour = rightNow.get(Calendar.HOUR);
            int minutes = rightNow.get(Calendar.MINUTE);
            int seconds = rightNow.get(Calendar.SECOND);
            String amOrPm = rightNow.get(Calendar.AM_PM) == 0 ? "AM" : "PM";

            String time = posts.get(position).getPostTime();
            int Hour = Integer.parseInt(time.substring(0, 2));
            int Minutes = Integer.parseInt(time.substring(3, 5));
            int Seconds = Integer.parseInt(time.substring(6, 8));
            String AMORPM = time.substring(9);

            int hourDiff = 0;
            int minutesDiff = 0;

            if (AMORPM.equalsIgnoreCase(amOrPm)) {
                hourDiff = hour - Hour;
                if (minutes < Minutes) {
                    hourDiff = hourDiff - 1;
                    minutesDiff = (60 - Minutes) + minutes;
                } else {
                    minutesDiff = Minutes - minutes;
                }
            } else {
                if(AMORPM.equalsIgnoreCase("AM") && amOrPm.equalsIgnoreCase("PM")){
                    hourDiff = hour + 12 - Hour;
                    minutesDiff = minutes + (60 - Minutes);
                }
            }
            holder.textTime.setText(hourDiff+" hours "+minutesDiff+" minutes ago");
        } else {
            holder.textTime.setText(date);
        }

    }

    @Override
    public int getItemCount() {
        return posts.size();
    }
}
