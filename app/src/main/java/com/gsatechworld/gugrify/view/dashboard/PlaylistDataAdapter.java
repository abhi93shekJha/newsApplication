package com.gsatechworld.gugrify.view.dashboard;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gsatechworld.gugrify.R;
import com.gsatechworld.gugrify.view.DisplayBreakingNewsActivity;
import com.gsatechworld.gugrify.view.playlist.CreatePlayListDialog;
import com.gsatechworld.gugrify.view.playlist.GetPlaylistsPojo;

import java.util.ArrayList;
import java.util.List;

public class PlaylistDataAdapter extends RecyclerView.Adapter<PlaylistDataAdapter.PlayListViewHolder> {

    GetPlaylistsPojo playlists;
    Context context;
    private CreatePlayListDialog createPlayListDialog;
    List<String> playlistNames = new ArrayList<>();

    public PlaylistDataAdapter(GetPlaylistsPojo playlists, Context context) {
        this.playlists = playlists;
        this.context = context;
        for(int i=0; i<playlists.getResult().size(); i++){
            playlistNames.add(playlists.getResult().get(i).getPlaylist_name());
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

        if(position == getItemCount()-1){
            holder.mainLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                            PopupMenu popup = new PopupMenu(context, view);
                            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                                @Override
                                public boolean onMenuItemClick(MenuItem menuItem) {
                                    if (menuItem.getTitle().equals("Add to playlist")) {
//                                Intent intent = new Intent(mContext, LoginActivity.class);
//                                mContext.startActivity(intent);

                                        // need to login first before creating playlist

                                        // show playlist for creating playlist
                                        createPlayListDialog = createPlayListDialog.getInstance(context, playlistNames);
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
        else{
            if(playlists.getResult() != null) {
                if (playlists.getResult().get(position).getPlaylist_image() instanceof String) {
                    String playListImage = (String) playlists.getResult().get(position).getPlaylist_image();
                    Glide.with(context).load(playListImage).into(holder.itemImage);
                }
                holder.tvTitle.setText(playlists.getResult().get(position).getPlaylist_name());
                holder.tvTitleCount.setText(playlists.getResult().get(position).getPlaylist_count());

                holder.mainLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, DisplayBreakingNewsActivity.class);
                        intent.putExtra("postId", playlists.getResult().get(position).getPlaylist_id());
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
            return playlists.getResult().size() + 1;
    }

}
