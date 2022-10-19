package com.bishal.coursemaster.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bishal.coursemaster.R;
import com.bishal.coursemaster.models.QuizModel;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class QuizAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final List<QuizModel> itemList;
    private final Context context;
    private QuizClickListener quizClickListener;

    public QuizAdapter(List<QuizModel> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_quiz_layout, parent, false);
        return new QuizViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        initLayoutOne((QuizViewHolder) holder, position);
        ((QuizViewHolder) holder).play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (quizClickListener != null)
                    quizClickListener.onItemClick(itemList.get(position).getUid(), itemList.get(position).getName());
            }
        });
    }

    private void initLayoutOne(QuizViewHolder holder, int listPosition) {
        holder.name.setText(itemList.get(listPosition).getName());
        RequestOptions requestedOption = new RequestOptions();
        requestedOption = requestedOption.transform(new CenterCrop(), new RoundedCorners(12));
        Glide.with(context).load(itemList.get(listPosition).getBackground()).apply(requestedOption).into(holder.background);
        holder.ratingBar.setRating(Float.parseFloat(itemList.get(listPosition).getRatting()));
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public void addQuizClickListener(QuizClickListener listener) {
        quizClickListener = listener;
    }

    public interface QuizClickListener {
        void onItemClick(String qid, String quizName);
    }

    // Static inner class to initialize the views of rows
    static class QuizViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView background;
        RatingBar ratingBar;

        Button play;

        public QuizViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.quizName);
            background = itemView.findViewById(R.id.quizback);
            play = itemView.findViewById(R.id.quizPlay);
            ratingBar = itemView.findViewById(R.id.quizRate);


        }
    }
}