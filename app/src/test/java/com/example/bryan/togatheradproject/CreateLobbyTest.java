package com.example.bryan.togatheradproject;

import junit.framework.Assert;

import org.junit.Test;

public class CreateLobbyTest {

    CreateLobbyActivity check = new CreateLobbyActivity();
    private String capacity = "10";
    private int correctCapacity = 10;
    private int wrongCapacity = 20;
    private String description = "coffee together maybe";
    private String location = "Blk 30 jalan ABC";
    private String blank = "";


    @Test
    public void registerWithCorrectInformation(){
        Assert.assertEquals(check.checkIfDataNotBlank(capacity, description), true);
    }

    @Test
    public void registerWithBlankCapacity(){
        Assert.assertEquals(check.checkIfDataNotBlank(blank, description), false);
    }

    @Test
    public void registerWithCorrectCapacity() {
        Assert.assertEquals(check.checkCorrectCapacity(correctCapacity), true);
    }

    @Test
    public void registerWithFalseCapacity() {
        Assert.assertEquals(check.checkCorrectCapacity(wrongCapacity), false);
    }

    @Test
    public void registerWithBlankDescription(){
        Assert.assertEquals(check.checkIfDataNotBlank(capacity, blank), false);
    }

    @Test
    public void registerWithBlankInformation() {
        Assert.assertEquals(check.checkIfDataNotBlank(blank, blank), false);
    }

    @Test
    public void registerWithLocation() {
        Assert.assertEquals(check.checkIfLocationisThere(location), true);
    }

    @Test
    public void registerWithBlankLocation() {
        Assert.assertEquals(check.checkIfLocationisThere(blank), false);
    }


}
