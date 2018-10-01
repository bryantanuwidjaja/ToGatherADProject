package com.example.bryan.togatheradproject;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

<<<<<<< HEAD
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

import java.util.ArrayList;
=======
>>>>>>> parent of 50d4854... Merge branch 'master' of https://github.com/bryantanuwijaya/ToGatherADProject
import java.util.List;

public class LobbyList extends ArrayAdapter<Lobby> {
    private Activity context;
    private List<Lobby> lobbyList;
<<<<<<< HEAD
    private static final String TAG = "LobbyList";
    ListenerRegistration capacityListener;
=======
>>>>>>> parent of 50d4854... Merge branch 'master' of https://github.com/bryantanuwijaya/ToGatherADProject

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
        TextView textView_Capacity = listViewItem.findViewById(R.id.textView_LobbyListLayout_capacity);
        TextView textView_Location = listViewItem.findViewById(R.id.textView_LobbyListLayout_location);
        TextView textView_Activity = listViewItem.findViewById(R.id.textView_LobbyListLayout_activity);
        TextView textView_Descriptions = listViewItem.findViewById(R.id.textView_LobbyListLayout_descriptions);

        final Lobby lobby = lobbyList.get(position);

//        getCapacity(lobby, new CapacityCallback() {
//            @Override
//            public void onCallback(ArrayList<User> guestList) {
//                textView_currentCapa.setText(Integer.toString(guestList.size()+1));
//            }
//        });

        capacityListener = FirebaseFirestore.getInstance().collection(Constants.LOBBY)
                .document(lobby.getLobbyID())
                .collection(Constants.LOBBY_GUESTLIST)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots,
                                        @javax.annotation.Nullable FirebaseFirestoreException e) {
                        getCapacity(lobby, new CapacityCallback() {
                            @Override
                            public void onCallback(ArrayList<User> guestList) {
                                textView_currentCapa.setText(Integer.toString(guestList.size()));
                            }
                        });
                    }
                });

        textView_Capacity.setText(String.valueOf(lobby.getCapacity()));
        textView_Location.setText(lobby.getLocation());
        textView_Activity.setText(lobby.getActivity());
        textView_Descriptions.setText(lobby.getLobbyDescriptions());

<<<<<<< HEAD
        return listViewItem;
    }

    private interface CapacityCallback{
        void onCallback(ArrayList<User> guestList);
    }

    private void getCapacity(Lobby lobby, final CapacityCallback capacityCallback){
        FirebaseFirestore.getInstance().collection(Constants.LOBBY)
                .document(lobby.getLobbyID())
                .collection(Constants.LOBBY_GUESTLIST)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        ArrayList<User> guestList = new ArrayList<>();
                        for (User user : task.getResult().toObjects(User.class)) {
                            guestList.add(user);
                            Log.d(TAG, "username: " + user.getUserName());
                        }
                        capacityCallback.onCallback(guestList);
                    }
                });
    }
=======

        return listViewItem;
    }
>>>>>>> parent of 50d4854... Merge branch 'master' of https://github.com/bryantanuwijaya/ToGatherADProject
}
