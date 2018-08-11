package com.example.bryan.togatheradproject;

import android.content.Intent;

import android.support.annotation.NonNull;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class RegistrationActivity extends AppCompatActivity {

    private static final String TAG = "RegistrationActivity";
    public static final String USER_ID = "userID";

    private EditText editText_Email;
    private EditText editText_Username;
    private EditText editText_Password;
    private EditText editText_RePassword;
    private TextView textView_Email;
    private TextView textView_Username;
    private TextView textView_Password;
    private TextView textView_RePassword;
    private Button button_Create;
    private Button button_Cancel;

    private char[] emailChar = {'@', '.'};
    private FirebaseAuth mAuth;
    private FirebaseAuthInvalidCredentialsException firebaseAuthInvalidCredentialsException;

    private String checkEmail(String email, char[] emailChar) {
        while (true) {
            for (int i = 0; i < emailChar.length; i++) {
                String currentChar = Character.toString(emailChar[i]);
                if (!email.contains(currentChar)) {
                    clearEditText();
                    Toast.makeText(RegistrationActivity.this, "Invalid Email Address",
                            Toast.LENGTH_SHORT).show();
                    email = "";
                    return email;
                } else {
                    return email;
                }
            }
        }
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        } else {
            clearEditText();
        }
    }

    private void clearEditText() {
        editText_Email.setText("");
        editText_Password.setText("");
        editText_RePassword.setText("");
        editText_Username.setText("");
    }

    private void updateUserDatabase(String regisPassword, String regisRePassword, String regisEmail, String regisName ){
        if (regisPassword.equals(regisRePassword) && regisPassword.length() >= 6 && !regisEmail.equals("")) {
            Log.d(TAG, "onClick: IF - in ");
            final User user = new User(regisPassword, regisName, regisEmail, 0, null);
            //createUser(regisEmail, regisPassword);
            FirebaseFirestore.getInstance().collection("user")
                    .add(user)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            String regisEmail = editText_Email.getText().toString();
                            String regisPassword = editText_Password.getText().toString();
                            String userID = documentReference.getId();
                            createUser(regisEmail, regisPassword);
                            Intent intent = new Intent(getApplication(), InterestActivity.class);
                            Log.d(TAG, "onSuccess: creation success");
                            Toast.makeText(getApplicationContext(), "Account creation successful", Toast.LENGTH_SHORT).show();
                            intent.putExtra(USER_ID, userID);
                            startActivity(intent);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            clearEditText();
                            Log.e(TAG, "onFailure: Account creation failed, please retry again" + e);
                        }
                    });
        } else if (regisPassword.length() < 6) {
            clearEditText();
            Toast.makeText(getApplicationContext(), "Password needs to be more than 6 characters", Toast.LENGTH_SHORT).show();
        } else if (!regisPassword.equals(regisRePassword)) {
            clearEditText();
            Toast.makeText(getApplicationContext(), "Passwords are not the same", Toast.LENGTH_SHORT).show();
        } else if (regisName.length() <= 4) {
            clearEditText();
            Toast.makeText(getApplicationContext(), "Username must be longer than 3 characters", Toast.LENGTH_SHORT).show();
        } else if (regisEmail.equals("") && regisName.equals("") && regisPassword.equals("") && regisRePassword.equals("")) {
            clearEditText();
            Toast.makeText(getApplicationContext(), "Please fill all of the fields", Toast.LENGTH_SHORT).show();
        }
    }

    private void createUser(String regisEmail, String regisPassword) {
        Log.d(TAG, "regisEmail :" + regisEmail);
        Log.d(TAG, "regisPassword: " + regisPassword);
        mAuth.createUserWithEmailAndPassword(regisEmail, regisPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Log.d(TAG, "createUserWithEmail: success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    updateUI(user);
                                } else {
                                    Log.w(TAG, "createUserWithEmail: failure", task.getException());
                                    Toast.makeText(RegistrationActivity.this, "Account creation failed",
                                            Toast.LENGTH_SHORT).show();
                                    updateUI(null);
                                }
                            }
                        }
                );
    }


//    @Override
//    protected void onStart() {
//        super.onStart();
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        updateUI(currentUser);
//   }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        Log.d(TAG, "onCreate: in " + TAG);

        mAuth = FirebaseAuth.getInstance();

        editText_Email = findViewById(R.id.editText_RegistrationActivity_email);
        editText_Username = findViewById(R.id.editText_RegistrationActivity_username);
        editText_Password = findViewById(R.id.editText_RegistrationActivity_password);
        editText_RePassword = findViewById(R.id.editText_RegistrationActivity_rePassword);
        textView_Email = findViewById(R.id.textView_RegistrationActivity_email);
        textView_Username = findViewById(R.id.textView_RegistrationActivity_username);
        textView_Password = findViewById(R.id.textView_RegistrationActivity_password);
        textView_RePassword = findViewById(R.id.textView_RegistrationActivity_rePassword);
        button_Create = findViewById(R.id.button_RegistrationActivity_create);
        button_Cancel = findViewById(R.id.button_RegistrationActivity_cancel);


        button_Create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: create button - in ");
                String regisEmail = editText_Email.getText().toString();
                String regisName = editText_Username.getText().toString();
                String regisPassword = editText_Password.getText().toString();
                String regisRePassword = editText_RePassword.getText().toString();

                regisEmail = checkEmail(regisEmail, emailChar);

//                Log.d(TAG, "regisEmail: " + regisEmail);
//                Log.d(TAG, "regisName: " + regisName);
//                Log.d(TAG, "regisPassword: " + regisPassword);
//                Log.d(TAG, "regisRePassword: " + regisRePassword);

                updateUserDatabase(regisPassword,regisRePassword,regisEmail,regisName);

//                if (regisPassword.equals(regisRePassword) && regisPassword.length() >= 6 && !regisEmail.equals("")) {
//                    Log.d(TAG, "onClick: IF - in ");
//                    User user = new User(regisPassword, regisName, regisEmail, 0, null);
//                    //createUser(regisEmail, regisPassword);
//
//                    FirebaseFirestore.getInstance().collection("user")
//                            .add(user)
//                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                                @Override
//                                public void onSuccess(DocumentReference documentReference) {
//                                    String regisEmail = editText_Email.getText().toString();
//                                    String regisPassword = editText_Password.getText().toString();
//                                    String userID = documentReference.getId();
//                                    createUser(regisEmail, regisPassword);
//                                    Intent intent = new Intent(getApplication(), LoginActivity.class);
//                                    Log.d(TAG, "onSuccess: creation success");
//                                    Toast.makeText(getApplicationContext(), "Account creation successful", Toast.LENGTH_SHORT).show();
//                                    startActivity(intent);
//                                }
//                            })
//                            .addOnFailureListener(new OnFailureListener() {
//                                @Override
//                                public void onFailure(@NonNull Exception e) {
//                                    Log.e(TAG, "onFailure: Account creation failed, please retry again" + e);
//                                }
//                            });
//                } else if (regisPassword.length() < 6) {
//                    clearEditText();
//                    Toast.makeText(getApplicationContext(), "Password needs to be more than 6 characters", Toast.LENGTH_SHORT).show();
//                } else if (!regisPassword.equals(regisRePassword)) {
//                    clearEditText();
//                    Toast.makeText(getApplicationContext(), "Passwords are not the same", Toast.LENGTH_SHORT).show();
//                } else if (regisName.length() <= 4) {
//                    clearEditText();
//                    Toast.makeText(getApplicationContext(), "Username must be longer than 3 characters", Toast.LENGTH_SHORT).show();
//                } else if (regisEmail.equals("") && regisName.equals("") && regisPassword.equals("") && regisRePassword.equals("")) {
//                    clearEditText();
//                    Toast.makeText(getApplicationContext(), "Please fill all of the fields", Toast.LENGTH_SHORT).show();
//                }

                Log.d(TAG, "onClick: create button - out");
            }
        });
        button_Cancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Toast.makeText(getApplicationContext(), "Registration cancelled", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent (getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
        Log.d(TAG, "onCreate: out " + TAG);
    }
}