package com.gsatechworld.gugrify.fragment;


import android.app.Fragment;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.gsatechworld.gugrify.R;

public class FragmentImage extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
// Inflate the layout for this fragment
       View view = inflater.inflate(R.layout.display_breaking_news_image_fragment, container, false);
        String imageString = getArguments().getString("image");
        ImageView imageView = view.findViewById(R.id.breakingNewsImage);

        if(getActivity() != null)
            Glide.with(getActivity()).load(imageString).into(imageView);

        ImageView image = view.findViewById(R.id.enlarge);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            }
        });

        return view;
    }
}
