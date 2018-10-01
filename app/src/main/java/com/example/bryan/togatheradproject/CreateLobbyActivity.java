package com.example.bryan.togatheradproject;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Handler;
import android.os.ResultReceiver;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.UUID;

public class CreateLobbyActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;
    private static final String TAG = "CreateLobbyActivity";
    private static final String ADDRESS_REQUESTED_KEY = "address-request-pending";
    private static final String LOCATION_ADDRESS_KEY = "location-address";
    private FusedLocationProviderClient mFusedLocationClient;
    private Location mLastLocation;
    private boolean mAddressRequested;
    private String mAddressOutput;
    private AddressResultReceiver mResultReceiver;
    private TextView mLocationAddressTextView;
    private EditText editText_Capacity;
    private EditText editText_Description;
    private Button button_Create;
    private Button button_Cancel;
    private RadioGroup radioGroup_lobbyType;
    private RadioButton radioButton_private;
    private RadioButton radioButton_public;
    boolean isPrivate = false;

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    protected void establish(){
        editText_Capacity = findViewById(R.id.editText_CreateLobbyActivity_capacity);
        editText_Description = findViewById(R.id.editText_CreateLobbyActivity_description);
        button_Create = findViewById(R.id.button_CreateLobbyActivity_create);
        button_Cancel = findViewById(R.id.button_CreateLobbyActivity_cancel);
        mLocationAddressTextView = findViewById(R.id.textView_CreateLobbyActivity_address);
        radioGroup_lobbyType = findViewById(R.id.radioGroup_CreateLobbyActivity_lobbyType);
        radioButton_private = findViewById(R.id.radioButton_CreateLobbyActivity_private);
        radioButton_public = findViewById(R.id.radioButton_CreateLobbyActivity_public);
    }

    private String whyError = "";

    protected boolean checkIfDataNotBlank(String capacity, String description) {
        boolean result = true;
        if (capacity.equals("") || description.equals("")) {
            whyError = "Please fill all of the fields properly";
            result = false;
        }
        return result;
    }

    protected boolean checkIfLocationisThere(String location) {
        boolean result = true;
        if (location.equals("")) {
            whyError = "GPS Error, could not create room";
            result = false;
        }
        return result;
    }

    protected boolean checkCorrectCapacity(int a) {
        boolean result = true;
        if (!(2 <= a && a <= 15)) {
            whyError = "Capacity Error, could not create room";
            result = false;
        }
        return result;
    }


    protected void clearEditTest(){
        editText_Capacity.setText("");
        editText_Description.setText("");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_lobby);

        Intent intent = getIntent();
        final User user = (User) intent.getSerializableExtra(Constants.USER);
        Log.d(TAG, "CreateLobby - Logged user : " + user.getUserID());

        establish();

        final Spinner spinner = findViewById(R.id.spinnerActivities);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.activities, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);


        mResultReceiver = new AddressResultReceiver(new Handler());
        mAddressRequested = false;
        mAddressOutput = "";
        updateValuesFromBundle(savedInstanceState);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        fetchAddressButtonHandler2();

        button_Create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String description = editText_Description.getText().toString();
                String temp_Capacity = editText_Capacity.getText().toString();
                int capacity = 1;
                try{
                    capacity = (Integer.parseInt(temp_Capacity));
                }
                catch (NumberFormatException e)
                {
                    whyError = "Please fill the fields properly";
                }
                String activity = spinner.getSelectedItem().toString();
                isPrivate = getLobbyType();
                ArrayList<User> guestList = new ArrayList<>();
                Log.d(TAG, "user = " + guestList);

                if (checkCorrectCapacity(capacity) && checkIfLocationisThere(mAddressOutput) && checkIfDataNotBlank(temp_Capacity, description)) {
                    final String lobbyID = UUID.randomUUID().toString();
                    final String chatlogID = UUID.randomUUID().toString();
                    final String[] output = mAddressOutput.split(",");
                    String address = "";
                    for (int i = 0; i < output.length - 1; i++) {
                        address += output[i];
                        if (i < output.length - 2) {
                            address += ",";
                        }
                    }
                    final Lobby lobby = new Lobby(lobbyID, user.getUserID(), capacity, address, description, activity, guestList, chatlogID, isPrivate);

                    FirebaseFirestore.getInstance().collection(Constants.LOBBY)
                            .document(lobbyID)
                            .set(lobby)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    //create an empty chat instance
                                    Chat chat = new Chat();

                                    //create an empty arraylist to contain all of the chat object
                                    ArrayList<Chat> chatlog = new ArrayList<>();

                                    //generate the creation message
                                    chat = chat.createEntryChat(user);

                                    //add the message to the empty arraylist
                                    chatlog.add(chat);

                                    //update the database
                                    chat.updateChat(chatlog, lobbyID, chatlogID);

                                    //create the intent along and pass the relevant information
                                    Intent intent = new Intent(getApplicationContext(), LobbyActivity.class);
                                    intent.putExtra(Constants.LOBBY, lobby);
                                    intent.putExtra(Constants.USER, user);
                                    startActivity(intent);
                                    finish();
                                    button_Create.invalidate();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e(TAG, "onFailure: Could not create lobby " + e);
                        }
                    });
                }
                else{
                    clearEditTest();
                    Toast.makeText(CreateLobbyActivity.this, whyError, Toast.LENGTH_SHORT).show();
                    whyError = "";
                }

            }
        });


        button_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                intent.putExtra(Constants.USER, user);
                Toast.makeText(CreateLobbyActivity.this, "Lobby creation cancelled", Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();

        if (!checkPermissions()) {
            requestPermissions();
        } else {
            getAddress();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        button_Cancel.performClick();
        button_Cancel.setPressed(true);
        button_Cancel.invalidate();
        button_Cancel.setPressed(false);
        button_Cancel.invalidate();
    }


    private boolean getLobbyType(){
        boolean isPrivate = false;
        if(radioButton_private.isChecked()){
            isPrivate = true;
        }
        else if (radioButton_public.isChecked()){
            isPrivate = false;
        }
        return isPrivate;
    }

    private void updateValuesFromBundle(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            if (savedInstanceState.keySet().contains(ADDRESS_REQUESTED_KEY)) {
                mAddressRequested = savedInstanceState.getBoolean(ADDRESS_REQUESTED_KEY);
            }
            if (savedInstanceState.keySet().contains(LOCATION_ADDRESS_KEY)) {
                mAddressOutput = savedInstanceState.getString(LOCATION_ADDRESS_KEY);
                displayAddressOutput();
            }
        }
    }

    @SuppressWarnings("unused")
    public void fetchAddressButtonHandler2() {
        if (mLastLocation != null) {
            startIntentService();
            return;
        }
        mAddressRequested = true;
    }

    private void startIntentService() {
        Intent intent = new Intent(this, FetchAddressIntentService.class);
        intent.putExtra(Constants.RECEIVER, mResultReceiver);
        intent.putExtra(Constants.LOCATION_DATA_EXTRA, mLastLocation);
        startService(intent);
    }

    @SuppressWarnings("MissingPermission")
    private void getAddress() {
        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location == null) {
                            Log.w(TAG, "onSuccess:null");
                            return;
                        }
                        mLastLocation = location;
                        if (!Geocoder.isPresent()) {
                            showSnackbar(getString(R.string.no_geocoder_available));
                            return;
                        }
                        if (mAddressRequested) {
                            startIntentService();
                        }
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "getLastLocation:onFailure", e);
                    }
                });
    }

    private void displayAddressOutput() {
        mLocationAddressTextView.setText(mAddressOutput);
    }


    private void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putBoolean(ADDRESS_REQUESTED_KEY, mAddressRequested);
        savedInstanceState.putString(LOCATION_ADDRESS_KEY, mAddressOutput);
        super.onSaveInstanceState(savedInstanceState);
    }

    private class AddressResultReceiver extends ResultReceiver {
        AddressResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            mAddressOutput = resultData.getString(Constants.RESULT_DATA_KEY);
            displayAddressOutput();
            if (resultCode == Constants.SUCCESS_RESULT) {
                showToast(getString(R.string.address_found));
            }
            mAddressRequested = false;
        }
    }

    private void showSnackbar(final String text) {
        View container = findViewById(android.R.id.content);
        if (container != null) {
            Snackbar.make(container, text, Snackbar.LENGTH_LONG).show();
        }
    }

    private void showSnackbar(final int mainTextStringId, final int actionStringId,
                              View.OnClickListener listener) {
        Snackbar.make(findViewById(android.R.id.content),
                getString(mainTextStringId),
                Snackbar.LENGTH_INDEFINITE)
                .setAction(getString(actionStringId), listener).show();
    }

    private boolean checkPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissions() {
        boolean shouldProvideRationale =
                ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.ACCESS_FINE_LOCATION);
        if (shouldProvideRationale) {
            Log.i(TAG, "Displaying permission rationale to provide additional context.");
            showSnackbar(R.string.permission_rationale, android.R.string.ok,
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ActivityCompat.requestPermissions(CreateLobbyActivity.this,
                                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                    REQUEST_PERMISSIONS_REQUEST_CODE);
                        }
                    });

        } else {
            Log.i(TAG, "Requesting permission");
            ActivityCompat.requestPermissions(CreateLobbyActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        Log.i(TAG, "onRequestPermissionResult");
        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length <= 0) {
                Log.i(TAG, "User interaction was cancelled.");
            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getAddress();
            } else {
                showSnackbar(R.string.permission_denied_explanation, R.string.settings,
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent();
                                intent.setAction(
                                        Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                Uri uri = Uri.fromParts("package",
                                        BuildConfig.APPLICATION_ID, null);
                                intent.setData(uri);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        });
            }
        }
    }
}
