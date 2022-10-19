package com.bishal.coursemaster.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;

public class CheckInternet {
    Activity activity;

    public CheckInternet(Activity activity) {
        this.activity = activity;
    }

    @SuppressLint("MissingPermission")
    public boolean netIsAvailable() {
        ConnectivityManager cm = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();

    }
}
