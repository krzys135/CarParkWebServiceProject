package com.park.car.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.sql.Date;
import java.sql.Time;

@JsonAutoDetect
public class EmailModel {
    private String emial;

    public String getEmial() {
        return emial;
    }

    public void setEmial(String emial) {
        this.emial = emial;
    }
}