package com.gsatechworld.gugrify.view.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gsatechworld.gugrify.R;
import com.gsatechworld.gugrify.model.SectionDataModel;

import java.util.List;

public class PostRecyclerAdapter extends RecyclerView.Adapter<PostRecyclerAdapter.RViewHolder> {
    Context context;
    List<SectionDataModel> lists;
    public PostRecyclerAdapter(Context context, List<SectionDataModel> lists){
        this.context = context;
        this.lists = lists;
    }
    @NonNull
    @Override
    public RViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_verti_test, parent, false);
        return new PostRecyclerAdapter.RViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RViewHolder holder, int position) {
        holder.itemTitle.setText(lists.get(position).getHeaderTitle());
        Glide.with(context)
                .load(lists.get(position).getImg())
                .into(holder.img);

    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    public class RViewHolder extends RecyclerView.ViewHolder{
        TextView itemTitle;
        ImageView img;
        public RViewHolder(View itemView) {
            super(itemView);
            itemTitle = itemView.findViewById(R.id.itemTitle);
            img = itemView.findViewById(R.id.img);
        }
    }
}
