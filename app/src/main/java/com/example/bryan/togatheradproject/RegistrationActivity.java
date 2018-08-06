package com.example.bryan.togatheradproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class RegistrationActivity extends AppCompatActivity {

    private EditText editText_Email;
    private EditText editText_Username;
    private EditText editText_Password;
    private EditText editText_RePassword;
    private TextView textView_Email;
    private TextView textView_Username;
    private TextView textView_Password;
    private TextView textView_RePassword;
    private Button button_Create;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
    }

    public void createButtonClick(View view){
        //setting the edittext
        editText_Email = findViewById(R.id.editText_RegistrationActivity_email);
        editText_Username = findViewById(R.id.editText_RegistrationActivity_username);
        editText_Password = findViewById(R.id.editText_RegistrationActivity_password);
        editText_RePassword = findViewById(R.id.editText_RegistrationActivity_rePassword);
        textView_Email = findViewById(R.id.textView_RegistrationActivity_email);
        textView_Username = findViewById(R.id.textView_RegistrationActivity_username);
        textView_Password = findViewById(R.id.textView_RegistrationActivity_password);
        textView_RePassword = findViewById(R.id.textView_RegistrationActivity_rePassword);
        button_Create = findViewById(R.id.button_RegistrationActivity_create);



        //take information from edittext
        String regisEmail = editText_Email.getText().toString();
        String regisName = editText_Username.getText().toString();
        String regisPassword = editText_Password.getText().toString();
        String regisRePassword = editText_RePassword.getText().toString();

        if (regisPassword==regisRePassword){}
        else{}
    }
}
