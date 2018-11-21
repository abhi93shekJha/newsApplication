package com.gsatechworld.gugrify.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.telephony.CellInfoGsm;
import android.telephony.CellSignalStrengthGsm;
import android.telephony.TelephonyManager;

/**
 * created by Gopal @ 23-AUG-18
 */

//NetworkUtil
public class NetworkUtil {


    public static String NO_INTERNET = "No Internet, Please try again";
    public static String INTERNET_BACK = "Internet Back";
    public static String SERVVER_ERROR = "Sorry! Server Error";
    public static int TYPE_WIFI = 1;
    public static int TYPE_MOBILE = 2;
    public static int TYPE_NOT_CONNECTED = 0;

    private Context _context;
    public static NetworkUtil instance;

    public NetworkUtil(Context context) {
        this._context = context;
    }

    public static NetworkUtil getInstance(Context _context) {
        if (instance == null) {
            instance = new NetworkUtil(_context);
        }
        return instance;
    }

    public boolean isConnectingToInternet() {

        ConnectivityManager connMgr = (ConnectivityManager) _context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }

    public static int getConnectivityStatus(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (null != activeNetwork) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
                return TYPE_WIFI;

            if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
                return TYPE_MOBILE;
        }
        return TYPE_NOT_CONNECTED;
    }

    public static Boolean getConnectivityStatusString(Context context) {
        int conn = NetworkUtil.getConnectivityStatus(context);
        Boolean statusFlag = false;
        //String status = null;
        if (conn == NetworkUtil.TYPE_WIFI) {
//            status = "Wifi enabled";
            statusFlag = true;
        } else if (conn == NetworkUtil.TYPE_MOBILE) {
//            status = "Mobile data enabled";
            statusFlag = true;
        } else if (conn == NetworkUtil.TYPE_NOT_CONNECTED) {
//            status = "Not connected to Internet";
            statusFlag = false;
        }
        return statusFlag;
    }

    public static int getConnectivityStatusWithNetworkStrength(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (null != activeNetwork) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
                return TYPE_WIFI;

            if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
                return TYPE_MOBILE;
        }
        return TYPE_NOT_CONNECTED;
    }

    public int getWifiSpeed(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        int linkSpeed = wifiManager.getConnectionInfo().getRssi();
        return linkSpeed;
    }

    public int getInternetSpeed(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        CellInfoGsm cellinfogsm = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR1) {
            cellinfogsm = (CellInfoGsm) telephonyManager.getAllCellInfo().get(0);
        }
        CellSignalStrengthGsm cellSignalStrengthGsm = cellinfogsm.getCellSignalStrength();
        int linkSpeed = cellSignalStrengthGsm.getDbm();
        return linkSpeed;
    }

}
