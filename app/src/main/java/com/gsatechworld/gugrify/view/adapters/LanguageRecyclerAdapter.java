package com.gsatechworld.gugrify.view.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gsatechworld.gugrify.NewsSharedPreferences;
import com.gsatechworld.gugrify.R;
import com.gsatechworld.gugrify.SelectLanguageAndCities;
import com.gsatechworld.gugrify.model.retrofit.City;
import com.gsatechworld.gugrify.view.dashboard.DashboardActivity;

import java.util.ArrayList;
import java.util.HashSet;

//import android.support.v7.app.AlertController;

public class LanguageRecyclerAdapter extends RecyclerView.Adapter<LanguageRecyclerAdapter.RecyclerViewHolder> {
    ArrayList<City> cities;
    private LayoutInflater layoutInflater;
    private HashSet<Integer> expandedPositionSet;
    private Context context;
    FloatingActionButton actionButton;
    Animation animation;
    RelativeLayout relativeLayout;
    boolean b=false;
    boolean once=false;
    View temp;
    NewsSharedPreferences sharedPreferences;
    ArrayList<Integer> selectedItem = new ArrayList<>();

    public LanguageRecyclerAdapter(final Context context, ArrayList<City> citie, FloatingActionButton action, RelativeLayout rLayout) {
        this.actionButton = action;
        this.relativeLayout = rLayout;
        this.layoutInflater = LayoutInflater.from(context);
        expandedPositionSet = new HashSet<>();
        this.context = context;
        this.cities = citie;
        sharedPreferences = NewsSharedPreferences.getInstance(context);

        action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DashboardActivity.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Log.d("No. of times", "it ran"+String.valueOf(once));
        View item = layoutInflater.inflate(R.layout.grid_view_items, parent, false);

        return new RecyclerViewHolder(item);
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder, final int position) {
         if (cities.get(position).isSelected() == true){
            holder.mainLayout.setBackgroundColor(context.getResources().getColor(R.color.brown));
            holder.tvBrown.setVisibility(View.GONE);
            holder.tvWhite.setVisibility(View.VISIBLE);
            actionButton.setVisibility(View.VISIBLE);
             relativeLayout.invalidate();
            if(!once) {
                animation = AnimationUtils.loadAnimation(context,
                        R.anim.slide_left);
                actionButton.startAnimation(animation);
                once=true;
            }
        } else {
            holder.mainLayout.setBackgroundColor(context.getResources().getColor(R.color.colorWhite));
            holder.tvBrown.setVisibility(View.VISIBLE);
            holder.tvWhite.setVisibility(View.GONE);
            if(selectedItem.size() == 0) {
                actionButton.setVisibility(View.GONE);
                once = false;
            }
        }

        //clicked on floating action button
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedPreferences.setCitySelected(cities.get(position).getCityName());
                Intent intent = new Intent(context, DashboardActivity.class);
                context.startActivity(intent);
            }
        });

         holder.tvWhite.setText(cities.get(position).getCityName());
         holder.tvBrown.setText(cities.get(position).getCityName());
    }

    @Override
    public int getItemCount() {
        return cities.size();
    }


    public class RecyclerViewHolder extends RecyclerView.ViewHolder{
        public TextView tvBrown;
        public TextView tvWhite;
        public ImageView image;
        public RelativeLayout mainLayout;
        public View view;

        public RecyclerViewHolder(View view) {
            super(view);
            this.view = view;
            image = view.findViewById(R.id.gridViewImage);
            tvBrown = view.findViewById(R.id.citiesGridView_text);
            tvWhite = view.findViewById(R.id.citiesGridView_text2);
            mainLayout = view.findViewById(R.id.grid_view_items_relativeLayout);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                        if(!b){
                            actionButton.setVisibility(View.VISIBLE);
                            animation = AnimationUtils.loadAnimation(context,
                                    R.anim.slide_left);
                            actionButton.startAnimation(animation);
                            b=true;
                        }

                        if(!cities.get(getAdapterPosition()).isSelected()){
                        cities.get(getAdapterPosition()).setSelected(true);

                        if(selectedItem.size() != 0){
                            cities.get(selectedItem.get(0)).setSelected(false);
                            notifyItemChanged(selectedItem.get(0));
                            selectedItem.clear();
                            selectedItem.add(getAdapterPosition());
                            notifyItemChanged(getAdapterPosition());
                        } else {
                            selectedItem.add(getAdapterPosition());
                            notifyItemChanged(getAdapterPosition());
                            //notifyDataSetChanged();
                        }
                    }
                    else{
                        cities.get(getAdapterPosition()).setSelected(false);
                            selectedItem.clear();
                            notifyItemChanged(getAdapterPosition());
                    }

                }
            });
        }

    }
}


