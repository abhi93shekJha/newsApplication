package com.gsatechworld.gugrify.view.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInstaller;
import android.media.MediaCas;
import android.media.tv.TvInputService;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.gsatechworld.gugrify.NewsSharedPreferences;
import com.gsatechworld.gugrify.R;
import com.gsatechworld.gugrify.SelectLanguageAndCities;
import com.gsatechworld.gugrify.model.NavItemModel;
import com.gsatechworld.gugrify.model.retrofit.NewsCategories;
import com.gsatechworld.gugrify.view.ActivityContactUs;
import com.gsatechworld.gugrify.view.ActivityShowWebView;
import com.gsatechworld.gugrify.view.PostByCategory;
import com.gsatechworld.gugrify.view.ReporterProfile;
import com.gsatechworld.gugrify.view.authentication.LoginActivity;
import com.gsatechworld.gugrify.view.authentication.ReporterLoginActivity;
import com.gsatechworld.gugrify.view.dashboard.DashboardActivity;
import com.gsatechworld.gugrify.view.dashboard.DisplayVideoActivity;

import java.util.ArrayList;

public class RecyclerViewNavAdapter extends RecyclerView.Adapter<RecyclerViewNavAdapter.ItemRowHolder> {

    private NewsCategories newsCategories;
    private Context mContext;
    private RecyclerView.RecycledViewPool recycledViewPool;
    NewsSharedPreferences sharedPreferences;
    public GoogleSignInClient mGoogleSignInClient;

    public RecyclerViewNavAdapter(NewsCategories dataList, Context mContext) {
        this.newsCategories = dataList;
        this.mContext = mContext;
        recycledViewPool = new RecyclerView.RecycledViewPool();
        sharedPreferences = NewsSharedPreferences.getInstance(mContext);
    }

    //Overriden so that I can display custom rows in the recyclerview
    @Override
    public int getItemViewType(int position) {
        int viewType = 0; //Default is 1
        if (position == newsCategories.getCategory().size())
            viewType = 1; //if zero, it will be a header view
        if (sharedPreferences.getSharedPrefValueBoolean("reporterLoggedIn")) {
            if (position == newsCategories.getCategory().size() + 1) viewType = 6;
            if (position == newsCategories.getCategory().size() + 2) viewType = 2;
            if (position == newsCategories.getCategory().size() + 3) viewType = 3;
            if (position == newsCategories.getCategory().size() + 4) viewType = 4;
            if (position == newsCategories.getCategory().size() + 5) viewType = 5;
        } else {
            if (position == newsCategories.getCategory().size() + 1) viewType = 2;
            if (position == newsCategories.getCategory().size() + 2) viewType = 3;
            if (position == newsCategories.getCategory().size() + 3) viewType = 4;
            if (position == newsCategories.getCategory().size() + 4) viewType = 5;
        }
        return viewType;
    }

    @Override
    public ItemRowHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        ItemRowHolder rowHolder;
        switch (viewType) {
            case 0:
                v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.nav_item_row, parent, false);
                rowHolder = new ItemRowHolder(v);
                return rowHolder;
            case 1:
                v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.nav_other_item, parent, false);
                rowHolder = new ItemRowHolder(v);
                return rowHolder;
            case 2:
                v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.nav_sign_out, parent, false);
                rowHolder = new ItemRowHolder(v);
                return rowHolder;
            case 3:
                v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.nav_about_us, parent, false);
                rowHolder = new ItemRowHolder(v);
                return rowHolder;
            case 4:
                v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.nav_contact_us, parent, false);
                rowHolder = new ItemRowHolder(v);
                return rowHolder;
            case 5:
                v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.nav_settings, parent, false);
                rowHolder = new ItemRowHolder(v);
                return rowHolder;
            default:
                v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.nav_reporter_dashboard, parent, false);
                rowHolder = new ItemRowHolder(v);
                return rowHolder;
        }
    }

    @Override
    public void onBindViewHolder(final ItemRowHolder holder, int position) {
        if (position < newsCategories.getCategory().size()) {
            final String sectionName = newsCategories.getCategory().get(position);
            holder.navItemTitle.setText(sectionName);

            holder.nav_linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, PostByCategory.class);
                    intent.putExtra("category", sectionName);
                    mContext.startActivity(intent);
                }
            });
        }

        //for showing video
        else if (position == newsCategories.getCategory().size()) {

            holder.nav_linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, DisplayVideoActivity.class);
                    mContext.startActivity(intent);
                }
            });

        }

        //for sign in and sign out
        else if (position == newsCategories.getCategory().size() + 1) {

            holder.nav_linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (holder.navItemTitle.getText().toString().equalsIgnoreCase("Reporter Sign In")) {
                        Intent intent = new Intent(mContext, ReporterLoginActivity.class);
                        mContext.startActivity(intent);
                        if(mContext instanceof DashboardActivity)
                            ((DashboardActivity) mContext).finish();
                    } else {
                        if (sharedPreferences.getSharedPrefValueBoolean("reporterLoggedIn")) {
                            Intent intent = new Intent(mContext, ReporterProfile.class);
                            mContext.startActivity(intent);
                        } else {
                            if (sharedPreferences.getLoggedInUsingFB()) {
                                FacebookSdk.sdkInitialize(mContext);
                                LoginManager.getInstance().logOut();
                                sharedPreferences.setLoggedIn(false);
                                sharedPreferences.setLoggedInUsingFB(false);
                            } else if (sharedPreferences.getLoggedInUsingGoogle()) {
                                sharedPreferences.setLoggedIn(false);
                                sharedPreferences.setLoggedInUsingGoogle(false);
                                GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                                        .requestIdToken(mContext.getString(R.string.server_client_id))
                                        .requestEmail()
                                        .build();

                                mGoogleSignInClient = GoogleSignIn.getClient(mContext, gso);
                                mGoogleSignInClient.revokeAccess()
                                        .addOnCompleteListener((Activity) mContext, new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                // ...
                                            }
                                        });
                            } else {
                                sharedPreferences.setLoggedIn(false);
                                sharedPreferences.setSharedPrefValueBoolean("reporterLoggedIn", false);
                            }
                            if (mContext instanceof DashboardActivity)
                                ((DashboardActivity) mContext).recreate();
                        }
                    }
                }
            });

            if (sharedPreferences.getSharedPrefValueBoolean("reporterLoggedIn")) {

            } else {
                if (sharedPreferences.getSharedPrefValueBoolean("reporterLoggedIn") || sharedPreferences.getIsLoggedIn()) {
                    holder.navItemTitle.setText("Sign Out");
                } else {
                    holder.navItemTitle.setText("Reporter Sign In");
                }
            }
        }
        //for about us
        else if (position == newsCategories.getCategory().size() + 2) {
            if (sharedPreferences.getSharedPrefValueBoolean("reporterLoggedIn") || sharedPreferences.getIsLoggedIn())
                holder.navItemTitle.setText("Sign Out");
            holder.nav_linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (sharedPreferences.getSharedPrefValueBoolean("reporterLoggedIn")) {
                        if (sharedPreferences.getLoggedInUsingFB()) {
                            FacebookSdk.sdkInitialize(mContext);
                            LoginManager.getInstance().logOut();
                            sharedPreferences.setLoggedIn(false);
                            sharedPreferences.setLoggedInUsingFB(false);
                        } else if (sharedPreferences.getLoggedInUsingGoogle()) {
                            sharedPreferences.setLoggedIn(false);
                            sharedPreferences.setLoggedInUsingGoogle(false);
                            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                                    .requestIdToken(mContext.getString(R.string.server_client_id))
                                    .requestEmail()
                                    .build();

                            mGoogleSignInClient = GoogleSignIn.getClient(mContext, gso);
                            mGoogleSignInClient.revokeAccess()
                                    .addOnCompleteListener((Activity) mContext, new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            // ...
                                        }
                                    });
                        } else {
                            sharedPreferences.setLoggedIn(false);
                            sharedPreferences.setSharedPrefValueBoolean("reporterLoggedIn", false);
                        }
                        if (mContext instanceof DashboardActivity)
                            ((DashboardActivity) mContext).recreate();
                    } else {
                        Intent intent = new Intent(mContext, ActivityShowWebView.class);
                        intent.putExtra("url", "www.gugrify.com/about-us.php");
                        mContext.startActivity(intent);
                    }
                }
            });
        }

        //for contact us
        else if (position == newsCategories.getCategory().size() + 3) {
            holder.nav_linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (sharedPreferences.getSharedPrefValueBoolean("reporterLoggedIn")) {
                        Intent intent = new Intent(mContext, ActivityShowWebView.class);
                        intent.putExtra("url", "www.gugrify.com/about-us.php");
                        mContext.startActivity(intent);
                    } else {
                        Intent intent = new Intent(mContext, ActivityContactUs.class);
                        mContext.startActivity(intent);
                    }
                }
            });
        } else if (position == newsCategories.getCategory().size() + 4) {
            holder.nav_linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (sharedPreferences.getSharedPrefValueBoolean("reporterLoggedIn")) {
                        Intent intent = new Intent(mContext, ActivityContactUs.class);
                        mContext.startActivity(intent);
                    } else {
                        Intent intent = new Intent(mContext, SelectLanguageAndCities.class);
                        intent.putExtra("fromSettings", true);
                        mContext.startActivity(intent);
                    }
                    /*if(mContext instanceof DashboardActivity)
                        ((DashboardActivity) mContext).finish();*/
                }
            });
        } else {
            holder.nav_linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(mContext, SelectLanguageAndCities.class);
                    intent.putExtra("fromSettings", true);
                    mContext.startActivity(intent);

                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (sharedPreferences.getSharedPrefValueBoolean("reporterLoggedIn")) {
            return (null != newsCategories.getCategory() ? newsCategories.getCategory().size() + 6 : 0);
        } else
            return (null != newsCategories.getCategory() ? newsCategories.getCategory().size() + 5 : 0);
    }

    public class ItemRowHolder extends RecyclerView.ViewHolder {
        protected TextView navItemTitle;
        protected LinearLayout nav_linearLayout;

        public ItemRowHolder(View itemView) {
            super(itemView);
            this.navItemTitle = itemView.findViewById(R.id.navItemTitle);
            this.nav_linearLayout = itemView.findViewById(R.id.nav_linearLayout);
        }
    }

}
