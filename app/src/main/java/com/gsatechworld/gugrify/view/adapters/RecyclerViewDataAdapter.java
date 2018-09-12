package com.gsatechworld.gugrify.view.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper;
import com.gsatechworld.gugrify.R;
import com.gsatechworld.gugrify.model.LatestNewItemModel;
import com.gsatechworld.gugrify.model.OtherNewsItemModel;
import com.gsatechworld.gugrify.model.PlayListItemModel;
import com.gsatechworld.gugrify.model.SectionDataModel;
import com.gsatechworld.gugrify.view.DisplayBreakingNewsActivity;
import com.gsatechworld.gugrify.view.authentication.LoginActivity;
import com.gsatechworld.gugrify.view.dashboard.AutoScrollViewPager;
import com.gsatechworld.gugrify.view.dashboard.DisplayVideoActivity;
import com.gsatechworld.gugrify.view.dashboard.EndlessScrollListener;

import java.util.ArrayList;

public class RecyclerViewDataAdapter extends RecyclerView.Adapter<RecyclerViewDataAdapter.ItemRowHolder>  {

    private ArrayList<SectionDataModel> dataList;
    private Context mContext;
    LinearLayoutManager l;
    ImageView[] dots;
    int dotscount;
    SectionListDataAdapter adapter;
    private RecyclerView.RecycledViewPool recycledViewPool;
    private SnapHelper snapHelper;
    EndlessScrollListener scrollListener;
    int count=0;
    boolean once = false;
    Animation animation;

    private int lastPosition = -1;

    public class ItemRowHolder extends RecyclerView.ViewHolder {
        protected TextView itemTitle;
        protected RecyclerView recyclerView;
        protected ImageView btnMore;
        protected ImageView img;
        RecyclerView recyclerView2;
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
            this.recyclerView2 = itemView.findViewById(R.id.homeScreenRecycler);
            this.viewPager = itemView.findViewById(R.id.viewPager);
            this.pager_indicator = itemView.findViewById(R.id.viewPagerCountDots);

            this.listItemCard = itemView.findViewById(R.id.listItemCard);

            this.card = itemView.findViewById(R.id.listItemCard);

        }
    }

    public RecyclerViewDataAdapter(ArrayList<SectionDataModel> dataList, Context mContext) {
        this.dataList = dataList;
        this.mContext = mContext;
        recycledViewPool = new RecyclerView.RecycledViewPool();
    }

    //Overriden so that I can display custom rows in the recyclerview
    @Override
    public int getItemViewType(int position) {
        int viewType = 1; //Default is 1
        if(position == 0)
            viewType = 0;
        if (position==1 ||position == 2)
            viewType = 2; //if zero, it will be a header view

        return viewType;
    }

    @Override
    public RecyclerViewDataAdapter.ItemRowHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        RecyclerViewDataAdapter.ItemRowHolder rowHolder;
        switch (viewType) {
            case 0:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_pager_test, parent, false);
                rowHolder = new RecyclerViewDataAdapter.ItemRowHolder(v);
                return rowHolder;
            case 2 :
                Log.d("Comes here", "to case 2");
                v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_item, parent, false);
                rowHolder = new RecyclerViewDataAdapter.ItemRowHolder(v);
                snapHelper = new GravitySnapHelper(Gravity.START);
                return rowHolder;
            default: //This is for posts
                v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_item_verti_test, parent, false);
                rowHolder = new RecyclerViewDataAdapter.ItemRowHolder(v);
                snapHelper = new GravitySnapHelper(Gravity.START);
                return rowHolder;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerViewDataAdapter.ItemRowHolder holder, int position) {
        ArrayList<LatestNewItemModel> latestNewItems;
        ArrayList<PlayListItemModel> playListItems;
        ArrayList<OtherNewsItemModel> otherNewsItems;
        final String sectionName = dataList.get(position).getHeaderTitle();

        if(position == 0 && !once){
            once = true;
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
        if (position == 1) {
            latestNewItems = dataList.get(position).getLatestNewItemModelArrayList();
            Log.d("Size of latestNews", String.valueOf(latestNewItems.size()));
            adapter = new SectionListDataAdapter(latestNewItems, mContext, 1, position-1);

           /* holder.recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
            holder.recyclerView.setAdapter(adapter);
            holder.recyclerView.setHasFixedSize(true);
            holder.recyclerView.setRecycledViewPool(recycledViewPool);
            snapHelper.attachToRecyclerView(holder.recyclerView);*/

            holder.itemTitle.setText(dataList.get(position).getHeaderTitle());
        }
        if (position == 2) {
//            Log.d("Time for", "playlist xml");
            playListItems = dataList.get(position).getPlayListItemModelArrayList();
//            Log.d("Size of playList", String.valueOf(playListItems.size()));
            adapter = new SectionListDataAdapter(playListItems, mContext, 2, position-1);

//            holder.btnMore.setVisibility(View.INVISIBLE);
            holder.itemTitle.setText(dataList.get(position).getHeaderTitle());

         /*   holder.recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
            holder.recyclerView.setAdapter(adapter);
            holder.recyclerView.setHasFixedSize(true);
            holder.recyclerView.setRecycledViewPool(recycledViewPool);
            snapHelper.attachToRecyclerView(holder.recyclerView);*/
        }

//        if (holder.itemTitle != null)
//            holder.itemTitle.setText(sectionName);

        if (position == 1 || position == 2) {
//            Log.d("position is", String.valueOf(position));
            holder.recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
            holder.recyclerView.setAdapter(adapter);
            holder.recyclerView.setHasFixedSize(true);
            holder.recyclerView.setRecycledViewPool(recycledViewPool);
//            holder.recyclerView.addItemDecoration(new DividerItemDecoration(mContext, 0));
            snapHelper.attachToRecyclerView(holder.recyclerView);
        }
        if(position > 2){
//            l = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
//            holder.recyclerView2.setLayoutManager(l);
//            PostRecyclerAdapter pA = new PostRecyclerAdapter(mContext, dataList.subList(3,dataList.size()));
//            holder.recyclerView2.setAdapter(pA);

            Glide.with(mContext).load(dataList.get(position).getImg()).into(holder.img);

            holder.itemTitle.setText(dataList.get(position).getHeaderTitle());
            Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.item_fall_down);
            holder.itemView.startAnimation(animation);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    /*Open Posted Video With You Tube View*/
                    mContext.startActivity(new Intent(mContext, DisplayBreakingNewsActivity.class));
                }
            });
            holder.btnMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PopupMenu popup = new PopupMenu(mContext, view);
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {
                            if(menuItem.getTitle().equals("Add to playlist")){
                                Intent intent = new Intent(mContext, LoginActivity.class);
                                mContext.startActivity(intent);
                            }
                            return false;
                        }
                    });
                    popup.inflate(R.menu.popup_menu);
                    popup.show();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public void showDialog(){

    }

}
