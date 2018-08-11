package com.example.bryan.togatheradproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class InterestActivity extends AppCompatActivity implements EditProfileDialog.OnInputListener {

    private static final String TAG = "InterestActivity";
    public static final String USER_ID = "userID";
    private String[] inputContainer;
    TextView textView_AddInterest1;
    TextView textView_AddInterest2;
    TextView textView_AddInterest3;
    TextView textView_AddInterest4;
    TextView textView_AddInterest5;
    TextView textView_AddInterest6;
    Button button_SaveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interest);
        Intent intent = getIntent();
        final String userID = intent.getStringExtra(RegistrationActivity.USER_ID);

        button_SaveButton = findViewById(R.id.button_InterestActivity_saveButton);
        textView_AddInterest1 = findViewById(R.id.textView_InterestActivity_interest1);
        textView_AddInterest2 = findViewById(R.id.textView2_InterestActivity_interest2);
        textView_AddInterest3 = findViewById(R.id.textView3_InterestActivity_interest3);
        textView_AddInterest4 = findViewById(R.id.textView4_InterestActivity_interest4);
        textView_AddInterest5 = findViewById(R.id.textView5_InterestActivity_interest5);
        textView_AddInterest6 = findViewById(R.id.textView6_InterestActivity_interest6);

        textView_AddInterest1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Log.d(TAG, "onClick: opening dialog");
                EditProfileDialog dialog = new EditProfileDialog();
                dialog.show(getFragmentManager(), "EditProfileDialog");
            }
        });

        textView_AddInterest2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: opening dialog");
                EditProfileDialog dialog = new EditProfileDialog();
                dialog.show(getFragmentManager(), "EditProfileDialog");
            }
        });

        textView_AddInterest3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: opening dialog");
                EditProfileDialog dialog = new EditProfileDialog();
                dialog.show(getFragmentManager(), "EditProfileDialog");
            }
        });

        textView_AddInterest4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: opening dialog");
                EditProfileDialog dialog = new EditProfileDialog();
                dialog.show(getFragmentManager(), "EditProfileDialog");
            }
        });

        textView_AddInterest5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: opening dialog");
                EditProfileDialog dialog = new EditProfileDialog();
                dialog.show(getFragmentManager(), "EditProfileDialog");
            }
        });

        textView_AddInterest6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: opening dialog");
                EditProfileDialog dialog = new EditProfileDialog();
                dialog.show(getFragmentManager(), "EditProfileDialog");
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();


    }

    @Override
    public void sendInput(String input) {
        Log.d(TAG, "sendInput: " + input );
           
    }
}
