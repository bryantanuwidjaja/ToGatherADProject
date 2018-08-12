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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistrationActivity extends AppCompatActivity {

    private static final String TAG = "RegistrationActivity";

    private EditText editText_Email;
    private EditText editText_Username;
    private EditText editText_Password;
    private EditText editText_RePassword;
    private TextView textView_Email;
    private TextView textView_Username;
    private TextView textView_Password;
    private TextView textView_RePassword;
    private Button button_Create;

    private FirebaseAuth mAuth;

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

    protected void createUser(String regisEmail, String regisPassword) {
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

    private Button button_Cancel;
    private String whyError = "";

    public boolean checkIfPasswordSame(String password1, String password2){
        boolean result = true;
        if (!password1.equals(password2)){
            whyError += "Passwords are not the same ";
            result = false;
        }
        return result;
    }

    public boolean checkIfPasswordValid(String password) {
        boolean result = true;
        if (password.length() < 6) {
            whyError = "Password needs to be more than 6 characters ";
            result = false;
        }
        return result;
    }

    public boolean checkUserValidity(String username){
        boolean result = true;
        if (username.length()<4){
            whyError = "Username must be 4 or more characters ";
            result = false;
        }
        return result;
    }

    public boolean checkEmailValidity(String email) {
        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        else {
            whyError = "invalid email ";
        }
        return isValid;
    }

    public boolean checkIfDataNotBlank (String email, String name, String password, String rePassword){
        boolean result = true;
        if (email.equals("") || name.equals("") || password.equals("") || rePassword.equals("")) {
            whyError = "Please fill all of the fields properly ";
            result = false;
        }
        return result;
    }


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

                Log.d(TAG, "regisEmail: " + regisEmail);
                Log.d(TAG, "regisName: " + regisName);
                Log.d(TAG, "regisPassword: " + regisPassword);
                Log.d(TAG, "regisRePassword: " + regisRePassword);

                if ( checkIfDataNotBlank(regisEmail,regisName,regisPassword,regisRePassword)
                        && checkEmailValidity(regisEmail)
                        && checkIfPasswordSame(regisPassword, regisRePassword)
                        && checkIfPasswordValid(regisPassword)
                        && checkUserValidity(regisName)){
                    Log.d(TAG, "onClick: IF - in ");
                    User user = new User(regisPassword, regisName, regisEmail, 0, null);
                    //createUser(regisEmail, regisPassword);

                    FirebaseFirestore.getInstance().collection("user")
                            .add(user)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    String regisEmail = editText_Email.getText().toString();
                                    String regisPassword = editText_Password.getText().toString();
                                    createUser(regisEmail, regisPassword);
                                    Intent intent = new Intent(getApplication(), LoginActivity.class);
                                    Log.d(TAG, "onSuccess: creation success");
                                    Toast.makeText(getApplicationContext(), "Account creation successful", Toast.LENGTH_SHORT).show();
                                    startActivity(intent);
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.e(TAG, "onFailure: Account creation failed, please retry again" + e);
                                }
                            });
                }else {
                    clearEditText();
                    Toast.makeText(getApplicationContext(), whyError, Toast.LENGTH_SHORT).show();
                    whyError = "";
                }

                Log.d(TAG, "onClick: create button - out");
            }
        });
        button_Cancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent (getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
        Log.d(TAG, "onCreate: out " + TAG);
    }
}