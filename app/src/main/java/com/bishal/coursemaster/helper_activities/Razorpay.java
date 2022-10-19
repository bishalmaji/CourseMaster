package com.bishal.coursemaster.helper_activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bishal.coursemaster.HiddenFiles;
import com.bishal.coursemaster.R;
import com.bishal.coursemaster.home_activities.PaidCourse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class Razorpay extends AppCompatActivity implements PaymentResultListener {
    String courseID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_razorpay);

        String name = getIntent().getStringExtra("name");
        courseID = getIntent().getStringExtra("courseID");
        String samount = getIntent().getStringExtra("rupees");


        // rounding off the amount.
        int amount = Math.round(Float.parseFloat(samount) * 100);

        // initialize Razorpay account.
        Checkout checkout = new Checkout();

        // set your id as below
        HiddenFiles hiddenFiles = new HiddenFiles();
        String key = hiddenFiles.getRazorPayKey();
        checkout.setKeyID(key);

        // set image
        checkout.setImage(R.mipmap.ic_launcher);

        // initialize json object
        JSONObject object = new JSONObject();

        try {
            // to put name
            object.put("name", name);

            // put description
            object.put("description", "Test Payment for the Course, Chose Internet Banking and Press Success Button");

            // to set theme color
            object.put("theme.color", "#16c48A");

            // put the currency
            object.put("currency", "INR");

            // put amount
            object.put("amount", amount);

            // put mobile number
            object.put("prefill.contact", "9284064503");

            // put email
            object.put("prefill.email", "coursemaster.in@gmail.com");

            // open razorpay to checkout activity
            checkout.open(Razorpay.this, object);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPaymentSuccess(String s) {
        FirebaseFirestore.getInstance().collection("user").document(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid())
                .update("courses", FieldValue.arrayUnion(courseID)).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Intent intent=new Intent();
                        intent.putExtra("message",true);
                        setResult(80,intent);
                        Razorpay.this.onBackPressed();


                    }
                });

    }

    @Override
    public void onPaymentError(int i, String s) {
        Intent intent=new Intent();
        intent.putExtra("message",false);
        setResult(80,intent);
        Razorpay.this.onBackPressed();

    }
}