package com.example.bryan.togatheradproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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

        listView_LobbyList = (ListView) findViewById(R.id.listView_HomeActivity_lobbylist);
        textView_Nearbylobby = (TextView) findViewById(R.id.textView_HomeActivity_nearbylobby);
        button_Createlobby = (Button) findViewById(R.id.button_HomeActivity_createlobby);
    }
}
