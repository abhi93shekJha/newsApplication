package com.gsatechworld.gugrify.view.dashboard;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper;
import com.gsatechworld.gugrify.R;
import com.gsatechworld.gugrify.view.genericadapter.BaseViewHolder;
import com.gsatechworld.gugrify.view.genericadapter.GenericRecyclerViewAdapter;
import com.gsatechworld.gugrify.view.genericadapter.OnRecyclerItemClickListener;

import java.util.ArrayList;

public class RecyclerViewDataAdapter extends GenericRecyclerViewAdapter<SectionDataModel, OnRecyclerItemClickListener, BaseViewHolder<SectionDataModel, OnRecyclerItemClickListener>> {
    private static final int VIEW_TYPE_NORMAL = R.layout.list_item;
//    private static final int VIEW_TYPE_COLORED = R.layout.item_user_accent;


    public RecyclerViewDataAdapter(Context context, OnRecyclerItemClickListener listener) {
        super(context, listener);
    }

    /**
     * Create here appropriate ViewHolder for each view type
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to an adapter position.
     * @param viewType The view type of the new View. It corresponds to layout resource id {@link android.support.annotation.LayoutRes} for convenience
     * @return ViewHolder which corresponds to needed view type
     */
    @Override
    public BaseViewHolder<SectionDataModel, OnRecyclerItemClickListener> onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_NORMAL:
                return new UserAccentViewHolder(inflate(viewType, parent), getListener());
//            case VIEW_TYPE_COLORED:
//                return new UserAccentViewHolder(inflate(viewType, parent), getListener());
            default:
                throw new IllegalArgumentException("Unexpected view type " + viewType);
        }
    }

    /**
     * Implement here returning various view types upon your requirements
     *
     * @param position item position
     * @return appropriate view type
     */
    @Override
    public int getItemViewType(int position) {
//        return (position % 2 == 0) ? VIEW_TYPE_NORMAL : VIEW_TYPE_COLORED;
        return (position % 2 == 0) ? VIEW_TYPE_NORMAL : VIEW_TYPE_NORMAL;
    }
}
