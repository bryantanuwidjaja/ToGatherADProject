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

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;


public class LobbyActivity extends AppCompatActivity {

    private static final String TAG = "LobbyActivity";

    private String userID;
    private String lobbyID;
    private ArrayList<Chat> chatlogList = new ArrayList<>();

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
        final Lobby lobby = (Lobby) intent.getSerializableExtra(Constants.LOBBY);
        final User user = (User) intent.getSerializableExtra(Constants.USER);
        Log.d(TAG, "User: " + user.getUserID());

        //joinLobby(user, lobby);
        updateDatabase(user, userID);

        Log.d(TAG, "userID : " + userID);
        Log.d(TAG, "lobbyID : " + lobbyID);

        textView_lobbyID = findViewById(R.id.textView_LobbyActivity_lobbyID);
        editView_chatDialog = findViewById(R.id.editText_LobbyActivity_chatDialog);
        listView_chatLog = findViewById(R.id.listView_LobbyActivity_chatLog);
        imageView_activityIcon = findViewById(R.id.imageView_LobbyActivity_activityIcon);
        button_enter = findViewById(R.id.button_LobbyActivity_enter);
        button_guestList = findViewById(R.id.button_LobbyActivity_guestList);
        button_lobbyDetail = findViewById(R.id.button_LobbyActivity_lobbyDetail);

        //create a function to get current chat log


        button_lobbyDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LobbyDetailActivity.class);
                intent.putExtra(Constants.USER_ID, userID);
                intent.putExtra(Constants.LOBBY_ID, lobbyID);
                startActivity(intent);
            }
        });

        button_guestList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), GuestListActivity.class);
                intent.putExtra(Constants.USER_ID, userID);
                intent.putExtra(Constants.LOBBY_ID, lobbyID);
                intent.putExtra(Constants.LOBBY, lobby);
                startActivity(intent);
            }
        });

        button_enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //create chat instance
                Chat chat = new Chat();
                //retrieve input from widget
                String input = editView_chatDialog.getText().toString();
                //generate the chat object
                chat = chat.inputChat(user, input);
                //update the database
                chat.updateChat(chat, lobbyID);
                editView_chatDialog.setText("");
            }
        });
        Log.d(TAG, "onCreate: out");
    }

    private void joinLobby(User user, Lobby lobby) {
        lobby.addGuest(user);
    }

    private void updateDatabase(User user, String userID) {
        FirebaseFirestore.getInstance().collection(Constants.LOBBY)
                .document(lobbyID)
                .collection(Constants.LOBBY_GUESTLIST)
                .document(userID)
                .set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "onSuccess: update successful");
                    }
                });
    }

//    private void getChatlog(){
//        FirebaseFirestore.getInstance().collection()
//    }
}
