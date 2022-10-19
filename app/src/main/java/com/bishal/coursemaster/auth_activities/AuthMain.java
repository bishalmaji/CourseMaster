package com.bishal.coursemaster.auth_activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.bishal.coursemaster.R;
import com.bishal.coursemaster.utils.CheckInternet;
import com.bishal.coursemaster.utils.NoInternetDialog;


public class AuthMain extends AppCompatActivity {
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_acitivity);
        EditText phoneno, name, refercode;
        phoneno = findViewById(R.id.phoneno);
        name = findViewById(R.id.name);
        refercode = findViewById(R.id.refercode);
        progressBar = findViewById(R.id.authprogress);

        Button loginbtn = findViewById(R.id.loginbtn);
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                if (TextUtils.isEmpty(phoneno.getText())) {
                    phoneno.setError("Please Enter Your Phone Number");
                    return;
                }
                if (TextUtils.isEmpty(name.getText())) {
                    name.setError("Please Enter Your Name");
                    return;
                }
                if (phoneno.getText().toString().trim().length() != 10) {
                    phoneno.setHint("10 Digits Required");
                    return;
                }
                Intent i = new Intent(AuthMain.this, Otp.class);
                i.putExtra("phone", "+91" + phoneno.getText().toString().trim());
                i.putExtra("name", name.getText().toString().trim());
                if (!TextUtils.isEmpty(refercode.getText())) {
                    i.putExtra("refer", refercode.getText().toString().trim());
                } else {
                    i.putExtra("refer", "0000");

                }
                CheckInternet checkInternet = new CheckInternet(AuthMain.this);
                if (checkInternet.netIsAvailable()) {
                    progressBar.setVisibility(View.GONE);
                    startActivity(i);
                } else {
                    progressBar.setVisibility(View.GONE);
                    new NoInternetDialog(AuthMain.this, progressBar).ShowDialog();
                }

            }
        });


    }


}