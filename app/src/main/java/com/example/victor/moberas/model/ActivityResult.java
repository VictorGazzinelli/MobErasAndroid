package com.example.victor.moberas.model;

import java.io.Serializable;

public class ActivityResult implements Serializable {

    private int id;
    private Object result;

    public ActivityResult(int id, Object result) {
        this.id = id;
        this.result = result;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
