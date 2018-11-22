package com.gsatechworld.gugrify.fragment;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.gsatechworld.gugrify.NewsSharedPreferences;
import com.gsatechworld.gugrify.R;
import com.gsatechworld.gugrify.SelectLanguageAndCities;
import com.gsatechworld.gugrify.model.CommentsPostPojo;
import com.gsatechworld.gugrify.model.retrofit.ApiClient;
import com.gsatechworld.gugrify.model.retrofit.ApiInterface;
import com.gsatechworld.gugrify.model.retrofit.City;
import com.gsatechworld.gugrify.model.retrofit.CityResponse;
import com.gsatechworld.gugrify.model.retrofit.LikePojo;
import com.gsatechworld.gugrify.utils.NetworkUtil;
import com.gsatechworld.gugrify.view.DisplayBreakingNewsActivity;
import com.gsatechworld.gugrify.view.adapters.RecyclerViewDataAdapter;
import com.gsatechworld.gugrify.view.authentication.LoginActivity;
import com.gsatechworld.gugrify.view.dashboard.DashboardActivity;
import com.gsatechworld.gugrify.view.playlist.CreatePlayListDialog;
import com.gsatechworld.gugrify.view.playlist.GetPlaylistsPojo;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentLayout extends Fragment {

    String comment = "";
    NewsSharedPreferences sharedPreferences;
    ApiInterface apiService;
    private CreatePlayListDialog createPlayListDialog;
    GetPlaylistsPojo playlistsPojo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
// Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.display_breaking_news_layout_fragment, container, false);

        TextView views = view.findViewById(R.id.viewsText);
        TextView likes = view.findViewById(R.id.likesText);
        TextView commentsNumber = view.findViewById(R.id.commentsText);
        ImageView comments_image = view.findViewById(R.id.comments_image);
        ImageView likesImage = view.findViewById(R.id.likesImage);
        sharedPreferences = NewsSharedPreferences.getInstance(getActivity());
        ImageView iv_share = view.findViewById(R.id.iv_share);

        if (sharedPreferences.getSharedPrefValueBoolean("") || sharedPreferences.getIsLoggedIn()) {
            if (RecyclerViewDataAdapter.playListIds != null && RecyclerViewDataAdapter.playListIds.size() == 0)
                getPlaylists();
        }

        LinearLayout layout = view.findViewById(R.id.addToPlaylistLayout);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(getActivity(), view);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        if (menuItem.getTitle().equals("Add to playlist")) {
//                                Intent intent = new Intent(mContext, LoginActivity.class);
//                                mContext.startActivity(intent);

                            // need to login first before creating playlist

                            // show playlist for creating playlist
                            if (sharedPreferences.getIsLoggedIn() || sharedPreferences.getSharedPrefValueBoolean("reporterLoggedIn")) {
                                createPlayListDialog = createPlayListDialog.getInstance(DisplayBreakingNewsActivity.postDetails.getResult().get(0).getPostId(), getActivity(), RecyclerViewDataAdapter.playlistNames, RecyclerViewDataAdapter.playListIds);
                                createPlayListDialog.showDialog();
//                                createPlayListDialog.show();
                            } else {
                                Intent intent = new Intent(getActivity(), LoginActivity.class);
                                getActivity().startActivity(intent);
                            }
                        }
                        return false;
                    }
                });
                popup.inflate(R.menu.popup_menu);
                popup.show();
            }
        });

        iv_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String imageToShare = DisplayBreakingNewsActivity.postDetails.getResult().get(0).getImage(); //Image You wants to share

                String title = DisplayBreakingNewsActivity.postDetails.getResult().get(0).getNewsTitle(); //Title you wants to share

                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, title);
                shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                shareIntent.putExtra(Intent.EXTRA_TEXT, imageToShare);
                startActivity(Intent.createChooser(shareIntent, "Select App to Share Text and Image"));
            }
        });

        likesImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sharedPreferences.getIsLoggedIn() || sharedPreferences.getSharedPrefValueBoolean("reporterLoggedIn")) {
                    LikePojo pojo = new LikePojo(DisplayBreakingNewsActivity.postDetails.getResult().get(0).getPostId(), sharedPreferences.getSharedPrefValue("user_id"));
                    //Log.d("post_id", DisplayBreakingNewsActivity.postDetails.getResult().get(0).getPostId()+" "+sharedPreferences.getSharedPrefValue("user_id"));
                    likeaPost(pojo);
                } else {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    getActivity().startActivity(intent);
                }
            }
        });

        Typeface fontRegular = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-Regular.ttf");
        views.setTypeface(fontRegular);
        likes.setTypeface(fontRegular);
        commentsNumber.setTypeface(fontRegular);

        views.setText(DisplayBreakingNewsActivity.postDetails.getResult().get(0).getViews());
        likes.setText("" + DisplayBreakingNewsActivity.postDetails.getResult().get(0).getLikes());
        commentsNumber.setText("" + DisplayBreakingNewsActivity.postDetails.getResult().get(0).getComments().size());
        comments_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (sharedPreferences.getIsLoggedIn() || sharedPreferences.getSharedPrefValueBoolean("reporterLoggedIn")) {

                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                    alertDialog.setTitle("Comment");
                    alertDialog.setMessage("Enter comment");

                    final EditText input = new EditText(getActivity());
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.MATCH_PARENT);
                    input.setLayoutParams(lp);
                    alertDialog.setView(input);
                    alertDialog.setIcon(R.mipmap.comments_icon);

                    alertDialog.setPositiveButton("YES",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    comment = input.getText().toString();
                                    CommentsPostPojo postPojo = new CommentsPostPojo(DisplayBreakingNewsActivity.postDetails.getResult().get(0).getPostId(), sharedPreferences.getSharedPrefValue("user_id"), comment);
                                    makeCommentPostRequest(postPojo);
                                }
                            });

                    alertDialog.setNegativeButton("NO",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });

                    alertDialog.show();
                } else {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    getActivity().startActivity(intent);
                }
            }
        });

        return view;
    }

    void makeCommentPostRequest(CommentsPostPojo pojo) {
        apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<CommentsPostPojo> call = apiService.postComments(pojo);

        call.enqueue(new Callback<CommentsPostPojo>() {
            @Override
            public void onResponse(Call<CommentsPostPojo> call, Response<CommentsPostPojo> response) {
                CommentsPostPojo commentsResponse = null;
                if (response.isSuccessful()) {
                    commentsResponse = response.body();
                    getActivity().recreate();
                } else {
                    Toast.makeText(getActivity(), "Server error!!", Toast.LENGTH_SHORT).show();
                }
//                Log.d(TAG, "Number of movies received: " + movies.size());
            }

            @Override
            public void onFailure(Call<CommentsPostPojo> call, Throwable t) {
                // Log error here since request failed
                Toast.makeText(getActivity(), "Server error!!", Toast.LENGTH_SHORT).show();
                Log.e(SelectLanguageAndCities.class.getSimpleName(), t.toString());
            }
        });
    }

    public void likeaPost(LikePojo pojo) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<LikePojo> call = apiService.likeAPost(pojo);
        call.enqueue(new Callback<LikePojo>() {
            @Override
            public void onResponse(Call<LikePojo> call, Response<LikePojo> response) {
                LikePojo like = null;
                if (response.isSuccessful()) {
                    like = response.body();
                    if (like.getResult() == null) {
                        Toast.makeText(getActivity(), "You have already liked this post!!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), "Post liked!!", Toast.LENGTH_SHORT).show();
                        getActivity().recreate();
                    }

                } else {
                    Toast.makeText(getActivity(), "Server error1!!", Toast.LENGTH_SHORT).show();
                }
//                Log.d(TAG, "Number of movies received: " + movies.size());
            }

            @Override
            public void onFailure(Call<LikePojo> call, Throwable t) {
                // Log error here since request failed
                Toast.makeText(getActivity(), "Server error2!!", Toast.LENGTH_SHORT).show();
                Log.e(SelectLanguageAndCities.class.getSimpleName(), t.toString());
            }
        });
    }


    public void getPlaylists() {

        if (NetworkUtil.getInstance(getActivity()).isConnectingToInternet()) {

            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<GetPlaylistsPojo> call = apiService.getPlaylists(sharedPreferences.getSharedPrefValue("user_id"));

            call.enqueue(new Callback<GetPlaylistsPojo>() {
                @Override
                public void onResponse(Call<GetPlaylistsPojo> call, Response<GetPlaylistsPojo> response) {

                    if (response.isSuccessful()) {
                        Log.d("Reached here", "to gettig playlist");
                        playlistsPojo = response.body();
                        if (playlistsPojo.getResult() != null) {
                            for (int i = 0; i < playlistsPojo.getResult().size(); i++) {
                                RecyclerViewDataAdapter.playlistNames.add(playlistsPojo.getResult().get(i).getPlaylist_name());
                            }
                            for (int i = 0; i < playlistsPojo.getResult().size(); i++) {
                                RecyclerViewDataAdapter.playListIds.add(playlistsPojo.getResult().get(i).getPlaylist_id());
                            }
                        }

//        adapter.addMoreContacts(allSampleData);

                    } else {
                        Toast.makeText(getActivity(), "Something went wrong!", Toast.LENGTH_SHORT).show();

                    }
                }

                @Override
                public void onFailure(Call<GetPlaylistsPojo> call, Throwable t) {
                    // Log error here since request failed
                    Toast.makeText(getActivity(), "Something went wrong!", Toast.LENGTH_SHORT).show();

                }
            });
        } else {
//
        }
    }
}

