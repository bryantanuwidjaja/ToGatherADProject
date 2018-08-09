package com.example.bryan.togatheradproject;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.protobuf.Any;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeActivity extends AppCompatActivity {

    public static final String TAG = "ToGather";
    List<Lobby> lobbyList;
    ListView listView_LobbyList;
    TextView textView_Nearbylobby;
    Button button_Createlobby;
    Button button_Viewprofile;
    ListenerRegistration listenerRegistration;


    private void retreiveLobby() {
        FirebaseFirestore.getInstance().collection("lobby")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        Map<String, Object> lobbyMap = new HashMap<String, Object>();
                        lobbyMap.put("activity", null);
                        lobbyMap.put("capacity", null);
                        lobbyMap.put("guestID", null);
                        lobbyMap.put("hostID", null);
                        lobbyMap.put("lobbyDescription", null);
                        lobbyMap.put("lobbyID", null);
                        lobbyMap.put("location", null);

                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, "DocumentSnapshot data : " + document.getData());
                                lobbyMap = document.getData();
                                String activity = lobbyMap.get("activity").toString();
                                String capacityString = lobbyMap.get("capacity").toString();
                                int capacity = Integer.parseInt(capacityString);
                                //String guestID = lobbyMap.get("guestID").toString();
                                String[] guestID = {""};
                                String hostID = "";
                                //String hostID = lobbyMap.get("hostID").toString();
                                String lobbyDescription = lobbyMap.get("lobbyDescriptions").toString();
                                //String lobbyID = lobbyMap.get("lobbyID").toString();
                                String lobbyID = "";
                                String location = lobbyMap.get("location").toString();
                                Log.d(TAG, "guestID : " + guestID);
                                Log.d(TAG, "capacity : " + capacity);
                                Log.d(TAG, "lobbyDescriptions : " + lobbyDescription);
                                Log.d(TAG, "activity : " + activity);
                                Log.d(TAG, "location : " + location);
                                Lobby lobby = new Lobby(capacity, location, activity, lobbyDescription);

                                lobbyList.add(lobby);
                                Log.d(TAG, "onComplete: " + lobbyList);
                            }
                        } else {
                            Log.d(TAG, "Data does not exist");
                        }

                        LobbyList adapter = new LobbyList(HomeActivity.this, lobbyList);
                        listView_LobbyList.setAdapter(adapter);
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
        retreiveLobby();


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Log.d(TAG, "onCreate: in");

        lobbyList = new ArrayList<>();
        listView_LobbyList = findViewById(R.id.listView_HomeActivity_lobbyList);
        textView_Nearbylobby = findViewById(R.id.textView_HomeActivity_nearbyLobby);
        button_Createlobby = findViewById(R.id.button_HomeActivity_createLobby);
        button_Viewprofile = findViewById(R.id.button_HomeActivity_viewProfile);

        button_Createlobby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CreateLobbyActivity.class);
                startActivity(intent);
            }
        });

        Log.d(TAG, "onCreate: out");

        button_Createlobby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CreateLobbyActivity.class);
                startActivity(intent);
            }
        });

        button_Viewprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(intent);
            }
        });

    }
}