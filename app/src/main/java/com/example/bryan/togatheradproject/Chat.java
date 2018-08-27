package com.example.bryan.togatheradproject;

import java.sql.Time;

public class Chat {
    private String username;
    private String chatMessage;
    private Time time;

    public Chat(String username, String chatMessage, Time time) {
        this.username = username;
        this.chatMessage = chatMessage;
        this.time = time;
    }

    public Chat() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getChatMessage() {
        return chatMessage;
    }

    public void setChatMessage(String chatMessage) {
        this.chatMessage = chatMessage;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }
}
