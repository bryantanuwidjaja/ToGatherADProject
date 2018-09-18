package com.example.bryan.togatheradproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Button;
import android.widget.ListView;
import android.content.Intent;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.ArrayList;

public class PromotionActivity extends AppCompatActivity{
    private static final String TAG ="PromotionActivity";
    private TextView textView_promotion;
    private Button button_Return;
    private ListView listView_promotion;
    //private ArrayList<String> pathArray;

//    private StorageReference mStorageRef;
//    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promotion);

        textView_promotion = findViewById(R.id.textView_PromotionLayout_Promotion);
        button_Return = findViewById(R.id.button_PromotionActivity_Return);
        listView_promotion = findViewById(R.id.listView_PromotionActivity_promotionList);
//        pathArray = new ArrayList<>();
//        auth = FirebaseAuth.getInstance();
//
//        mStorageRef = FirebaseStorage.getInstance().getReference();
        checkFilePermissions();
        Intent intent = getIntent();
        final String promotionID = intent.getStringExtra(Constants.PROMOTION_ID);
        final Promotion promotionName = (Promotion) intent.getSerializableExtra(Constants.PROMOTION_NAME);
        final Promotion promotionDesc = (Promotion) intent.getSerializableExtra(Constants.PROMOTION_DESCRIPTION);




        button_Return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LobbyActivity.class);
                intent.putExtra(Constants.PROMOTION_NAME, (Serializable) promotionName);
                intent.putExtra(Constants.PROMOTION_ID, promotionID);
                intent.putExtra(Constants.PROMOTION_DESCRIPTION, (Serializable) promotionDesc);
                startActivity(intent);
            }
        });

        listView_promotion.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), PromotionActivity.class);
                intent.putExtra(Constants.PROMOTION_ID, promotionID);
                intent.putExtra(Constants.PROMOTION_DESCRIPTION, (Serializable) promotionDesc);
                intent.putExtra(Constants.PROMOTION_NAME, (Serializable) promotionName);
                startActivity(intent);
            }
        });

    }
    private void checkFilePermissions() {

    }

}
