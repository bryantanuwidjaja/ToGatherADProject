package com.example.bryan.togatheradproject;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
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

import junit.framework.Assert;

import org.junit.Test;

public class RegistrationTest {
    private static final String TAG = "RegistrationTest";
    private String expectedEmail = "dummy1@dummy.com";
    private String expectedUsername = "dummy1";
    private String expectedPassword = "nopassword";
    private String wrongPassword = "password";

    private FirebaseAuth mAuth;
    private FirebaseAuthInvalidCredentialsException firebaseAuthInvalidCredentialsException;

    @Test
    public void isUserRegistered(){
        User user = new User(expectedPassword, expectedUsername, expectedEmail, 0, null);
        RegistrationActivity isUserRegistered = new RegistrationActivity();
        isUserRegistered.createUser(expectedEmail, expectedPassword);
    }

    @Test
    public void registerWithWrongPassword(){
        RegistrationActivity checkPassword = new RegistrationActivity();
        Assert.assertEquals(checkPassword.checkIfPasswordSame(expectedPassword, wrongPassword), false);
    }

    @Test
    public void registerWithCorrectPassword(){
        RegistrationActivity checkPassword = new RegistrationActivity();
        Assert.assertEquals(checkPassword.checkIfPasswordSame(expectedPassword, expectedPassword), true);
    }

}
