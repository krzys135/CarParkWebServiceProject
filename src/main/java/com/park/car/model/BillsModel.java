package com.park.car.model;

import java.util.Calendar;

public class BillsModel {
   private Calendar enterDateTime;
   private Integer place;
   private Integer floornumer;
   private String seg;


    public Integer getPlace() {
        return place;
    }

    public void setPlace(Integer place) {
        this.place = place;
    }

    public Integer getFloornumer() {
        return floornumer;
    }

    public void setFloornumer(Integer floornumer) {
        this.floornumer = floornumer;
    }

    public String getSeg() {
        return seg;
    }

    public void setSeg(String seg) {
        this.seg = seg;
    }

    public Calendar getEnterDateTime() {
        return enterDateTime;
    }

    public void setEnterDateTime(Calendar enterDateTime) {
        this.enterDateTime = enterDateTime;
    }
}
