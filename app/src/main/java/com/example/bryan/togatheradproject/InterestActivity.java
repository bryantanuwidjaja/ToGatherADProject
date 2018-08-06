package com.example.bryan.togatheradproject;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class InterestActivity extends AppCompatActivity {

    private static final String TAG = "InterestActivity";
    FloatingActionButton floatingActionButton_AddInterest1;
    FloatingActionButton floatingActionButton_AddInterest2;
    FloatingActionButton floatingActionButton_AddInterest3;
    FloatingActionButton floatingActionButton_AddInterest4;
    FloatingActionButton floatingActionButton_AddInterest5;
    FloatingActionButton floatingActionButton_AddInterest6;
    EditText editText_UserDescription;
    Button button_SaveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interest);

        floatingActionButton_AddInterest1 = findViewById(R.id.floatingActionButton_InterestActivity_addInterest1);
        floatingActionButton_AddInterest2 = findViewById(R.id.floatingActionButton_InterestActivity_addInterest2);
        floatingActionButton_AddInterest3 = findViewById(R.id.floatingActionButton_InterestActivity_addInterest3);
        floatingActionButton_AddInterest4 = findViewById(R.id.floatingActionButton_InterestActivity_addInterest4);
        floatingActionButton_AddInterest5 = findViewById(R.id.floatingActionButton_InterestActivity_addInterest5);
        floatingActionButton_AddInterest6 = findViewById(R.id.floatingActionButton_InterestActivity_addInterest6);
        editText_UserDescription = findViewById(R.id.editText_InterestActivity_userDescription);
        button_SaveButton = findViewById(R.id.button_InterestActivity_saveButton);

    }
}
