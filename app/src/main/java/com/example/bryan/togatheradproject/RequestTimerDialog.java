package com.example.bryan.togatheradproject;

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

import com.google.firebase.firestore.FirebaseFirestore;

public class RequestTimerDialog extends DialogFragment {
    private static final String TAG = "RequestTimerDialog";

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    //widgets
    private Button button_cancelButton;
    private TextView textView_timer;
    private CountDownTimer countDownTimer;
    private long timeLeftInMilisecond = 60000; // a minute
    private boolean timerRunning;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final User user = (User) getArguments().getSerializable(Constants.USER);
        final Lobby lobby = (Lobby) getArguments().getSerializable(Constants.LOBBY_REQUEST);
        View view = inflater.inflate(R.layout.activity_confirm_delete_dialog, container, false);
        button_cancelButton = view.findViewById(R.id.button_TimerDialog_cancel);
        textView_timer = view.findViewById(R.id.textView_TimerDialog_timer);

        startStop(lobby, user);

        button_cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: closing dialog");
                getDialog().dismiss();
                //delete request
                deleteRequest(lobby ,user);
                destroyFragment();
                button_cancelButton.invalidate();
            }
        });
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
                timeLeftInMilisecond = 1;
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
    }
}
