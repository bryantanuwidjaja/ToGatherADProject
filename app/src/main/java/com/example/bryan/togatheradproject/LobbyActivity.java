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
        final Lobby lobby = (Lobby) intent.getSerializableExtra(Constants.LOBBY);
        final User user = (User) intent.getSerializableExtra(Constants.USER);
        Log.d(TAG, "User: " + user.getUserID());

        //join lobby
        updateDatabase(user, lobby);

        Log.d(TAG, "userID : " + user.getUserID());
        Log.d(TAG, "lobbyID : " + lobby.getLobbyID());

        textView_lobbyID = findViewById(R.id.textView_LobbyActivity_lobbyID);
        editView_chatDialog = findViewById(R.id.editText_LobbyActivity_chatDialog);
        listView_chatLog = findViewById(R.id.listView_LobbyActivity_chatLog);
        imageView_activityIcon = findViewById(R.id.imageView_LobbyActivity_activityIcon);
        button_enter = findViewById(R.id.button_LobbyActivity_enter);
        button_guestList = findViewById(R.id.button_LobbyActivity_guestList);
        button_lobbyDetail = findViewById(R.id.button_LobbyActivity_lobbyDetail);

        //create a function to get current chat log
        readData(lobby ,new FirestoreCallback() {
            @Override
            public void onCallBack(Chatlog chatlog) {
                chatlogList = chatlog.getChatlog();
                Log.d(TAG, "onCallBack: " + chatlogList.toString());
            }
        });

        FirebaseFirestore.getInstance().collection(Constants.LOBBY)
                .document(lobby.getLobbyID())
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
                            readData(lobby, new FirestoreCallback() {
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
                intent.putExtra(Constants.USER_ID, user.getUserID());
                intent.putExtra(Constants.LOBBY_ID, lobby.getLobbyID());
                intent.putExtra(Constants.USER, user);
                intent.putExtra(Constants.LOBBY, lobby);
                intent.putExtra(Constants.LOBBY_CHATLOG_ID, lobby.getChatlogID());
                startActivity(intent);
            }
        });

        button_guestList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), GuestListActivity.class);
                intent.putExtra(Constants.USER_ID, user.getUserID());
                intent.putExtra(Constants.LOBBY_ID, lobby.getLobbyID());
                intent.putExtra(Constants.LOBBY, lobby);
                intent.putExtra(Constants.USER, user);
                intent.putExtra(Constants.LOBBY_CHATLOG_ID, lobby.getChatlogID());
                startActivity(intent);
            }
        });

        button_enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Clear the list
                //chatlogList.clear();

                //Read the database;
                readData(lobby, new FirestoreCallback() {
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
                chat.updateChat(chatlogList, lobby.getLobbyID(), lobby.getChatlogID());

                //clear the edit text
                editView_chatDialog.setText("");
            }
        });
        Log.d(TAG, "onCreate: out");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();
        final Lobby lobby = (Lobby) intent.getSerializableExtra(Constants.LOBBY);
        final User user = (User) intent.getSerializableExtra(Constants.USER);
        listenerRegistration = FirebaseFirestore.getInstance().collection(Constants.LOBBY)
                .document(lobby.getLobbyID())
                .collection(Constants.LOBBY_CHATLOG)
                .document(lobby.getChatlogID())
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
                .document(lobby.getLobbyID())
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
                            deleteLobby(lobby);
                        }
                    }
                });
    }

    //delete lobby function
    private void deleteLobby(Lobby lobby){
        FirebaseFirestore.getInstance().collection(Constants.LOBBY)
                .document(lobby.getLobbyID())
                .collection(Constants.LOBBY_CHATLOG)
                .document(lobby.getChatlogID())
                .delete();

        FirebaseFirestore.getInstance().collection(Constants.LOBBY)
                .document(lobby.getLobbyID())
                .delete();
    }

    //join lobby function
    private void updateDatabase(User user , Lobby lobby) {
        FirebaseFirestore.getInstance().collection(Constants.LOBBY)
                .document(lobby.getLobbyID())
                .collection(Constants.LOBBY_GUESTLIST)
                .document(user.getUserID())
                .set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "onSuccess: update successful");
                    }
                });
    }

    private void readData(Lobby lobby, final FirestoreCallback firestoreCallback) {
        FirebaseFirestore.getInstance()
                .collection(Constants.LOBBY)
                .document(lobby.getLobbyID())
                .collection(Constants.LOBBY_CHATLOG)
                .document(lobby.getChatlogID())
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
        final Lobby lobby = (Lobby) intent.getSerializableExtra(Constants.LOBBY);
        backCounter++;
        if(backCounter == 1) {
            Toast.makeText(getApplicationContext(), "Press back again to leave the room", Toast.LENGTH_SHORT).show();
        } else if (backCounter == 2) {
            //leave room
            leaveRoom(user,lobby);

            //intent back to home
            Intent intentback = new Intent(getApplicationContext(), HomeActivity.class);
            intentback.putExtra(Constants.USER, user);
            startActivity(intentback);
        }
    }

    private void leaveRoom(User user, Lobby lobby){
        FirebaseFirestore.getInstance().collection(Constants.LOBBY)
                .document(lobby.getLobbyID())
                .collection(Constants.LOBBY_GUESTLIST)
                .document(user.getUserID())
                .delete();
    }

    private interface FirestoreCallback {
        void onCallBack(Chatlog chatlog);
    }
}
