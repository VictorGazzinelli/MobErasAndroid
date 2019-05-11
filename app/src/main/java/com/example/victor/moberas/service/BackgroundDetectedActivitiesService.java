package com.example.victor.moberas.service;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.example.victor.moberas.intent_service.DetectedActivitiesIntentService;
import com.example.victor.moberas.model.Constants;
import com.google.android.gms.location.ActivityRecognitionClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;


public class BackgroundDetectedActivitiesService extends Service {

    protected static final String TAG_SERVICE = "Tag_Service";

    private Intent mIntentService;
    private PendingIntent mPendingIntent;
    private ActivityRecognitionClient mActivityRecognitionClient;

    IBinder mBinder = new BackgroundDetectedActivitiesService.LocalBinder();

    public class LocalBinder extends Binder {
        public BackgroundDetectedActivitiesService getServerInstance() {
            return BackgroundDetectedActivitiesService.this;
        }
    }

    public BackgroundDetectedActivitiesService() {

    }

    @Override
    public void onCreate() {
        super.onCreate();
        mActivityRecognitionClient = new ActivityRecognitionClient(this);
        mIntentService = new Intent(this, DetectedActivitiesIntentService.class);
        mPendingIntent = PendingIntent.getService(this, 1, mIntentService, PendingIntent.FLAG_UPDATE_CURRENT);
        requestActivityTransitionUpdates();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        removeActivityTransitionUpdates();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }

    public void requestActivityTransitionUpdates() {
        Task<Void> task = mActivityRecognitionClient.requestActivityUpdates(Constants.MOTION_INTERVAL, mPendingIntent);
        task.addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getApplicationContext() , "Sensor De Movimento Ativado" , Toast.LENGTH_LONG).show();
                Log.d(TAG_SERVICE, "Activity Updates request was successfull");
            }
        });
        task.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG_SERVICE, "Activity Updates request has failed");
                Log.d(TAG_SERVICE, e.getMessage());
            }
        });
    }

    public void removeActivityTransitionUpdates() {
        Task<Void> task = mActivityRecognitionClient.removeActivityUpdates(mPendingIntent);
        task.addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getApplicationContext() , "Sensor De Movimento Desativado" , Toast.LENGTH_LONG).show();
                Log.d(TAG_SERVICE, "Activity Updates removal was successfull");
            }
        });
        task.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG_SERVICE, "Activity Updates removal has failed");
                Log.d(TAG_SERVICE, e.getMessage());
            }
        });
    }
}
