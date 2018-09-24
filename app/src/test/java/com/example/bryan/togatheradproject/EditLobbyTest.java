package com.example.bryan.togatheradproject;

import junit.framework.Assert;

import org.junit.Test;

public class EditLobbyTest {
    EditLobbyActivity check = new EditLobbyActivity();
    private int correctCapacity = 10;
    private int wrongCapacity = 50;

    @Test
    public void registerWithCorrectInformation() {
        Assert.assertEquals(check.checkCorrectCapacity(correctCapacity), true);
    }

    @Test
    public void registerWithBlankDescription() {
        Assert.assertEquals(check.checkCorrectCapacity(wrongCapacity), false);
    }

}
