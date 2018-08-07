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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        Log.d(TAG, "onCreate: in " + TAG);

        editText_Email = findViewById(R.id.editText_RegistrationActivity_email);
        editText_Username = findViewById(R.id.editText_RegistrationActivity_username);
        editText_Password = findViewById(R.id.editText_RegistrationActivity_password);
        editText_RePassword = findViewById(R.id.editText_RegistrationActivity_rePassword);
        textView_Email = findViewById(R.id.textView_RegistrationActivity_email);
        textView_Username = findViewById(R.id.textView_RegistrationActivity_username);
        textView_Password = findViewById(R.id.textView_RegistrationActivity_password);
        textView_RePassword = findViewById(R.id.textView_RegistrationActivity_rePassword);
        button_Create = findViewById(R.id.button_RegistrationActivity_create);

        button_Create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: create button - in ");
                String regisEmail = editText_Email.getText().toString();
                String regisName = editText_Username.getText().toString();
                String regisPassword = editText_Password.getText().toString();
                String regisRePassword = editText_RePassword.getText().toString();


                if (regisPassword.equals(regisRePassword)) {
                    Log.d(TAG, "onClick: IF - in ");
                    User user = new User(regisPassword, regisName, regisEmail, 0, null);
                    FirebaseFirestore.getInstance().collection("user")
                            .add(user)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Intent intent = new Intent(getApplication(), LoginActivity.class);
                                    startActivity(intent);
                                    Log.d(TAG, "onSuccess: creation success");
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.e(TAG, "onFailure: Account creation failed, please retry again" + e);
                                }
                            });
                    Toast.makeText(getApplicationContext(), "Account creation successful", Toast.LENGTH_SHORT).show();
                }
                Log.d(TAG, "onClick: create button - out");
            }
        });
        Log.d(TAG, "onCreate: out " + TAG);
    }
}
