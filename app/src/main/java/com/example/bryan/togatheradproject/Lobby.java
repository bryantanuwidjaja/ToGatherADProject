package com.example.bryan.togatheradproject;

public class Lobby {

    private String lobbyID;
    private String hostID;
    private int capacity;
    private String location;
    private String lobbyDescriptions;
    private String activity;

    public Lobby(String lobbyID, String hostID, int capacity, String location, String lobbyDescriptions, String activity) {
        this.lobbyID = lobbyID;
        this.hostID = hostID;
        this.capacity = capacity;
        this.location = location;
        this.lobbyDescriptions = lobbyDescriptions;
        this.activity = activity;
    }

    public Lobby(){

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
}
