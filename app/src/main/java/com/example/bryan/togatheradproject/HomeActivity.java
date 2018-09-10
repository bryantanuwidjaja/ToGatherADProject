package com.example.bryan.togatheradproject;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.protobuf.Any;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

public class HomeActivity extends AppCompatActivity {

    public static final String TAG = "ToGather";
    List<Lobby> lobbyList;
    ArrayList<Chat> chatlogList = new ArrayList<>();
    ListView listView_LobbyList;
    TextView textView_Nearbylobby;
    Button button_Createlobby;
    Button button_Viewprofile;
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    ListenerRegistration listenerRegistration;
    CollectionReference lobbyCollection = firebaseFirestore.collection(Constants.LOBBY);
    String loggedID;



    @Override
    protected void onStart() {
        super.onStart();
        retreivedLobby();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setListenerRegistration();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Log.d(TAG, "onCreate: in");

        Intent intent = getIntent();
        loggedID = intent.getStringExtra(Constants.USER_ID);
        final User user = (User) intent.getSerializableExtra(Constants.USER);
        Log.d(TAG, "HomeActivity : Logged user : " + loggedID);

        lobbyList = new ArrayList<>();
        listView_LobbyList = findViewById(R.id.listView_HomeActivity_lobbyList);
        textView_Nearbylobby = findViewById(R.id.textView_HomeActivity_nearbyLobby);
        button_Createlobby = findViewById(R.id.button_HomeActivity_createLobby);
        button_Viewprofile = findViewById(R.id.button_HomeActivity_viewProfile);

        button_Createlobby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CreateLobbyActivity.class);
                intent.putExtra(Constants.USER_ID, loggedID);
                intent.putExtra(Constants.USER, user);
                startActivity(intent);
            }
        });

        button_Viewprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                intent.putExtra(Constants.USER_ID, loggedID);
                intent.putExtra(Constants.USER, user);
                startActivity(intent);
            }
        });

        listView_LobbyList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Lobby lobby = lobbyList.get(position);
                String lobbyID = lobby.getLobbyID();

                //retrieve the chat log id to refer to the clicked lobby
                String chatlogID = lobby.getChatlogID();

                //retrieve current chat log
                retrieveChatLog(lobbyID, chatlogID, user, lobby);
//                Log.d(TAG, "chatloglist: 1 " + chatlogList);

                //create an empty chat instance
//                Chat chat = new Chat();
//
//                //create the entry message
//                chat = chat.entryChat(user);
//
//                //add the entry message to the array
//                chatlogList.add(chat);
//                Log.d(TAG, "chatloglist: 2 " + chatlogList);
//
//                //update the database
//                chat.updateChat(chatlogList, lobbyID, chatlogID);
//
//                //create the intent along with the relevant information to be passed
//                Intent intent = new Intent(getApplicationContext(), LobbyActivity.class);
//                intent.putExtra(Constants.USER_ID, loggedID);
//                intent.putExtra(Constants.LOBBY_ID, lobbyID);
//                intent.putExtra(Constants.LOBBY_CHATLOG_ID ,chatlogID);
//                intent.putExtra(Constants.USER, user);
//                intent.putExtra(Constants.LOBBY, lobby);
//                Log.d(TAG, "User: " + user.getUserID());
//                Log.d(TAG, "userID : " + loggedID);
//                Log.d(TAG, "lobbyID : " + lobbyID);
//                startActivity(intent);
            }
        });
        Log.d(TAG, "onCreate: out");
    }


    public void setListenerRegistration() {
        lobbyCollection.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if( e != null ){
                    Log.w(TAG, "onEvent: listen failed",e );
                    return;
                }
                if (queryDocumentSnapshots != null && !queryDocumentSnapshots.isEmpty() ){
                    Log.d(TAG, "onEvent: current metadata " + queryDocumentSnapshots.getMetadata());
                    Log.d(TAG, "onEvent: current query " + queryDocumentSnapshots.getQuery());
                    Log.d(TAG, "onEvent: current documents " +  queryDocumentSnapshots.getDocuments());
                }
                else{
                    Log.d(TAG, "onEvent: current data null " );
                }
            }
        });
    }

    private void retreivedLobby() {
        //reset the list
        lobbyList.clear();

        FirebaseFirestore.getInstance().collection(Constants.LOBBY)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            Lobby lobby = documentSnapshot.toObject(Lobby.class);
                            String chatlogID = lobby.getChatlogID();
                            String lobbyID = lobby.getLobbyID();
                            String hostID = lobby.getHostID();
                            int capacity = lobby.getCapacity();
                            String location = lobby.getLocation();
                            String lobbyDescriptions = lobby.getLobbyDescriptions();
                            String activity = lobby.getActivity();
                            ArrayList<User> guestList = lobby.getGuestList();
                            Lobby newLobby = new Lobby(lobbyID, hostID, capacity, location, lobbyDescriptions, activity, guestList, chatlogID);
                            lobbyList.add(newLobby);
//                            LobbyList adapter = new LobbyList(HomeActivity.this, lobbyList);
//                            listView_LobbyList.setAdapter(adapter);
                        }
                        LobbyList adapter = new LobbyList(HomeActivity.this, lobbyList);
                        listView_LobbyList.setAdapter(adapter);
                    }
                });
    }

    private void retrieveChatLog(final String lobbyID, final String chatlogID, final User user, final Lobby lobby) {
        //clear the chat log
        chatlogList.clear();
        FirebaseFirestore.getInstance()
                .collection(Constants.LOBBY)
                .document(lobbyID)
                .collection(Constants.LOBBY_CHATLOG)
                .document(chatlogID)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        Log.d(TAG, "onSuccess retrievechatlog : in ");
                        Chatlog chatlog = documentSnapshot.toObject(Chatlog.class);
                        ArrayList<Chat> chatArray = chatlog.getChatlog();
                        for (Chat chat : chatArray) {
                            Log.d(TAG, "chatmes:  retrievechatlog " + chat.getChatMessage());
                            chatlogList.add(chat);
                        }
                        Log.d(TAG, "chatloglist:  retrievechatlog " + chatlogList);
                        Chat chat = new Chat();

                        //create the entry message
                        chat = chat.entryChat(user);

                        //add the entry message to the array
                        chatlogList.add(chat);
                        Log.d(TAG, "chatloglist: 2 " + chatlogList);

                        //update the database
                        chat.updateChat(chatlogList, lobbyID, chatlogID);

                        //create the intent along with the relevant information to be passed
                        Intent intent = new Intent(getApplicationContext(), LobbyActivity.class);
                        intent.putExtra(Constants.USER_ID, loggedID);
                        intent.putExtra(Constants.LOBBY_ID, lobbyID);
                        intent.putExtra(Constants.LOBBY_CHATLOG_ID, chatlogID);
                        intent.putExtra(Constants.USER, user);
                        intent.putExtra(Constants.LOBBY, lobby);
                        Log.d(TAG, "User: " + user.getUserID());
                        Log.d(TAG, "userID : " + loggedID);
                        Log.d(TAG, "lobbyID : " + lobbyID);
                        startActivity(intent);
                    }
                });
        Log.d(TAG, "chatLoglist : 3  " + chatlogList);
    }
}