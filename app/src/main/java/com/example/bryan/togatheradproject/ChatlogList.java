package com.example.bryan.togatheradproject;

import android.app.Activity;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;


public class ChatlogList extends ArrayAdapter {
    private android.app.Activity context;
    private ArrayList<Chat> chatLogList;

    public ChatlogList(Activity context, ArrayList<Chat> chatLogList) {
        super(context, R.layout.chatlog_list_layout, chatLogList);
        this.context = context;
        this.chatLogList = chatLogList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.chatlog_list_layout, null, true);
        TextView textView_username = listViewItem.findViewById(R.id.textView_ChatlogListLayout_username);
        TextView textView_chatmessage = listViewItem.findViewById(R.id.textView_ChatlogListLayout_chatmessage);
        TextView textView_time = listViewItem.findViewById(R.id.textView_ChatlogListLayout_date);

        Chat chat = chatLogList.get(position);
        textView_username.setText(chat.getUsername());
        textView_chatmessage.setText(chat.getChatMessage());
        textView_time.setText(chat.getTime());
        //get index and assign color
        int color = getColor(chat.getColorindex());
        switch (color) {
            case 0:
                textView_username.setTextColor(Color.parseColor("#000000"));
                textView_chatmessage.setTextColor(Color.parseColor("#000000"));
                break;
            case 1:
                textView_username.setTextColor(Color.parseColor("#44BFC7"));
                break;
            case 2:
                textView_username.setTextColor(Color.parseColor("#FFC206"));
                break;
            case 3:
                textView_username.setTextColor(Color.parseColor("#F63E4C"));
                break;
            case 4:
                textView_username.setTextColor(Color.parseColor("#D496BB"));
                break;
            case 5:
                textView_username.setTextColor(Color.parseColor("#659ACC"));
                break;
            case 6:
                textView_username.setTextColor(Color.parseColor("#14CE13"));
                break;
            case 7:
                textView_username.setTextColor(Color.parseColor("#FF7E2A"));
                break;
            case 8:
                textView_username.setTextColor(Color.parseColor("#E88486"));
                break;
            case 9:
                textView_username.setTextColor(Color.parseColor("#7646FE"));
                break;
            case 10:
                textView_username.setTextColor(Color.parseColor("#20CDF7"));
                break;
            case 11:
                textView_username.setTextColor(Color.parseColor("#67B968"));
                break;
            case 12:
                textView_username.setTextColor(Color.parseColor("#D5A889"));
                break;
            case 13:
                textView_username.setTextColor(Color.parseColor("#FF5CA1"));
                break;
            case 14:
                textView_username.setTextColor(Color.parseColor("#A795C9"));
                break;
            case 15:
                textView_username.setTextColor(Color.parseColor("#1375D3"));
        }

        return listViewItem;
    }

    private int getColor(int colorindex) {

        return colorindex;
    }
}
