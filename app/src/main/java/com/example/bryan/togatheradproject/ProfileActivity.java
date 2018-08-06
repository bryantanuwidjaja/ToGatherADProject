package com.example.bryan.togatheradproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {

    ImageView imageView_Image;
    TextView textView_Username;
    TextView textView_Interest;
    TextView textView_Interest1;
    TextView textView_Interest2;
    TextView textView_Interest3;
    TextView textView_Interest4;
    TextView textView_Interest5;
    TextView textView_Interest6;
    TextView textView_Interest7;
    TextView textView_Interest8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        imageView_Image = findViewById(R.id.imageView_ProfileScreen_image);
        textView_Username = findViewById(R.id.textView_ProfileScreen_username);
        textView_Interest = findViewById(R.id.textView_ProfileScreen_interest);
        textView_Interest1 = findViewById(R.id.textView_ProfileScreen_interest1);
        textView_Interest2 = findViewById(R.id.textView_ProfileScreen_interest2);
        textView_Interest3 = findViewById(R.id.textView_ProfileScreen_interest3);
        textView_Interest4 = findViewById(R.id.textView_ProfileScreen_interest4);
        textView_Interest5 = findViewById(R.id.textView_ProfileScreen_interest5);
        textView_Interest6 = findViewById(R.id.textView_ProfileScreen_interest6);
        textView_Interest7 = findViewById(R.id.textView_ProfileScreen_interest7);
        textView_Interest8 = findViewById(R.id.textView_ProfileScreen_interest8);

    }
}
