package com.gsatechworld.gugrify.view.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gsatechworld.gugrify.R;

public class CitiesGridViewAdapter extends BaseAdapter{
    Context context;
    TextView tv1, tv2;
    View convertView;
    ViewHolder holder;
    boolean b;
    View[] views = new View[9];
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
        if (convertView == null){
            //Log.d("Times: ", "Hi");
        holder = new ViewHolder();
        convertView = LayoutInflater.from(context).inflate(R.layout.grid_view_items, null);
        holder.image = convertView.findViewById(R.id.gridViewImage);
        holder.tv1 = convertView.findViewById(R.id.citiesGridView_text);
        holder.tv2 = convertView.findViewById(R.id.citiesGridView_text2);
        convertView.setTag(holder);
    }

        else {
            holder = (ViewHolder) convertView.getTag();
        }

        Typeface fontMedium = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Medium.ttf");

        holder.tv1.setTypeface(fontMedium);
        holder.tv2.setTypeface(fontMedium);

//        holder.image.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.d("Poistion of image is:", String.valueOf(i));
//                if(!b) {
//                    Log.d("In : ", "False");
//                    TextView tv = views[i].findViewById(R.id.citiesGridView_text2);
//                    RelativeLayout r = views[i].findViewById(R.id.grid_view_items_relativeLayout);
//                    r.setBackgroundColor(context.getResources().getColor(R.color.brown));
//                    tv.setVisibility(View.VISIBLE);
//                    b = true;
//                    notifyDataSetChanged();
//                }
//                else{
//                    Log.d("In : ", "True");
//                    TextView tv = views[i].findViewById(R.id.citiesGridView_text2);
//                    RelativeLayout r = views[i].findViewById(R.id.grid_view_items_relativeLayout);
//                    r.setBackgroundColor(context.getResources().getColor(R.color.colorWhite));
//                    tv.setVisibility(View.GONE);
//                    b = false;
//                    notifyDataSetChanged();
//                }
//            }
//        });
//
//        holder.tv1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.d("Poistion of text is:", String.valueOf(i));
//                if(!b) {
//                    Log.d("In : ", "False");
//                    TextView tv = views[i].findViewById(R.id.citiesGridView_text2);
//                    RelativeLayout r = views[i].findViewById(R.id.grid_view_items_relativeLayout);
//                    r.setBackgroundColor(context.getResources().getColor(R.color.brown));
//                    tv.setVisibility(View.VISIBLE);
//                    b = true;
//                    notifyDataSetChanged();
//                }
//                else{
//                    Log.d("In : ", "True");
//                    TextView tv = views[i].findViewById(R.id.citiesGridView_text2);
//                    RelativeLayout r = views[i].findViewById(R.id.grid_view_items_relativeLayout);
//                    r.setBackgroundColor(context.getResources().getColor(R.color.colorWhite));
//                    tv.setVisibility(View.GONE);
//                    b = false;
//                    notifyDataSetChanged();
//                }
//            }
//        });
        views[i] = convertView;
        return convertView;
    }

    public class ViewHolder{
        private ImageView image;
        private TextView tv1;
        private TextView tv2;
    }

}
