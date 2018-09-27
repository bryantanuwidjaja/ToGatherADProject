package com.example.bryan.togatheradproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;
import android.widget.ListView;
import android.content.Intent;

import java.util.ArrayList;

public class PromotionActivity extends AppCompatActivity{
    private static final String TAG ="PromotionActivity";
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

        Promotion coffee1 = new Promotion("desc" , "coffee", R.drawable.coffee);
        Promotion coffee2 = new Promotion("desc", "coffee", R.drawable.coffee2);
        Promotion hangout1 = new Promotion("desc", "Hang out", R.drawable.hangout);
        Promotion hangout2= new Promotion("desc" , "Hang out", R.drawable.hangout2);
        Promotion eatout1 = new Promotion("desc", "Eat out" , R.drawable.eatout);
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

        String lobbyActivity = lobby.getActivity();
        switch(lobbyActivity){
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
