package com.example.bryan.togatheradproject;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;


public class CreateLobbyActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{


    private static final String TAG = "CreateLobbyActivity";
    TextView textView_Activity;
    TextView textView_Capacity;
    TextView textView_Description;
    EditText editText_Capacity;
    EditText editText_Description;
    Button button_Create;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_lobby);

        textView_Activity = findViewById(R.id.textView_CreateLobbyActivity_activity);
        textView_Capacity = findViewById(R.id.textView_CreateLobbyActivity_capacity);
        textView_Description = findViewById(R.id.textView_CreateLobbyActivity_description);
        editText_Capacity = findViewById(R.id.editText_CreateLobbyActivity_capacity);
        editText_Description = findViewById(R.id.editText_CreateLobbyActivity_description);
        button_Create = findViewById(R.id.button_CreateLobbyActivity_create);

        button_Create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String description = editText_Description.getText().toString();
                String temp_Capacity = editText_Capacity.getText().toString();
                int capacity = (Integer.parseInt(temp_Capacity));

                Map data = new HashMap();
                data.put("capacity", capacity);
                data.put("hostID", "dummyID");
                data.put("guestID array", "guest IDs");
                data.put("description", description);
                data.put("activity", "dummy activity");
                data.put("location", "dummy location");


                FirebaseFirestore.getInstance().collection("lobby")
                        .add(data)
                        .addOnSuccessListener(new OnSuccessListener() {
                            @Override
                            public void onSuccess(Object o) {
                                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                                startActivity(intent);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.e(TAG, "onFailure: Could not create lobby " + e);
                            }
                        });

            }
        });
        Spinner spinner = findViewById(R.id.spinnerActivities);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.activities, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item );
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
