package com.bishal.coursemaster.home_activities;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bishal.coursemaster.BuildConfig;
import com.bishal.coursemaster.R;
import com.bishal.coursemaster.adapters.EbookAdapter;
import com.bishal.coursemaster.models.EbookModel;
import com.bishal.coursemaster.utils.CheckInternet;
import com.bishal.coursemaster.utils.DownloadFile;
import com.bishal.coursemaster.utils.DownloadFileFromDrive;
import com.bishal.coursemaster.utils.NoInternetDialog;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Ebook extends AppCompatActivity implements DownloadFile.MethodCallBack, EbookAdapter.BookClickListener {
    private static final int CODE = 11;
    private final String[] allPermission = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    //    private PDFView pdfView;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ebook);

        if (check()) {
            LoadUI();
        } else {
            checkAll();
        }


        recyclerView = findViewById(R.id.ebookrecycler);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(Ebook.this);
        recyclerView.setLayoutManager(linearLayoutManager);


        Query query = FirebaseFirestore.getInstance()
                .collection("Ebook")
                .limit(50);


        query.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot snapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    new NoInternetDialog(Ebook.this, progressBar).ShowDialog();
                    return;
                }

                // Convert query snapshot to a list of chats
                List<EbookModel> books = snapshot.toObjects(EbookModel.class);
                EbookAdapter adapter = new EbookAdapter(Ebook.this, books);
                recyclerView.setAdapter(adapter);
                adapter.bookClickListener(Ebook.this);

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void checkAll() {
        ArrayList<String> permissionList = new ArrayList<>();
        for (String permission : allPermission) {
            if (!check(permission)) {
                permissionList.add(permission);
            }
        }
        if (permissionList.size() > 0) {
            String[] reqPermission = permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(Ebook.this, reqPermission, CODE);
        }
    }

    private void LoadUI() {
        progressBar = findViewById(R.id.progressEbook);

    }

    private boolean check() {
        for (String permission : allPermission) {
            if (!check(permission)) {
                return false;
            }
        }
        Toast.makeText(this, "true", Toast.LENGTH_SHORT).show();
        return true;
    }

    private boolean check(String permission) {
        int state = ActivityCompat.checkSelfPermission(Ebook.this, permission);
        return state == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case CODE:
                if (!check()) {
                    checkAll();
                } else {
                    LoadUI();
                }
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void OnMethodCallBack(String pathname, String filename) {
        openPdf("my_books", filename);
    }


    @Override
    public void onItemClick(String link, int position, String title) {
        CheckInternet auth = new CheckInternet(Ebook.this);
        if (auth.netIsAvailable())
            new DownloadFileFromDrive(Ebook.this, link, title, "my_books").execute();
        else
            new NoInternetDialog(Ebook.this, progressBar).ShowDialog();

    }

    private void openPdf(String pathname, String pathStr) {
        File file = new File(getFilesDir(), pathname);
        file = new File(file, pathStr);
//        path = new File(pathStr);
        Uri path = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".provider", file);
        Log.i("Fragment2", String.valueOf(path));

        Intent pdfOpenintent = new Intent(Intent.ACTION_VIEW);
        pdfOpenintent.setDataAndType(path, "application/pdf");
        pdfOpenintent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        pdfOpenintent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        pdfOpenintent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        try {
            Ebook.this.startActivity(pdfOpenintent);
        } catch (ActivityNotFoundException e) {

        }
    }

}