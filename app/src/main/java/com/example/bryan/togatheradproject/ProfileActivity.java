package com.example.bryan.togatheradproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity implements EditProfileDialog.OnInputListener {

    @Override
    public void sendInput(String input) {
        Log.d(TAG, "sendInput: received interestV: " + input);
        String newInterest = input;
        //update fire the fire store and refresh the UI
    }

    private static final String TAG = "ProfileActivity";

    ImageView imageView_Image;
    TextView textView_Username;
    TextView textView_Interest1;
    TextView textView_Interest2;
    TextView textView_Interest3;
    TextView textView_Interest4;
    TextView textView_Interest5;
    TextView textView_Interest6;
    TextView textView_Interest7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        imageView_Image = findViewById(R.id.imageView_ProfileScreen_image);
        textView_Username = findViewById(R.id.textView_ProfileScreen_username);
        textView_Interest1 = findViewById(R.id.textView_ProfileScreen_interest1);
        textView_Interest2 = findViewById(R.id.textView_ProfileScreen_interest2);
        textView_Interest3 = findViewById(R.id.textView_ProfileScreen_interest3);
        textView_Interest4 = findViewById(R.id.textView_ProfileScreen_interest4);
        textView_Interest5 = findViewById(R.id.textView_ProfileScreen_interest5);
        textView_Interest6 = findViewById(R.id.textView_ProfileScreen_interest6);
        textView_Interest7 = findViewById(R.id.textView_ProfileScreen_interest7);

        textView_Interest2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: opening dialog");
                EditProfileDialog dialog = new EditProfileDialog();
                dialog.show(getFragmentManager(), "EditProfileDialog");
            }
        });

        textView_Interest3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: opening dialog");
                EditProfileDialog dialog = new EditProfileDialog();
                dialog.show(getFragmentManager(), "EditProfileDialog");
            }
        });

        textView_Interest4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: opening dialog");
                EditProfileDialog dialog = new EditProfileDialog();
                dialog.show(getFragmentManager(), "EditProfileDialog");
            }
        });

        textView_Interest5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: opening dialog");
                EditProfileDialog dialog = new EditProfileDialog();
                dialog.show(getFragmentManager(), "EditProfileDialog");
            }
        });

        textView_Interest6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: opening dialog");
                EditProfileDialog dialog = new EditProfileDialog();
                dialog.show(getFragmentManager(), "EditProfileDialog");
            }
        });

        textView_Interest7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: opening dialog");
                EditProfileDialog dialog = new EditProfileDialog();
                dialog.show(getFragmentManager(), "EditProfileDialog");
            }
        });

        textView_Interest1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: opening dialog");
                EditProfileDialog dialog = new EditProfileDialog();
                dialog.show(getFragmentManager(), "EditProfileDialog");
            }
        });
    }


}
