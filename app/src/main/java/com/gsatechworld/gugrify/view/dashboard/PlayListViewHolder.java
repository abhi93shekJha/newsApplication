/*
 * Copyright (C) 2017 Leonid Ustenko Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.gsatechworld.gugrify.view.dashboard;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gsatechworld.gugrify.R;
import com.gsatechworld.gugrify.view.genericadapter.BaseViewHolder;
import com.gsatechworld.gugrify.view.genericadapter.OnRecyclerItemClickListener;

import java.util.ArrayList;

/**
 * A view holder implementation.
 *
 * @author Leonid Ustenko (Leo.Droidcoder@gmail.com)
 * @since 1.0.0
 */

public class PlayListViewHolder extends BaseViewHolder<SectionDataModel, OnRecyclerItemClickListener> {

    protected TextView itemTitle;
    protected RecyclerView recyclerView;
    protected ImageView btnMore;
    private SectionListDataAdapter adapter;


    public PlayListViewHolder(View itemView, OnRecyclerItemClickListener listener) {
        super(itemView, listener);
        initViews();
    }

    /**
     * Initialize views and set the listeners
     */
    private void initViews() {
        this.itemTitle = itemView.findViewById(R.id.itemTitle);
        this.recyclerView = itemView.findViewById(R.id.recycler_view_list);
        this.btnMore = itemView.findViewById(R.id.btnMore);
        if (getListener() != null) {
            //itemView.setOnClickListener(v -> getListener().onItemClick(getAdapterPosition()));
        }
    }

    @Override
    public void onBind(SectionDataModel item, int position) {
        ArrayList<LatestNewItemModel> latestNewItems = item.getLatestNewItemModelArrayList();
        ArrayList<PlayListItemModel> playListItems;
        ArrayList<OtherNewsItemModel> otherNewsItems;
        //final String sectionName = latestNewItems.get(position).getName();
        if(position == 0){
            latestNewItems =  item.getLatestNewItemModelArrayList();
           adapter = new SectionListDataAdapter(latestNewItems, itemView.getContext(), 1, 1);
        }

        recyclerView.setHasFixedSize(true);
        if(position >= 2){
            recyclerView.setLayoutManager(new LinearLayoutManager(itemView.getContext(), LinearLayoutManager.VERTICAL, false));
            recyclerView.setAdapter(adapter);
        } else {
            recyclerView.setLayoutManager(new LinearLayoutManager(itemView.getContext(), LinearLayoutManager.HORIZONTAL, false));
            recyclerView.setAdapter(adapter);
        }
        // bind data to the views
        itemTitle.setText(item.getHeaderTitle());
        //ageTv.setText(String.valueOf(item.getAge()));
    }
}
