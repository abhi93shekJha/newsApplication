package com.gsatechworld.gugrify.view.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gsatechworld.gugrify.R;
import com.gsatechworld.gugrify.view.dashboard.DashboardActivity;

import java.util.ArrayList;
import java.util.HashSet;

import static com.gsatechworld.gugrify.SelectLanguageAndCities.list;

//import android.support.v7.app.AlertController;

public class LanguageRecyclerAdapter extends RecyclerView.Adapter<LanguageRecyclerAdapter.RecyclerViewHolder> {
    ArrayList<City> cities;
    private LayoutInflater layoutInflater;
    private HashSet<Integer> expandedPositionSet;
    private Context context;
    FloatingActionButton actionButton;
    Animation animation;
    boolean once;
    View temp;
    ArrayList<Integer> selectedItem = new ArrayList<>();

    public LanguageRecyclerAdapter(final Context context, ArrayList<City> citie, FloatingActionButton action) {
        this.actionButton = action;
        this.layoutInflater = LayoutInflater.from(context);
        expandedPositionSet = new HashSet<>();
        this.context = context;
        this.cities = citie;

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
         if (cities.get(position).isSelected == true){
            holder.mainLayout.setBackgroundColor(context.getResources().getColor(R.color.brown));
            holder.tvBrown.setVisibility(View.GONE);
            holder.tvWhite.setVisibility(View.VISIBLE);
            actionButton.setVisibility(View.VISIBLE);
            if(!once) {
                animation = AnimationUtils.loadAnimation(context,
                        R.anim.slide_left);
                actionButton.setAnimation(animation);
                once=true;
            }
             actionButton.invalidate();
        } else {
            holder.mainLayout.setBackgroundColor(context.getResources().getColor(R.color.colorWhite));
            holder.tvBrown.setVisibility(View.VISIBLE);
            holder.tvWhite.setVisibility(View.GONE);
            if(selectedItem.size() == 0) {
                actionButton.setVisibility(View.GONE);
                once = false;
            }
        }
         holder.tvBrown.setText(cities.get(position).cityName);
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

                        if(!cities.get(getAdapterPosition()).isSelected){
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


