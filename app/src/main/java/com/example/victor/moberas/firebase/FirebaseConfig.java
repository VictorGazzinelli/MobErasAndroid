package com.example.victor.moberas.firebase;

import com.example.victor.moberas.model.Feedback;
import com.example.victor.moberas.model.MotionActivity;
import com.example.victor.moberas.model.TimePick;
import com.example.victor.moberas.model.UserData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class FirebaseConfig {

    private static DatabaseReference databaseReference;
    private static FirebaseAuth firebaseAuth;

    public static DatabaseReference getDatabaseReference(){
        if(databaseReference == null){
            return FirebaseDatabase.getInstance().getReference();
        }
        return databaseReference;
    }

    public static FirebaseAuth getFirebaseAuth(){
        if(firebaseAuth == null){
            return FirebaseAuth.getInstance();
        }
        return firebaseAuth;
    }

    public static UserData getUserData(HashMap hashMap){
        UserData userData = new UserData();
        if(hashMap.get("name") != null){
            userData.name = hashMap.get("name").toString();
        }
        if(hashMap.get("birthDate") != null){
            userData.birthDate = hashMap.get("birthDate").toString();
        }
        if(hashMap.get("lastFeedbackTime") != null){
            userData.lastFeedbackTime = hashMap.get("lastFeedbackTime").toString();
        }
        if(hashMap.get("lastLoginTime") != null){
            userData.lastLoginTime = hashMap.get("lastLoginTime").toString();
        }
        if(hashMap.get("lastLogoutTime") != null){
            userData.lastLogoutTime = hashMap.get("lastLogoutTime").toString();
        }
        if(hashMap.get("motionMinutes") != null){
            userData.motionMinutes = (Long)hashMap.get("motionMinutes");
        }
        if(hashMap.get("motionTrackList") != null){
            userData.motionTrackList = (ArrayList<MotionActivity>)hashMap.get("motionTrackList");
        }
        if(hashMap.get("feedbackList") != null){
            userData.feedbackList = (ArrayList<Feedback>)hashMap.get("feedbackList");
        }
        if(hashMap.get("bladderCatheterRemovalTime") != null){
            userData.bladderCatheterRemovalTime = getTimePick((HashMap) hashMap.get("bladderCatheterRemovalTime"));
        }
        if(hashMap.get("firstOralDietIntakeTime") != null){
            userData.firstOralDietIntakeTime = getTimePick((HashMap) hashMap.get("firstOralDietIntakeTime"));
        }
        if(hashMap.get("firstDiuresisTime") != null){
            userData.firstDiuresisTime = getTimePick((HashMap) hashMap.get("firstDiuresisTime"));
        }
        if(hashMap.get("firstFlatusTime") != null){
            userData.firstFlatusTime = getTimePick((HashMap) hashMap.get("firstFlatusTime"));
        }
        if(hashMap.get("firstStoolTime") != null){
            userData.firstStoolTime = getTimePick((HashMap) hashMap.get("firstStoolTime"));
        }
        if(hashMap.get("dripRemovalTime") != null){
            userData.dripRemovalTime = getTimePick((HashMap) hashMap.get("dripRemovalTime"));
        }
        return userData;
    }

    private static TimePick getTimePick(HashMap hashMap){
        TimePick timePick = new TimePick();
        timePick.time = hashMap.get("time").toString();
        timePick.timestamp = hashMap.get("timestamp").toString();
        return  timePick;
    }
}
