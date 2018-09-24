package com.example.bryan.togatheradproject;

import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class Chat {

    private static final String TAG = "Chat";
    private String username;
    private String chatMessage;
    private String time;
    private int colorindex;

    public Chat(String username, String chatMessage, String time) {
        this.username = username;
        this.chatMessage = chatMessage;
        this.time = time;
    }

    public Chat(String username, String chatMessage, String time, int colorindex) {
        this.username = username;
        this.chatMessage = chatMessage;
        this.time = time;
        this.colorindex = colorindex;
    }



    public Chat() {
    }

    public int getColorindex() {
        return colorindex;
    }

    public void setColorindex(int colorindex) {
        this.colorindex = colorindex;
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

    protected Chat rehostChat(User user){
        String chatMessage = " is the new host";
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

    protected Chat entryChat(User user) {
        String chatMessage = " entered the room";
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

    protected Chat leaveEntryChat(User user) {
        String chatMessage = " left the room";
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

    protected Chat createEntryChat(User user) {
        String chatMessage = " created the room";
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
        Date date = new Date();
        String formattedInput = ": " + input;
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(date);
        int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);
        String stringHour = Integer.toString(hour);
        String stringMinute = Integer.toString(minute);
        String stringTime = stringHour + ":" + stringMinute;
        String username = user.getUserName();
        Chat chat = new Chat(username, formattedInput, stringTime);
        chat.setColorindex(user.getIndex());
        return chat;
    }

    protected void updateChat(ArrayList<Chat> chatlog, String lobbyID, String chatlogID) {
        Chatlog newChatlog = new Chatlog(chatlog);
        Log.d(TAG, "chatloglist: " + newChatlog);
        FirebaseFirestore.getInstance()
                .collection(Constants.LOBBY)
                .document(lobbyID)
                .collection(Constants.LOBBY_CHATLOG)
                .document(chatlogID)
                .set(newChatlog, SetOptions.merge())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "updated");
                    }
                });
    }
}
