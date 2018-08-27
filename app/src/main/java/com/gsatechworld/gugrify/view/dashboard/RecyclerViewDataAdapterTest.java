package com.gsatechworld.gugrify.view.dashboard;

import android.content.Context;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
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
import java.util.List;
import java.util.Random;

public class RecyclerViewDataAdapterTest extends ListAdapter<SectionDataModel, RecyclerViewDataAdapterTest.ItemRowHolder> {

    private ArrayList<SectionDataModel> dataList;
    private Context mContext;
    private RecyclerView.RecycledViewPool recycledViewPool;
    private SnapHelper snapHelper;
    private SectionListDataAdapter adapter;
    Animation animation;

    private int lastPosition = -1;

    public static final DiffUtil.ItemCallback<SectionDataModel> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<SectionDataModel>() {
                @Override
                public boolean areItemsTheSame(SectionDataModel oldItem, SectionDataModel newItem) {
                    return oldItem.getHeaderTitle() == newItem.getHeaderTitle();
                }
                @Override
                public boolean areContentsTheSame(SectionDataModel oldItem, SectionDataModel newItem) {
                    return (oldItem.getHeaderTitle() == newItem.getHeaderTitle() /*&& oldItem.isOnline() == newItem.isOnline()*/ );
                }
            };

    public class ItemRowHolder extends RecyclerView.ViewHolder {
        protected TextView itemTitle;
        protected RecyclerView recyclerView;
        protected ImageView btnMore;
        protected ImageView img;
        protected View line;

        protected CardView listItemCard;

        protected CardView card;


        public ItemRowHolder(View itemView) {
            super(itemView);
            this.itemTitle = itemView.findViewById(R.id.itemTitle);
            this.recyclerView = itemView.findViewById(R.id.recycler_view_list);
            this.btnMore = itemView.findViewById(R.id.btnMore);
//            this.img = itemView.findViewById(R.id.img);
            this.img = itemView.findViewById(R.id.img);
            this.line = itemView.findViewById(R.id.viewItemLine);

            this.listItemCard = itemView.findViewById(R.id.listItemCard);

            this.card = itemView.findViewById(R.id.listItemCard);

        }
    }

    public RecyclerViewDataAdapterTest(ArrayList<SectionDataModel> dataList, Context mContext) {
        super(DIFF_CALLBACK);
        this.dataList = dataList;
        this.mContext = mContext;
        recycledViewPool = new RecyclerView.RecycledViewPool();
        submitList(dataList);
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
            case 0 : //This would be the header view in my Recycler
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
        final String sectionName = getItem(position).getHeaderTitle();

        if (position == 0) {
            latestNewItems = getItem(position).getLatestNewItemModelArrayList();
            adapter = new SectionListDataAdapter(latestNewItems, mContext, 1, position);
        } else if (position == 1) {
            playListItems = getItem(position).getPlayListItemModelArrayList();
            adapter = new SectionListDataAdapter(playListItems, mContext, 2, position);
            holder.btnMore.setVisibility(View.INVISIBLE);
            holder.line.setVisibility(View.GONE);
        }

        if (holder.itemTitle != null)
            holder.itemTitle.setText(sectionName);

        if (position <= 1) {
            holder.recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
            holder.recyclerView.setAdapter(adapter);
            holder.recyclerView.setHasFixedSize(true);
            holder.recyclerView.setRecycledViewPool(recycledViewPool);
            snapHelper.attachToRecyclerView(holder.recyclerView);

        } else {
            Glide.with(mContext)
                    .load(getItem(position).getImg())
                    .into(holder.img);


//            animation = AnimationUtils.loadAnimation(mContext,
//                    R.anim.item_animation_fall_down);
//            holder.card.startAnimation(animation);

            holder.btnMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(view.getContext(), "Button More Clicked!" + sectionName, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void addMoreContacts(List<SectionDataModel> sectionDataModels) {
        dataList.addAll(sectionDataModels);
        submitList(dataList); // DiffUtil takes care of the check
    }
//    @Override
//    public int getItemCount() {
//        return (null != dataList ? dataList.size() : 0);
//    }

}


