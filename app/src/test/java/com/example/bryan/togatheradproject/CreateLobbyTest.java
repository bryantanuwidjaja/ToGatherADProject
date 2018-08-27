package com.example.bryan.togatheradproject;

import junit.framework.Assert;

import org.junit.Test;

public class CreateLobbyTest {

    CreateLobbyActivity check = new CreateLobbyActivity();
    private String capacity = "10";
    private String description = "coffee together maybe";
    private String location = "Blk 30 jalan ABC";
    private String blank = "";


    @Test
    public void registerWithCorrectInformation(){
        Assert.assertEquals(check.checkIfDataNotBlank(capacity, description, location), true);
    }

    @Test
    public void registerWithBlankCapacity(){
        Assert.assertEquals(check.checkIfDataNotBlank(blank, description, location), false);
    }

    @Test
    public void registerWithBlankDescription(){
        Assert.assertEquals(check.checkIfDataNotBlank(capacity, blank, location), false);
    }
    @Test
    public void registerWithBlankLocation(){
        Assert.assertEquals(check.checkIfDataNotBlank(capacity, description, blank), false);
    }

    @Test
    public void registerWithBlankInformation(){
        Assert.assertEquals(check.checkIfDataNotBlank(blank, blank, blank), false);
    }



}
