package com.example.victor.moberas.model;
import java.io.Serializable;
import java.util.ArrayList;

public class UserData implements Serializable {

    public UserData(){}

    public String birthDate;
    public String name;
    public String lastFeedbackTime;
    public String lastLoginTime;
    public String lastLogoutTime;
    public long motionMinutes;
    public ArrayList<MotionActivity> motionTrackList;
    public ArrayList<Feedback> feedbackList;
    public TimePick bladderCatheterRemovalTime;
    public TimePick firstOralDietIntakeTime;
    public TimePick firstDiuresisTime;
    public TimePick firstFlatusTime;
    public TimePick firstStoolTime;
    public TimePick dripRemovalTime;


    public String getID() {
        return name.trim().toLowerCase().replaceAll(" ", "_") + "-" + birthDate.replaceAll("/","_");
    }

    @Override
    public String toString() {
        return "UserData{" +
                "birthDate='" + birthDate + '\'' +
                ", name='" + name + '\'' +
                ", lastFeedbackTime='" + lastFeedbackTime + '\'' +
                ", lastLoginTime='" + lastLoginTime + '\'' +
                ", lastLogoutTime='" + lastLogoutTime + '\'' +
                ", motionMinutes=" + motionMinutes +
                ", motionTrackList=" + motionTrackList +
                ", feedbackList=" + feedbackList +
                ", bladderCatheterRemovalTime=" + bladderCatheterRemovalTime +
                ", firstOralDietIntakeTime=" + firstOralDietIntakeTime +
                ", firstDiuresisTime=" + firstDiuresisTime +
                ", firstFlatusTime=" + firstFlatusTime +
                ", firstStoolTime=" + firstStoolTime +
                ", dripRemovalTime=" + dripRemovalTime +
                '}';
    }
}
