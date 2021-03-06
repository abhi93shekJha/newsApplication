package com.gsatechworld.gugrify.view.adapters;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.provider.MediaStore;
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
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper;
import com.gsatechworld.gugrify.NewsSharedPreferences;
import com.gsatechworld.gugrify.R;
import com.gsatechworld.gugrify.model.retrofit.ActivePostsPojo;
import com.gsatechworld.gugrify.model.retrofit.ApiClient;
import com.gsatechworld.gugrify.model.retrofit.ApiInterface;
import com.gsatechworld.gugrify.model.retrofit.LatestNewsByCity;
import com.gsatechworld.gugrify.view.DisplayBreakingNewsActivity;
import com.gsatechworld.gugrify.view.authentication.LoginActivity;
import com.gsatechworld.gugrify.view.dashboard.AutoScrollViewPager;
import com.gsatechworld.gugrify.view.dashboard.DashboardActivity;
import com.gsatechworld.gugrify.view.dashboard.EndlessScrollListener;
import com.gsatechworld.gugrify.view.dashboard.LatesNewsDataAdapter;
import com.gsatechworld.gugrify.view.dashboard.PlaylistDataAdapter;
import com.gsatechworld.gugrify.view.playlist.CreatePlayListDialog;
import com.gsatechworld.gugrify.view.playlist.CreatePlayListPojo;
import com.gsatechworld.gugrify.view.playlist.GetPlaylistsPojo;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecyclerViewDataAdapter extends RecyclerView.Adapter<RecyclerViewDataAdapter.ItemRowHolder> {

    private Context mContext;
    LinearLayoutManager l;
    ImageView[] dots;
    int dotscount;
    private RecyclerView.RecycledViewPool recycledViewPool;
    private SnapHelper snapHelper;
    EndlessScrollListener scrollListener;
    int count = 0;
    boolean once = false;
    Animation animation;
    LatestNewsByCity news;
    GetPlaylistsPojo playlists;
    ActivePostsPojo activePosts;
    public static List<String> playlistNames, playListIds;
    NewsSharedPreferences sharedPreferences;
    TextView cancel, tv_ok;
    EditText et_playlistName;
    Calendar rightNow;

    private int lastPosition = -1;
    private CreatePlayListDialog createPlayListDialog;

    public class ItemRowHolder extends RecyclerView.ViewHolder {
        protected TextView itemTitle, tvViews, tvTime, tv_location;
        protected RecyclerView recycler_view_list;
        protected ImageView btnMore;
        protected LinearLayout ivShare;
        protected ImageView img;
        RecyclerView recyclerView2;
        protected AutoScrollViewPager viewPager;
        protected LinearLayout pager_indicator;
        protected CardView listItemCard;
        protected LinearLayout ll_playlist;
        protected CircleImageView profile_image;
        FrameLayout frame1;

        protected CardView card;
        RelativeLayout rl_headLine;

        public ItemRowHolder(View itemView) {
            super(itemView);
            this.itemTitle = itemView.findViewById(R.id.itemTitle);
            this.recycler_view_list = itemView.findViewById(R.id.recycler_view_list);
            this.btnMore = itemView.findViewById(R.id.btnMore);
//            this.img = itemView.findViewById(R.id.img);
            this.img = itemView.findViewById(R.id.img);
            this.recyclerView2 = itemView.findViewById(R.id.homeScreenRecycler);
            this.viewPager = itemView.findViewById(R.id.viewPager);
            this.pager_indicator = itemView.findViewById(R.id.viewPagerCountDots);
            this.ll_playlist = itemView.findViewById(R.id.ll_playlist);
            this.profile_image = itemView.findViewById(R.id.profile_image);
            this.tvViews = itemView.findViewById(R.id.tvViews);
            this.tvTime = itemView.findViewById(R.id.tvTime);
            this.tv_location = itemView.findViewById(R.id.tv_location);
            frame1 = itemView.findViewById(R.id.frame1);
            this.ivShare = itemView.findViewById(R.id.ivShare);

            this.listItemCard = itemView.findViewById(R.id.listItemCard);

            this.card = itemView.findViewById(R.id.listItemCard);
            rl_headLine = itemView.findViewById(R.id.rl_headLine);

        }
    }

    List<LatestNewsByCity.Result> forViewPager = new ArrayList<>();
    List<LatestNewsByCity.Result> forRecycler = new ArrayList<>();
    String frontImage, frontUrl;

    public RecyclerViewDataAdapter(ActivePostsPojo posts, GetPlaylistsPojo playlists, LatestNewsByCity news, Context mContext) {
        this.news = news;
        this.mContext = mContext;
        this.playlists = playlists;
        this.activePosts = posts;
        recycledViewPool = new RecyclerView.RecycledViewPool();

        rightNow = Calendar.getInstance();

        sharedPreferences = NewsSharedPreferences.getInstance(mContext);

        playlistNames = new ArrayList<>();
        playListIds = new ArrayList<>();

        if (playlists.getResult() != null) {
            for (int i = 0; i < playlists.getResult().size(); i++) {
                playlistNames.add(playlists.getResult().get(i).getPlaylist_name());
            }
            for (int i = 0; i < playlists.getResult().size(); i++) {
                playListIds.add(playlists.getResult().get(i).getPlaylist_id());
            }
        }
        if (news.getResult() != null) {
            for (int i = 0; i < 4; i++) {
                if (news.getResult().size() > i)
                    forViewPager.add(news.getResult().get(i));
            }

            frontImage = news.getHome().get(0).getImage();
            frontUrl = news.getHome().get(0).getUrl();
            if (news.getResult().size() <= 4) {
            } else {
                for (int i = 4; i < news.getResult().size(); i++)
                    forRecycler.add(news.getResult().get(i));
            }
        }

    }

    //Overriden so that I can display custom rows in the recyclerview
    @Override
    public int getItemViewType(int position) {
        int viewType = 1; //Default is 1
        if (position == 0)
            viewType = 0;
        if (position == 1)
            viewType = 2;
        if (position == 2)
            viewType = 3; //if zero, it will be a header view

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
            case 2:
                Log.d("Comes here", "to case 2");
                v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_item3, parent, false);
                rowHolder = new RecyclerViewDataAdapter.ItemRowHolder(v);
                snapHelper = new GravitySnapHelper(Gravity.START);
                return rowHolder;
            case 3:
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
    public void onBindViewHolder(RecyclerViewDataAdapter.ItemRowHolder holder, final int position) {

        if (position == 0 && !once) {
            once = true;
            holder.viewPager.startAutoScroll();
//        viewPager.setAnimation();
            holder.viewPager.setInterval(5000);
            holder.viewPager.setCycle(true);
            holder.viewPager.setStopScrollWhenTouch(true);

            ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(mContext, forViewPager, frontImage, frontUrl);
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

        } else if (position == 1) {
            LatesNewsDataAdapter adapter = new LatesNewsDataAdapter(forRecycler, mContext);
            holder.btnMore.setVisibility(View.GONE);
            holder.itemTitle.setText("Latest News");
            holder.recycler_view_list.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
            holder.recycler_view_list.setAdapter(adapter);
        } else if (position == 2) {

            if (playlists.getResult() == null) {
                holder.ll_playlist.setVisibility(View.GONE);
            } else {
                holder.ll_playlist.setVisibility(View.VISIBLE);
                holder.itemTitle.setText("Playlist");
                PlaylistDataAdapter adapter = new PlaylistDataAdapter(playlists, mContext);
                holder.recycler_view_list.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
                holder.recycler_view_list.setAdapter(adapter);

                holder.btnMore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        PopupMenu popup = new PopupMenu(mContext, view);
                        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem menuItem) {
                                if (menuItem.getTitle().equals("Create Playlist")) {
                                    showCreatePlaylistDialog();
                                }
                                return false;
                            }
                        });
                        popup.inflate(R.menu.menu_create);
                        popup.show();
                    }
                });
            }

        } else if (position >= 3) {

            if(position == 3){
                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                        FrameLayout.LayoutParams.WRAP_CONTENT,
                        FrameLayout.LayoutParams.WRAP_CONTENT
                );
                params.setMargins(0, 35, 0, 0);
                holder.frame1.setLayoutParams(params);
            }


            if (activePosts.getResult() != null) {
                holder.itemTitle.setText(activePosts.getResult().get(position - 3).getNewsHeadline());
                holder.tv_location.setText(activePosts.getResult().get(position - 3).getReporterLocation());
                holder.tvTime.setText(getDate(activePosts, position-3));
                holder.tvViews.setText(activePosts.getResult().get(position - 3).getViews() + " views");

                Glide.with(mContext).load(activePosts.getResult().get(position - 3).getImage()).into(holder.img);

                Glide.with(mContext).load(activePosts.getResult().get(position - 3).getReporterImage()).into(holder.profile_image);

                holder.btnMore.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {

                            PopupMenu popup = new PopupMenu(mContext, view);
                            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                                @Override
                                public boolean onMenuItemClick(MenuItem menuItem) {
                                    if (menuItem.getTitle().equals("Add to playlist")) {
//                                Intent intent = new Intent(mContext, LoginActivity.class);
//                                mContext.startActivity(intent);

                                        // need to login first before creating playlist

                                        // show playlist for creating playlist
//                                        if(sharedPreferences.ge)
                                        if(sharedPreferences.getIsLoggedIn() || sharedPreferences.getSharedPrefValueBoolean("reporterLoggedIn")) {
                                            createPlayListDialog = createPlayListDialog.getInstance(activePosts.getResult().get(position - 3).getPostId(), mContext, playlistNames, playListIds);
                                            createPlayListDialog.showDialog();
//                                            createPlayListDialog.show();
                                        }
                                        else {
                                            Intent intent = new Intent(mContext, LoginActivity.class);
                                            mContext.startActivity(intent);
                                        }

                                    }
                                    return false;
                                }
                            });
                            popup.inflate(R.menu.popup_menu);
                            popup.show();

                    }
                });

                holder.ivShare.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        String imageToShare = activePosts.getResult().get(position - 3).getImage(); //Image You wants to share

                        String title = activePosts.getResult().get(position - 3).getNewsTitle(); //Title you wants to share

                        Intent shareIntent = new Intent();
                        shareIntent.setAction(Intent.ACTION_SEND);
                        shareIntent.setType("text/plain");
                        shareIntent.putExtra(Intent.EXTRA_SUBJECT, title);
                        shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        shareIntent.putExtra(Intent.EXTRA_TEXT, "http://www.gugrify.com/posts/"+activePosts.getResult().get(position - 3).getPostId());
                        mContext.startActivity(Intent.createChooser(shareIntent, "Select App to Share Text and Image"));
                    }
                });

                holder.frame1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(mContext, DisplayBreakingNewsActivity.class);
                        intent.putExtra("postId", activePosts.getResult().get(position - 3).getPostId());
                        mContext.startActivity(intent);
                        if(mContext instanceof DashboardActivity)
                            ((DashboardActivity) mContext).finish();
                    }
                });

                Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.item_fall_down);
                holder.itemView.startAnimation(animation);

            }

//        holder.itemTitle.setText(dataList.get(position).getHeaderTitle());

         /* holder.recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
            holder.recyclerView.setAdapter(adapter);
            holder.recyclerView.setHasFixedSize(true);
            holder.recyclerView.setRecycledViewPool(recycledViewPool);
            snapHelper.attachToRecyclerView(holder.recyclerView);*/
        }

//        if (holder.itemTitle != null)
//            holder.itemTitle.setText(sectionName);
/*
        if(position ==1||position ==2)

    {
//            Log.d("position is", String.valueOf(position));
        holder.recyclerView.setLayoutManager();
        holder.recyclerView.setAdapter(adapter);
        holder.recyclerView.setHasFixedSize(true);
        holder.recyclerView.setRecycledViewPool(recycledViewPool);
//            holder.recyclerView.addItemDecoration(new DividerItemDecoration(mContext, 0));
        snapHelper.attachToRecyclerView(holder.recyclerView);
    }
        if(position >2)

    {
//            l = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
//            holder.recyclerView2.setLayoutManager(l);
//            PostRecyclerAdapter pA = new PostRecyclerAdapter(mContext, dataList.subList(3,dataList.size()));
//            holder.recyclerView2.setAdapter(pA);

        Glide.with(mContext).load(dataList.get(position).getImg()).into(holder.img);

        holder.itemTitle.setText(dataList.get(position).getHeaderTitle());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                *//*Open Posted Video With You Tube View*//*
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
                        if (menuItem.getTitle().equals("Add to playlist")) {
//                                Intent intent = new Intent(mContext, LoginActivity.class);
//                                mContext.startActivity(intent);

                            // need to login first before creating playlist

                            // show playlist for creating playlist
                            createPlayListDialog = createPlayListDialog.getInstance(mContext, (Activity) mContext);
                            createPlayListDialog.showDialog();
                            createPlayListDialog.show();
                        }
                        return false;
                    }
                });
                popup.inflate(R.menu.popup_menu);
                popup.show();
            }
        });
    }

        if(position ==1)

    {
        holder.btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(mContext, view);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        if (menuItem.getTitle().equals("Add to playlist")) {
//                                Intent intent = new Intent(mContext, LoginActivity.class);
//                                mContext.startActivity(intent);

                            // need to login first before creating playlist

                            // show playlist for creating playlist
                            createPlayListDialog = createPlayListDialog.getInstance(mContext, (Activity) mContext);
                            createPlayListDialog.showDialog();
                            createPlayListDialog.show();
                        }
                        return false;
                    }
                });
                popup.inflate(R.menu.popup_menu);
                popup.show();
            }
        });
    }*/

    }

    @Override
    public int getItemCount() {
        if (activePosts.getResult() != null) {
            return 3 + activePosts.getResult().size();
        } else
            return 3;
    }

    public void showCreatePlaylistDialog() {
        final Dialog dialog = new Dialog(mContext, R.style.DialogSlideAnim);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        //dialog.setCanceledOnTouchOutside(false);
        //dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_create_playlist_item);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(dialog.getWindow().getAttributes());
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(layoutParams);

        cancel = dialog.findViewById(R.id.cancel);
        tv_ok = dialog.findViewById(R.id.tv_ok);
        et_playlistName = dialog.findViewById(R.id.et_playlistName);

        dialog.show();

        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (et_playlistName.getText().toString().trim().equalsIgnoreCase("")) {
                    et_playlistName.setError("Empty");
                    et_playlistName.setFocusable(true);
                } else {
                    CreatePlayListPojo post = new CreatePlayListPojo(et_playlistName.getText().toString().trim(), sharedPreferences.getSharedPrefValue("user_id"));
                    makeCommentPost(post, dialog);
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
    }

    public void makeCommentPost(CreatePlayListPojo pojo, final Dialog dialog) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<CreatePlayListPojo> call = apiService.createPlaylist(pojo);

        call.enqueue(new Callback<CreatePlayListPojo>() {
            @Override
            public void onResponse(Call<CreatePlayListPojo> call, Response<CreatePlayListPojo> response) {
                CreatePlayListPojo playlistResponse = null;
                if (response.isSuccessful()) {
                    Log.d("Reached here", "true");
                    playlistResponse = response.body();
                    Toast.makeText(mContext, "Playlist saved!!", Toast.LENGTH_SHORT).show();
                    dialog.cancel();
                    if (mContext instanceof DashboardActivity){
                        ((DashboardActivity) mContext).recreate();
                    }
                } else {
                    Toast.makeText(mContext, "Server error!!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CreatePlayListPojo> call, Throwable t) {
                // Log error here since request failed
                Toast.makeText(mContext, "Server error!!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public String getDate(ActivePostsPojo activePosts, int position){
        String day = "" + rightNow.get(Calendar.DATE);
        if (day.length() == 1) {
            day = "0" + day;
        }

        String month = "" + (rightNow.get(Calendar.MONTH) + 1);
        if (month.length() == 1) {
            month = "0" + month;
        }
        String year = "" + rightNow.get(Calendar.YEAR);

        String date = year + "-" + month + "-" + day;

        if (date.equalsIgnoreCase(activePosts.getResult().get(position).getPublished_date())) {

            int hour = rightNow.get(Calendar.HOUR);
            int minutes = rightNow.get(Calendar.MINUTE);
            int seconds = rightNow.get(Calendar.SECOND);
            String amOrPm = rightNow.get(Calendar.AM_PM) == 0 ? "AM" : "PM";
            if(hour == 0){
                hour = 12;
            }

            String time = activePosts.getResult().get(position).getTimeOfPost();
            int Hour = Integer.parseInt(time.substring(0, 2));
            int Minutes = Integer.parseInt(time.substring(3, 5));
            int Seconds = Integer.parseInt(time.substring(6, 8));
            String AMORPM = time.substring(9);
            if(Hour == 0){
                Hour = 12;
            }

            int hourDiff = 0;
            int minutesDiff = 0;

            if (AMORPM.equalsIgnoreCase(amOrPm)) {
                if (hour == Hour) {
                    minutesDiff = minutes - Minutes;
                    hourDiff = 0;
                }
                else{
                    hourDiff = (hour - Hour) - 1;
                    minutesDiff = 60 - Minutes;
                    minutesDiff = minutesDiff + minutes;
                }
                if(minutesDiff > 60){
                    hourDiff = hourDiff + (minutesDiff/60);
                    minutesDiff = minutesDiff % 60;
                }

            } else {
                if (AMORPM.equalsIgnoreCase("AM") && amOrPm.equalsIgnoreCase("PM")) {
                    hourDiff = hour + 12 - Hour - 1;
                    minutesDiff = minutes + (60 - Minutes);
                    if(minutesDiff > 60){
                        hourDiff = hourDiff + (minutesDiff/60);
                        minutesDiff = minutesDiff % 60;
                    }
                }
            }
            return hourDiff + "h " + minutesDiff + "m ago";
        } else {
            return date;
        }
    }
}
