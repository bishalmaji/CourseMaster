package com.bishal.coursemaster.auth_activities;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.bishal.coursemaster.MainActivity;
import com.bishal.coursemaster.R;
import com.bishal.coursemaster.utils.CheckInternet;
import com.bishal.coursemaster.utils.NoInternetDialog;
import com.chaos.view.PinView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Otp extends AppCompatActivity {
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    PinView pinView;
    PhoneAuthCredential credential;
    LottieAnimationView lottieAnimationView;
    ImageView editNo;
    String phone, name, refer;
    TextView timer, mobileNo;
    private ProgressBar progressBar;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        mAuth = FirebaseAuth.getInstance();

        pinView = findViewById(R.id.pinview);
        Button bt = findViewById(R.id.otpsubmitbtn);
        lottieAnimationView = findViewById(R.id.progressanim);
        timer = findViewById(R.id.timertv);
        mobileNo = findViewById(R.id.tv_mobile);
        editNo = findViewById(R.id.editnoimgbtn);
        progressBar = findViewById(R.id.otpprogress);
        editNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Otp.super.onBackPressed();

            }
        });
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                CheckInternet auth = new CheckInternet(Otp.this);
                if (auth.netIsAvailable()) {

                    signin();
                } else {
                    progressBar.setVisibility(View.GONE);
                    new NoInternetDialog(Otp.this, progressBar).ShowDialog();
                }


            }
        });

        pinView.setAnimationEnable(true);// start animation when adding text
        pinView.setCursorVisible(false);


        phone = getIntent().getStringExtra("phone");
        mobileNo.setText(phone);
        name = getIntent().getStringExtra("name");
        refer = getIntent().getStringExtra("refer");
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phone)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(Otp.this)                 // Activity (for callback binding)
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                            @Override
                            public void onVerificationCompleted(PhoneAuthCredential credential) {

                            }

                            @Override
                            public void onVerificationFailed(FirebaseException e) {

                                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                                    // Invalid request
                                } else if (e instanceof FirebaseTooManyRequestsException) {
                                    // The SMS quota for the project has been exceeded
                                }

                                // Show a message and update the UI
                                Toast.makeText(Otp.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCodeSent(@NonNull String verificationId,
                                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {

                                mVerificationId = verificationId;
                                mResendToken = token;


                            }
                        })          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
        new CountDownTimer(30000, 1000) {

            public void onTick(long millisUntilFinished) {
                timer.setText(String.valueOf(millisUntilFinished / 1000));
            }

            public void onFinish() {
                timer.setText("0");
                lottieAnimationView.pauseAnimation();
            }
        }.start();
    }

    private void signin() {
        String pin = pinView.getText().toString();

        credential = PhoneAuthProvider.getCredential(mVerificationId, pin);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            boolean isNewUser=task.getResult().getAdditionalUserInfo().isNewUser();
                            if (isNewUser){
                                FirebaseUser user = task.getResult().getUser();
                                Map<String, Object> data = new HashMap<String, Object>();
                                int randomNo = gen();
                                data.put("name", name);
                                data.put("refer", refer);
                                data.put("phone", phone);
                                data.put("totalRefer", 0);
                                data.put("myCode", randomNo);
                                data.put("courses", Arrays.asList("abc"));

                                SharedPreferences sp = getSharedPreferences("user", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sp.edit();
                                editor.putString("name", name);
                                editor.putString("refer", refer);
                                editor.putString("phone", phone);
                                editor.putInt("totalRefer", 0);

                                editor.apply();

                                assert user != null;
                                FirebaseFirestore.getInstance().collection("user").document(user.getUid()).set(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        progressBar.setVisibility(View.GONE);
                                        startActivity(new Intent(Otp.this, MainActivity.class));
                                        finish();
                                    }
                                });
                            }
                            else{
                                SharedPreferences sp = getSharedPreferences("user", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sp.edit();
                                editor.putString("name", name);
                                editor.putString("refer", refer);
                                editor.putString("phone", phone);
                                editor.putInt("totalRefer", 0);
                                editor.apply();

                                progressBar.setVisibility(View.GONE);
                                startActivity(new Intent(Otp.this, MainActivity.class));
                                finish();
                            }


                            // Update UI
                        } else {
                            progressBar.setVisibility(View.GONE);
                            // Sign in failed, display a message and update the UI
//                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid\
                                Toast.makeText(Otp.this, "Code entered was invalid", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    public int gen() {
        Random r = new Random(System.currentTimeMillis());
        return 10000 + r.nextInt(20000);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Otp.this);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
                Otp.super.onBackPressed();
            }
        }).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        }).setTitle("Want to go back ?").setMessage("You may lost your progress").create().show();
    }
}