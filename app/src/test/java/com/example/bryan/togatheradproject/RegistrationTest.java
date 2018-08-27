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
    private String invalidEmail = "dummy@@a..com";
    private String expectedUsername = "dummy1";
    private String invalidUsername = "dum";
    private String expectedPassword = "nopassword";
    private String wrongPassword = "password";
    private String shortPassword = "pass";


    RegistrationActivity check = new RegistrationActivity();

    private FirebaseAuth mAuth;
    private FirebaseAuthInvalidCredentialsException firebaseAuthInvalidCredentialsException;

//    @Test
//    public void isUserRegistered(){
//        User user = new User(expectedPassword, expectedUsername, expectedEmail, 0, null);
//        RegistrationActivity isUserRegistered = new RegistrationActivity();
//        isUserRegistered.createUser(expectedEmail, expectedPassword);
//    }

    @Test
    public void registerWithWrongPassword(){
        Assert.assertEquals(check.checkIfPasswordSame(expectedPassword, wrongPassword), false);
    }

    @Test
    public void registerWithCorrectPassword(){
        Assert.assertEquals(check.checkIfPasswordSame(expectedPassword, expectedPassword), true);
    }

    @Test
    public void registerWithInvalidPassword(){
        Assert.assertEquals(check.checkIfPasswordValid(shortPassword), false);

    }

    @Test
    public void registerWithValidPassword(){
        Assert.assertEquals(check.checkIfPasswordValid(expectedPassword), true);
    }

    @Test
    public void registerWithInvalidEmail(){
        Assert.assertEquals(check.checkEmailValidity(invalidEmail), false);
    }

    @Test
    public void registerWithValidEmail(){
        Assert.assertEquals(check.checkEmailValidity(expectedEmail), true);
    }

    @Test
    public void registerWithInvalidUsername(){
        Assert.assertEquals(check.checkUserValidity(invalidUsername), false);
    }

    @Test
    public void registerWithValidUsername(){
        Assert.assertEquals(check.checkUserValidity(expectedUsername), true);
    }

    @Test
    public void registerWithCorrectInformation(){
        Assert.assertEquals(check.checkIfDataNotBlank(expectedEmail, expectedUsername, expectedPassword, expectedPassword), true);
    }
}
