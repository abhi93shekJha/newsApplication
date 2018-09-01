package com.gsatechworld.gugrify.view.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gsatechworld.gugrify.R;
import com.gsatechworld.gugrify.model.NewsListHolder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class SearchRecyclerAdapter extends RecyclerView.Adapter<SearchRecyclerAdapter.MyViewHolder> implements Filterable {

    private List<NewsListHolder> list;
    private Context context;
    private List<NewsListHolder> listFiltered;

    public SearchRecyclerAdapter(List<NewsListHolder> list, Context context) {
        listFiltered = new ArrayList<>();
        this.list = list;
        this.context = context;
    }

    @Override
    public SearchRecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_list_item1, parent, false);
        return new SearchRecyclerAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final SearchRecyclerAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        Picasso.with(context)
                .load(listFiltered.get(position)
                        .getImage())
                .into(holder.image);
        holder.likes.setText(listFiltered.get(position).getLikes());
        holder.views.setText(listFiltered.get(position).getViews());
        holder.description.setText(listFiltered.get(position).getDescription());
        holder.heading.setText(listFiltered.get(position).getPostHeading());
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube://" + list.get(position).getYoutubekey()));
//                context.startActivity(intent);
//            }
//        });
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView heading, description, views, likes;
        ImageView image;

        MyViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imageView);
            heading = itemView.findViewById(R.id.textViewname);
            description = itemView.findViewById(R.id.textViewdescription);
            views = itemView.findViewById(R.id.text1);
            likes = itemView.findViewById(R.id.text2);
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                   listFiltered = list;
                } else {
                    List<NewsListHolder> filtered = new ArrayList<>();
                    for (NewsListHolder row : list) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getDescription().toLowerCase().contains(charString.toLowerCase()) || row.getPostHeading().toLowerCase().contains(charSequence)) {
                            filtered.add(row);
                        }
                    }

                    listFiltered = filtered;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values =  listFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                listFiltered = (ArrayList<NewsListHolder>) filterResults.values;

                // refresh the list with filtered data
                notifyDataSetChanged();
            }
        };
    }
    @Override
    public int getItemCount() {
        return listFiltered.size();
    }
}
