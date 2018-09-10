package com.example.bryan.togatheradproject;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import javax.annotation.Nullable;


public class LobbyActivity extends AppCompatActivity {

    private static final String TAG = "LobbyActivity";

    private String userID;
    private String lobbyID;
    private String chatlogID;
    private int backCounter;
    private ArrayList<Chat> chatlogList = new ArrayList<>();
    ListenerRegistration listenerRegistration;
    ListenerRegistration lobbyListener;

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
        chatlogID = intent.getStringExtra(Constants.LOBBY_CHATLOG_ID);
        final Lobby lobby = (Lobby) intent.getSerializableExtra(Constants.LOBBY);
        final User user = (User) intent.getSerializableExtra(Constants.USER);
        Log.d(TAG, "User: " + user.getUserID());

        //join lobby
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
        readData(new FirestoreCallback() {
            @Override
            public void onCallBack(Chatlog chatlog) {
                chatlogList = chatlog.getChatlog();
                Log.d(TAG, "onCallBack: " + chatlogList.toString());
            }
        });

        FirebaseFirestore.getInstance().collection(Constants.LOBBY)
                .document(lobbyID)
                .collection(Constants.LOBBY_CHATLOG)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.w(TAG, "Listen failed: ", e);
                            return;
                        }
                        if (queryDocumentSnapshots != null && !queryDocumentSnapshots.isEmpty()) {
                            Log.d(TAG, "current data " + queryDocumentSnapshots.getDocuments());
                            readData(new FirestoreCallback() {
                                @Override
                                public void onCallBack(Chatlog chatlog) {
                                    chatlogList = chatlog.getChatlog();
                                    Log.d(TAG, "onCallBack real time: " + chatlogList.toString());
                                }
                            });
                        }
                    }
                });

        button_lobbyDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LobbyDetailActivity.class);
                intent.putExtra(Constants.USER_ID, userID);
                intent.putExtra(Constants.LOBBY_ID, lobbyID);
                intent.putExtra(Constants.USER, user);
                intent.putExtra(Constants.LOBBY, lobby);
                intent.putExtra(Constants.LOBBY_CHATLOG_ID, chatlogID);
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
                intent.putExtra(Constants.USER, user);
                intent.putExtra(Constants.LOBBY_CHATLOG_ID, chatlogID);
                startActivity(intent);
            }
        });

        button_enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Clear the list
                //chatlogList.clear();

                //Read the database;
                readData(new FirestoreCallback() {
                    @Override
                    public void onCallBack(Chatlog chatlog) {
                        chatlogList = chatlog.getChatlog();
                        Log.d(TAG, "after read data : " + chatlogList);
                    }
                });

                //create chat instance
                Chat chat = new Chat();

                //retrieve input from widget
                String input = editView_chatDialog.getText().toString();

                //generate the chat object
                chat = chat.inputChat(user, input);

                //add the chat to the current chat log
                chatlogList.add(chat);

                //update the database
                chat.updateChat(chatlogList, lobbyID, chatlogID);

                //clear the edit text
                editView_chatDialog.setText("");
            }
        });
        Log.d(TAG, "onCreate: out");
    }

    @Override
    protected void onStart() {
        super.onStart();
        listenerRegistration = FirebaseFirestore.getInstance().collection(Constants.LOBBY)
                .document(lobbyID)
                .collection(Constants.LOBBY_CHATLOG)
                .document(chatlogID)
                .addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.d(TAG, "onEvent: error loading data" + e);
                            return;
                        }
                        if (documentSnapshot.exists()) {
                            Chatlog chatlog = documentSnapshot.toObject(Chatlog.class);
                            chatlogList = chatlog.getChatlog();
                            ChatlogList adapter = new ChatlogList(LobbyActivity.this, chatlogList);
                            listView_chatLog.setAdapter(adapter);
                        }
                    }
                });

        lobbyListener = FirebaseFirestore.getInstance().collection(Constants.LOBBY)
                .document(lobbyID)
                .collection(Constants.LOBBY_GUESTLIST)
                .addSnapshotListener(this, new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots,
                                        @Nullable FirebaseFirestoreException e) {
                        ArrayList<User> guestList = new ArrayList<>();
                        for (User user : queryDocumentSnapshots.toObjects(User.class)) {
                            guestList.add(user);
                        }
                        int size = guestList.size();
                        if(size == 0){
                            //delete lobby
                            deleteLobby();
                        }
                    }
                });
    }

    //delete lobby function
    private void deleteLobby(){
        FirebaseFirestore.getInstance().collection(Constants.LOBBY)
                .document(lobbyID)
                .collection(Constants.LOBBY_CHATLOG)
                .document(chatlogID)
                .delete();

        FirebaseFirestore.getInstance().collection(Constants.LOBBY)
                .document(lobbyID)
                .delete();
    }

    //join lobby function
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

    private void readData(final FirestoreCallback firestoreCallback) {
        FirebaseFirestore.getInstance()
                .collection(Constants.LOBBY)
                .document(lobbyID)
                .collection(Constants.LOBBY_CHATLOG)
                .document(chatlogID)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot documentSnapshot = task.getResult();
                            Chatlog chatlog = documentSnapshot.toObject(Chatlog.class);
                            firestoreCallback.onCallBack(chatlog);
                        } else {
                            Log.d(TAG, "Error getting documents ", task.getException());
                        }
                    }
                });
    }

    @Override
    public void onBackPressed() {
        Intent intent = getIntent();
        final User user = (User) intent.getSerializableExtra(Constants.USER);
        backCounter++;
        if(backCounter == 1) {
            Toast.makeText(getApplicationContext(), "Press back again to leave the room", Toast.LENGTH_SHORT).show();
        } else if (backCounter == 2) {
            //leave room
            leaveRoom();

            //intent back to home
            Intent intentback = new Intent(getApplicationContext(), HomeActivity.class);
            intent.putExtra(Constants.USER, user);
            startActivity(intentback);
        }
    }

    private void leaveRoom(){
        FirebaseFirestore.getInstance().collection(Constants.LOBBY)
                .document(lobbyID)
                .collection(Constants.LOBBY_GUESTLIST)
                .document(userID)
                .delete();
    }

    private interface FirestoreCallback {
        void onCallBack(Chatlog chatlog);
    }
}
