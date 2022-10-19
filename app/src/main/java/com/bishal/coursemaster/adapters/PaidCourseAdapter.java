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
import com.bishal.coursemaster.models.CourseModel;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

public class PaidCourseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final List<CourseModel> itemList;
    private final Context context;
    private final ArrayList<String> courseArray;

    private ItemClickListenersa itemClickListenersa;

    // Constructor of the class
    public PaidCourseAdapter(List<CourseModel> itemList, Context context, ArrayList<String> courseArray) {
        this.itemList = itemList;
        this.context = context;
        this.courseArray = courseArray;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_paid_course, parent, false);
        return new ViewHolderOne(view);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        initLayoutOne((ViewHolderOne) holder, position);
        String quizId = itemList.get(position).getUid();
        if (courseArray.contains(itemList.get(position).getUid())) {
            ((ViewHolderOne) holder).getItBtn.setText("Open");
        }
        ((ViewHolderOne) holder).getItBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (courseArray.contains(itemList.get(position).getUid())) {
                    if (itemClickListenersa != null)
                        itemClickListenersa.onItemClick(quizId, itemList.get(position).getRupees(), itemList.get(position).getLink(), itemList.get(position).getName(), true);
                } else {
                    if (itemClickListenersa != null)
                        itemClickListenersa.onItemClick(quizId, itemList.get(position).getRupees(), itemList.get(position).getLink(), itemList.get(position).getName(), false);


                }

            }
        });

    }

    public void updateAdapter(int position) {

        // then in order to refresh the views notify the RecyclerView
        notifyDataSetChanged();
    }


    private void initLayoutOne(ViewHolderOne holder, int listPosition) {
        if (courseArray.contains(itemList.get(listPosition).getUid())) {
            //user paid for this course
            holder.rupee.setText("Purchased");
        } else {
            holder.rupee.setText("₹ " + itemList.get(listPosition).getRupees());
        }
        holder.name.setText(itemList.get(listPosition).getName());
        holder.course_detail.setText(itemList.get(listPosition).getBackground());
        Glide.with(context).load(itemList.get(listPosition).getIcon()).into(holder.icon);
        RequestOptions requestedOption = new RequestOptions();
        requestedOption = requestedOption.transform(new CenterCrop(), new RoundedCorners(12));
//Glide.with(context).load(itemList.get(listPosition).getBackground()).apply(requestedOption).into(holder.background);
        holder.ratingBar.setRating(Float.parseFloat(itemList.get(listPosition).getRatting()));
    }

    // get the size of the list
    @Override
    public int getItemCount() {
        return itemList == null ? 0 : itemList.size();
    }

    public void addItemClickListenersa(ItemClickListenersa listener) {
        itemClickListenersa = listener;
    }

    public interface ItemClickListenersa {
        void onItemClick(String qid, String amnt, String link, String name, boolean payStatus);

    }

    // Static inner class to initialize the views of rows
    static class ViewHolderOne extends RecyclerView.ViewHolder {
        TextView name, rupee, course_detail;
        ImageView icon;
        RatingBar ratingBar;

        Button getItBtn;

        public ViewHolderOne(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.surveyname);
            rupee = itemView.findViewById(R.id.rupeetv);

            icon = itemView.findViewById(R.id.icon);
            getItBtn = itemView.findViewById(R.id.getitbutton);
            ratingBar = itemView.findViewById(R.id.rattingbar);
            course_detail = itemView.findViewById(R.id.course_detail);


        }
    }

}
