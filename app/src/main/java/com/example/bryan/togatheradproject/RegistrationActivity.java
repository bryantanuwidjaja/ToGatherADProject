package com.example.bryan.togatheradproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class RegistrationActivity extends AppCompatActivity {

    private EditText editTextEmail;
    private EditText editTextUsername;
    private EditText editTextPassword;
    private EditText editTextRePassword;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
    }

    public void createButtonClick(View view){
        //setting the edittext
        editTextEmail = (EditText) findViewById(R.id.editText_RegistrationActivity_Email);
        editTextUsername = (EditText) findViewById(R.id.editText_RegistrationActivity_Username);
        editTextPassword = (EditText) findViewById(R.id.editText_RegistrationActivity_Password);
        editTextRePassword = (EditText) findViewById(R.id.editText_RegistrationActivity_RePassword);


        //take information from edittext
        String regisEmail = editTextEmail.getText().toString();
        String regisName = editTextUsername.getText().toString();
        String regisPassword = editTextPassword.getText().toString();
        String regisRePassword = editTextRePassword.getText().toString();

        if (regisPassword==regisRePassword){}
        else{}
    }
}
