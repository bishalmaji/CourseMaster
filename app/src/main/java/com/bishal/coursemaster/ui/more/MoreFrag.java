package com.bishal.coursemaster.ui.more;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.bishal.coursemaster.R;
import com.bishal.coursemaster.helper_activities.SplashScreen;
import com.bishal.coursemaster.home_activities.Profile;
import com.google.firebase.auth.FirebaseAuth;

public class MoreFrag extends Fragment {


    TextView profile, logout, help, payment;
    FirebaseAuth.AuthStateListener authStateListener = new FirebaseAuth.AuthStateListener() {
        @Override
        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            if (firebaseAuth.getCurrentUser() == null) {
                //Do anything here which needs to be done after signout is complete
                startActivity(new Intent(getActivity(), SplashScreen.class));
                requireActivity().finish();
            } else {
            }
        }
    };
    //Declaration and defination
    private FirebaseAuth firebaseAuth;

    public MoreFrag() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_more, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        logout = view.findViewById(R.id.tv_logout);
        profile = view.findViewById(R.id.tv_profile);
        help = view.findViewById(R.id.tv_help);
        payment = view.findViewById(R.id.tv_payment);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), Profile.class));
            }
        });
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                EditText editText = new EditText(getContext());
                builder.setTitle("Enter your Message").setView(editText).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getContext(), "Thank You", Toast.LENGTH_SHORT).show();
                        dialogInterface.cancel();
                    }
                });
                builder.create().show();
            }
        });
        payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                EditText editText = new EditText(getContext());
                builder.setTitle("Payment is in Test Mode").setMessage("You can chose internet baking and select success button, you won't be charged any amount for any paid courses").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                builder.create().show();
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Init and attach
                Toast.makeText(getContext(), "Sign Out", Toast.LENGTH_SHORT).show();
                firebaseAuth = FirebaseAuth.getInstance();
                firebaseAuth.addAuthStateListener(authStateListener);
                firebaseAuth.signOut();
            }
        });
    }


}