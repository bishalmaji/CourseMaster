package com.bishal.coursemaster.home_activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bishal.coursemaster.R;
import com.bishal.coursemaster.adapters.PaidCourseAdapter;
import com.bishal.coursemaster.helper_activities.Razorpay;
import com.bishal.coursemaster.models.CourseModel;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class PaidCourse extends AppCompatActivity implements PaidCourseAdapter.ItemClickListenersa {

    private static final int RCODE = 81;
    PaidCourseAdapter adapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paid_cource);

        ArrayList<String> fcourseArray = new ArrayList<>();
        fcourseArray = getIntent().getStringArrayListExtra("courseArray");

        recyclerView = findViewById(R.id.courseReccycler);

        recyclerView.setHasFixedSize(false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(PaidCourse.this);
        recyclerView.setLayoutManager(linearLayoutManager);


        Query query = FirebaseFirestore.getInstance()
                .collection("PaidCourse")
                .limit(50);

        ArrayList<String> finalFcourseArray = fcourseArray;
        query.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot snapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    // Handle error
                    //...
                    return;
                }

                // Convert query snapshot to a list of chats
                List<CourseModel> chats = snapshot.toObjects(CourseModel.class);
                adapter = new PaidCourseAdapter(chats, PaidCourse.this, finalFcourseArray);
                recyclerView.setAdapter(adapter);
                adapter.addItemClickListenersa(PaidCourse.this);


            }
        });

    }

    @Override
    public void onItemClick(String courseID, String courseAmount, String link, String name, boolean payStatus) {

        if (payStatus) {
            Intent i2 = new Intent(PaidCourse.this, VideoList.class);
            i2.putExtra("uid", courseID);
            i2.putExtra("name", name);
            startActivity(i2);
        } else {
            //on pay succcess update course Array of user on firebase
            Toast.makeText(PaidCourse.this, "Opening payment... ", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(PaidCourse.this, Razorpay.class);
            i.putExtra("courseID", courseID);
            i.putExtra("rupees", courseAmount);
            i.putExtra("name", name);
            activityResultLauncher.launch(i);
        }

    }

    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == 80) {
                        Intent intent = result.getData();
                        if (intent != null) {
                            boolean stat = intent.getBooleanExtra("message", false);
                            if (stat) {
                                Toast.makeText(PaidCourse.this, "Payment Success", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(PaidCourse.this, "Payment Failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
            }
    );
}
