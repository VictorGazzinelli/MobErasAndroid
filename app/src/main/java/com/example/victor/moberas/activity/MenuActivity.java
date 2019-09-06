package com.example.victor.moberas.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
//import android.support.v4.app.NotificationCompat;
//import android.support.v4.app.NotificationManagerCompat;
//import android.support.v4.content.LocalBroadcastManager;
//import android.support.v7.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.victor.moberas.R;
import com.example.victor.moberas.firebase.FirebaseConfig;
import com.example.victor.moberas.model.Constants;
import com.example.victor.moberas.model.Feedback;
import com.example.victor.moberas.model.FeedbackResult;
import com.example.victor.moberas.model.MedicalResult;
import com.example.victor.moberas.model.MotionActivity;
import com.example.victor.moberas.model.TimePick;
import com.example.victor.moberas.model.UserData;
import com.example.victor.moberas.service.BackgroundDetectedActivitiesService;
import com.example.victor.moberas.model.ActivityResult;
import com.example.victor.moberas.util.InfoButton;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import static com.example.victor.moberas.model.Constants.REQUEST_CODE_FEEDBACK;
import static com.example.victor.moberas.model.Constants.REQUEST_CODE_MEDICAL;
import static com.example.victor.moberas.model.Constants.RESULT_CODE_OK;
import static com.example.victor.moberas.model.Constants.TAG_AUTH;
import static com.example.victor.moberas.model.Constants.TAG_DB;
import static com.example.victor.moberas.model.Constants.TAG_RUNTIME;
import static com.example.victor.moberas.model.Constants.TAG_SERVICE;
import static com.example.victor.moberas.model.Constants.infoMessages;

public class MenuActivity extends AppCompatActivity {

    InfoButton activities, feedback, information;
    FirebaseUser currentUser;
    UserData userData;
    boolean broadcastReceiverStarted, broadcastReceiverRegistered;
    boolean informationalNotificationsStarted, trackingStarted;
    boolean feedbackNotificationsStarted, feedbackAvailable;
    int messageIndex, notificationID, databaseChangeID;
    ActivityResult lastActivityResult;
    BroadcastReceiver broadcastReceiver;
    final Handler scheduler = new Handler();
    final Runnable notificationTask = getNotificationTask();
    final Runnable feedbackTask = getFeedbackTask();

    /*
        Initialization Methods
     */

    private Runnable getFeedbackTask(){
        return new Runnable() {
            @Override
            public void run() {
                int hourOfDay = getHourOfDay();
                if(inNotificationTime(hourOfDay) && (!feedbackAvailable)){
                    feedbackAvailable = true;
                    setFeedbackAvailabilty();
                    Log.d(TAG_RUNTIME, "Feedback Is Now Available");
//                    String contentTitle = "Avaliação";
//                    String contentText = "sua avaliação está agora disponível!";
//                    showNotification(contentTitle,contentText.toUpperCase(), notificationID++);
                    vibrate(Constants.FEEDBACK);
                }
                if(userData.name.contains("demo")){
                    scheduler.postDelayed(this, 60000);
                }
                else{
                    scheduler.postDelayed(this, Constants.FEEDBACK_INTERVAL_IN_MILLIS);
                }
            }
        };

    }

    private Runnable getNotificationTask(){
        return new Runnable() {
            @Override
            public void run() {
                int hourOfDay = getHourOfDay();
                messageIndex = hourOfDay%infoMessages.length;
                String contentText = infoMessages[messageIndex];
//                String contentTitle = "Informação";
                information.setMessage(contentText);
                if(inNotificationTime(hourOfDay)){
//                    showNotification(contentTitle,contentText.toUpperCase(), notificationID++);
                    vibrate(Constants.UPDATE);
                }
                scheduler.postDelayed(this,Constants.NOTIFICATION_INTERVAL_IN_MILLIS);
            }
        };

    }

    /*
        Activity Lifecycle Methods
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading); // Hide UI
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON); // Keep Screen On
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG_RUNTIME, "Getting Results From Activity ...");
        if(requestCode == REQUEST_CODE_MEDICAL){
            if(resultCode == RESULT_CODE_OK){
                lastActivityResult = getMedicalResult(data);
            }
        }
        if(requestCode == REQUEST_CODE_FEEDBACK){
            if(resultCode == RESULT_CODE_OK){
                lastActivityResult = getFeedbackResult(data);
            }
        }
    }

    private MedicalResult getMedicalResult(Intent data){
        int medicalActivityCode = data.getIntExtra("activityCalledCode", -1);
        TimePick timePick = (TimePick) data.getSerializableExtra("timePick");
        return new MedicalResult(medicalActivityCode, timePick);
    }

    private FeedbackResult getFeedbackResult(Intent data){
        Feedback feedback = (Feedback) data.getSerializableExtra("feedback");
        return new FeedbackResult(-1, feedback);
    }

    @Override
    protected void onStart() {
        super.onStart();
        currentUser = FirebaseConfig.getFirebaseAuth().getCurrentUser();
        if(currentUser == null){
            Log.d(TAG_AUTH, "Error - No User LogedIn!");
            finish();
        }
        final String userID = currentUser.getEmail().replace("@fake.com","");
        boolean firstTimeLogin = getIntent().getBooleanExtra("firstTimeLogin", false);
        if(firstTimeLogin) {
            userData = (UserData) getIntent().getSerializableExtra("userData");
            updateDatabase(userData);
        }
        setDatabaseReferenceWithValueEventListener(userID);
    }

    private void  handleMedicalResult(){
        Log.d(TAG_RUNTIME, "Handling Result...");
        int medicalActivityCode = lastActivityResult.getId();
        TimePick timePick = (TimePick)lastActivityResult.getResult();
        switch (medicalActivityCode){
            default:{
                break;
            }
            case Constants.ORAL_DIET_INTAKE:{
                userData.firstOralDietIntakeTime = timePick;
                break;
            }
            case Constants.BLADDER_CATHETER_REMOVAL:{
                userData.bladderCatheterRemovalTime = timePick;
                break;
            }
            case Constants.DIURESIS:{
                userData.firstDiuresisTime = timePick;
                break;
            }
            case Constants.FLATUS:{
                userData.firstFlatusTime = timePick;
                break;
            }
            case Constants.STOOL:{
                userData.firstStoolTime = timePick;
                break;
            }
            case Constants.DRIP:{
                userData.dripRemovalTime = timePick;
                break;
            }
        }
        Log.d(TAG_RUNTIME, "New User Data: " + userData);
        updateDatabase(userData);
        Toast.makeText(this,"Atividade Registrada Com Sucesso!", Toast.LENGTH_SHORT).show();
    }

    private void handleFeedbackResult(){
        Log.d(TAG_RUNTIME, "Handling Result...");
        Feedback feedback = (Feedback) lastActivityResult.getResult();
//        if(userData.feedbackList == null){
//            userData.feedbackList = new ArrayList<>();
//        }
        userData.feedbackList.add(feedback);
        userData.lastFeedbackTime = feedback.time;
        Log.d(TAG_RUNTIME, "New User Data: " + userData);
        feedbackAvailable = false;
        Log.d(TAG_RUNTIME, "Feedback Is Now Unavailable");
        setFeedbackAvailabilty();
        updateDatabase(userData);
    }

    @Override
    protected void onResume() {
        super.onResume();
        startBroadcastReceiver();
        registerBroadcastReceiver();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // TODO: Show Dialog Confirming LogOut
        unregisterBroadcastReceiver();
        stopBroadcastReceiver();
        stopTracking();
        stopInformationalNotifications();
        stopFeedbackNotifications();
        logOut();
    }

    /*
        Basic Start Functions
     */

    private void identifyViews() {
        activities = findViewById(R.id.ib_activities);
        feedback = findViewById(R.id.ib_feedback);
        information = findViewById(R.id.ib_information);
    }

    private void registerListeners() {
        activities.setActionListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, MedicalActivitiesActivity.class);
                intent.putExtra("userData",userData);
                Log.d(TAG_RUNTIME, "Going To MedicalActivitiesActivity...");
                startActivityForResult(intent,REQUEST_CODE_MEDICAL);
            }
        });
        feedback.setActionListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, FeedbackActivity.class);
                intent.putExtra("userData",userData);
                Log.d(TAG_RUNTIME, "Going To FeedbackActivity...");
                startActivityForResult(intent,REQUEST_CODE_FEEDBACK);
            }
        });
        information.setActionClickable(false);
    }

    private void setInitialInfoMessages() {
        int hourOfDay = getHourOfDay();
        messageIndex = hourOfDay%infoMessages.length;
        information.setMessage(infoMessages[messageIndex]);
        activities.setMessage("Clique aqui para registrar uma atividade");
    }

    private void setActivitiesAvailabilty() {
        if(activities != null){
            activities.setVisibility(
                    userData.firstOralDietIntakeTime == null ||
                    userData.bladderCatheterRemovalTime == null ||
                    userData.firstDiuresisTime == null ||
                    userData.firstFlatusTime == null ||
                    userData.firstStoolTime == null ||
                    userData.dripRemovalTime == null
            );
        }
    }

    private void setFeedbackAvailabilty() {
        if(feedbackAvailable){
            feedback.setMessage("Como você se sentiu nas últimas 6 horas? Clique aqui");
        }
        else {
            feedback.setMessage("Sua avaliação não pode ser preenchida ainda");
        }
        feedback.setActionClickable(feedbackAvailable);
    }

    private void updateUserDataTime(){
        userData.lastLoginTime = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
        userData.lastLogoutTime = null;
        updateDatabase(userData);
    }

    private void executeStartTasks() {
        setContentView(R.layout.activity_menu); // show UI
        identifyViews();
        registerListeners();
        setActivitiesAvailabilty();
        setInitialInfoMessages();
        setFeedbackAvailabilty();
        if(! broadcastReceiverStarted){
            startBroadcastReceiver();
        }
        if(! broadcastReceiverRegistered){
            registerBroadcastReceiver();
        }
        if(! informationalNotificationsStarted){
            startInformationalNotifications();
        }
        if(! feedbackNotificationsStarted){
            startFeedbackNotifications();
        }
        if(! trackingStarted){
            startTracking();
        }
        if(lastActivityResult != null){
            Log.d(TAG_RUNTIME, "LastActivityResult is not null");
            if(lastActivityResult instanceof MedicalResult){
                handleMedicalResult();
            }
            if(lastActivityResult instanceof FeedbackResult){
                handleFeedbackResult();
            }
            Log.d(TAG_RUNTIME, "Result: " + userData);
            lastActivityResult = null;
        }
    }

    /*
        Database Functions
     */

    private void setDatabaseReferenceWithValueEventListener(final String userID){
            DatabaseReference databaseReference = FirebaseConfig.getDatabaseReference().child("pacientes");
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for(DataSnapshot dts : dataSnapshot.getChildren()) {
                                if(dts.getKey().equals(userID)){
                                    UserData userDataInDatabase = FirebaseConfig.getUserData((HashMap)dts.getValue());
                                    Log.d(TAG_RUNTIME,"UserData Database Update");
                                    Log.d(TAG_RUNTIME, userDataInDatabase.toString());
                                    userData = userDataInDatabase;
                                    if( databaseChangeID++ == 0){
                                        updateUserDataTime();
                                    }
                                    else {
                                        executeStartTasks();
                                    }
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Log.d(TAG_DB, "Database Error!");
                            Log.d(TAG_DB, databaseError.getMessage());
                            Log.d(TAG_DB, databaseError.getDetails());
                        }
                    });
    }

    private void updateDatabase(UserData userData){
        // Save Data to Database
        if(userData != null){
            DatabaseReference databaseReference = FirebaseConfig.getDatabaseReference().child("pacientes")
                    .child(userData.getID());
            databaseReference.setValue(userData);
            databaseReference = FirebaseConfig.getDatabaseReference().child("lastDatabaseUpdate");
            SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            String lastUpdate = dt.format(new Date());
            databaseReference.setValue(lastUpdate);
        }
        else {
            Toast.makeText(this, "Erro ao armazenar dados do usuario", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    /*
        Notifications and System-Related functions
     */

    private void showNotification(String contentTitle, String contentText, int uniqueId){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle(contentTitle)
                .setContentText(contentText)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(uniqueId,builder.build());
    }

    private void startInformationalNotifications() {
        scheduler.postDelayed(notificationTask,Constants.NOTIFICATION_INTERVAL_IN_MILLIS);
        Log.d(TAG_SERVICE,"Started Information Notifications");
        informationalNotificationsStarted = true;
    }

    private void stopInformationalNotifications() {
        scheduler.removeCallbacks(notificationTask);
        Log.d(TAG_SERVICE,"Stopped Information Notifications");
        informationalNotificationsStarted = false;
    }

    private void startFeedbackNotifications() {
//        scheduler.postDelayed(feedbackTask,Constants.FEEDBACK_INTERVAL_IN_MILLIS);
        if(userData.name.contains("demo")){
            scheduler.postDelayed(feedbackTask, 60000);
        }
        else{
            scheduler.postDelayed(feedbackTask, Constants.FEEDBACK_INTERVAL_IN_MILLIS);
        }
        Log.d(TAG_SERVICE,"Started Feedback Notifications");
        feedbackNotificationsStarted = true;
    }

    private void stopFeedbackNotifications() {
        scheduler.removeCallbacks(feedbackTask);
        Log.d(TAG_SERVICE,"Stopped Feedback Notifications");
        feedbackNotificationsStarted = false;
    }

    private boolean inNotificationTime(int hourOfDay) {
        return true;
    }

    private int getHourOfDay(){
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
        return hourOfDay;
    }

    private void vibrate(int pattern) {
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        switch (pattern){
            case Constants.FEEDBACK:{
                long[] wave = {0,1000,500,1000,500,1000,500};
                v.vibrate(wave,-1);
                break;
            }
            case Constants.UPDATE:{
                long[] wave = {0,1000,500,1000,500};
                v.vibrate(wave,-1);
                break;
            }
            default:{
                long[] wave = {0,1000,500};
                v.vibrate(wave,-1);
                break;
            }
        }
    }

    /*
        Backgorund Service Functions
     */

    private void handleUserActivity(int confidence, String time) {
        SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        try{
            date = dt.parse(time);
        }
        catch (Exception e){
            Log.d("Tag_Error", e.getMessage());
            Log.d("Tag_Error", e.toString());
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, (int)(-1 * Constants.MOTION_INTERVAL_IN_MINUTES));
        Date startDate = calendar.getTime();
        String start = dt.format(startDate);
        MotionActivity motionActivity = new MotionActivity(start, time, confidence);
        if(userData.motionTrackList == null){
            userData.motionTrackList = new ArrayList<>();
        }
        userData.motionTrackList.add(motionActivity);
        userData.motionMinutes += Constants.MOTION_INTERVAL_IN_MINUTES;
        updateDatabase(userData);
        Log.d(TAG_SERVICE,"MotionActivityAdded: " + motionActivity.toString());
//        showNotification("Caminhada",
//                "Você Realizou " + userData.motionMinutes + " Minutos De Caminhada Até Agora!",
//                notificationID++);
    }

    private void startTracking() {
        Intent intent1 = new Intent(this, BackgroundDetectedActivitiesService.class);
        startService(intent1);
        trackingStarted = true;
    }

    private void stopTracking() {
        Intent intent = new Intent(this, BackgroundDetectedActivitiesService.class);
        stopService(intent);
        trackingStarted = false;
    }

    /*
        Broadcast Receiver Functions
     */

    private void startBroadcastReceiver() {
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(Constants.MOTION_ACTION)) {
                    if(intent.getIntExtra("intentID", -1) > 0){
                        int confidence = intent.getIntExtra("confidence", 0);
                        String time = intent.getStringExtra("time");
                        handleUserActivity(confidence, time);
                    }
                }
            }
        };
        broadcastReceiverStarted = true;
    }

    private void stopBroadcastReceiver() {
        broadcastReceiver = null;
        broadcastReceiverStarted = false;
    }

    private void registerBroadcastReceiver() {
        LocalBroadcastManager.getInstance(MenuActivity.this).registerReceiver(broadcastReceiver,
                new IntentFilter(Constants.MOTION_ACTION));
        broadcastReceiverRegistered = true;
    }

    private void unregisterBroadcastReceiver() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
        broadcastReceiverRegistered = false;
    }

    /*
        Authentication Functions
     */

    private void logOut() {
        SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        userData.lastLogoutTime = dt.format(new Date());
        updateDatabase(userData);
        FirebaseConfig.getFirebaseAuth().signOut();
        //showNotification("Log Out", dt.format(new Date()), notificationID++ );
        Log.d(TAG_AUTH, "Log Out Sucessfull");
        vibrate(Constants.LOGOUT);
    }
}
