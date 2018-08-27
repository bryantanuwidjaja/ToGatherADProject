package com.example.bryan.togatheradproject;

import android.app.Activity;
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

        return listViewItem;
    }
}
