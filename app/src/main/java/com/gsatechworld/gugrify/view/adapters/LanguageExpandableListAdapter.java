package com.gsatechworld.gugrify.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.gsatechworld.gugrify.R;
import com.gsatechworld.gugrify.SelectLanguageAndCities;

import java.util.List;

public class LanguageExpandableListAdapter extends BaseExpandableListAdapter {

    Context context;
    List<String> listDataHeader;


    public LanguageExpandableListAdapter(Context context, List<String> listDataHeader) {
        this.context = context;
        this.listDataHeader = listDataHeader;
    }

    @Override
    public int getGroupCount() {
        return 1;
    }

    @Override
    public int getChildrenCount(int i) {
        return listDataHeader.size();
    }

    @Override
    public Object getGroup(int i) {
        return SelectLanguageAndCities.selectedLanguage;
    }

    @Override
    public Object getChild(int i, int i1) {
        return listDataHeader.get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return 0;
    }

    @Override
    public long getChildId(int i, int i1) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.activity_header, null);
        }
        TextView tv = view.findViewById(R.id.languageSelectText);
        tv.setText(SelectLanguageAndCities.selectedLanguage);
        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.list_items, null);
        }

        TextView language = view.findViewById(R.id.languagesText);
        language.setText(listDataHeader.get(i1));
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
