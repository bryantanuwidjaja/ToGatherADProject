package com.example.bryan.togatheradproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class LobbyActivity extends AppCompatActivity {

    private static final String TAG = "LobbyActivity";

    TextView textView_lobbyID;
    EditText editView_chatDialog;
    Button button_enter;
    TextView textView_currentUser;
    TextView textView_outOf;
    TextView textView_maxUser;
    ListView listView_chatLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);
        Log.d(TAG, "onCreate: in");

        textView_lobbyID = findViewById(R.id.textView_LobbyActivity_lobbyID);
        textView_currentUser = findViewById(R.id.textView_LobbyActivity_currentUser);
        textView_outOf = findViewById(R.id.textView_LobbyActivity_outOf);
        textView_maxUser = findViewById(R.id.textView_LobbyActivity_maxUser);
        button_enter = findViewById(R.id.button_LobbyActivity_enter);
        editView_chatDialog = findViewById(R.id.editText_LobbyActivity_chatDialog);
        listView_chatLog = findViewById(R.id.listView_LobbyActivity_chatLog);

        Log.d(TAG, "onCreate: out");
    }
}
