package com.bishal.coursemaster.utils;

import android.content.Context;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;

import com.bishal.coursemaster.R;
import com.bishal.coursemaster.helper_activities.CustomVideoPlayer;
import com.bishal.coursemaster.home_activities.VideoList;
import com.bumptech.glide.Glide;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;

@Layout(R.layout.row_vl_child)
public class ChildView {
    private static final String TAG = "ChildView";
    private final Context mContext;
    private final String subTopic;
    private final String link;
    private final String thumb;
    @View(R.id.rvl_child_title)
    TextView textViewChild;
    @View(R.id.rvl_child_image)
    ImageView childImage;


    public ChildView(VideoList mContext, String subTopic, String link, String thumb) {
        this.mContext = mContext;
        this.subTopic = subTopic;
        this.link = link;
        this.thumb = thumb;
    }


    @Resolve
    private void onResolve() {
        textViewChild.setText(subTopic);
        Glide.with(mContext).load(thumb).into(childImage);
        textViewChild.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                getData(link);
            }
        });
    }

    private void getData(String url) {
        Intent i = new Intent(mContext, CustomVideoPlayer.class);
        i.putExtra("vimo_link", url);
        mContext.startActivity(i);
    }
}
