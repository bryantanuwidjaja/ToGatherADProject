package com.example.bryan.togatheradproject;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import com.google.firebase.auth.FirebaseAuthException;



public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    Button button_SignIn;
    Button button_SignUp;
    ImageView imageView_Image;
    EditText editText_InsertEmail;
    EditText editText_InsertPassword;
    TextView textView_Email;
    TextView textView_Password;

    private FirebaseAuth mAuth;

    private void Login(String email, String password) {
        Log.d(TAG, "Login: in");
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Log.d(TAG, "onSuccess: in");
                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                        Toast.makeText(getApplicationContext(), "Login successful", Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG, "onFailure: Could not sign in user" + e);
                Toast.makeText(getApplicationContext(), "Login failed", Toast.LENGTH_SHORT).show();
            }
        });
        Log.d(TAG, "Login: out");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Log.d(TAG, "onCreate: in");

        mAuth = FirebaseAuth.getInstance();
        button_SignIn = findViewById(R.id.button_LoginActivity_signIn);
        button_SignUp = findViewById(R.id.button_LoginActivity_signUp);
        imageView_Image = findViewById(R.id.imageView_LoginActivity_image);
        editText_InsertEmail = findViewById(R.id.editText_LoginActivity_insertEmail);
        editText_InsertPassword = findViewById(R.id.editText_LoginActivity_insertPassword);
        textView_Email = findViewById(R.id.textView_LoginActivity_email);
        textView_Password = findViewById(R.id.textView_LoginActivity_password);


        button_SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: sign in - in ");
                String email = editText_InsertEmail.getText().toString();
                String password = editText_InsertPassword.getText().toString();
                Log.d(TAG, "onClick: sign in - before login");
                Login(email, password);
            }
        });

        button_SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: signup - in");
                Intent intent = new Intent(getApplicationContext(), RegistrationActivity.class);
                Log.d(TAG, "onClick: sign up - before intent");
                startActivity(intent);
            }
        });
        Log.d(TAG, "onCreate: out");
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }
}