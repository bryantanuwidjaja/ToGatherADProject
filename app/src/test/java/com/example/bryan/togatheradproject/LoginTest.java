package com.example.bryan.togatheradproject;

import android.util.Log;

import junit.framework.Assert;

import org.junit.Test;

public class LoginTest {

    LoginActivity check = new LoginActivity();
    private String email = "sukiliong@yahoo.com";
    private String password = "123123";
    private String blank = "";

    @Test
    public void registerWithCorrectInformation(){
        Assert.assertEquals(check.checkIfDataNotBlank(email, password), true);
    }

    @Test
    public void registerWithBlankEmail(){
        Assert.assertEquals(check.checkIfDataNotBlank(blank, password), false);
    }

    @Test
    public void registerWithBlankPassword(){
        Assert.assertEquals(check.checkIfDataNotBlank(email, blank), false);
    }
}
