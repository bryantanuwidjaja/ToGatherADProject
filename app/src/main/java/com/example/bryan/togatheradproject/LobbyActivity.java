package com.example.bryan.togatheradproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


public class LobbyActivity extends AppCompatActivity {

    private static final String TAG = "LobbyActivity";

    private String userID;
    private String lobbyID;


    TextView textView_lobbyID;
    EditText editView_chatDialog;
    ListView listView_chatLog;
    ImageView imageView_activityIcon;
    Button button_enter;
    Button button_guestList;
    Button button_lobbyDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);
        Log.d(TAG, "onCreate: in");

        Intent intent = getIntent();
        userID = intent.getStringExtra(Constants.USER_ID);
        lobbyID = intent.getStringExtra(Constants.LOBBY_ID);
        Log.d(TAG, "userID : " + userID);
        Log.d(TAG, "lobbyID : " + lobbyID);

        textView_lobbyID = findViewById(R.id.textView_LobbyActivity_lobbyID);
        editView_chatDialog = findViewById(R.id.editText_LobbyActivity_chatDialog);
        listView_chatLog = findViewById(R.id.listView_LobbyActivity_chatLog);
        imageView_activityIcon = findViewById(R.id.imageView_LobbyActivity_activityIcon);
        button_enter = findViewById(R.id.button_LobbyActivity_enter);
        button_guestList = findViewById(R.id.button_LobbyActivity_guestList);
        button_lobbyDetail = findViewById(R.id.button_LobbyActivity_lobbyDetail);

        button_lobbyDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LobbyDetailActivity.class);
                intent.putExtra(Constants.USER_ID, userID);
                intent.putExtra(Constants.LOBBY_ID, lobbyID);
                startActivity(intent);
            }
        });

        Log.d(TAG, "onCreate: out");
    }
}
