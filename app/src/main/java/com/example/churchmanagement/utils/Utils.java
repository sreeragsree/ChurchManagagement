package com.example.churchmanagement.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class Utils {
    public static SharedPreferences preferences;
    public static SharedPreferences.Editor editor;
    public static final String Name = "ims";
    public static String timeString = "";
    public static final String SETTINGS = "settings";


    public static void log(String log, String value) {
        Log.d(log, value);
    }

    public static SharedPreferences.Editor getSharedPreferenceEdit() {

        if (editor == null) {
            editor = getSharedPreference().edit();
        }
        return editor;

    }

    public static SharedPreferences getSharedPreference() {

        if (preferences == null) {
            preferences = App.instance().getSharedPreferences(Name, Context.MODE_PRIVATE);
        }
        return preferences;
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }







}
