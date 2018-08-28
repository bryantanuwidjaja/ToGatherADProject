package com.example.bryan.togatheradproject;

import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Chat {

    private static final String TAG = "Chat";
    private String username;
    private String chatMessage;
    private String time;

    public Chat(String username, String chatMessage, String time) {
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    protected Chat entryChat(User user) {
        String chatMessage = user.getUserName() + " entered the room";
        Date date = new Date();
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(date);
        int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);
        String stringHour = Integer.toString(hour);
        String stringMinute = Integer.toString(minute);
        String stringTime = stringHour + ":" + stringMinute;
        String username = user.getUserName();
        Chat chat = new Chat(username, chatMessage, stringTime);
        return chat;
    }

    protected Chat inputChat(User user, String input) {
        String chatMessage = user.getUserName() + ": " + input;
        Date date = new Date();
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(date);
        int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);
        String stringHour = Integer.toString(hour);
        String stringMinute = Integer.toString(minute);
        String stringTime = stringHour + ":" + stringMinute;
        String username = user.getUserName();
        Chat chat = new Chat(username, chatMessage, stringTime);
        return chat;
    }

    protected void updateChat(Chat chat, String lobbyID) {
        FirebaseFirestore.getInstance()
                .collection(Constants.LOBBY)
                .document(lobbyID)
                .collection(Constants.LOBBY_CHATLOG)
                .add(chat)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "onSuccess: updated");
                    }
                });
    }
}
