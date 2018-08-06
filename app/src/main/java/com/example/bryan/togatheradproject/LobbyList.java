package com.example.bryan.togatheradproject;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class LobbyList extends ArrayAdapter<Lobby> {
    private Activity context;
    private List<Lobby> lobbyList;

    public LobbyList(Activity context, List<Lobby> lobbyList) {
        super(context, R.layout.lobby_list_layout, lobbyList);
        this.context = context;
        this.lobbyList = lobbyList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.lobby_list_layout, null, true);
        TextView textView_HostID = listViewItem.findViewById(R.id.textView_LobbyListLayout_hostID);
        TextView textView_Location = listViewItem.findViewById(R.id.textView_LobbyListLayout_location);
        TextView textView_Activity = listViewItem.findViewById(R.id.textView_LobbyListLayout_activity);
        Lobby lobby = lobbyList.get(position);

        return listViewItem;
    }
}
