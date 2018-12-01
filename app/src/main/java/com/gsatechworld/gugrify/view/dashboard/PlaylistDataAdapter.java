package com.gsatechworld.gugrify.view.dashboard;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.gsatechworld.gugrify.NewsSharedPreferences;
import com.gsatechworld.gugrify.R;
import com.gsatechworld.gugrify.model.retrofit.ApiClient;
import com.gsatechworld.gugrify.model.retrofit.ApiInterface;
import com.gsatechworld.gugrify.model.retrofit.GetPostsByPlaylistId;
import com.gsatechworld.gugrify.view.DisplayBreakingNewsActivity;
import com.gsatechworld.gugrify.view.playlist.CreatePlayListDialog;
import com.gsatechworld.gugrify.view.playlist.CreatePlayListPojo;
import com.gsatechworld.gugrify.view.playlist.GetPlaylistsPojo;
import com.gsatechworld.gugrify.view.playlist.PlayListDetaildViewActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlaylistDataAdapter extends RecyclerView.Adapter<PlaylistDataAdapter.PlayListViewHolder> {

    GetPlaylistsPojo playlists;
    Context context;
    private CreatePlayListDialog createPlayListDialog;
    List<String> playlistNames = new ArrayList<>();
    List<GetPlaylistsPojo.Result> results;

    TextView cancel, tv_ok;
    EditText et_playlistName;
    NewsSharedPreferences sharedPreferences;

    public PlaylistDataAdapter(GetPlaylistsPojo playlists, Context context) {
        this.playlists = playlists;
        this.context = context;
        results = new ArrayList<>();
        sharedPreferences = NewsSharedPreferences.getInstance(context);
        for (int i = 0; i < playlists.getResult().size(); i++) {
            playlistNames.add(playlists.getResult().get(i).getPlaylist_name());
        }
        if (playlists.getResult() != null) {
            for (int i = 0; i < playlists.getResult().size(); i++) {
                if (!playlists.getResult().get(i).getPlaylist_count().equalsIgnoreCase("0"))
                    results.add(playlists.getResult().get(i));
            }
        }
    }

    public class PlayListViewHolder extends RecyclerView.ViewHolder {
        ImageView itemImage;
        TextView tvTitle, tvTitleCount;
        LinearLayout mainLayout;

        public PlayListViewHolder(View itemView) {
            super(itemView);
            itemImage = itemView.findViewById(R.id.itemImage);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvTitleCount = itemView.findViewById(R.id.tvTitleCount);
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }
    }

    @Override
    public int getItemViewType(int position) {
        int viewType = 0;
        if (position == getItemCount() - 1) {
            viewType = 1;
        }
        return viewType;
    }

    @NonNull
    @Override
    public PlaylistDataAdapter.PlayListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = null;
        switch (viewType) {
            case 0:
                v = LayoutInflater.from(context).inflate(R.layout.list_single_card_paly_list, null);
                return new PlaylistDataAdapter.PlayListViewHolder(v);
            default:
                v = LayoutInflater.from(context).inflate(R.layout.activity_add_playlist, null);
                return new PlaylistDataAdapter.PlayListViewHolder(v);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull PlaylistDataAdapter.PlayListViewHolder holder, final int position) {

        if (position == getItemCount() - 1) {
            holder.mainLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PopupMenu popup = new PopupMenu(context, view);
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
        } else {
            if (playlists.getResult() != null) {
                holder.mainLayout.setVisibility(View.VISIBLE);
                if (results.get(position).getPlaylist_image() instanceof String) {
                    String playListImage = (String) results.get(position).getPlaylist_image();
                    Glide.with(context).load(playListImage).into(holder.itemImage);
                }
                holder.tvTitle.setText(results.get(position).getPlaylist_name());
                holder.tvTitleCount.setText(results.get(position).getPlaylist_count());

                holder.mainLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, PlayListDetaildViewActivity.class);
                        intent.putExtra("playlistId", results.get(position).getPlaylist_id());
                        context.startActivity(intent);
                    }
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        if (playlists.getResult() == null) {
            return 0;
        } else
            return results.size() + 1;
    }
    public void showCreatePlaylistDialog() {
        final Dialog dialog = new Dialog(context, R.style.DialogSlideAnim);
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
                    Toast.makeText(context, "Playlist saved!!", Toast.LENGTH_SHORT).show();
                    dialog.cancel();
                    if (context instanceof DashboardActivity){
                        ((DashboardActivity) context).recreate();
                    }
                } else {
                    Toast.makeText(context, "Server error!!", Toast.LENGTH_SHORT).show();
                    dialog.cancel();
                }
            }

            @Override
            public void onFailure(Call<CreatePlayListPojo> call, Throwable t) {
                // Log error here since request failed
                Toast.makeText(context, "Server error!!", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
