package com.example.victor.moberas.model;

import java.io.Serializable;

public class MotionActivity implements Serializable {
    public String start;
    public String end;
    public int confidence;

    public MotionActivity(String start, String end, int confidence) {
        this.start = start;
        this.end = end;
        this.confidence = confidence;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public int getConfidence() {
        return confidence;
    }

    public void setConfidence(int confidence) {
        this.confidence = confidence;
    }

    @Override
    public String toString() {
        return "MotionActivity{" +
                "start='" + start + '\'' +
                ", end='" + end + '\'' +
                ", confidence=" + confidence +
                '}';
    }
}
