package com.bishal.coursemaster.home_activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bishal.coursemaster.R;
import com.bishal.coursemaster.models.VideoListModel;
import com.bishal.coursemaster.utils.ChildView;
import com.bishal.coursemaster.utils.HeaderView;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.mindorks.placeholderview.ExpandablePlaceHolderView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class VideoList extends AppCompatActivity {
    ExpandablePlaceHolderView expandablePlaceHolderView;
    HashMap<String, List<VideoListModel>> categoryMap;
    Map<Integer, VideoListModel> map = new HashMap<Integer, VideoListModel>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_list);
        String courseId = getIntent().getStringExtra("uid");
        String name = getIntent().getStringExtra("name");
        categoryMap = new HashMap<>();

        expandablePlaceHolderView = findViewById(R.id.expandablePlaceHolder);


        getAndSetTheData(courseId);


    }

    private void getHeaderAndChild(List<VideoListModel> videoListModels) {
        for (int i = 0; i < videoListModels.size(); i++) {
            map.put(i, videoListModels.get(i));
        }
        for (int i = 0; i < videoListModels.size(); i++) {
            VideoListModel videoListModel = map.get(i);

            assert videoListModel != null;
            expandablePlaceHolderView.addView(new HeaderView(this, videoListModel.getTopic()));
            for (int j = 0; j < videoListModel.getLinks().size(); j++) {
                expandablePlaceHolderView.addView(new ChildView(this, videoListModel.getSubTopics().get(j), videoListModel.getLinks().get(j), videoListModel.getThumbs().get(j)));
            }
        }

    }

    private void getAndSetTheData(String courseId) {

        Query query = FirebaseFirestore.getInstance()
                .collection(courseId)
                .orderBy("order", Query.Direction.ASCENDING)
                .limit(50);

        query.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot snapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    // Handle error
                    //...
                    return;
                }

                assert snapshot != null;
                List<VideoListModel> videoListModels = snapshot.toObjects(VideoListModel.class);
                getHeaderAndChild(videoListModels);

            }
        });
    }


}