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
import java.util.List;

public class GuestList extends ArrayAdapter {
    private android.app.Activity context;
    private List<User> guestList;

    public GuestList(Activity context, ArrayList<User> guestList) {
        super(context, R.layout.guest_list_layout, guestList);
        this.context = context;
        this.guestList = guestList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.guest_list_layout, null, true);
        TextView textView_userName = listViewItem.findViewById(R.id.textView_GuestList_userName);

        User user = guestList.get(position);
        textView_userName.setText(user.getUserName().toString());

        return listViewItem;
    }
}
