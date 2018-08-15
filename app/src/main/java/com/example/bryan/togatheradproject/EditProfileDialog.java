package com.example.bryan.togatheradproject;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


public class EditProfileDialog extends DialogFragment {
    private static final String TAG = "EditProfileDialog";
    public static final String USER_ID = "userID";

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<String> interests = new ArrayList<>();


    public interface OnInputListener {
        void sendInput(String input);
    }

    public OnInputListener onInputListener;

    //widgets
    private TextView textView_enterYourInterest;
    private EditText editText_interestEditText;
    private Button button_saveButton;
    private Button button_cancelButton;
    private TextView textView_container;

    private ArrayList<String> updateInterestList(DocumentReference userRef, String newInterest, ArrayList<String> interests) {
        //add the new input to the list
        interests.add(newInterest);

        //result: current interest + new interest
        return interests;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final String userID = getArguments().getString(Constants.USER_ID);
        User currentUser = (User) getArguments().getSerializable(Constants.USER);
        final DocumentReference userRef = FirebaseFirestore.getInstance().collection(Constants.USER).document(userID);
        View view = inflater.inflate(R.layout.dialog_edit_profile, container, false);
        textView_enterYourInterest = view.findViewById(R.id.textView_FragmentEditProfile_enterYourInterest);
        editText_interestEditText = view.findViewById(R.id.editText_FragmentEditProfile_interestEditText);
        button_saveButton = view.findViewById(R.id.button_FragmentEditProfile_saveButton);
        button_cancelButton = view.findViewById(R.id.button_FragmentEditProfile_cancelButton);
        //get the current interest list
        Log.d(TAG, "test1 getemail : " + currentUser.getUserEmail());

        Log.d(TAG, "current list before addition : " + currentUser.getUserInterests());


        button_cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: closing dialog");
                getDialog().dismiss();
                destroyFragment();
            }
        });

        button_saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: retrieving input");
                //retrieve information from widget
                String newInterest = editText_interestEditText.getText().toString();
                onInputListener.sendInput(newInterest);

                //add the new input to the database
                //interests = updateInterestList(userRef,newInterest, interests);

                Log.d(TAG, "current list after addition : " + interests);
                //update database
                //userRef.update(Constants.USER_INTERESTS, interests);
                getDialog().dismiss();
                //destroyFragment();
                //interests.clear();
            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "onDestroyView: destroyed ");
    }

    private void destroyFragment() {
        Log.d(TAG, "destroyFragment: in");
        getActivity().getFragmentManager().beginTransaction().remove(this).commit();
        Log.d(TAG, "destroyFragment: out");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            onInputListener = (OnInputListener) getActivity();
        } catch (ClassCastException e) {
            Log.e(TAG, "onAttach: Class Cast Exception " + e.getMessage());
        }
    }
}
