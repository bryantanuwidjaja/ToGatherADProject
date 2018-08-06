package com.example.bryan.togatheradproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class HomeActivity extends AppCompatActivity {

    public static final String TAG = "ToGather";
    List<Lobby> lobbyList;
    ListView listView_LobbyList;
    TextView textView_Nearbylobby;
    Button button_Createlobby;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        listView_LobbyList = findViewById(R.id.listView_HomeActivity_lobbyList);
        textView_Nearbylobby = findViewById(R.id.textView_HomeActivity_nearbyLobby);
        button_Createlobby = findViewById(R.id.button_HomeActivity_createLobby);
    }
}
