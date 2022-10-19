package com.bishal.coursemaster.home_activities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bishal.coursemaster.R;
import com.google.firebase.auth.FirebaseAuth;

public class Profile extends AppCompatActivity {
    TextView profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        profile = findViewById(R.id.main_tv_profile);
        profile.setText("User Logged In" + "\n"
                + "Name= " + FirebaseAuth.getInstance().getCurrentUser().getDisplayName() + "\n"
                + "Phone Number= " + FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber());
    }
}