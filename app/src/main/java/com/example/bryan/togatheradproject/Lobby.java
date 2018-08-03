package com.example.bryan.togatheradproject;

public class Lobby {

    private String lobbyID;
    private int capacity;
    private String lobbyDescriptions;
    private String hostID;
    private String activity;
    private String guestID[];

    public Lobby(String lobbyID, int capacity, String lobbyDescriptions, String hostID, String activity) {
        this.lobbyID = lobbyID;
        this.capacity = capacity;
        this.lobbyDescriptions = lobbyDescriptions;
        this.hostID = hostID;
        this.activity = activity;
    }

    public Lobby(){

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
}
