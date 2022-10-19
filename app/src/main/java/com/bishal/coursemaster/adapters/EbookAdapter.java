package com.bishal.coursemaster.adapters;


import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.bishal.coursemaster.BuildConfig;
import com.bishal.coursemaster.R;
import com.bishal.coursemaster.models.EbookModel;
import com.bumptech.glide.Glide;

import java.io.File;
import java.util.List;

public class EbookAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<EbookModel> ebookModelList;
    private BookClickListener itemBookClickListener;

    public EbookAdapter(Context context, List<EbookModel> ebookModelList) {
        this.context = context;
        this.ebookModelList = ebookModelList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_ebook, parent, false);
        return new ViewHolderOne(view);
    }

    private boolean checkFile(String pathname, String filename) {
        File file = new File(context.getFilesDir(), pathname);
        file = new File(file, filename);
        return file.exists();
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        initLayoutOne((ViewHolderOne) holder, position);
        String link = ebookModelList.get(position).getLink();
        String filename = ebookModelList.get(position).getFilename();

        if (checkFile("my_books", filename)) {
            ((ViewHolderOne) holder).download.setText("Open Book");
        } else {
            ((ViewHolderOne) holder).download.setText("Download");
        }

        ((ViewHolderOne) holder).download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (itemBookClickListener != null) {
                    if (checkFile("my_books", filename)) {
                        Toast.makeText(context, "present", Toast.LENGTH_SHORT).show();
                        openPdf("my_books", filename);
                    } else {
                        Toast.makeText(context, "not", Toast.LENGTH_SHORT).show();
                        itemBookClickListener.onItemClick(link, position, filename);
                        ((ViewHolderOne) holder).download.setText("Open Book");

                    }

                } else {
                    Toast.makeText(context, "null", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void openPdf(String pathname, String pathStr) {
        File file = new File(context.getFilesDir(), pathname);
        file = new File(file, pathStr);
//        path = new File(pathStr);
        Uri path = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".provider", file);
        Log.i("Fragment2", String.valueOf(path));

        Intent pdfOpenintent = new Intent(Intent.ACTION_VIEW);
        pdfOpenintent.setDataAndType(path, "application/pdf");
        pdfOpenintent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        pdfOpenintent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        pdfOpenintent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        try {
            context.startActivity(pdfOpenintent);
        } catch (ActivityNotFoundException e) {

        }
    }

    private void initLayoutOne(ViewHolderOne holder, int position) {
        holder.title.setText(ebookModelList.get(position).getTitle());
        holder.desc.setText(ebookModelList.get(position).getDetail());
        holder.ratingBar.setRating(Float.parseFloat(ebookModelList.get(position).getRatting()));
        Glide.with(context).load(ebookModelList.get(position).getIcon()).into(holder.icon);

    }

    @Override
    public int getItemCount() {
        return ebookModelList.size();
    }

    public void bookClickListener(BookClickListener listener) {
        itemBookClickListener = listener;
    }

    public interface BookClickListener {
        void onItemClick(String link, int position, String filename);

    }

    // Static inner class to initialize the views of rows
    static class ViewHolderOne extends RecyclerView.ViewHolder {
        TextView title, desc;
        ImageView icon;
        RatingBar ratingBar;
        Button download;

        public ViewHolderOne(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.etitle);
            desc = itemView.findViewById(R.id.edesc);
            icon = itemView.findViewById(R.id.eicon);
            download = itemView.findViewById(R.id.eload_open_btn);
            ratingBar = itemView.findViewById(R.id.eratting);


        }
    }

}
