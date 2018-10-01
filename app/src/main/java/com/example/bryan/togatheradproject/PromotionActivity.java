package com.example.bryan.togatheradproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;
import android.widget.ListView;
import android.content.Intent;

import java.util.ArrayList;

public class PromotionActivity extends AppCompatActivity {
    private static final String TAG = "PromotionActivity";
    TextView textView_promotion;
    Button button_Return;
    ListView listView_promotion;
    private ArrayList<Promotion> promotionArrayList = new ArrayList<>();
    private PromotionList promotionListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promotion);

        promotionListAdapter = new PromotionList(PromotionActivity.this, promotionArrayList);
        textView_promotion = findViewById(R.id.textView_PromotionActivity_promotionList);
        button_Return = findViewById(R.id.button_PromotionActivity_Return);
        listView_promotion = findViewById(R.id.listView_PromotionActivity_promotionList);

        Intent intent = getIntent();
        final Lobby lobby = (Lobby) intent.getSerializableExtra(Constants.LOBBY);
        final User user = (User) intent.getSerializableExtra(Constants.USER);

<<<<<<< HEAD
        Promotion coffee1 = new Promotion("desc", "coffee", R.drawable.coffee);
        Promotion coffee2 = new Promotion("desc", "coffee", R.drawable.coffee2);
        Promotion hangout1 = new Promotion("desc", "Hang out", R.drawable.hangout);
        Promotion hangout2 = new Promotion("desc", "Hang out", R.drawable.hangout2);
        Promotion eatout1 = new Promotion("desc", "Eat out", R.drawable.eatout);
        Promotion eatout2 = new Promotion("desc", "Eat out", R.drawable.eatout2);
        Promotion study1 = new Promotion("desc", "study", R.drawable.study);
        Promotion study2 = new Promotion("desc", "study", R.drawable.study2);
        Promotion outdoor1 = new Promotion("desc", "outdoor", R.drawable.outdoor);
        Promotion outdoot2 = new Promotion("desc", "outdoor", R.drawable.outdoor2);
        Promotion games1 = new Promotion("desc", "games", R.drawable.games);
        Promotion games2 = new Promotion("desc", "games", R.drawable.games2);
        Promotion movie1 = new Promotion("desc", "Movies", R.drawable.movie);
        Promotion movie2 = new Promotion("desc", "Movies", R.drawable.movie2);
        Promotion sport1 = new Promotion("desc", "sports", R.drawable.sports);
        Promotion sport2 = new Promotion("desc", "sports", R.drawable.sports2);
=======
        Promotion coffee1 = new Promotion("Enjoy our morning coffee on every weekdays from now - 30 March 2018" , "coffee", R.drawable.coffee);
        Promotion coffee2 = new Promotion("Celebrating 20 Years of Singapore, here's $2 off.", "coffee", R.drawable.coffee2);
        Promotion hangout1 = new Promotion("Happy hour promotion starts now from 7-10PM excluding weekends.", "Hang out", R.drawable.hangout);
        Promotion hangout2= new Promotion("2 hour singing for only $10 nett! Desserts included." , "Hang out", R.drawable.hangout2);
        Promotion eatout1 = new Promotion("National Day promotion! Try our buffet local cuisine from 18.00-22.00", "Eat out" , R.drawable.eatout);
        Promotion eatout2 = new Promotion("Calling out for the food lover to try our special 1-1 buffet promotion.", "Eat out", R.drawable.eatout2);
        Promotion study1 = new Promotion("10% discounts for all books", "study", R.drawable.study);
        Promotion study2 = new Promotion("Buy 1 Get 1 free book to all readers out there!", "study", R.drawable.study2);
        Promotion outdoor1 = new Promotion("Free rides for one full month celebrating 100th day", "outdoor", R.drawable.outdoor);
        Promotion outdoot2 = new Promotion("Get Forest Adventure ticket for every 4.4kg breeze purchased", "outdoor", R.drawable.outdoor2);
        Promotion games1 = new Promotion("Birthday age promotion. Get birthday discount equal to your age", "games", R.drawable.games);
        Promotion games2 = new Promotion("Get our powerclub promotion only by using our app. Save and Enjoy More.", "games", R.drawable.games2);
        Promotion movie1 = new Promotion("MOVIE TIME! 1-1 weekend ticket is now available at your nearest cinema", "Movies", R.drawable.movie);
        Promotion movie2 = new Promotion("HSBC Platinum promotion for only $5 per ticket", "Movies", R.drawable.movie2);
        Promotion sport1 = new Promotion("Sign Up for Active SG to enjoy $7 for 2 hours", "sports", R.drawable.sports);
        Promotion sport2 = new Promotion("Save $300 for new members. SIGN UP NOW!", "sports", R.drawable.sports2);
>>>>>>> 18ed04127502a3ed602b65080f579b4824a97d4a

        String lobbyActivity = lobby.getActivity();
        switch (lobbyActivity) {
            case "coffee":
                promotionArrayList.add(coffee1);
                promotionArrayList.add(coffee2);
                break;
            case "Eat out":
                promotionArrayList.add(eatout1);
                promotionArrayList.add(eatout2);
                break;
            case "Hang out":
                promotionArrayList.add(hangout1);
                promotionArrayList.add(hangout2);
                break;
            case "Movies":
                promotionArrayList.add(movie1);
                promotionArrayList.add(movie2);
                break;
            case "games":
                promotionArrayList.add(games1);
                promotionArrayList.add(games2);
                break;
            case "sports":
                promotionArrayList.add(sport1);
                promotionArrayList.add(sport2);
                break;
            case "study":
                promotionArrayList.add(study1);
                promotionArrayList.add(study2);
                break;
            case "outdoor":
                promotionArrayList.add(outdoor1);
                promotionArrayList.add(outdoot2);
                break;
        }
        promotionListAdapter = new PromotionList(PromotionActivity.this, promotionArrayList);
        listView_promotion.setAdapter(promotionListAdapter);

        button_Return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button_Return.setEnabled(false);
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
        button_Return.performClick();
    }
}
