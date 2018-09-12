package com.example.bryan.togatheradproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

public class EditLobbyActivity extends AppCompatActivity {

    EditText editText_description;
    EditText editText_capacity;
    Button button_save;
    Button button_cancel;
    RadioGroup radioGroup_lobbyType;
    RadioButton radioButton_privateLobby;
    RadioButton radioButton_publicLobby;

    boolean isPrivate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_lobby);

        //get intent
        Intent intent = getIntent();
        final User user =(User) intent.getSerializableExtra(Constants.USER);
        final Lobby lobby = (Lobby) intent.getSerializableExtra(Constants.LOBBY);

        //initialise widget
        editText_description = findViewById(R.id.editText_EditLobbyActivity_description);
        editText_capacity = findViewById(R.id.editText_EditLobbyActivity_capacity);
        button_save = findViewById(R.id.button_EditLobbyActivity_save);
        button_cancel = findViewById(R.id.button_EditLobbyActivity_cancel);
        radioGroup_lobbyType = findViewById(R.id.radioGroup_EditLobbyActivity_lobbyType);
        radioButton_privateLobby = findViewById(R.id.radioButton_EditLobbyActivity_privateLobby);
        radioButton_publicLobby = findViewById(R.id.radioButton_EditLobbyActivity_public);
        //set current data
        editText_description.setText(lobby.getLobbyDescriptions());
        editText_capacity.setText(Integer.toString(lobby.getCapacity()));
        isPrivate = lobby.getPrivateLobby();
        setCurrentRadioButton(isPrivate);

        button_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LobbyActivity.class);
                intent.putExtra(Constants.USER, user);
                intent.putExtra(Constants.LOBBY, lobby);
                startActivity(intent);
                finish();
            }
        });

        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newDesc = editText_description.getText().toString();
                int newCapa = Integer.parseInt(editText_capacity.getText().toString());
                lobby.setLobbyDescriptions(newDesc);
                lobby.setCapacity(newCapa);
                isPrivate = getLobbyType();
                lobby.setPrivateLobby(isPrivate);

                FirebaseFirestore.getInstance().collection(Constants.LOBBY)
                        .document(lobby.getLobbyID())
                        .set(lobby)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(EditLobbyActivity.this, "Lobby updated", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), LobbyActivity.class);
                                intent.putExtra(Constants.USER, user);
                                intent.putExtra(Constants.LOBBY, lobby);
                                startActivity(intent);
                                finish();
                            }
                        });
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

    private void setCurrentRadioButton(boolean isPrivate){
        if(isPrivate){
            radioButton_privateLobby.setChecked(true);
        }
        else{
            radioButton_publicLobby.setChecked(true);
        }
    }

    private boolean getLobbyType(){
        boolean isPrivate = true;
        if(radioButton_privateLobby.isChecked()){
            isPrivate = true;
        }
        else if(radioButton_publicLobby.isChecked()){
            isPrivate = false;
        }
        return isPrivate;
    }
}