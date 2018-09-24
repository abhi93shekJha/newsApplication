package com.gsatechworld.gugrify.view.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gsatechworld.gugrify.R;

public class ReporterProfileRecyclerAdapter extends RecyclerView.Adapter<ReporterProfileRecyclerAdapter.ReporterViewHolder> {

    Context context;
    public ReporterProfileRecyclerAdapter(Context context){
        this.context = context;
    }

    public class ReporterViewHolder extends RecyclerView.ViewHolder  {
        public ReporterViewHolder(View itemView) {
            super(itemView);
        }
    }

    @NonNull
    @Override
    public ReporterProfileRecyclerAdapter.ReporterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.reporter_profile_recycler_item, parent, false);
        return new ReporterProfileRecyclerAdapter.ReporterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReporterProfileRecyclerAdapter.ReporterViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }
}
