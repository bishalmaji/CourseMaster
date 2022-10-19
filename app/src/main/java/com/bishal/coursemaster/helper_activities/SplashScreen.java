package com.bishal.coursemaster.helper_activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.bishal.coursemaster.MainActivity;
import com.bishal.coursemaster.R;
import com.bishal.coursemaster.auth_activities.AuthMain;
import com.google.firebase.auth.FirebaseAuth;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View decorView = getWindow().getDecorView();
        int uiop = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
        decorView.setSystemUiVisibility(uiop);
        setContentView(R.layout.activity_splash_screen);
        FirebaseAuth auth = FirebaseAuth.getInstance();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (auth.getCurrentUser() == null) {
                    startActivity(new Intent(SplashScreen.this, AuthMain.class));
                } else {
                    startActivity(new Intent(SplashScreen.this, MainActivity.class));
                }
                finish();
            }
        }, 3000);

    }
}