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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class EditProfileDialog extends DialogFragment {
    private static final String TAG = "EditProfileDialog";
    public static final String USER_ID = "userID";

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference userCollectionRef = db.collection("user");
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

    private void updateInterestList(final String interest) {
        Query userQuery = userCollectionRef
                .whereEqualTo("userID", FirebaseAuth.getInstance().getCurrentUser().getUid());

        userQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        User user = document.toObject(User.class);
                        interests = user.getUserInterests();
                        interests.add(interest);
                    }
                    Log.d(TAG, "onComplete: Query Success");
                }
                    else{
                    Log.d(TAG, "onComplete: Query Failed ");
                    }
            }
        });
    }




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final String userID = getArguments().getString(USER_ID);
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
                destroyFragment();
            }
        });

        button_saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: retrieving input");

                getDialog().dismiss();
                destroyFragment();
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
