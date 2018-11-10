package com.gsatechworld.gugrify.fragment;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.gsatechworld.gugrify.NewsSharedPreferences;
import com.gsatechworld.gugrify.R;
import com.gsatechworld.gugrify.view.DisplayBreakingNewsActivity;
import com.gsatechworld.gugrify.view.authentication.LoginActivity;

import java.util.ArrayList;

public class FragmentLayout extends Fragment {

    String comment = "";
    NewsSharedPreferences sharedPreferences;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
// Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.display_breaking_news_layout_fragment, container, false);

        TextView views = view.findViewById(R.id.viewsText);
        TextView likes = view.findViewById(R.id.likesText);
        TextView commentsNumber = view.findViewById(R.id.commentsText);
        ImageView comments_image = view.findViewById(R.id.comments_image);
        sharedPreferences = NewsSharedPreferences.getInstance(getActivity());

        LinearLayout layout = view.findViewById(R.id.addToPlaylistLayout);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(getActivity(), view);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        if (menuItem.getTitle().equals("Add to playlist")) {
                            Intent intent = new Intent(getActivity(), LoginActivity.class);
                            getActivity().startActivity(intent);
                        }
                        return false;
                    }
                });
                popup.inflate(R.menu.popup_menu);
                popup.show();
            }
        });

        Typeface fontRegular = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-Regular.ttf");
        views.setTypeface(fontRegular);
        likes.setTypeface(fontRegular);
        commentsNumber.setTypeface(fontRegular);

        views.setText(DisplayBreakingNewsActivity.postDetails.getResult().get(0).getViews());
        likes.setText(""+DisplayBreakingNewsActivity.postDetails.getResult().get(0).getLikes());
        commentsNumber.setText("" + DisplayBreakingNewsActivity.postDetails.getResult().get(0).getComments().size());
        comments_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (sharedPreferences.getIsLoggedIn()) {

                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                    alertDialog.setTitle("Comment");
                    alertDialog.setMessage("Enter comment");

                    final EditText input = new EditText(getActivity());
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.MATCH_PARENT);
                    input.setLayoutParams(lp);
                    input.setPadding(10, 0, 10, 0);
                    alertDialog.setView(input);
                    alertDialog.setIcon(R.mipmap.comments_icon);

                    alertDialog.setPositiveButton("YES",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    comment = input.getText().toString();
                                    makeCommentPostRequest(comment);
                                }
                            });

                    alertDialog.setNegativeButton("NO",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });

                    alertDialog.show();
                }
                else{
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    getActivity().startActivity(intent);
                }
            }
        });



        return view;
    }
}
