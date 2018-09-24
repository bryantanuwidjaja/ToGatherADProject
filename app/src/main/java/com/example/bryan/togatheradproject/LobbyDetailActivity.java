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

    private TextView textView_activity;
    private TextView textView_maximumCapacity;
    private TextView textView_host;
    private TextView textView_description;
    private TextView textView_location;
    private Button button_returnToLobby;
    private Button button_editLobby;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby_detail);

        Intent intent = getIntent();
        final Lobby lobby = (Lobby) intent.getSerializableExtra(Constants.LOBBY);
        final User user = (User) intent.getSerializableExtra(Constants.USER);
        Log.d(TAG, "userID : " + user.getUserID());
        Log.d(TAG, "lobbyID : " + lobby.getLobbyID());

        textView_activity = findViewById(R.id.textView_ActivityLobbyDetail_activity);
        textView_maximumCapacity = findViewById(R.id.textView_ActivityLobbyDetail_maximumCapacity);
        textView_host = findViewById(R.id.textView_ActivityLobbyDetail_host);
        textView_description = findViewById(R.id.textView_ActivityLobbyDetail_description);
        textView_location = findViewById(R.id.textView_ActivityLobbyDetail_location);
        button_returnToLobby = findViewById(R.id.button_ActivityLobbyDetail_returnToLobby);
        button_editLobby = findViewById(R.id.button_ActivityLobbyDetail_editLobby);

        queryInformation(lobby.getLobbyID());

        String hostID = textView_host.getText().toString();
        Log.d(TAG, "hostID : " + hostID);

        if(checkHost(user, lobby)){
            button_editLobby.setVisibility(View.VISIBLE);
            button_editLobby.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), EditLobbyActivity.class);
                    intent.putExtra(Constants.LOBBY, lobby);
                    intent.putExtra(Constants.USER, user);
                    startActivity(intent);
                    finish();
                }
            });
        }
        else{
            button_editLobby.setVisibility(View.INVISIBLE);
            button_editLobby.setClickable(false);
        }

        button_returnToLobby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LobbyActivity.class);
                intent.putExtra(Constants.USER, user);
                intent.putExtra(Constants.LOBBY, lobby);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        button_returnToLobby.performClick();
        button_returnToLobby.setPressed(true);
        button_returnToLobby.invalidate();
        button_returnToLobby.setPressed(false);
        button_returnToLobby.invalidate();
    }

    private boolean checkHost(User user, Lobby lobby){
        String hostID = lobby.getHostID();
        String userID = user.getUserID();
        if(hostID.equals(userID)){
            return true;
        }
        else{
            return false;
        }
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