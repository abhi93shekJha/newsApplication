package com.gsatechworld.gugrify.view.playlist;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.gsatechworld.gugrify.R;

import java.util.ArrayList;

public class CreatePlayListDialog {
    static String ROOT = CreatePlayListDialog.class.getSimpleName();
    private  Activity mActivity;
    public Context mContext;
    public static CreatePlayListDialog instance;
    public Dialog dialog;
    private ArrayList<String> playListExistingList;
    private LinearLayout ll_container_playlistList, ll_createPlayList;

    public CreatePlayListDialog(Context context, Activity activity) {
        this.mContext = context;
        this.mActivity = activity;

    }

    public static CreatePlayListDialog getInstance(Context context, Activity activity){
        instance = new CreatePlayListDialog(context, activity);
        //  }
        return instance;
    }

    public void showDialog() {
        if(dialog != null && dialog.isShowing()){
            dialog.dismiss();
        }

        playListExistingList = new ArrayList<>();
        playListExistingList.add("js");
        playListExistingList.add("dsjh");
        playListExistingList.add("cdsvfds");
        playListExistingList.add("cdsafdwrew");

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

        ll_container_playlistList = (LinearLayout)dialog.findViewById(R.id.ll_container_playlistList);
        ll_createPlayList = (LinearLayout) dialog.findViewById(R.id.ll_createPlayList);

        View rowView = null;
        if(playListExistingList != null && playListExistingList != null) {
            for (int i = 0; i < playListExistingList.size(); i++) {

                rowView = mActivity.getLayoutInflater().inflate(R.layout.dynaic_view_row_create_play_list, null);

                TextView tv_playListTitle = (TextView) rowView.findViewById(R.id.tv_playListTitle);
                tv_playListTitle.setText(playListExistingList.get(i).toString());
                ll_container_playlistList.addView(rowView);
            }
        }

        ll_createPlayList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                Dialog dialog = new Dialog(mContext, R.style.DialogSlideAnim);
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

                dialog.show();
            }
        });
    }

    public void show() {
        dialog.show();
    }

}
