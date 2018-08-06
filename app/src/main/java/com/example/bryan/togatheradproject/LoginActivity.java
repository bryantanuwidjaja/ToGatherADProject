package com.example.bryan.togatheradproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    Button button_SignIn;
    Button button_SignUp;
    ImageView imageView_Image;
    EditText editText_InsertEmail;
    EditText editText_InsertPassword;
    TextView textView_Email;
    TextView textView_Password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        button_SignIn = findViewById(R.id.button_LoginActivity_signIn);
        button_SignUp = findViewById(R.id.button_LoginActivity_signUp);
        imageView_Image = findViewById(R.id.imageView_LoginActivity_image);
        editText_InsertEmail = findViewById(R.id.editText_LoginActivity_insertEmail);
        editText_InsertPassword = findViewById(R.id.editText_LoginActivity_insertPassword);
        textView_Email = findViewById(R.id.textView_LoginActivity_email);
        textView_Password = findViewById(R.id.textView_LoginActivity_password);

    }
}
