package com.gsatechworld.gugrify;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class NewsSharedPreferences implements SharedPreferences.OnSharedPreferenceChangeListener{

    private SharedPreferences androidPref;
    private SharedPreferences.Editor editor;

    private Context context;
    private static NewsSharedPreferences mySharedPref;

    private NewsSharedPreferences(Context context) {
        this.context = context;
        this.androidPref = context.getSharedPreferences(context.getPackageName(), Activity.MODE_PRIVATE);
        this.androidPref.registerOnSharedPreferenceChangeListener(this);
    }

    public static NewsSharedPreferences getInstance(Context context) {
        if (mySharedPref == null) {
            mySharedPref = new NewsSharedPreferences(context);
        }
        return mySharedPref;
    }

    public void setIsFirstTime(boolean value){
        editor = androidPref.edit();
        editor.putBoolean("FirstTime", value);
        editor.commit();
    }

    public boolean getIsFirstTime(){
        return androidPref.getBoolean("FirstTime", true);
    }

    public void setCitySelected(String value){
        editor = androidPref.edit();
        editor.putString("citySelected", value);
        editor.commit();
    }

    public String getCitySelected(){
        return androidPref.getString("citySelected", "");
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {

    }

    public void setClickedPosition(int position){
        editor = androidPref.edit();
        editor.putInt("position", position);
        editor.commit();
    }

    public int getClickedPosition(){
        return androidPref.getInt("position", 0);
    }
}
