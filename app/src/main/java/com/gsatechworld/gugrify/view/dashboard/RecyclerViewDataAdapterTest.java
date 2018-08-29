package com.gsatechworld.gugrify.view.dashboard;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
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

public class RecyclerViewDataAdapterTest extends RecyclerView.Adapter<RecyclerViewDataAdapterTest.ItemRowHolder> {

    private ArrayList<SectionDataModel> dataList;
    private Context mContext;
    private RecyclerView.RecycledViewPool recycledViewPool;
    private SnapHelper snapHelper;
    ImageView[] dots;
    int dotscount;
    private SectionListDataAdapter adapter;
    Animation animation;

    private int lastPosition = -1;

    public class ItemRowHolder extends RecyclerView.ViewHolder {
        protected TextView itemTitle;
        protected RecyclerView recyclerView;
        protected ImageView btnMore;
        protected ImageView img;
        protected View line;
        protected AutoScrollViewPager viewPager;
        protected LinearLayout pager_indicator;

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
            this.viewPager = itemView.findViewById(R.id.viewPager);
            this.pager_indicator = itemView.findViewById(R.id.viewPagerCountDots);

            this.listItemCard = itemView.findViewById(R.id.listItemCard);

            this.card = itemView.findViewById(R.id.listItemCard);

        }
    }

    public RecyclerViewDataAdapterTest(ArrayList<SectionDataModel> dataList, Context mContext) {
        this.dataList = dataList;
        this.mContext = mContext;
        recycledViewPool = new RecyclerView.RecycledViewPool();
    }

    //Overriden so that I can display custom rows in the recyclerview
    @Override
    public int getItemViewType(int position) {
        int viewType = 1; //Default is 1
        if (position >= 1) viewType = 0; //if zero, it will be a header view
        return viewType;
    }

    @Override
    public ItemRowHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        ItemRowHolder rowHolder;
        switch (viewType) {
            case 0 : //This would be the header view in my Recycler
                v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_item, parent, false);
                rowHolder = new ItemRowHolder(v);
                snapHelper = new GravitySnapHelper(Gravity.START);
                return  rowHolder;

            default: //This would be the normal list with the pictures of the places in the world
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.view_pager_test, parent, false);
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

        if (position == 0) {

            holder.viewPager.startAutoScroll();
//        viewPager.setAnimation();
            holder.viewPager.setInterval(5000);
            holder.viewPager.setCycle(true);
            holder.viewPager.setStopScrollWhenTouch(true);

            ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(mContext);

            holder.viewPager.setAdapter(viewPagerAdapter);

            dotscount = viewPagerAdapter.getCount();
            dots = new ImageView[dotscount];

            for (int i = 0; i < dotscount; i++) {

                dots[i] = new ImageView(mContext);
                dots[i].setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.non_active_dot));

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);

                params.setMargins(8, 0, 8, 0);

                holder.pager_indicator.addView(dots[i], params);

            }

            dots[0].setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.active_dot));

            holder.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {

                    for (int i = 0; i < dotscount; i++) {
                        dots[i].setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.non_active_dot));
                    }

                    dots[position].setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.active_dot));

                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });

        }

         else if (position == 1) {
            playListItems = dataList.get(position).getPlayListItemModelArrayList();
            adapter = new SectionListDataAdapter(playListItems, mContext, 2, position);
            holder.btnMore.setVisibility(View.INVISIBLE);
            holder.line.setVisibility(View.GONE);
        }

//        if (holder.itemTitle != null)
//            holder.itemTitle.setText(sectionName);

        if (position == 1 || position == 2) {
            holder.recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
            holder.recyclerView.setAdapter(adapter);
            holder.recyclerView.setHasFixedSize(true);
            holder.recyclerView.setRecycledViewPool(recycledViewPool);
            snapHelper.attachToRecyclerView(holder.recyclerView);

        } else{
        latestNewItems = dataList.get(position).getLatestNewItemModelArrayList();
        adapter = new SectionListDataAdapter(latestNewItems, mContext, 1, position);
    }
    }

    @Override
    public int getItemCount() {
        return 3;
    }

}


