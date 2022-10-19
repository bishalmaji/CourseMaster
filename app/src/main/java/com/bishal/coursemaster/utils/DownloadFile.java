package com.bishal.coursemaster.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

public class DownloadFile extends AsyncTask<String, String, Boolean> {
    //    public static String downloadFile;
    public static File path;
    private final String ext;
    private final Activity activity;
    private final String pathname;
    private final MethodCallBack methodCallBack;
    private ProgressDialog progressDialog;

    public DownloadFile(Activity activity, String ext, String pathname) {
        this.pathname = pathname;
        this.activity = activity;
        this.ext = ext;
        methodCallBack = (MethodCallBack) activity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        path = new File(activity.getFilesDir(), pathname);
        path.mkdirs();
        path = new File(path, ext);
//        downloadFile=String.valueOf(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS))+ File.separator+ext;
        progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    protected Boolean doInBackground(String... strings) {
        int count;
        try {

            URL url = new URL(strings[0]);
            URLConnection connection = url.openConnection();
            connection.connect();
            InputStream is = new BufferedInputStream(url.openStream(), 8192);
            OutputStream os = new FileOutputStream(path);
            byte[] data = new byte[10248];
            while ((count = is.read(data)) != -1) {
                os.write(data, 0, count);
            }
            os.flush();
            os.close();
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
//            Log.d("bikkkk", "doInBackground: "+e.getLocalizedMessage());
            return false;
        }
        return true;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        if (aBoolean) {
            methodCallBack.OnMethodCallBack(pathname, ext);
        } else {
            Toast.makeText(activity, "Download Failed..!", Toast.LENGTH_SHORT).show();
        }


    }

    public interface MethodCallBack {
        void OnMethodCallBack(String pathname, String filename);
    }
}
