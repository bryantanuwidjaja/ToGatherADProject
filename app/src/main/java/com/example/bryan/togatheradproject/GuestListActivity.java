package com.example.bryan.togatheradproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletionService;

public class GuestListActivity extends AppCompatActivity {
    private static final String TAG = "GuestListActivity";

    private ArrayList<User> guestList = new ArrayList<>();
    private TextView textView_guestListTAG;
    private Button button_returnToLobby;
    private ListView listView_guestList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_list);

        Intent intent = getIntent();
        final Lobby lobby = (Lobby) intent.getSerializableExtra(Constants.LOBBY);
        final User user = (User) intent.getSerializableExtra(Constants.USER);

        textView_guestListTAG = findViewById(R.id.textView_GuestListActivity_guestListTAG);
        button_returnToLobby = findViewById(R.id.button_GuestListActivity_returnToLobby);
        listView_guestList = findViewById(R.id.listView_GuestListActivity_guestList);

        retreiveGuestList(lobby.getLobbyID());

        button_returnToLobby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LobbyActivity.class);
                intent.putExtra(Constants.LOBBY, lobby);
                intent.putExtra(Constants.USER, user);
                startActivity(intent);
            }
        });

        listView_guestList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                User clickedUser = guestList.get(position);
                Intent intent = new Intent(getApplicationContext(), GuestProfileActivity.class);
                intent.putExtra(Constants.USER, user);
                intent.putExtra(Constants.CLICKED_USER, clickedUser);
                intent.putExtra(Constants.LOBBY, lobby);
                startActivity(intent);
            }
        });

    }

    private void retreiveGuestList(String lobbyID) {
        //reset the list
        guestList.clear();

        FirebaseFirestore.getInstance().collection(Constants.LOBBY)
                .document(lobbyID)
                .collection(Constants.LOBBY_GUESTLIST)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            User user = documentSnapshot.toObject(User.class);
                            guestList.add(user);
                            GuestList adapter = new GuestList(GuestListActivity.this, guestList);
                            listView_guestList.setAdapter(adapter);
                        }
                        GuestList adapter = new GuestList(GuestListActivity.this, guestList);
                        listView_guestList.setAdapter(adapter);
                    }
                });
    }
}
