package com.example.victor.moberas.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Feedback implements Serializable {

    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    public int ratingPain;
    public int ratingVomitingAndNausea;
    public int ratingDietTolerance;
    public int ratingVolumeDiuresis;
    public int ratingGeneralHealth;
    public String flatusElimination;
    public String stoolElimination;
    public String time;

    public Feedback(){
        this.ratingPain = -1;
        this.ratingVomitingAndNausea = -1;
        this.ratingDietTolerance = -1;
        this.ratingVolumeDiuresis = -1;
        this.ratingGeneralHealth = -1;
        this.flatusElimination = "???";
        this.stoolElimination = "???";
        this.time = simpleDateFormat.format(new Date());
    }

    @Override
    public String toString() {
        return "Feedback{" +
                "ratingPain=" + ratingPain +
                ", ratingVomitingAndNausea=" + ratingVomitingAndNausea +
                ", ratingDietTolerance=" + ratingDietTolerance +
                ", ratingVolumeDiuresis=" + ratingVolumeDiuresis +
                ", ratingGeneralHealth=" + ratingGeneralHealth +
                ", flatusElimination='" + flatusElimination + '\'' +
                ", stoolElimination='" + stoolElimination + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
