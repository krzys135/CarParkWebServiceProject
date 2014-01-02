package com.park.car.model;

import java.sql.Time;

public class TicketModel {
   private int id;
   private double fee;
   private Time duration;
   private String state;
   private int user_id;
   private int space_id;

    public TicketModel(int id, double fee, Time duration, String state, int user_id, int space_id) {
        this.id = id;
        this.fee = fee;
        this.duration=duration;
        this.state = state;
        this.user_id = user_id;
        this.space_id = space_id;
    }

    public Time getDuration() {
        return duration;
    }

    public void setDuration(Time duration) {
        this.duration = duration;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getSpace_id() {
        return space_id;
    }

    public void setSpace_id(int space_id) {
        this.space_id = space_id;
    }
}
