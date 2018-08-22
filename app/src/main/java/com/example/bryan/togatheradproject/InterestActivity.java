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
import android.widget.Toast;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InterestActivity extends AppCompatActivity implements EditProfileDialog.OnInputListener {

    private static final String TAG = "InterestActivity";
    public static final String EMPTY_INTEREST = "Enter your interest";
    private String retrievedInterest;
    private ArrayList<String> inputContainer = new ArrayList<>();
    private int inputContainerSize = 0;
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
        if(inputContainerSize<= 6) {
            inputContainer.add(input);
        }
        else{
            Toast.makeText(this,"Interest full", Toast.LENGTH_SHORT).show();
        }

        Log.d(TAG, "inputContainer: " + inputContainer);
        inputContainerSize = inputContainer.size();
        update();
    }

    private void update() {
        for (int i = 0; i <= inputContainerSize; i++)
            switch (i) {
                case 1:
                    if (textView_AddInterest1.getText().toString().equals(EMPTY_INTEREST) && !inputContainer.get(0).isEmpty()) {
                        textView_AddInterest1.setText(inputContainer.get(0));
                    }
                    break;
                case 2:
                    if (textView_AddInterest2.getText().toString().equals(EMPTY_INTEREST) && !inputContainer.get(1).isEmpty()) {
                        textView_AddInterest2.setText(inputContainer.get(1));
                    }
                    break;
                case 3:
                    if (textView_AddInterest3.getText().toString().equals(EMPTY_INTEREST) && !inputContainer.get(2).isEmpty()) {
                        textView_AddInterest3.setText(inputContainer.get(2));
                    }
                    break;
                case 4:
                    if (textView_AddInterest4.getText().toString().equals(EMPTY_INTEREST) && !inputContainer.get(3).isEmpty()) {
                        textView_AddInterest4.setText(inputContainer.get(3));
                    }
                    break;
                case 5:
                    if (textView_AddInterest5.getText().toString().equals(EMPTY_INTEREST) && !inputContainer.get(4).isEmpty()) {
                        textView_AddInterest5.setText(inputContainer.get(4));
                    }
                    break;
                case 6:
                    if (textView_AddInterest6.getText().toString().equals(EMPTY_INTEREST) && !inputContainer.get(5).isEmpty()) {
                        textView_AddInterest6.setText(inputContainer.get(5));
                    }
                    break;
            }
    }

        @Override
        protected void onStart () {
            super.onStart();
            Log.d(TAG, "onStart: in");

            Log.d(TAG, "onStart: out");
        }

        @Override
        protected void onResume () {
            super.onResume();
            Log.d(TAG, "onResume: in");
            Log.d(TAG, "onResume: out");
        }

        @Override
        protected void onCreate (Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_interest);
            Log.d(TAG, "onCreate: in");
            Intent intent = getIntent();
            final String userID = intent.getStringExtra(Constants.USER_ID);
            final User currentUser = (User) intent.getSerializableExtra(Constants.USER);

            final DocumentReference userRef = FirebaseFirestore.getInstance().collection(Constants.USER).document(userID);
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
                    bundle.putString(Constants.USER_ID, userID);
                    bundle.putSerializable(Constants.USER, currentUser);
                    dialog.setArguments(bundle);
                    dialog.show(getFragmentManager(), "EditProfileDialog");
                    Log.d(TAG, "onClick: out");
                }
            });

            button_SaveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "onClick: in");
                    userRef.update(Constants.USER_INTERESTS, inputContainer);
                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                    intent.putExtra(Constants.USER_ID, userID);
                    startActivity(intent);
                    Log.d(TAG, "onClick: out");
                }
            });
            Log.d(TAG, "onCreate: out");
        }
    }
