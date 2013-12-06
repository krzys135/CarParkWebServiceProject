package com.park.car.service;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.sql.Date;
import java.sql.Time;

@JsonAutoDetect
public class Emial {
    private String emial;
    private Date date;
    private Time time;

    public String getEmial() {
        return emial;
    }

    public void setEmial(String emial) {
        this.emial = emial;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public Date getDate() {
        return date;
    }

    public Time getTime() {
        return time;
    }
}