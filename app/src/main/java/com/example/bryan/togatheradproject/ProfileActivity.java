package com.example.bryan.togatheradproject;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity implements EditProfileDialog.OnInputListener {

    private ArrayList<String> interestList = new ArrayList<>();
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    private int pointer = 0;

    @Override
    public void sendInput(String input) {
        Log.d(TAG, "sendInput: " + input);
        switch(pointer){
            case 1:
                textView_Interest1.setText(input);
                break;
            case 2:
                textView_Interest2.setText(input);
                break;
            case 3:
                textView_Interest3.setText(input);
                break;
            case 4:
                textView_Interest4.setText(input);
                break;
            case 5:
                textView_Interest5.setText(input);
                break;
            case 6:
                textView_Interest6.setText(input);
                break;
        }
    }

    private static final String TAG = "ProfileActivity";

    ImageView imageView_Image;
    TextView textView_Username;
    TextView textView_Interest1;
    TextView textView_Interest2;
    TextView textView_Interest3;
    TextView textView_Interest4;
    TextView textView_Interest5;
    TextView textView_Interest6;
    TextView textView_deleteAccount;
    Button button_save;
    Button button_cancel;



    public void updateInformation(String userID) {
        FirebaseFirestore.getInstance().collection(Constants.USER)
                .document(userID)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        //convert the document snap shot into user object
                        User user = documentSnapshot.toObject(User.class);

                        //retrieve username from user object
                        String userName = user.getUserName().toString();

                        //set the user name to according to the database
                        textView_Username.setText(userName);

                        //retrieve the interest list
                        interestList = user.getUserInterests();
                        Log.d(TAG, "interestList = " + interestList);
                        int listLength = interestList.size();
                        Log.d(TAG, "listLength = " + listLength);

                        //update the interest according to the database
                        for (int i = 0; i <= listLength; i++) {
                            String interest;
                            switch (i) {
                                case 1:
                                    interest = interestList.get(0);
                                    textView_Interest1.setText(interest);
                                    break;
                                case 2:
                                    interest = interestList.get(1);
                                    textView_Interest2.setText(interest);
                                    break;
                                case 3:
                                    interest = interestList.get(2);
                                    textView_Interest3.setText(interest);
                                    break;
                                case 4:
                                    interest = interestList.get(3);
                                    textView_Interest4.setText(interest);
                                    break;
                                case 5:
                                    interest = interestList.get(4);
                                    textView_Interest5.setText(interest);
                                    break;
                                case 6:
                                    interest = interestList.get(5);
                                    textView_Interest6.setText(interest);
                                    break;
                            }
                        }
                    }
                });
    }

    public void updateDatabase(String userID){
        interestList.clear();
        interestList.add(textView_Interest1.getText().toString());
        interestList.add(textView_Interest2.getText().toString());
        interestList.add(textView_Interest3.getText().toString());
        interestList.add(textView_Interest4.getText().toString());
        interestList.add(textView_Interest5.getText().toString());
        interestList.add(textView_Interest6.getText().toString());
        FirebaseFirestore.getInstance().collection(Constants.USER)
                .document(userID)
                .update(Constants.USER_INTERESTS, interestList);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Intent intent = getIntent();
        final User loggedUser = (User) intent.getSerializableExtra(Constants.USER);
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        DocumentReference userRef = firebaseFirestore.collection(Constants.USER).document(loggedUser.getUserID());

        imageView_Image = findViewById(R.id.imageView_ProfileScreen_image);
        textView_Username = findViewById(R.id.textView_ProfileScreen_username);

        textView_Interest1 = findViewById(R.id.textView_ProfileScreen_interest1);
        textView_Interest2 = findViewById(R.id.textView_ProfileScreen_interest2);
        textView_Interest3 = findViewById(R.id.textView_ProfileScreen_interest3);
        textView_Interest4 = findViewById(R.id.textView_ProfileScreen_interest4);
        textView_Interest5 = findViewById(R.id.textView_ProfileScreen_interest5);
        textView_Interest6 = findViewById(R.id.textView_ProfileScreen_interest6);
        textView_deleteAccount = findViewById(R.id.textView_ProfileScreen_deleteAccount); 
        button_cancel = findViewById(R.id.button_ProfileScreen_cancelButton);
        button_save = findViewById(R.id.button_ProfileScreen_saveButton);

        updateInformation(loggedUser.getUserID());

        textView_deleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //show dialog
                ConfirmDeleteDialog dialog = new ConfirmDeleteDialog();
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constants.USER, loggedUser);
                dialog.setArguments(bundle);
                dialog.show(getFragmentManager(), "ConfirmDeleteDialog");
            }
        });

        textView_Interest1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: opening dialog");
                pointer = 1;
                EditProfileDialog dialog = new EditProfileDialog();
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constants.USER, loggedUser);
                dialog.setArguments(bundle);
                dialog.show(getFragmentManager(), "EditProfileDialog");
            }
        });

        textView_Interest2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: opening dialog");
                pointer = 2;
                EditProfileDialog dialog = new EditProfileDialog();
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constants.USER, loggedUser);
                dialog.setArguments(bundle);
                dialog.show(getFragmentManager(), "EditProfileDialog");
            }
        });

        textView_Interest3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: opening dialog");
                pointer = 3;
                EditProfileDialog dialog = new EditProfileDialog();
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constants.USER, loggedUser);
                dialog.setArguments(bundle);
                dialog.show(getFragmentManager(), "EditProfileDialog");
            }
        });

        textView_Interest4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: opening dialog");
                pointer = 4;
                EditProfileDialog dialog = new EditProfileDialog();
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constants.USER, loggedUser);
                dialog.setArguments(bundle);
                dialog.show(getFragmentManager(), "EditProfileDialog");
            }
        });

        textView_Interest5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: opening dialog");
                pointer = 5;
                EditProfileDialog dialog = new EditProfileDialog();
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constants.USER, loggedUser);
                dialog.setArguments(bundle);
                dialog.show(getFragmentManager(), "EditProfileDialog");
            }
        });

        textView_Interest6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: opening dialog");
                pointer = 6;
                EditProfileDialog dialog = new EditProfileDialog();
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constants.USER, loggedUser);
                dialog.setArguments(bundle);
                dialog.show(getFragmentManager(), "EditProfileDialog");
            }
        });

        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDatabase(loggedUser.getUserID());
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                intent.putExtra(Constants.USER, loggedUser);
                startActivity(intent);
            }
        });

        button_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext() , HomeActivity.class);
                intent.putExtra(Constants.USER, loggedUser);
                Toast.makeText(ProfileActivity.this, "Vacancy creation canceled", Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        button_cancel.performClick();
        button_cancel.setPressed(true);
        button_cancel.invalidate();
        button_cancel.setPressed(false);
        button_cancel.invalidate();
    }
}
