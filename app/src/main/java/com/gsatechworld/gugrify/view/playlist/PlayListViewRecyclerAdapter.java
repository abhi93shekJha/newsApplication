package com.gsatechworld.gugrify.view.playlist;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.aakira.expandablelayout.ExpandableLinearLayout;
import com.gsatechworld.gugrify.NewsSharedPreferences;
import com.gsatechworld.gugrify.R;
import com.gsatechworld.gugrify.fragment.FragmentImage;
import com.gsatechworld.gugrify.fragment.FragmentLayout;
import com.gsatechworld.gugrify.model.PostsByCategory;
import com.gsatechworld.gugrify.view.DisplayBreakingNewsActivity;

import java.util.ArrayList;

public class PlayListViewRecyclerAdapter extends RecyclerView.Adapter<PlayListViewRecyclerAdapter.MyViewHolder> {

    Context context;

    ArrayList<PlayListModel> posts;
    NewsSharedPreferences sharedPreferences;
    boolean b = false;
    boolean once = false;
    ArrayList<Integer> selectedItem = new ArrayList<>();
    public int currentItemClickedPosition = 0;

    public PlayListViewRecyclerAdapter(Context context, ArrayList<PlayListModel> posts) {
        this.context = context;
        this.posts = posts;
        sharedPreferences = NewsSharedPreferences.getInstance(context);
    }

    public PlayListViewRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.playlist_item_view, null, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final PlayListViewRecyclerAdapter.MyViewHolder holder, final int position) {

//        holder.setIsRecyclable(false);
//        holder.expandableLinearLayout.setInRecyclerView(true);

        if (posts.get(position).isClicked() == true) {
            holder.cardView.setBackgroundColor(context.getResources().getColor(R.color.black_overlay));
            holder.likes.setTextColor(context.getResources().getColor(R.color.colorWhite));
            holder.headline.setTextColor(context.getResources().getColor(R.color.colorWhite));
            holder.views.setTextColor(context.getResources().getColor(R.color.colorWhite));

            holder.iv_like.setColorFilter(context.getResources().getColor(R.color.colorWhite));
            holder.iv_view.setColorFilter(context.getResources().getColor(R.color.colorWhite));
            if (!once) {
                once = true;
            }
        } else {
            holder.cardView.setBackgroundColor(context.getResources().getColor(R.color.colorWhite));
            holder.likes.setTextColor(context.getResources().getColor(R.color.color_black));
            holder.headline.setTextColor(context.getResources().getColor(R.color.color_black));
            holder.views.setTextColor(context.getResources().getColor(R.color.color_black));
            holder.iv_like.setColorFilter(context.getResources().getColor(R.color.color_black));
            holder.iv_view.setColorFilter(context.getResources().getColor(R.color.color_black));
            if (selectedItem.size() == 0) {
                once = false;
            }
        }

        Glide.with(context).load(posts.get(position).getImage()).into(holder.image);
        holder.headline.setText(posts.get(position).getNews_headlines());
        holder.likes.setText(posts.get(position).getLikes());
        holder.views.setText(posts.get(position).getViews());
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public int getCurrentItemSelectedPosition() {
        return currentItemClickedPosition;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView textView, headline, views, likes;
        private LinearLayout linearLayout, l;
        private ExpandableLinearLayout expandableLinearLayout;
        private de.hdodenhof.circleimageview.CircleImageView imageView;
        private CardView cardView;
        private ImageView image, arrow, iv_like, iv_view;

        MyViewHolder(View view) {
            super(view);
            expandableLinearLayout = view.findViewById(R.id.expandableLayout);
            linearLayout = view.findViewById(R.id.expandable_ll);
            textView = view.findViewById(R.id.news_headline_text);
            imageView = view.findViewById(R.id.reporter_circular_image);
            iv_view = view.findViewById(R.id.iv_view);
            iv_like = view.findViewById(R.id.iv_like);
            l = view.findViewById(R.id.hiddenLinearLayout);
            cardView = view.findViewById(R.id.youtube_row_card_view);
            image = view.findViewById(R.id.video_thumbnail_image_view);
            headline = view.findViewById(R.id.textViewname);
            views = view.findViewById(R.id.text1);
            likes = view.findViewById(R.id.text2);
            arrow = view.findViewById(R.id.dropImage);

            view.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
//                    if(posts.get(position).isClicked){
//                        posts.get(position).setClicked(false);
//                        view.setBackgroundColor(view.getContext().getResources().getColor(R.color.colorWhite));
//                        holder.likes.setTextColor(view.getContext().getResources().getColor(R.color.color_black));
//                        holder.headline.setTextColor(view.getContext().getResources().getColor(R.color.color_black));
//                        holder.views.setTextColor(view.getContext().getResources().getColor(R.color.color_black));
//
//                        holder.iv_like.setColorFilter(view.getContext().getResources().getColor(R.color.color_black));
//                        holder.iv_view.setColorFilter(view.getContext().getResources().getColor(R.color.color_black));
//                        Toast.makeText(context, String.valueOf(position), Toast.LENGTH_SHORT).show();
//
//                    } else {
//                        posts.get(position).setClicked(true);
//                        view.setBackgroundColor(view.getContext().getResources().getColor(R.color.black_overlay));
//                        holder.likes.setTextColor(view.getContext().getResources().getColor(R.color.colorWhite));
//                        holder.headline.setTextColor(view.getContext().getResources().getColor(R.color.colorWhite));
//                        holder.views.setTextColor(view.getContext().getResources().getColor(R.color.colorWhite));
//
//                        holder.iv_like.setColorFilter(view.getContext().getResources().getColor(R.color.colorWhite));
//                        holder.iv_view.setColorFilter(view.getContext().getResources().getColor(R.color.colorWhite));
//                        FragmentImage fragment1 = new FragmentImage();
//                        if(context instanceof PlayListDetaildViewActivity){
//                            ((PlayListDetaildViewActivity) context).loadFragment(fragment1, position);
//                        }
//                    }
                    currentItemClickedPosition = getAdapterPosition();
                    itemClicked(getAdapterPosition());
                }
            });
        }
    }

    public void itemClicked(int adapterPosition) {
        if (adapterPosition != -1 && adapterPosition < posts.size()) {
            currentItemClickedPosition = adapterPosition;
            if (!posts.get(adapterPosition).isClicked()) {
                posts.get(adapterPosition).setClicked(true);
                FragmentImage fragment1 = new FragmentImage();
                if (context instanceof PlayListDetaildViewActivity) {
                    ((PlayListDetaildViewActivity) context).loadFragment(fragment1, adapterPosition);
                }

                if (selectedItem.size() != 0) {
                    posts.get(selectedItem.get(0)).setClicked(false);
                    notifyItemChanged(selectedItem.get(0));
                    selectedItem.clear();
                    selectedItem.add(adapterPosition);
                    notifyItemChanged(adapterPosition);
                } else {
                    selectedItem.add(adapterPosition);
                    notifyItemChanged(adapterPosition);
                    //notifyDataSetChanged();
                }
            } else {
                posts.get(adapterPosition).setClicked(false);
                selectedItem.clear();
                notifyItemChanged(adapterPosition);
            }
        }
    }
}
