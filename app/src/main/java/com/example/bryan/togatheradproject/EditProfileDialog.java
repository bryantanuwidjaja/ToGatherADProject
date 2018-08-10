package com.example.bryan.togatheradproject;

import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class EditProfileDialog extends DialogFragment {
    private static final String TAG = "EditProfileDialog";

    public interface OnInputListener {
        void sendInput(String input);
    }

    public OnInputListener onInputListener;

    //widgets
    private TextView textView_enterYourInterest;
    private EditText editText_interestEditText;
    private Button button_saveButton;
    private Button button_cancelButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_edit_profile, container, false);
        textView_enterYourInterest = view.findViewById(R.id.textView_FragmentEditProfile_enterYourInterest);
        editText_interestEditText = view.findViewById(R.id.editText_FragmentEditProfile_interestEditText);
        button_saveButton = view.findViewById(R.id.button_FragmentEditProfile_saveButton);
        button_cancelButton = view.findViewById(R.id.button_FragmentEditProfile_cancelButton);

        button_cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: closing dialog");
                getDialog().dismiss();
            }
        });

        button_saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: retrieving input");

                String interest = editText_interestEditText.getText().toString();
                onInputListener.sendInput(interest);
                getDialog().dismiss();
            }
        });
        return view;
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
