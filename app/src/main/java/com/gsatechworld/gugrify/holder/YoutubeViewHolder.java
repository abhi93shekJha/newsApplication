package com.gsatechworld.gugrify.holder;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeThumbnailView;
import com.gsatechworld.gugrify.R;


public class YoutubeViewHolder extends RecyclerView.ViewHolder{
    public YouTubeThumbnailView videoThumbnailImageView;
    public CardView youtubeCardView;

    public TextView textViewname, textViewdescription;

    public YoutubeViewHolder(View itemView) {
        super(itemView);
        videoThumbnailImageView = itemView.findViewById(R.id.video_thumbnail_image_view);
        youtubeCardView = itemView.findViewById(R.id.youtube_row_card_view);
        textViewname = itemView.findViewById(R.id.textViewname);
        textViewdescription = itemView.findViewById(R.id.textViewdescription);
    }
}
