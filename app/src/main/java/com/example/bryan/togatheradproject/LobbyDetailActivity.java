package com.example.bryan.togatheradproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class LobbyDetailActivity extends AppCompatActivity {

    private static final String TAG = "LobbyDetailActivity";

    private String userID;
    private String lobbyID;

    private TextView textView_activity;
    private TextView textView_maximumCapacity;
    private TextView textView_host;
    private TextView textView_description;
    private TextView textView_location;
    private Button button_returnToLobby;


    protected void establish(){
        textView_activity = findViewById(R.id.textView_ActivityLobbyDetail_activity);
        textView_maximumCapacity = findViewById(R.id.textView_ActivityLobbyDetail_maximumCapacity);
        textView_host = findViewById(R.id.textView_ActivityLobbyDetail_host);
        textView_description = findViewById(R.id.textView_ActivityLobbyDetail_description);
        textView_location = findViewById(R.id.textView_ActivityLobbyDetail_location);
        button_returnToLobby = findViewById(R.id.button_ActivityLobbyDetail_returnToLobby);
    }

    protected void acceptIntent(){
        Intent intent = getIntent();
        userID = intent.getStringExtra(Constants.USER_ID);
        lobbyID = intent.getStringExtra(Constants.LOBBY_ID);
        String hostID = textView_host.getText().toString();
        Log.d(TAG, "hostID : " + hostID);
        Log.d(TAG, "userID : " + userID);
        Log.d(TAG, "lobbyID : " + lobbyID);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby_detail);

        establish();
        acceptIntent();
        queryInformation(lobbyID);

        button_returnToLobby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LobbyActivity.class);
                intent.putExtra(Constants.USER_ID, userID);
                intent.putExtra(Constants.LOBBY_ID, lobbyID);
                startActivity(intent);
            }
        });
    }

    private void queryInformation(final String lobbyID) {
        FirebaseFirestore.getInstance().collection(Constants.LOBBY).document(lobbyID)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        Lobby lobby = documentSnapshot.toObject(Lobby.class);
                        textView_activity.setText(lobby.getActivity().toString());
                        textView_description.setText(lobby.getLobbyDescriptions().toString());
                        FirebaseFirestore.getInstance().collection(Constants.USER)
                                .document(lobby.getHostID().toString())
                                .get()
                                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                    @Override
                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                                        User user = documentSnapshot.toObject(User.class);
                                        textView_host.setText(user.getUserName().toString());
                                    }
                                });
                        textView_location.setText(lobby.getLocation().toString());
                        String stringLobbyCapacity = Integer.toString(lobby.getCapacity());
                        textView_maximumCapacity.setText(stringLobbyCapacity);
                    }
                });
    }
}


