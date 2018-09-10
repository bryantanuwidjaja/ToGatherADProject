package com.example.bryan.togatheradproject;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.Constraints;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class ConfirmDeleteDialog extends DialogFragment{
    private static final String TAG = "ConfirmDeleteDialog";

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    //widgets
    private TextView textView_warningTAG;
    private TextView textView_directionTAG;
    private EditText editText_confirmation;
    private Button button_delete;
    private Button button_cancel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        //get user from bundle
        final User user = (User) getArguments().getSerializable(Constants.USER);

        //inflate dialog
        View view = inflater.inflate(R.layout.activity_confirm_delete_dialog, container, false);

        //initialise widgets
        textView_warningTAG = view.findViewById(R.id.textView_ConfirmDialog_warningTAG);
        textView_directionTAG = view.findViewById(R.id.textView_ConfirmDialog_directionTAG);
        editText_confirmation = view.findViewById(R.id.editText_ConfirmDialog_confirmation);
        button_delete = view.findViewById(R.id.button_ConfirmDialog_delete);
        button_cancel = view.findViewById(R.id.button_ConfirmDialog_cancel);

        button_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String confirmText = editText_confirmation.getText().toString();
                if(confirmText.equals("CONFIRM")){
                    //delete account
                    FirebaseFirestore.getInstance().collection(Constants.USER)
                            .document(user.getUserID())
                            .delete()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Log.d(TAG, "deletion complete");
                                }
                            });
                    FirebaseAuth.getInstance().getCurrentUser().delete();
                    Toast.makeText(getActivity(), "User Deleted" , Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }
                else{
                    editText_confirmation.setText("");
                    Toast.makeText(getActivity(), "Please enter the confirmation properly" , Toast.LENGTH_SHORT).show();
                }
            }
        });

        button_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ProfileActivity.class);
                intent.putExtra(Constants.USER, user);
                intent.putExtra(Constants.USER_ID, user.getUserID());
                startActivity(intent);
            }
        });
        return view;
    }
}
