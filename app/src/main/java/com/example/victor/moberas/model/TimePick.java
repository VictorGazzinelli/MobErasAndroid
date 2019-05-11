package com.example.victor.moberas.model;

import java.io.Serializable;

public class TimePick implements Serializable {

    public TimePick(){}

    public String time;
    public String timestamp;

    @Override
    public String toString() {
        return "TimePick{" +
                "time='" + time + '\'' +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }
}
