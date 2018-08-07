package com.gsatechworld.gugrify.view.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gsatechworld.gugrify.R;
import com.gsatechworld.gugrify.SelectLanguageAndCities;

import java.util.HashSet;

import static com.gsatechworld.gugrify.SelectLanguageAndCities.list;

//import android.support.v7.app.AlertController;

public class LanguageRecyclerAdapter extends RecyclerView.Adapter<LanguageRecyclerAdapter.RecyclerViewHolder> {
    private LayoutInflater layoutInflater;
    private HashSet<Integer> expandedPositionSet;
    private Context context;
    boolean once;
    View temp;

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        public TextView tv;
        public ImageView image;
        public LinearLayout mainLayout;
        public View view;
        public View.OnClickListener myClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        };
        public RecyclerViewHolder(View view) {
            super(view);
            this.view = view;
            image = view.findViewById(R.id.activity_header_down_image);
            tv = view.findViewById(R.id.languageSelectText);
            mainLayout = view.findViewById(R.id.expandable_layout);
        }
    }

    public LanguageRecyclerAdapter(Context context) {
        this.layoutInflater = LayoutInflater.from(context);
        expandedPositionSet = new HashSet<>();
        this.context = context;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Log.d("No. of times", "it ran"+String.valueOf(once));
        View item = layoutInflater.inflate(R.layout.activity_header, parent, false);
        if(!once){
            temp = item;
            once = true;
        }
        //item.setOnClickListener(new SelectLanguageAndCities());
        return new RecyclerViewHolder(item);
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder, final int position) {

        holder.tv.setText(list.get(position));

        Log.d("Positions are", String.valueOf(position));

        if(position > 0)
        {
            holder.image.setVisibility(View.GONE);
        }
        else {
            ImageView imageView = temp.findViewById(R.id.activity_header_down_image);
            imageView.setVisibility(View.GONE);
            TextView textView = temp.findViewById(R.id.languageSelectText);
            textView.setText(list.get(list.size()-1));

            holder.image.setVisibility(View.VISIBLE);
            temp = holder.view;
        }
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Yes", String.valueOf(position));
                if(position == 0){
                    //TextView tv = temp.findViewById(R.id.languageSelectText);
                    SelectLanguageAndCities.pickedLanguage = list.get(list.size() - 1);
                }
                else
                SelectLanguageAndCities.pickedLanguage = list.get(position);

                list.clear();
                list.add(SelectLanguageAndCities.pickedLanguage);
                notifyDataSetChanged();

            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        return position % 2;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

//    class ReyclerViewHolder extends RecyclerView.ViewHolder {
//        private ExpandableLinearLayout expandableLayout;
//        private TextView showInfo;
//
//        private ReyclerViewHolder(final View view) {
//            super(view);
//            expandableLayout = (ExpandableLinearLayout) view.findViewById(R.id.expandable_layout);
//            showInfo = (TextView) view.findViewById(R.id.headerLanguage);
//        }
//
//        private void updateItem(final int position) {
//            expandableLayout.setOnExpandListener(new ExpandableLayout.OnExpandListener() {
//                @Override
//                public void onExpand(boolean expanded) {
//                    registerExpand(position, showInfo);
//                }
//            });
//            expandableLayout.setExpand(expandedPositionSet.contains(position));
//
//        }
//    }
//
//    private void registerExpand(int position, TextView textView) {
//        if (expandedPositionSet.contains(position)) {
//            removeExpand(position);
//            textView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_down, 0);
//            textView.setText("Show description");
//            Toast.makeText(context, "Position: " + position + " collapsed!", Toast.LENGTH_SHORT).show();
//        } else {
//            addExpand(position);
//            textView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_up, 0);
//            textView.setText("Hide description");
//            Toast.makeText(context, "Position: " + position + " expanded!", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    private void removeExpand(int position) {
//        expandedPositionSet.remove(position);
//    }
//
//    private void addExpand(int position) {
//        expandedPositionSet.add(position);
//    }

