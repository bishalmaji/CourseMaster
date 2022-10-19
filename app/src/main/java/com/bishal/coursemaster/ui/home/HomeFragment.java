package com.bishal.coursemaster.ui.home;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.bishal.coursemaster.R;
import com.bishal.coursemaster.adapters.SliderAdapter;
import com.bishal.coursemaster.home_activities.DailyQuiz;
import com.bishal.coursemaster.home_activities.Ebook;
import com.bishal.coursemaster.home_activities.FreeCourse;
import com.bishal.coursemaster.home_activities.InfoShorts;
import com.bishal.coursemaster.home_activities.Interview;
import com.bishal.coursemaster.home_activities.Job;
import com.bishal.coursemaster.home_activities.PaidCourse;
import com.bishal.coursemaster.home_activities.Profile;
import com.bishal.coursemaster.models.SliderModel;
import com.bishal.coursemaster.utils.CheckInternet;
import com.bishal.coursemaster.utils.NoInternetDialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class HomeFragment extends Fragment {


    private final Handler sliderHandler = new Handler();
    private ViewPager2 viewPager2;
    private final Runnable sliderRunnable = new Runnable() {
        @Override
        public void run() {
            viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
        }
    };
    private ProgressBar progressBar;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView demoClasses, freebook, paid, quiz, interviewQ, jobAlert;
        ImageView shortsBtn,launch_profile;
        demoClasses = view.findViewById(R.id.tvfree);
        freebook = view.findViewById(R.id.tvbook);
        paid = view.findViewById(R.id.tvpaid);
        quiz = view.findViewById(R.id.tvquiz);
        interviewQ = view.findViewById(R.id.tvinterview);
        jobAlert = view.findViewById(R.id.tvjob);
        progressBar = view.findViewById(R.id.homeProgress);
        TextView name = view.findViewById(R.id.home_name);
        shortsBtn = view.findViewById(R.id.shortsBtn);
        launch_profile = view.findViewById(R.id.launch_profile);
        launch_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), Profile.class));
            }
        });
        CheckInternet auth = new CheckInternet(getActivity());
        SharedPreferences sp = requireActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        String nameStr = sp.getString("name", "User Z.");
        String[] parts = nameStr.split(" ");
        String finals = parts[0] + " " + parts[1].charAt(0) + ".";
        name.setText(finals);
        shortsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), InfoShorts.class));
            }
        });
        interviewQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), Interview.class));
            }
        });
        jobAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (auth.netIsAvailable())
                    startActivity(new Intent(getContext(), Job.class));
            }
        });
        demoClasses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), FreeCourse.class));
            }
        });
        freebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), Ebook.class));
            }
        });
        quiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (auth.netIsAvailable())
                    startActivity(new Intent(getContext(), DailyQuiz.class));
                else
                    new NoInternetDialog(getActivity(), progressBar).ShowDialog();
            }
        });
        paid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (auth.netIsAvailable()) {
                    FirebaseFirestore.getInstance().collection("user").document(FirebaseAuth.getInstance()
                            .getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.getException() == null) {
                                ArrayList<String> arrayList = new ArrayList<String>();
                                arrayList.addAll((Collection<? extends String>) Objects.requireNonNull(task.getResult().get("courses")));
                                Intent i = new Intent(getContext(), PaidCourse.class);
                                i.putStringArrayListExtra("courseArray", arrayList);
                                startActivity(i);


                            }
                        }
                    });

                } else {
                    new NoInternetDialog(getActivity(), progressBar).ShowDialog();
                }

            }
        });
        //for view pager

        viewPager2 = view.findViewById(R.id.viewPagerImageSlider);

        Query query = FirebaseFirestore.getInstance()
                .collection("slider")
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

                List<SliderModel> chats = snapshot.toObjects(SliderModel.class);
                viewPager2.setAdapter(new SliderAdapter(chats, viewPager2, getContext()));

                viewPager2.setClipToPadding(false);
                viewPager2.setClipChildren(false);
                viewPager2.setOffscreenPageLimit(3);
                viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

                CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
                compositePageTransformer.addTransformer(new MarginPageTransformer(40));
                compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
                    @Override
                    public void transformPage(@NonNull View page, float position) {
                        float r = 1 - Math.abs(position);
                        page.setScaleY(0.85f + r * 0.15f);
                    }
                });

                viewPager2.setPageTransformer(compositePageTransformer);

                viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                    @Override
                    public void onPageSelected(int position) {
                        super.onPageSelected(position);
                        sliderHandler.removeCallbacks(sliderRunnable);
                        sliderHandler.postDelayed(sliderRunnable, 3500);
                    }
                });
            }
        });


    }

}