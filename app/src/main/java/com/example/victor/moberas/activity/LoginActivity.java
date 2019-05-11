package com.example.victor.moberas.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.victor.moberas.R;
import com.example.victor.moberas.firebase.FirebaseConfig;
import com.example.victor.moberas.model.UserData;
import com.example.victor.moberas.util.BirthEntry;
import com.example.victor.moberas.util.ButtonEntry;
import com.example.victor.moberas.util.NameEntry;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.SimpleDateFormat;
import java.util.Date;

public class LoginActivity extends AppCompatActivity {

    NameEntry nameEntry;
    BirthEntry birthEntry;
    ButtonEntry buttonEntry;
    FirebaseAuth firebaseAuth;

    private final String TAG_AUTH = "Tag_Auth";
    private final String TAG_RUNTIME = "Tag_Runtime";

    private final View.OnClickListener registerListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String name = nameEntry.getName().trim().toLowerCase();
            String birthDate = birthEntry.getBirthDate();
            boolean nameErrors = checkNameForErrors(name);
            boolean birthDateErrors = checkBirthDateForErrors(birthDate);
            if( (!nameErrors) && (!birthDateErrors)){
                final UserData userData = new UserData();
                userData.name = name;
                userData.birthDate = birthDate;
                firebaseAuth = FirebaseConfig.getFirebaseAuth();
                firebaseAuth.createUserWithEmailAndPassword(userData.getID() + "@fake.com", userData.getID() )
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                hideAllErrors();
                                FirebaseUser currentFirebaseUser = null;
                                if (task.isSuccessful()) {
                                    Log.d(TAG_AUTH, "User Created Successfully");
                                    currentFirebaseUser = firebaseAuth.getCurrentUser();
                                    final String userID = currentFirebaseUser.getEmail().replace("@fake.com","");
                                    Log.d(TAG_AUTH, "UserID: " + userID);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.d(TAG_AUTH, "User Can't be Created!");
                                    Log.d(TAG_AUTH, task.getException().toString(), task.getException());
                                    buttonEntry.showError();
                                }
                                if(currentFirebaseUser != null){
                                    hideAllErrors();
                                    userData.lastLoginTime = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
                                    Log.d(TAG_RUNTIME, "Going To VideoPlaybackActivity...");
                                    Intent intent = new Intent(LoginActivity.this, VideoPlaybackActivity.class);
                                    intent.putExtra("userData",userData);
                                    intent.putExtra("firstTimeLogin",true);
                                    startActivity(intent);
                                }
                            }
                        });
            }
            else {
                if(nameErrors){
                    nameEntry.showError();
                }
                if(birthDateErrors){
                    birthEntry.showError();
                }
            }
        }
    };
    private final View.OnClickListener loginListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String name = nameEntry.getName().trim().toLowerCase();
            String birthDate = birthEntry.getBirthDate();
            boolean nameErrors = checkNameForErrors(name);
            boolean birthDateErrors = checkBirthDateForErrors(birthDate);
            if( (!nameErrors) && (!birthDateErrors)){
                final UserData userData = new UserData();
                userData.name = name;
                userData.birthDate = birthDate;
                firebaseAuth = FirebaseConfig.getFirebaseAuth();
                firebaseAuth.signInWithEmailAndPassword(userData.getID() + "@fake.com", userData.getID() )
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                hideAllErrors();
                                FirebaseUser currentFirebaseUser = null;
                                if (task.isSuccessful()) {
                                    Log.d(TAG_AUTH, "User Created Successfully");
                                    currentFirebaseUser = firebaseAuth.getCurrentUser();
                                    final String userID = currentFirebaseUser.getEmail().replace("@fake.com","");
                                    Log.d(TAG_AUTH, "UserID: " + userID);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.d(TAG_AUTH, "User Can't be Created!");
                                    Log.d(TAG_AUTH, task.getException().toString(), task.getException());
                                    buttonEntry.showError();
                                }
                                if(currentFirebaseUser != null){
                                    hideAllErrors();
                                    Log.d(TAG_RUNTIME, "Going To MenuActivity...");
                                    Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                                    intent.putExtra("firstTimeLogin",false);
                                    intent.putExtra("lastLoginTime", new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
                                    startActivity(intent);
                                }
                            }
                        });
            }
            else {
                if(nameErrors){
                    nameEntry.showError();
                }
                if(birthDateErrors){
                    birthEntry.showError();
                }
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        identifyViews();
        hideAllErrors();
        registerListeners();
    }

    private void identifyViews() {
        nameEntry = findViewById(R.id.ne_name_field);
        birthEntry = findViewById(R.id.be_birth_field);
        buttonEntry = findViewById(R.id.be_button_field);
    }

    private void hideAllErrors() {
        nameEntry.hideError();
        birthEntry.hideError();
        buttonEntry.hideError();
    }

    private void registerListeners() {
        buttonEntry.setLoginOnClickListener(loginListener);
        buttonEntry.setRegisterOnClickListener(registerListener);
    }

    private boolean checkNameForErrors(String name){
        return (name.length() == 0);
    }

    private boolean checkBirthDateForErrors(String birthDate){
        String[] content = birthDate.split("/");
        int day = Integer.parseInt(content[0]);
        int month = Integer.parseInt(content[1]);
        int year = Integer.parseInt(content[2]);
        return false;
    }
}
