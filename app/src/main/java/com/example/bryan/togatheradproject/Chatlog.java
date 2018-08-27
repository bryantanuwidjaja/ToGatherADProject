package com.example.bryan.togatheradproject;

import java.io.Serializable;
import java.util.ArrayList;

public class Chatlog implements Serializable {
    private ArrayList<Chat> chatlog;

    public Chatlog() {

    }


    public Chatlog(ArrayList<Chat> chatlog) {
        this.chatlog = chatlog;
    }

    public ArrayList<Chat> getChatlog() {
        return chatlog;
    }

    public void setChatlog(ArrayList<Chat> chatlog) {
        this.chatlog = chatlog;
    }
}
