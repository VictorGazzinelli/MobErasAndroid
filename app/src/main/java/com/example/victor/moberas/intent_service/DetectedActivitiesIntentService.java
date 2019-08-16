package com.example.victor.moberas.intent_service;

import android.app.IntentService;
import android.content.Intent;
//import android.support.v4.content.LocalBroadcastManager;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.victor.moberas.model.Constants;
import com.google.android.gms.location.ActivityRecognitionResult;
import com.google.android.gms.location.DetectedActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

;

public class DetectedActivitiesIntentService extends IntentService {

    public static int intentID = 0;

    public DetectedActivitiesIntentService() {
        // Use the TAG to name the worker thread.
        super("Tag_Worker_Thread_Detected_Activities");
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void onHandleIntent(Intent intent) {
        if(ActivityRecognitionResult.hasResult(intent)){
            ActivityRecognitionResult result = ActivityRecognitionResult.extractResult(intent);
            ArrayList<DetectedActivity> detectedActivities = (ArrayList) result.getProbableActivities();
            intentID++;
            for (DetectedActivity activity : detectedActivities) {
                if(activity.getType() == DetectedActivity.WALKING ){
                    broadcastActivity(activity, result.getTime());
                }
            }
        }
    }

    private void broadcastActivity(DetectedActivity activity, long time) {
        Intent intent = new Intent(Constants.MOTION_ACTION);
        intent.putExtra("intentID", intentID);
        intent.putExtra("type", activity.getType());
        intent.putExtra("confidence", activity.getConfidence());
        intent.putExtra("time",
                new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date(time)));
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }
}
