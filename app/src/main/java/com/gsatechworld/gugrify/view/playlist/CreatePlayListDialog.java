package com.gsatechworld.gugrify.view.playlist;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.gsatechworld.gugrify.NewsSharedPreferences;
import com.gsatechworld.gugrify.R;
import com.gsatechworld.gugrify.model.CommentsPostPojo;
import com.gsatechworld.gugrify.model.retrofit.ApiClient;
import com.gsatechworld.gugrify.model.retrofit.ApiInterface;
import com.gsatechworld.gugrify.model.retrofit.GetMainAdvertisement;
import com.gsatechworld.gugrify.model.retrofit.PlaylistPostPojo;
import com.gsatechworld.gugrify.view.DisplayBreakingNewsActivity;
import com.gsatechworld.gugrify.view.SplashActivity;
import com.gsatechworld.gugrify.view.dashboard.DashboardActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreatePlayListDialog {
    static String ROOT = CreatePlayListDialog.class.getSimpleName();
    public Context mContext;
    public static CreatePlayListDialog instance;
    public Dialog dialog;
    List<String> playListExistingList, playListIds;
    private LinearLayout ll_container_playlistList, ll_createPlayList;
    EditText et_playlistName;
    TextView cancel, tv_ok;
    ApiInterface apiService;
    NewsSharedPreferences sharedPreferences;
    String postId;


    public CreatePlayListDialog(String postId, Context context, List<String> playList, List<String> playListIds) {
        this.mContext = context;
        playListExistingList = playList;
        sharedPreferences = NewsSharedPreferences.getInstance(context);
        this.postId = postId;
        this.playListIds = playListIds;
    }

    public static CreatePlayListDialog getInstance(String postId, Context context, List<String> playList, List<String> playListIds) {
        instance = new CreatePlayListDialog(postId, context, playList, playListIds);
        return instance;
    }

    public void showDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }

        dialog = new Dialog(mContext, R.style.DialogSlideAnim);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        //dialog.setCanceledOnTouchOutside(false);
        //dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_create_playlist);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(dialog.getWindow().getAttributes());
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(layoutParams);

        ll_container_playlistList = (LinearLayout) dialog.findViewById(R.id.ll_container_playlistList);
        ll_createPlayList = (LinearLayout) dialog.findViewById(R.id.ll_createPlayList);

        if (mContext instanceof DisplayBreakingNewsActivity || mContext instanceof DashboardActivity) {
            ll_createPlayList.setVisibility(View.GONE);
        }

        View rowView = null;
        if (playListExistingList != null && playListExistingList.size() > 0) {
            for (int i = 0; i < playListExistingList.size(); i++) {

                rowView = LayoutInflater.from(mContext).inflate(R.layout.dynaic_view_row_create_play_list, null);

                final TextView tv_playListTitle = (TextView) rowView.findViewById(R.id.tv_playListTitle);
                tv_playListTitle.setText(playListExistingList.get(i));
                ll_container_playlistList.addView(rowView);

                tv_playListTitle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int i = 0;
                        for (i = 0; i < playListExistingList.size(); i++) {
                            if (tv_playListTitle.getText().toString().equals(playListExistingList.get(i)))
                                break;
                        }
                        PlaylistPostPojo pojo = new PlaylistPostPojo(sharedPreferences.getSharedPrefValue("user_id"), postId, playListIds.get(i), tv_playListTitle.getText().toString());
                        Log.d("playlist id is ", playListIds.get(i));
                        addPostToPlaylist(pojo);
                        dialog.dismiss();
                        if (mContext instanceof DashboardActivity) {
                            ((DashboardActivity) mContext).recreate();
                        }
                    }
                });
            }
            dialog.show();
        } else{
            dialog.dismiss();
            final Dialog dialog = new Dialog(mContext, R.style.DialogSlideAnim);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            //dialog.setCanceledOnTouchOutside(false);
            //dialog.setCancelable(false);
            dialog.setContentView(R.layout.dialog_create_playlist_item);
            layoutParams = new WindowManager.LayoutParams();
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
                        dialog.dismiss();
                        CreatePlayListPojo post = new CreatePlayListPojo(et_playlistName.getText().toString().trim(), sharedPreferences.getSharedPrefValue("user_id"));
                        makeCommentPost(post);
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

        /*ll_createPlayList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
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
                            dialog.dismiss();
                            CreatePlayListPojo post = new CreatePlayListPojo(et_playlistName.getText().toString().trim(), sharedPreferences.getSharedPrefValue("user_id"));
                            makeCommentPost(post);
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
        });*/
    }

    public void makeCommentPost(CreatePlayListPojo pojo) {
        apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<CreatePlayListPojo> call = apiService.createPlaylist(pojo);

        call.enqueue(new Callback<CreatePlayListPojo>() {
            @Override
            public void onResponse(Call<CreatePlayListPojo> call, Response<CreatePlayListPojo> response) {
                CreatePlayListPojo playlistResponse = null;
                if (response.isSuccessful()) {
                    Log.d("Reached here", "true");
                    playlistResponse = response.body();
                    playListExistingList.add(playlistResponse.getResults().getPlaylist_name());
                    Toast.makeText(mContext, "Playlist saved!!", Toast.LENGTH_SHORT).show();
                    if (mContext instanceof DashboardActivity) {
                        ((DashboardActivity) mContext).recreate();
                    }
                    if(mContext instanceof DisplayBreakingNewsActivity)
                        ((DisplayBreakingNewsActivity) mContext).recreate();

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

    public void show() {
        dialog.show();
    }

    public void addPostToPlaylist(PlaylistPostPojo pojo) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<PlaylistPostPojo> call = apiService.addPostToPlaylist(pojo);

        call.enqueue(new Callback<PlaylistPostPojo>() {
            @Override
            public void onResponse(Call<PlaylistPostPojo> call, Response<PlaylistPostPojo> response) {
                PlaylistPostPojo playlistResponse = null;
                if (response.isSuccessful()) {
                    Log.d("Reached here", "PlaylistPostPojo");
                    playlistResponse = response.body();
                    Toast.makeText(mContext, "Post successfully addedd!!", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(mContext, "Server error!!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PlaylistPostPojo> call, Throwable t) {
                // Log error here since request failed
                Toast.makeText(mContext, "Server error!!", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
