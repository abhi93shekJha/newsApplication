package com.gsatechworld.gugrify.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefUtil {
    private String PRO_NAME = "Gugrify";
    private static Context mContext;
    private static SharedPrefUtil sharedPrefUtil;
    SharedPreferences.Editor editor;


    public SharedPrefUtil(Context mCon) {
        this.mContext = mCon;
    }


    public static SharedPrefUtil getInstance(Context context) {
        if (sharedPrefUtil == null) {
            sharedPrefUtil = new SharedPrefUtil(context);
        }

        return sharedPrefUtil;
    }

    public void setPrefrence(String key, String value) {
        SharedPreferences prefrence = mContext.getSharedPreferences(PRO_NAME, 0);
        SharedPreferences.Editor editor = prefrence.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public void setPrefrenceBoolean(String key, boolean value) {
        SharedPreferences prefrence = mContext.getSharedPreferences(PRO_NAME, 0);
        SharedPreferences.Editor editor = prefrence.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public String getPrefrence(String key) {
        SharedPreferences prefrence = mContext.getSharedPreferences(PRO_NAME, 0);
        String data = prefrence.getString(key, null);
        return data;
    }

    public boolean getPrefrenceBoolean(String key) {
        SharedPreferences prefrence = mContext.getSharedPreferences(PRO_NAME, 0);
        boolean data = prefrence.getBoolean(key, false);
        return data;
    }


    public void clearPrefrence() {
        SharedPreferences prefrence = mContext.getSharedPreferences(PRO_NAME, 0);
        SharedPreferences.Editor editor = prefrence.edit();
        editor.clear();
        editor.commit();
    }

    public void clearPrefrenceKey(String key) {
        SharedPreferences prefrence = mContext.getSharedPreferences(PRO_NAME, 0);
        prefrence.edit().remove(key).commit();
    }



    /**
     * Clear session details
     * */
/*
    public void logoutUser(){
        // Clearing all data from Shared Preferences

        editor.clear();
        editor.commit();

        // After logout redirect user to Loing Activity
        Intent i = new Intent(mContext, LoginActivity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        mContext.startActivity(i);
    }
*/


}
