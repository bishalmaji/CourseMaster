package com.bishal.coursemaster.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.widget.Toast;

import com.bishal.coursemaster.services.APIInterface;

import java.net.URL;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DownloadFileFromDrive extends AsyncTask<String, String, Boolean> {

    private final Activity activity;
    private final String url;
    private final String ext;
    private final String pathname;
    private ProgressDialog progressDialog;
    private final Callback<ResponseBody> myHTMLres = new Callback<ResponseBody>() {
        @Override
        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
            try {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                URL url = response.raw().request().url().url();
                if (url.toString() != null) {
                    new DownloadFile(activity, ext, pathname).execute(url.toString());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Call<ResponseBody> call, Throwable t) {
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            Toast.makeText(activity, "retry", Toast.LENGTH_SHORT).show();
        }
    };

    public DownloadFileFromDrive(Activity activity, String url, String ext, String pathname) {
        this.url = url;
        this.ext = ext;
        this.activity = activity;
        this.pathname = pathname;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage("Downloading file for the first time..");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    protected Boolean doInBackground(String... strings) {
        if (isOnline(activity)) {
            Retrofit retrofit = new Retrofit.Builder().baseUrl(APIInterface.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
            APIInterface apiInterface = retrofit.create(APIInterface.class);
            Call<ResponseBody> call = apiInterface.getHtmlResponse(url);
            call.enqueue(myHTMLres);
            return true;
        } else {
            return false;
        }

    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        if (!aBoolean) {
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            Toast.makeText(activity, "No Internet", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("MissingPermission")
    private boolean isOnline(Activity activity) {
        try {
            ConnectivityManager cm = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = null;
            if (cm != null) {
                info = cm.getActiveNetworkInfo();
                return info != null && info.isConnectedOrConnecting();
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }
}
