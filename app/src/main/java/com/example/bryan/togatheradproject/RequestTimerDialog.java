package com.example.bryan.togatheradproject;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.os.CountDownTimer;
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

public class RequestTimerDialog extends DialogFragment {
    private static final String TAG = "RequestTimerDialog";

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    ListenerRegistration stateListener;

    //widgets
    private Button button_cancelButton;
    private TextView textView_timer;
    private CountDownTimer countDownTimer;
    private long timeLeftInMilisecond = 60000; // a minute
    private boolean timerRunning = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final User user = (User) getArguments().getSerializable(Constants.USER);
        final Lobby lobby = (Lobby) getArguments().getSerializable(Constants.LOBBY);
        View view = inflater.inflate(R.layout.activity_request_timer_dialog, container, false);
        Log.d(TAG, "lobby ID = " + lobby.getLobbyID());
        button_cancelButton = (Button) view.findViewById(R.id.button_TimerDialog_cancel);
        textView_timer =  (TextView) view.findViewById(R.id.textView_TimerDialog_timer);

        setCancelable(false);
        setStateListener(lobby, user);

        button_cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: closing dialog");
                button_cancelButton.invalidate();
                stopTimer();
                //delete request
                deleteRequest(lobby ,user);
                getDialog().dismiss();
                destroyFragment();
            }
        });
        startStop(lobby, user);
        updateTimer();
        return view;
    }

    private void destroyFragment() {
        Log.d(TAG, "destroyFragment: in");
        getActivity().getFragmentManager().beginTransaction().remove(this).commit();
        Log.d(TAG, "destroyFragment: out");
    }

    private void deleteRequest(Lobby lobby, User user){
        FirebaseFirestore.getInstance().collection(Constants.LOBBY)
                .document(lobby.getLobbyID())
                .collection(Constants.LOBBY_REQUEST)
                .document(user.getUserID())
                .delete();
    }

    public void startStop(Lobby lobby, User user){
        if(timerRunning){
            stopTimer();
        }
        else{
            startTimer(lobby, user);
        }
    }

    private void startTimer(final Lobby lobby, final User user){
        countDownTimer = new CountDownTimer(timeLeftInMilisecond, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
               timeLeftInMilisecond = millisUntilFinished;
               updateTimer();
            }

            @Override
            public void onFinish() {
                button_cancelButton.performClick();
            }
        }.start();
        timerRunning = true;
    }

    private  void stopTimer(){
        countDownTimer.cancel();
        timerRunning = false;
    }

    private void updateTimer(){
        int seconds = (int) timeLeftInMilisecond / 1000;
        String timeLeftText;
        timeLeftText = ""+seconds;
        textView_timer.setText(timeLeftText);
        Log.d(TAG, "timeleftinmilisecond = " + timeLeftInMilisecond);
        Log.d(TAG, "current second : " + timeLeftText);
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: in");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: in");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "onDestroyView: in");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: in");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "onDetach: in");
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new Dialog(getActivity(), getTheme()){
            @Override
            public void onBackPressed() {
                button_cancelButton.performClick();
            }
        };
    }

    private void setStateListener(final Lobby lobby, final User user){
        stateListener = FirebaseFirestore.getInstance().collection(Constants.LOBBY)
                .document(lobby.getLobbyID())
                .collection(Constants.LOBBY_REQUEST)
                .document(user.getUserID())
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@javax.annotation.Nullable DocumentSnapshot documentSnapshot,
                                        @javax.annotation.Nullable FirebaseFirestoreException e) {
                        Request request;
                        String state;
                        try{
                            request = documentSnapshot.toObject(Request.class);
                            state = request.getState();
                            if(state.equals(Constants.ACCEPTED)){
                                stateListener.remove();
                                stopTimer();
                                deleteRequest(lobby, user);
                                ((HomeActivity)getActivity()).retrieveChatLog(lobby.getLobbyID(), lobby.getChatlogID(), request.getUser(), lobby);
                                getDialog().dismiss();
                                destroyFragment();
                            }
                            else if(state.equals(Constants.REJECTED)){
                                stateListener.remove();
                                Toast.makeText(getContext(), "Request denied" , Toast.LENGTH_SHORT).show();
                                button_cancelButton.performClick();
                            }
                        }
                        catch (Exception e1){
                            return;
                        }


                    }
                });
    }
}
