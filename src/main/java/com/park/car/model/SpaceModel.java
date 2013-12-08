package com.park.car.model;

/**
 * Created by Jarek on 07.12.13.
 */
public class SpaceModel {
    public SpaceModel(int id, String place, String state, int floor_id, String sensor) {
        this.id = id;
        this.place = place;
        this.state = state;
        this.floor_id = floor_id;
        this.sensor = sensor;
    }

    @Override
    public String toString() {
        return "SpaceModel{" +
                "id=" + id +
                ", place='" + place + '\'' +
                ", state='" + state + '\'' +
                ", floor_id=" + floor_id +
                ", sensor='" + sensor + '\'' +
                '}';
    }

    public String getSensor() {
        return sensor;
    }

    public void setSensor(String sensor) {
        this.sensor = sensor;
    }

    private int id;
    private String place;
    private String state;
    private int floor_id;
    private String sensor;

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

    public int getFloor_id() {
        return floor_id;
    }

    public void setFloor_id(int floor_id) {
        this.floor_id = floor_id;
    }
}
