package com.example.bryan.togatheradproject;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;

import junit.framework.Assert;

import org.junit.Test;

public class RegistrationTest {
    private String expectedEmail = "dummy1@dummy.com";
    private String expectedUsername = "dummy1";
    private String expectedPassword = "nopassword";
    private String wrongPassword = "password";

    private FirebaseAuth mAuth;
    private FirebaseAuthInvalidCredentialsException firebaseAuthInvalidCredentialsException;

    @Test
    public void isUserRegistered(){
//        mAuth.createUserWithEmailAndPassword(expectedEmail, expectedPassword);
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
