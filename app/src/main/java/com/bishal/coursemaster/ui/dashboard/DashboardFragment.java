package com.bishal.coursemaster.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bishal.coursemaster.BuildConfig;
import com.bishal.coursemaster.R;


public class DashboardFragment extends Fragment {


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageView whatsapp, fb, insta, telegram, other;
        whatsapp = view.findViewById(R.id.imgwhatsapp);
        fb = view.findViewById(R.id.imgfb);
        insta = view.findViewById(R.id.imginsta);
        telegram = view.findViewById(R.id.imgtg);
        other = view.findViewById(R.id.imgmore);

        whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendToApp("whatsapp");
            }
        });
        insta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendToApp("instagram");
            }
        });

        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendToApp("facebook");
            }
        });

        telegram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendToApp("telegram");
            }
        });

        other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendToApp("other");
            }
        });


    }

    private void sendToApp(String appName) {
        switch (appName) {
            case "whatsapp":
                openIntent("com.whatsapp", "whatsapp");
                break;
            case "facebook":
                openIntent("com.facebook.android", "facebook");
                break;
            case "telegram":
                openIntent("org.telegram.messenger", "telegram");
                break;
            case "other":
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.app_name));
                String shareMessage = "\nDownload the app and learn made for job courses\n\n";
                shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "\n\n";

                shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                startActivity(Intent.createChooser(shareIntent, "choose one"));
                break;
            case "instagram":
                openIntent("com.instagram.android", "telegram");
                break;
            default:

        }

    }

    private void openIntent(String packageStr, String name) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.app_name));
        String shareMessage = "\nDownload the app and learn made for job courses\n\n";
        shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + "\n\n";
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
        shareIntent.setPackage(packageStr);

        try {
            getActivity().startActivity(shareIntent);
        } catch (android.content.ActivityNotFoundException ex) {

            Toast.makeText(getContext(), name + " is not installed ", Toast.LENGTH_SHORT).show();
        }
    }
}