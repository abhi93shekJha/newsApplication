package com.gsatechworld.gugrify.view.dashboard;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper;
import com.gsatechworld.gugrify.R;

import java.util.ArrayList;
import java.util.Random;

public class RecyclerViewDataAdapterTest extends RecyclerView.Adapter<RecyclerViewDataAdapterTest.ItemRowHolder> {

    private ArrayList<SectionDataModel> dataList;
    private Context mContext;
    private RecyclerView.RecycledViewPool recycledViewPool;
    private SnapHelper snapHelper;
    private SectionListDataAdapter adapter;
    Animation slide;

    private int lastPosition = -1;

    public RecyclerViewDataAdapterTest(ArrayList<SectionDataModel> dataList, Context mContext) {
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
        switch (viewType) {
            case 0: //This would be the header view in my Recycler
                v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_item_verti_test, parent, false);
                rowHolder = new ItemRowHolder(v);
                snapHelper = new GravitySnapHelper(Gravity.START);
                return  rowHolder;
            default: //This would be the normal list with the pictures of the places in the world
                v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_item, parent, false);
                rowHolder = new ItemRowHolder(v);
                snapHelper = new GravitySnapHelper(Gravity.START);
                return  rowHolder;
        }
    }

    @Override
    public void onBindViewHolder(ItemRowHolder holder, int position) {
        ArrayList<LatestNewItemModel> latestNewItems;
        ArrayList<PlayListItemModel> playListItems;
        ArrayList<OtherNewsItemModel> otherNewsItems;
        final String sectionName = dataList.get(position).getHeaderTitle();


        if(position == 0){
            latestNewItems = dataList.get(position).getLatestNewItemModelArrayList();
            adapter = new SectionListDataAdapter(latestNewItems, mContext, 1, position);


        } else if(position == 1){
            playListItems = dataList.get(position).getPlayListItemModelArrayList();
            adapter = new SectionListDataAdapter(playListItems, mContext, 2, position);
            holder.btnMore.setVisibility(View.INVISIBLE);
            holder.line.setVisibility(View.GONE);
        }


       /* else if(position == 2){
           *//* otherNewsItems = dataList.get(position).getOtherNewsItemModelArrayList();
            adapter = new SectionListDataAdapter(otherNewsItems, mContext, 3,position);
*//*
            Animation animation = AnimationUtils.loadAnimation(mContext, (position
                    > lastPosition) ? R.anim.push_left_to_right :
                    R.anim.push_left_to_right);
            holder.itemView.startAnimation(animation);
            lastPosition = position;
        }*/
        if(holder.itemTitle != null)
        holder.itemTitle.setText(sectionName);

        if(position <= 1){
            holder.recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
            holder.recyclerView.setAdapter(adapter);
            holder.recyclerView.setHasFixedSize(true);
            holder.recyclerView.setRecycledViewPool(recycledViewPool);
            snapHelper.attachToRecyclerView(holder.recyclerView);

        } else {
            Glide.with(mContext)
                    .load(dataList.get(position).getImg())
                    .into(holder.img);

            setAnimation(holder.itemView, position);

         /*   Animation animation = AnimationUtils.loadAnimation(mContext, (position
                    > lastPosition) ? R.anim.push_left_to_right :
                    R.anim.push_left_to_right);
            holder.itemView.startAnimation(animation);
            lastPosition = position;*/
            //holder.img.setImageResource(dataList.get(position).getImg());

           /* int resId = R.anim.layout_animation_fall_down;
            LayoutAnimationController animationg = AnimationUtils.loadLayoutAnimation(mContext, resId);
            holder.listItemCard.setLayoutAnimation(animationg);*/
        }
        //slide = AnimationUtils.loadAnimation(mContext, R.anim.slide_left);
//        else {
//            holder.recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
//            holder.recyclerView.setAdapter(adapter);
//        }

        holder.btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Button More Clicked!" + sectionName, Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void setAnimation(View viewToAnimate, int position) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition) {
            ScaleAnimation anim = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            anim.setDuration(new Random().nextInt(5001));//to make duration random number between [0,501)
            viewToAnimate.startAnimation(anim);
            lastPosition = position;
        }
    }

    @Override
    public int getItemCount() {
        return (null != dataList ? dataList.size() : 0);
    }

    public class ItemRowHolder extends RecyclerView.ViewHolder {
        protected TextView itemTitle;
        protected RecyclerView recyclerView;
        protected ImageView btnMore;
        protected ImageView img;
        protected View line;
        protected CardView listItemCard;

        public ItemRowHolder(View itemView) {
            super(itemView);
            this.itemTitle = itemView.findViewById(R.id.itemTitle);
            this.recyclerView = itemView.findViewById(R.id.recycler_view_list);
            this.btnMore = itemView.findViewById(R.id.btnMore);
//            this.img = itemView.findViewById(R.id.img);
            this.img = itemView.findViewById(R.id.img);
            this.line = itemView.findViewById(R.id.viewItemLine);
            this.listItemCard = itemView.findViewById(R.id.listItemCard);
        }
    }
}
