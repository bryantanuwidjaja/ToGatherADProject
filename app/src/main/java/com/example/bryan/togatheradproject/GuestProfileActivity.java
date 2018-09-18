package com.example.bryan.togatheradproject;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class GuestProfileActivity extends AppCompatActivity {

    private static final String TAG = "GuestProfileActivity";

    TextView textView_username;
    TextView textView_interest1;
    TextView textView_interest2;
    TextView textView_interest3;
    TextView textView_interest4;
    TextView textView_interest5;
    TextView textView_interest6;
    TextView textView_currentRating;
    Button button_rateUp;
    Button button_returnToLobby;
    ImageView imageView_profilePic;
    ImageView imageView_thumbsUp;

    private String ratingContainer;
    private ArrayList<String> interestList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_profile);

        Intent intent = getIntent();
        final User loggeduser = (User) intent.getSerializableExtra(Constants.USER);
        final User clickedUser = (User) intent.getSerializableExtra(Constants.CLICKED_USER);
        final Lobby lobby = (Lobby) intent.getSerializableExtra(Constants.LOBBY);
        String clickedID = clickedUser.getUserID();

        textView_username = findViewById(R.id.textView_GuestProfileActivity_username);
        textView_interest1 = findViewById(R.id.textView_GuestProfileActivity_interest1);
        textView_interest2 = findViewById(R.id.textView_GuestProfileActivity_interest2);
        textView_interest3 = findViewById(R.id.textView_GuestProfileActivity_interest3);
        textView_interest4 = findViewById(R.id.textView_GuestProfileActivity_interest4);
        textView_interest5 = findViewById(R.id.textView_GuestProfileActivity_interest5);
        textView_interest6 = findViewById(R.id.textView_GuestProfileActivity_interest6);
        textView_currentRating = findViewById(R.id.textView_GuestProfileActivity_currentRating);
        button_rateUp = findViewById(R.id.button_GuestProfileActivity_rateUp);
        button_returnToLobby = findViewById(R.id.button_GuestProfileActivity_returnToLobby);
        imageView_profilePic = findViewById(R.id.imageView_GuestProfileActivity_profilepicture);
        imageView_thumbsUp = findViewById(R.id.imageView_GuestProfileActivity_thumbsup);

        retrieveInformation(clickedID);

        button_returnToLobby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LobbyActivity.class);
                intent.putExtra(Constants.USER, loggeduser);
                intent.putExtra(Constants.LOBBY, lobby);
                startActivity(intent);
                finish();
            }
        });

        button_rateUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button_rateUp.setBackgroundColor(Color.rgb(248,103,46));
                button_rateUp.setTextColor(Color.rgb(255,255,255));
                updateRating(clickedUser);
                ratingContainer = Integer.toString(clickedUser.getUserRating());
                Log.d(TAG, "onClick: rating container : " + ratingContainer);
                textView_currentRating.setText(ratingContainer);
                Toast.makeText(getApplicationContext(), "Liked", Toast.LENGTH_SHORT).show();
                disableRateButton();
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

    private void disableRateButton() {
        button_rateUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "You already liked this user", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateRating(User clickedUser) {
        clickedUser.rateUser();
        String clickedGuestID = clickedUser.getUserID();
        FirebaseFirestore.getInstance().collection(Constants.USER)
                .document(clickedGuestID)
                .set(clickedUser)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "onSuccess: update successful");
                    }
                });
    }

    private void retrieveInformation(String clickedID) {
        FirebaseFirestore.getInstance().collection(Constants.USER)
                .document(clickedID)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        User user = documentSnapshot.toObject(User.class);
                        textView_username.setText(user.getUserName());
                        interestList = user.getUserInterests();
                        textView_interest1.setText(interestList.get(0));
                        textView_interest2.setText(interestList.get(1));
                        textView_interest3.setText(interestList.get(2));
                        textView_interest4.setText(interestList.get(3));
                        textView_interest5.setText(interestList.get(4));
                        textView_interest6.setText(interestList.get(5));
                        ratingContainer = Integer.toString(user.getUserRating());
                        textView_currentRating.setText(ratingContainer);
                    }
                });
    }
}
