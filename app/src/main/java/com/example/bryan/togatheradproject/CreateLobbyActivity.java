package com.example.bryan.togatheradproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class CreateLobbyActivity extends AppCompatActivity {

    private static final String TAG = "CreateLobbyActivity";
    TextView textView_Activity;
    TextView textView_Capacity;
    TextView textView_Description;
    EditText editText_Capacity;
    EditText editText_Description;
    Button button_Create;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_lobby);

        textView_Activity = findViewById(R.id.textView_CreateLobbyActivity_activity);
        textView_Capacity = findViewById(R.id.textView_CreateLobbyActivity_capacity);
        textView_Description = findViewById(R.id.textView_CreateLobbyActivity_description);
        editText_Capacity = findViewById(R.id.editText_CreateLobbyActivity_capacity);
        editText_Description = findViewById(R.id.editText_CreateLobbyActivity_description);
        button_Create = findViewById(R.id.button_CreateLobbyActivity_create);

    }
}
