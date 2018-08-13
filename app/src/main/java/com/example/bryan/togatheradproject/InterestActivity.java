package com.example.bryan.togatheradproject;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InterestActivity extends AppCompatActivity implements EditProfileDialog.OnInputListener {

    private static final String TAG = "InterestActivity";
    public static final String USER_ID = "userID";
    public static final String EMPTY_INTEREST = "Enter your interest";
    private List<String> inputContainer = new ArrayList<>();

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    TextView textView_AddInterest1;
    TextView textView_AddInterest2;
    TextView textView_AddInterest3;
    TextView textView_AddInterest4;
    TextView textView_AddInterest5;
    TextView textView_AddInterest6;
    Button button_SaveButton;
    Button button_AddButton;

    @Override
    public void sendInput(String input) {
        Log.d(TAG, "sendInput: " + input);
        inputContainer.add(input);
    }

    private void update(){
        if (inputContainer.size() >= 1) {
            textView_AddInterest1.setText(inputContainer.get(0));
            textView_AddInterest2.setText(inputContainer.get(1));
            textView_AddInterest3.setText(inputContainer.get(2));
            textView_AddInterest4.setText(inputContainer.get(3));
            textView_AddInterest5.setText(inputContainer.get(4));
            textView_AddInterest6.setText(inputContainer.get(5));
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: in");



        Log.d(TAG, "onStart: out");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: in");
        Log.d(TAG, "onResume: out");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interest);
        Log.d(TAG, "onCreate: in");
        Intent intent = getIntent();
        final String userID = intent.getStringExtra(RegistrationActivity.USER_ID);

        button_SaveButton = findViewById(R.id.button_InterestActivity_saveButton);
        button_AddButton = findViewById(R.id.button2_InterestActivity_addButton);
        textView_AddInterest1 = findViewById(R.id.textView_InterestActivity_interest1);
        textView_AddInterest2 = findViewById(R.id.textView2_InterestActivity_interest2);
        textView_AddInterest3 = findViewById(R.id.textView3_InterestActivity_interest3);
        textView_AddInterest4 = findViewById(R.id.textView4_InterestActivity_interest4);
        textView_AddInterest5 = findViewById(R.id.textView5_InterestActivity_interest5);
        textView_AddInterest6 = findViewById(R.id.textView6_InterestActivity_interest6);

        textView_AddInterest1.setText(EMPTY_INTEREST);
        textView_AddInterest2.setText(EMPTY_INTEREST);
        textView_AddInterest3.setText(EMPTY_INTEREST);
        textView_AddInterest4.setText(EMPTY_INTEREST);
        textView_AddInterest5.setText(EMPTY_INTEREST);
        textView_AddInterest6.setText(EMPTY_INTEREST);

        button_AddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: opening dialog");
                EditProfileDialog dialog = new EditProfileDialog();
                Bundle bundle = new Bundle();
                bundle.putString(USER_ID, userID);
                dialog.setArguments(bundle);
                dialog.show(getFragmentManager(), "EditProfileDialog");
                Log.d(TAG, "onClick: out");
            }
        });
        Log.d(TAG, "onCreate: out");
    }
}
