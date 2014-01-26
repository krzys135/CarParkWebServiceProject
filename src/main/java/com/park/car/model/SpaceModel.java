package com.park.car.model;

public class SpaceModel {
    private int id;
    private String place;
    private String state;
    private int segment_id;
    private String sensor;

    public SpaceModel(int id, String place, String state, int segment_id, String sensor) {
        this.id = id;
        this.place = place;
        this.state = state;
        this.segment_id = segment_id;
        this.sensor = sensor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getSegment_id() {
        return segment_id;
    }

    public void setSegment_id(int segment_id) {
        this.segment_id = segment_id;
    }

    public String getSensor() {
        return sensor;
    }

    public void setSensor(String sensor) {
        this.sensor = sensor;
    }
}
