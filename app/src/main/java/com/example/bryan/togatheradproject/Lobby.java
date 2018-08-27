package com.example.bryan.togatheradproject;

import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Lobby implements Serializable {

    private String lobbyID;
    private String hostID;
    private int capacity;
    private String location;
    private String lobbyDescriptions;
    private String activity;
    private ArrayList<User> guestList;

    public Lobby(String lobbyID, String hostID, int capacity, String location, String lobbyDescriptions, String activity, ArrayList<User> guestList) {
        this.lobbyID = lobbyID;
        this.hostID = hostID;
        this.capacity = capacity;
        this.location = location;
        this.lobbyDescriptions = lobbyDescriptions;
        this.activity = activity;
        this.guestList = guestList;
    }

    public Lobby(){

    }

    public ArrayList<User> getGuestList() {
        return guestList;
    }

    public void setGuestList(ArrayList<User> guestList) {
        this.guestList = guestList;
    }

    public String getLocation() {
        return location;
    }

    public void getLocation(String location) {
        this.location = location;
    }

    public String getLobbyID() {
        return lobbyID;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getLobbyDescriptions() {
        return lobbyDescriptions;
    }

    public String getHostID() {
        return hostID;
    }

    public String getActivity() {
        return activity;
    }

    public void setLobbyID(String lobbyID) {
        this.lobbyID = lobbyID;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setLobbyDescriptions(String lobbyDescriptions) {
        this.lobbyDescriptions = lobbyDescriptions;
    }

    public void setHostID(String hostID) {
        this.hostID = hostID;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void addGuest(User user) {
        guestList.add(user);
    }
}
