package com.example.bryan.togatheradproject;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;

import java.util.ArrayList;

public class RespondRequestDialog extends DialogFragment {
    private static final String TAG = "RespondRequestDialog";

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    ArrayList<String> interests = new ArrayList<>();
    ListenerRegistration cancelListener;
    ListenerRegistration requestExistListener;

    //widgets
    private TextView textView_username;
    private TextView textView_interest1;
    private TextView textView_interest2;
    private TextView textView_interest3;
    private TextView textView_interest4;
    private TextView textView_interest5;
    private TextView textView_interest6;
    private Button button_accept;
    private Button button_reject;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, Bundle savedInstanceState) {
        final Request request = (Request) getArguments().getSerializable(Constants.LOBBY_REQUEST);
        final Lobby lobby = (Lobby) getArguments().getSerializable(Constants.LOBBY);
        View view = inflater.inflate(R.layout.respond_request_dialog, container, false);
        textView_username = view.findViewById(R.id.textView_RespondRequest_username);
        textView_interest1 = view.findViewById(R.id.textView_RespondRequest_interest1);
        textView_interest2 = view.findViewById(R.id.textView_RespondRequest_interest2);
        textView_interest3 = view.findViewById(R.id.textView_RespondRequest_interest3);
        textView_interest4 = view.findViewById(R.id.textView_RespondRequest_interest4);
        textView_interest5 = view.findViewById(R.id.textView_RespondRequest_interest5);
        textView_interest6 = view.findViewById(R.id.textView_RespondRequest_interest6);
        button_accept = view.findViewById(R.id.button_RespondRequest_accept);
        button_reject = view.findViewById(R.id.button_RespondRequest_reject);

        //get the current interest list
        User requestUser = request.getUser();
        interests = requestUser.getUserInterests();
        textView_interest1.setText(requestUser.getUserInterests().get(0));
        textView_interest2.setText(requestUser.getUserInterests().get(1));
        textView_interest3.setText(requestUser.getUserInterests().get(2));
        textView_interest4.setText(requestUser.getUserInterests().get(3));
        textView_interest5.setText(requestUser.getUserInterests().get(4));
        textView_interest6.setText(requestUser.getUserInterests().get(5));
        textView_username.setText(requestUser.getUserName());

        setCancelable(false);

        button_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                request.setState(Constants.ACCEPTED);
                try {
                    FirebaseFirestore.getInstance().collection(Constants.LOBBY)
                            .document(lobby.getLobbyID())
                            .collection(Constants.LOBBY_REQUEST)
                            .document(request.getRequestID())
                            .set(request);
                }
                catch (Exception e){
                    Toast.makeText(getActivity(), "Request cancelled" , Toast.LENGTH_SHORT).show();
                    getDialog().dismiss();
                    destroyFragment();
                }
                getDialog().dismiss();
                destroyFragment();
            }
        });

        button_reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                request.setState(Constants.REJECTED);
                try {
                    FirebaseFirestore.getInstance().collection(Constants.LOBBY)
                            .document(lobby.getLobbyID())
                            .collection(Constants.LOBBY_REQUEST)
                            .document(request.getRequestID())
                            .set(request);
                }
                catch (Exception e){
                    Toast.makeText(getActivity(), "Request cancelled" , Toast.LENGTH_SHORT).show();
                    getDialog().dismiss();
                    destroyFragment();
                }
                getDialog().dismiss();
                destroyFragment();
            }
        });
        return view;
    }

    private void destroyFragment() {
        Log.d(TAG, "destroyFragment: in");
        getActivity().getFragmentManager().beginTransaction().remove(this).commit();
        Log.d(TAG, "destroyFragment: out");
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new Dialog(getActivity(), getTheme()){
            @Override
            public void onBackPressed() {
                button_reject.performClick();
            }
        };
    }

    private void setCancelListener(final Lobby lobby, Request request){
            cancelListener = FirebaseFirestore.getInstance().collection(Constants.LOBBY)
                    .document(lobby.getLobbyID())
                    .collection(Constants.LOBBY_REQUEST)
                    .document(request.getRequestID())
                    .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                        @Override
                        public void onEvent(@javax.annotation.Nullable DocumentSnapshot documentSnapshot,
                                            @javax.annotation.Nullable FirebaseFirestoreException e) {
                            Request request = documentSnapshot.toObject(Request.class);
                            try {
                                if (request.getState().equals(Constants.CANCELLED)) {
                                    Toast.makeText(getActivity(), "Request cancelled", Toast.LENGTH_SHORT).show();
                                    deleteRequest(lobby, request.getUser());
                                    getDialog().dismiss();
                                    destroyFragment();
                                    cancelListener.remove();
                                }
                            }catch (NullPointerException nullRequest){
                                Log.d(TAG, "nullRequest : " + nullRequest.getStackTrace());
                            }
                        }
                    });
    }

    private void deleteRequest(Lobby lobby, User user){
        FirebaseFirestore.getInstance().collection(Constants.LOBBY)
                .document(lobby.getLobbyID())
                .collection(Constants.LOBBY_REQUEST)
                .document(user.getUserID())
                .delete();
    }

    @Override
    public void onStart() {
        super.onStart();
        final Request request = (Request) getArguments().getSerializable(Constants.LOBBY_REQUEST);
        final Lobby lobby = (Lobby) getArguments().getSerializable(Constants.LOBBY);
        requestExistListener = FirebaseFirestore.getInstance().collection(Constants.LOBBY)
                .document(lobby.getLobbyID())
                .collection(Constants.LOBBY_REQUEST)
                .document(Constants.LOBBY_REQUEST)
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@javax.annotation.Nullable DocumentSnapshot documentSnapshot,
                                        @javax.annotation.Nullable FirebaseFirestoreException e) {
                        setCancelListener(lobby,request);
                    }
                });
    }
}
