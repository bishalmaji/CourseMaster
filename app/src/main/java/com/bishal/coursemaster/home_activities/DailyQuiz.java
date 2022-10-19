package com.bishal.coursemaster.home_activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bishal.coursemaster.R;
import com.bishal.coursemaster.adapters.QuizAdapter;
import com.bishal.coursemaster.models.QuizModel;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class DailyQuiz extends AppCompatActivity implements QuizAdapter.QuizClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_quiz);

        RecyclerView recyclerView = findViewById(R.id.quizRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);


        Query query = FirebaseFirestore.getInstance()
                .collection("quiz")
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

                List<QuizModel> chats = snapshot.toObjects(QuizModel.class);
                QuizAdapter adapter = new QuizAdapter(chats, DailyQuiz.this);

                recyclerView.setAdapter(adapter);
                adapter.addQuizClickListener(DailyQuiz.this);
                //this adapter will work in th

            }
        });
    }


    @Override
    public void onItemClick(String qid, String quizName) {
        Intent i = new Intent(DailyQuiz.this, Game.class);
        i.putExtra("quizId", qid);
        i.putExtra("quizName", quizName);
        startActivity(i);
    }
}