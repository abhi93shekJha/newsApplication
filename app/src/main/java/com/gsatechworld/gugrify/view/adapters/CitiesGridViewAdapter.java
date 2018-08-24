package com.gsatechworld.gugrify.view.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import static com.gsatechworld.gugrify.SelectLanguageAndCities.*;

import com.gsatechworld.gugrify.R;

public class CitiesGridViewAdapter extends BaseAdapter{
    Context context;
    TextView tv1, tv2;
    View convertView;
    View prev;
    ImageView image;
    RelativeLayout relativeLayout;
    boolean once=false;
    boolean[] views = new boolean[9];
    public CitiesGridViewAdapter(Context context){
        this.context = context;
    }
    @Override
    public int getCount() {
        return 9;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        convertView = view;
        convertView = LayoutInflater.from(context).inflate(R.layout.grid_view_items, null);

        Typeface fontMedium = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Medium.ttf");

        image = convertView.findViewById(R.id.gridViewImage);
        tv1 = convertView.findViewById(R.id.citiesGridView_text);
        tv2 = convertView.findViewById(R.id.citiesGridView_text2);
        relativeLayout = convertView.findViewById(R.id.grid_view_items_relativeLayout);


        tv1.setTypeface(fontMedium);
        tv2.setTypeface(fontMedium);

        if(!views[i]){
            Log.d("Ran for", String.valueOf(i));
            relativeLayout.setBackgroundColor(context.getResources().getColor(R.color.colorWhite));
            tv1.setVisibility(View.VISIBLE);
            tv2.setVisibility(View.GONE);
        }
       image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!views[i]) {
                    Log.d("Ran", "inside true");
                    relativeLayout.setBackgroundColor(context.getResources().getColor(R.color.brown));
                    tv1.setVisibility(View.GONE);
                    tv2.setVisibility(View.VISIBLE);
                    views[i] = true;
                    notifyDataSetChanged();
                }
                else{
                    Log.d("Ran", "inside false");
                    relativeLayout.setBackgroundColor(context.getResources().getColor(R.color.colorWhite));
                    tv1.setVisibility(View.VISIBLE);
                    tv2.setVisibility(View.GONE);
                    views[i] = false;
                    notifyDataSetChanged();
                }
            }
        });

        return convertView;
    }


}
