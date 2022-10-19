package com.bishal.coursemaster.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bishal.coursemaster.R;


public class NoInternetDialog {
    private final ProgressBar progressBar;
    Activity activity;
    AlertDialog alertDialog;

    public NoInternetDialog(Activity activity, ProgressBar progressBar) {
        this.activity = activity;
        this.progressBar = progressBar;
    }

    public void ShowDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.no_internet, null);
        dialogBuilder.setView(dialogView);
        dialogBuilder.setCancelable(false);
        alertDialog = dialogBuilder.create();

        alertDialog.show();
        Button okBtn = dialogView.findViewById(R.id.netokbtn);
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                CheckInternet checkInternet = new CheckInternet(activity);
                if (checkInternet.netIsAvailable()) {
                    progressBar.setVisibility(View.GONE);
                    alertDialog.cancel();
                } else {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(activity, "Check your Internet Connetion", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}
