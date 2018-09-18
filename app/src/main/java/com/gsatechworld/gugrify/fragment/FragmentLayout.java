package com.gsatechworld.gugrify.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
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
import com.gsatechworld.gugrify.view.authentication.LoginActivity;

import java.util.ArrayList;

public class FragmentLayout extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
// Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.display_breaking_news_layout_fragment, container, false);

        ArrayList<String> viewsAndLikes = getArguments().getStringArrayList("forLinearLayout");
        TextView views = view.findViewById(R.id.viewsText);
        TextView likes = view.findViewById(R.id.likesText);
        TextView comments = view.findViewById(R.id.commentsText);
        TextView shares = view.findViewById(R.id.shareText);

        LinearLayout layout = view.findViewById(R.id.addToPlaylistLayout);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(getActivity(), view);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        if(menuItem.getTitle().equals("Add to playlist")){
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
        comments.setTypeface(fontRegular);
        shares.setTypeface(fontRegular);

        views.setText(viewsAndLikes.get(0));
        likes.setText(viewsAndLikes.get(1));

        return view;
    }
}
