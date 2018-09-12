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
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    Button button_SignIn;
    Button button_SignUp;
    ImageView imageView_Image;
    EditText editText_InsertEmail;
    EditText editText_InsertPassword;
    TextView textView_Container;

    private FirebaseAuth mAuth;
    private FirebaseFirestore firebaseFirestore;

    private void getUserID(String email, String password){
        FirebaseFirestore.getInstance().collection(Constants.USER)
                .whereEqualTo(Constants.USER_EMAIL , email)
                .whereEqualTo(Constants.PASSWORD, password)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                            User user = documentSnapshot.toObject(User.class);
                            String userID = user.getUserID();
                            Log.d(TAG, "userID - login : " + userID);
                            textView_Container.setText(userID);
                            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                            intent.putExtra(Constants.USER, user);
                            Log.d(TAG, "User: " + user.getUserID());
                            Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                            startActivity(intent);
                        }
                    }
                });
    }

    private void Login(final String email, final String password) {
        Log.d(TAG, "Login: in");
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Log.d(TAG, "onSuccess: login successful");
                        getUserID(email, password);
                        String userID = textView_Container.getText().toString();
                        Log.d(TAG,"logged user id : " + userID);
                        //Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                        Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                        //startActivity(intent);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG, "onFailure: Could not sign in user" + e);
                Toast.makeText(getApplicationContext(), "Login Failed", Toast.LENGTH_SHORT).show();
            }
        });
        Log.d(TAG, "Login: out");
    }

    private String whyError = "";

    protected boolean checkIfDataNotBlank(String username,String password){
        boolean result = true;
        if (username.equals("") || password.equals("")) {
            whyError = "Please fill all of the fields properly ";
            result = false;
        }
        return result;
    }

    protected void clearEditTest(){
        editText_InsertEmail.setText("");
        editText_InsertPassword.setText("");
    }

    protected void establish(){
        mAuth = FirebaseAuth.getInstance();
        button_SignIn = findViewById(R.id.button_LoginActivity_signIn);
        button_SignUp = findViewById(R.id.button_LoginActivity_signUp);
        imageView_Image = findViewById(R.id.imageView_LoginActivity_image);
        editText_InsertEmail = findViewById(R.id.editText_LoginActivity_insertEmail);
        editText_InsertPassword = findViewById(R.id.editText_LoginActivity_insertPassword);
        textView_Container = findViewById(R.id.textView_LoginActivity_container);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Log.d(TAG, "onCreate: in");

        establish();

        button_SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                Log.d(TAG, "onClick: sign in - in ");
                String email = editText_InsertEmail.getText().toString();
                String password = editText_InsertPassword.getText().toString();
                Log.d(TAG, "onClick: sign in - before login");
                if (checkIfDataNotBlank(email,password)){
                Login(email, password);
                button_SignIn.invalidate();
                }
                else{
                        clearEditTest();
                        Toast.makeText(LoginActivity.this, whyError, Toast.LENGTH_SHORT).show();
                        whyError = "";
                    }}
                catch (Exception e){
                    Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        button_SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: sign up - in");
                Intent intent = new Intent(getApplicationContext(), RegistrationActivity.class);
                Log.d(TAG, "onClick: sign up - before intent");
                button_SignUp.invalidate();
                startActivity(intent);
            }
        });
        Log.d(TAG, "onCreate: out");
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            mAuth.signOut();
            Log.d(TAG, "onStart: signed out");
        } else {
            Log.d(TAG, "onStart: current user = " + currentUser);
        }
    }
}