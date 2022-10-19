package com.bishal.coursemaster.home_activities;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bishal.coursemaster.R;
import com.bishal.coursemaster.adapters.QuestionAdapter;
import com.bishal.coursemaster.models.QuestionModel;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.List;

public class Game extends AppCompatActivity implements QuestionAdapter.ItemClickListener {

    public int correct = 0;
    private RecyclerView recyclerView;
    private int position;
    private int size;
    private QuestionAdapter adapter;
    private boolean stat = false;
    private boolean ans = false;
    private TextView questionNo;
    private CircularProgressIndicator progressIndicator;
    private int progress = 0;
    private int progressConst = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View decorView = getWindow().getDecorView();
        int uiop = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
        decorView.setSystemUiVisibility(uiop);
        setContentView(R.layout.activity_game);
        //init
        position = 0;
        size = 0;
        questionNo = findViewById(R.id.questionN);
        progressIndicator = findViewById(R.id.gameProg);
        recyclerView = findViewById(R.id.recyclerView);
        Button nextButton = findViewById(R.id.nextBtn);

        nextButton.setOnClickListener(view -> {
            if (stat) {
                questionNo.setText(String.valueOf(position + 2));
                progressConst++;
                progress = (size * 100 * progressConst * 10) / 100;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    progressIndicator.setProgress(progress, true);
                } else {
                    progressIndicator.setProgress(progress);

                }


                if (ans) {
                    correct++;
                }
                if (position < size - 1) {
                    recyclerView.getLayoutManager().scrollToPosition(position + 1);
                } else {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            showCustDialog();
                        }
                    }, 500);

                }
                stat = false;
            } else {
                Toast.makeText(Game.this, "Chose one Option", Toast.LENGTH_SHORT).show();

            }


        });

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(Game.this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        recyclerView.setLayoutManager(linearLayoutManager);
        //change from here

        String quizId = getIntent().getStringExtra("quizId");


        Query query = FirebaseFirestore.getInstance()
                .collection(quizId)
                .limit(50);

        query.addSnapshotListener((snapshot, e) -> {
            if (e != null) {
                // Handle error
                //...
                return;
            }

            // Convert query snapshot to a list of chats
            List<QuestionModel> chats = snapshot.toObjects(QuestionModel.class);
            adapter = new QuestionAdapter(chats, Game.this);
            recyclerView.setAdapter(adapter);
            adapter.addItemClickListener(Game.this);
            // Update UI
            // ...
            size = snapshot.getDocuments().size();
            progressIndicator.setMax(size * size * 10);


        });

    }

    private void showCustDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Game.this);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.game_finish_dialog, viewGroup, false);
        builder.setView(dialogView).setCancelable(false);

        builder.create().show();
        Button btn = dialogView.findViewById(R.id.finishgamebtn);
        TextView correctT, wrongT, totalT;
        correctT = dialogView.findViewById(R.id.correctq);
        wrongT = dialogView.findViewById(R.id.wrongq);
        totalT = dialogView.findViewById(R.id.totalq);
        if (correct > 9) {
            correctT.setText(String.valueOf(correct));
            wrongT.setText("0" + (size - correct));
        } else {
            correctT.setText("0" + correct);
            if ((size - correct) > 9) {
                wrongT.setText(String.valueOf(size - correct));
            } else {
                wrongT.setText("0" + (size - correct));
            }
        }
        totalT.setText(String.valueOf(size));

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Game.super.onBackPressed();
            }
        });
    }

    @Override
    public void onItemClick(int p, boolean s, boolean tf) {
        position = p;
        stat = s;
        ans = tf;

    }

    public class CustomGridLayoutManager extends LinearLayoutManager {
        private boolean isScrollEnabled = true;

        public CustomGridLayoutManager(Context context) {
            super(context);
        }

        public void setScrollEnabled(boolean flag) {
            this.isScrollEnabled = flag;
        }

        @Override
        public boolean canScrollVertically() {
            //Similarly you can customize "canScrollHorizontally()" for managing horizontal scroll
            return isScrollEnabled && super.canScrollVertically();
        }
    }

}