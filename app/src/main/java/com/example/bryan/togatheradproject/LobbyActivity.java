package com.example.bryan.togatheradproject;

import android.app.FragmentManager;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

import javax.annotation.Nullable;


public class LobbyActivity extends AppCompatActivity {

    private static final String TAG = "LobbyActivity";
    ListenerRegistration listenerRegistration;
    ListenerRegistration lobbyListener;
    ListenerRegistration requestListener;
    TextView textView_lobbyID;
    EditText editView_chatDialog;
    ListView listView_chatLog;
    ImageView imageView_activityIcon;
    Button button_enter;
    Button button_guestList;
    Button button_lobbyDetail;
    private String activity;
    private int backCounter;
    private int clickIndicator = 0;
    private ArrayList<Chat> chatlogList = new ArrayList<>();
    Button button_promotion;
    boolean inLobby = true;

    protected void establish() {
        textView_lobbyID = findViewById(R.id.textView_LobbyActivity_lobbyID);
        editView_chatDialog = findViewById(R.id.editText_LobbyActivity_chatDialog);
        listView_chatLog = findViewById(R.id.listView_LobbyActivity_chatLog);
        imageView_activityIcon = findViewById(R.id.imageView_LobbyActivity_activityIcon);
        button_enter = findViewById(R.id.button_LobbyActivity_enter);
        button_guestList = findViewById(R.id.button_LobbyActivity_guestList);
        button_lobbyDetail = findViewById(R.id.button_LobbyActivity_lobbyDetail);
        button_promotion = findViewById(R.id.button_LobbyActivity_promotion);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);
        Log.d(TAG, "onCreate: in");

        Intent intent = getIntent();
        final Lobby lobby = (Lobby) intent.getSerializableExtra(Constants.LOBBY);
        final User user = (User) intent.getSerializableExtra(Constants.USER);

        //use the rating field as container for the index
        getGuestList(lobby, new GuestListCallback() {
            @Override
            public void onCallBack(ArrayList<User> guestList) {
                int index = guestList.size();
                Log.d(TAG, "color index : " + index);
                user.setIndex(index);
            }
        });
        Log.d(TAG, "User: " + user.getUserID());

        activity = lobby.getActivity();

        updateDatabase(user, lobby);
        Log.d(TAG, "color index 2 : " + user.getIndex());

        Log.d(TAG, "userID : " + user.getUserID());
        Log.d(TAG, "lobbyID : " + lobby.getLobbyID());

        establish();

        //assign the proper icon for the activity
        switch (activity){
            case "Coffee":
                imageView_activityIcon.setImageResource(R.drawable.ic_activity_coffee);
                break;
            case "Eat out":
                imageView_activityIcon.setImageResource(R.drawable.ic_action_eat_out);
                break;
            case "Hang out":
                imageView_activityIcon.setImageResource(R.drawable.ic_action_hang_out);
                break;
            case "Movies":
                imageView_activityIcon.setImageResource(R.drawable.ic_action_movies);
                break;
            case "Games":
                imageView_activityIcon.setImageResource(R.drawable.ic_action_games);
                break;
            case "Sports":
                imageView_activityIcon.setImageResource(R.drawable.ic_action_sports);
                break;
            case "Study":
                imageView_activityIcon.setImageResource(R.drawable.ic_action_study);
                break;
            case "Outdoor":
                imageView_activityIcon.setImageResource(R.drawable.ic_action_outdoor);
                break;
        }

        //create a function to get current chat log
        readData(lobby, new FirestoreCallback() {
            @Override
            public void onCallBack(Chatlog chatlog) {
                chatlogList = chatlog.getChatlog();
                Log.d(TAG, "onCallBack: " + chatlogList.toString());
            }
        });


        textView_lobbyID.setText(lobby.getActivity());

        textView_lobbyID.setText(lobby.getActivity());
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
                                        try {
                                            chatlogList = chatlog.getChatlog();
                                            Log.d(TAG, "onCallBack real time: " + chatlogList.toString());
                                        }
                                        catch (NullPointerException e1){
                                            Log.d(TAG, "null chatlog");
                                        }
                                    }
                                });
                            }
                        }
                });

        button_promotion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickIndicator = 1;
                button_promotion.setEnabled(false);

                Intent intent = new Intent(getApplicationContext(), PromotionActivity.class);
                intent.putExtra(Constants.LOBBY, lobby);
                intent.putExtra(Constants.USER, user);
                startActivity(intent);

                button_promotion.invalidate();

                finish();
            }
        });


        button_lobbyDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button_lobbyDetail.setEnabled(false);
                clickIndicator = 1;
                Intent intent = new Intent(getApplicationContext(), LobbyDetailActivity.class);
                intent.putExtra(Constants.USER, user);
                intent.putExtra(Constants.LOBBY, lobby);
                startActivity(intent);
                finish();
            }
        });

        button_guestList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button_guestList.setEnabled(false);
                clickIndicator = 1;
                Intent intent = new Intent(getApplicationContext(), GuestListActivity.class);
                intent.putExtra(Constants.LOBBY, lobby);
                intent.putExtra(Constants.USER, user);
                startActivity(intent);
                finish();
            }
        });

        button_enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button_enter.setEnabled(false);
                clickIndicator = 1;
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

                //get user index from the guest list to assign color
                getGuestList(lobby, new GuestListCallback() {
                    @Override
                    public void onCallBack(ArrayList<User> guestList) {
                        //create chat instance
                        Chat chat = new Chat();

                        //retrieve input from widget
                        String input = editView_chatDialog.getText().toString();

                        Log.d(TAG, "index: " + user.getIndex());
                        //generate the chat object
                        chat = chat.inputChat(user, input);

                        //add the chat to the current chat log
                        chatlogList.add(chat);

                        //update the database
                        chat.updateChat(chatlogList, lobby.getLobbyID(), lobby.getChatlogID());

                        //clear the edit text
                        editView_chatDialog.setText("");
                        button_enter.setEnabled(true);
                    }
                });
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

        requestListener = FirebaseFirestore.getInstance().collection(Constants.LOBBY)
                .document(lobby.getLobbyID())
                .collection(Constants.LOBBY_REQUEST)
                .addSnapshotListener(this, new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots,
                                        @Nullable FirebaseFirestoreException e) {
                            for (Request request : queryDocumentSnapshots.toObjects(Request.class)) {
                                Log.d(TAG, "for in ");
                                if (lobby.getHostID().equals(user.getUserID()) && request.getState().equals(Constants.WAITING)) {
                                    //inflate dialog fragment
                                    Log.d(TAG, "if - in ");
                                    RespondRequestDialog dialog = new RespondRequestDialog();
                                    Bundle bundle = new Bundle();
                                    bundle.putSerializable(Constants.LOBBY_REQUEST, request);
                                    bundle.putSerializable(Constants.LOBBY, lobby);
                                    dialog.setArguments(bundle);
                                    dialog.show(getFragmentManager(), "RespondRequestDialog");
                                }
                                else{
                                    Log.d(TAG, "buffer");
                                }
                            }
                    }
                });

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
                        if (size == 0) {
                            //delete lobby
                            deleteLobby(lobby, user);
                        }
                    }
                });
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(clickIndicator == 0 || backCounter >= 2) {
            Intent intent = getIntent();
            final User user = (User) intent.getSerializableExtra(Constants.USER);
            final Lobby lobby = (Lobby) intent.getSerializableExtra(Constants.LOBBY);
            leaveRoom(user, lobby);
            inLobby = false;
            finish();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Intent intent = getIntent();
        final User user = (User) intent.getSerializableExtra(Constants.USER);
        Intent restartIntent = new Intent(getApplicationContext(), HomeActivity.class);
        restartIntent.putExtra(Constants.USER, user );
        startActivity(restartIntent);
    }

    @Override
    public void onBackPressed() {
        Intent intent = getIntent();
        final User user = (User) intent.getSerializableExtra(Constants.USER);
        final Lobby lobby = (Lobby) intent.getSerializableExtra(Constants.LOBBY);
        backCounter++;
        if (backCounter == 1) {
            Toast.makeText(getApplicationContext(), "Press back again to leave the room", Toast.LENGTH_SHORT).show();
        } else if (backCounter >= 2) {
            Log.d(TAG, "backCounter : " + backCounter);
            //leave room
            onStop();

            //leaveRoom(user, lobby);

        }
    }

    //delete lobby function


    private void deleteLobby(final Lobby lobby, User user) {

        FirebaseFirestore.getInstance().collection(Constants.LOBBY)
                .document(lobby.getLobbyID())
                .collection(Constants.LOBBY_CHATLOG)
                .document(lobby.getChatlogID())
                .delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.d(TAG, "onComplete: Chatlog deletion complete");
                    }
                });

        FirebaseFirestore.getInstance().collection(Constants.LOBBY)
                .document(lobby.getLobbyID())
                .collection(Constants.LOBBY_GUESTLIST)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (User user :
                                task.getResult().toObjects(User.class)) {
                            FirebaseFirestore.getInstance().collection(Constants.LOBBY)
                                    .document(lobby.getLobbyID())
                                    .collection(Constants.LOBBY_GUESTLIST)
                                    .document(user.getUserID())
                                    .delete();
                        }
                    }
                });

        FirebaseFirestore.getInstance().collection(Constants.LOBBY)
                .document(lobby.getLobbyID())
                .delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.d(TAG, "onComplete: Lobby deletion complete");
                    }
                });

        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        intent.putExtra(Constants.USER, user);
        startActivity(intent);
        finish();
    }
    
    private void updateDatabase(User user, Lobby lobby) {
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


    private void leaveRoom(final User user, final Lobby lobby) {

        FirebaseFirestore.getInstance().collection(Constants.LOBBY)
                .document(lobby.getLobbyID())
                .collection(Constants.LOBBY_GUESTLIST)
                .document(user.getUserID())
                .delete();

        if(inLobby) {
            Chat chat = new Chat();
            chat = chat.leaveEntryChat(user);
            chatlogList.add(chat);
            chat.updateChat(chatlogList, lobby.getLobbyID(), lobby.getChatlogID());
        }

        hostConstraint(user, lobby);
        isEmpty(lobby, new BooleanCallback() {
            @Override
            public void onCallBack(Boolean isEmpty) {
                if(isEmpty){
                    deleteLobby(lobby, user);
                }
            }
        });
    }

    //after removal
    private void hostConstraint(final User user, final Lobby lobby) {
        String hostID = lobby.getHostID();
        String userID = user.getUserID();
        //check if host
        if (userID.equals(hostID)) {
            //get list of candidate(s) to host
            FirebaseFirestore.getInstance().collection(Constants.LOBBY)
                    .document(lobby.getLobbyID())
                    .collection(Constants.LOBBY_GUESTLIST)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            ArrayList<User> guestList = new ArrayList<>();
                            for (User user : task.getResult().toObjects(User.class)) {
                                guestList.add(user);
                            }
                            if (guestList.size() > 0) {
                                //randomise and select the host
                                Random random = new Random();
                                int randomIndex = (int) ((Math.random() * guestList.size()));
                                Log.d(TAG, "random index : " + randomIndex);
                                User nexthost = guestList.get(randomIndex);
                                //set the new hostID to the lobby
                                lobby.setHostID(nexthost.getUserID());
                                //update the firebase
                                FirebaseFirestore.getInstance().collection(Constants.LOBBY)
                                        .document(lobby.getLobbyID())
                                        .update(Constants.HOST_ID, nexthost.getUserID());

                                //create a rehost message
                                Chat chat = new Chat();
                                chat = chat.rehostChat(nexthost);
                                chatlogList.add(chat);
                                chat.updateChat(chatlogList, lobby.getLobbyID(), lobby.getChatlogID());
                            }
                        }
                    });
        }
    }

    private void isEmpty(Lobby lobby, final BooleanCallback booleanCallback) {
        FirebaseFirestore.getInstance().collection(Constants.LOBBY)
                .document(lobby.getLobbyID())
                .collection(Constants.LOBBY_GUESTLIST)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        ArrayList<User> guestList = new ArrayList<>();
                        for (User user :
                                task.getResult().toObjects(User.class)) {
                            guestList.add(user);
                        }
                        if (guestList.size() > 0) {
                            booleanCallback.onCallBack(false);
                        } else {
                            booleanCallback.onCallBack(true);
                        }
                    }
                });
    }

    private void getGuestList(Lobby lobby, final GuestListCallback guestListCallback){
        FirebaseFirestore.getInstance().collection(Constants.LOBBY)
                .document(lobby.getLobbyID())
                .collection(Constants.LOBBY_GUESTLIST)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (!task.getResult().isEmpty()) {
                            ArrayList<User> guestList = new ArrayList<>();
                            for (User user : task.getResult().toObjects(User.class)) {
                                guestList.add(user);
                            }
                            guestListCallback.onCallBack(guestList);
                        }
                    }
                });
    }

    private interface BooleanCallback {
        void onCallBack(Boolean isEmpty);
    }

    private interface FirestoreCallback {
        void onCallBack(Chatlog chatlog);
    }

    private interface GuestListCallback{
        void onCallBack(ArrayList<User> guestList);
    }
}
