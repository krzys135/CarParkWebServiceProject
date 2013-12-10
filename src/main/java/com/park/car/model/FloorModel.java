package com.park.car.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

/**
 * Created by Jarek on 08.12.13.
 */
@JsonAutoDetect
public class FloorModel {
    private int id;
    private int place_id;
    private int floornumber;
    private int allspaces;
    private int freespaces;

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "FloorModel{" +
                "id=" + id +
                ", place_id=" + place_id +
                ", floornumber=" + floornumber +
                ", allspaces=" + allspaces +
                ", freespaces=" + freespaces +
                '}';
    }

    public FloorModel(int id, int place_id, int floornumber, int allspaces, int freespaces) {
        this.id = id;
        this.place_id = place_id;
        this.floornumber = floornumber;
        this.allspaces = allspaces;
        this.freespaces = freespaces;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPlace_id() {
        return place_id;
    }

    public void setPlace_id(int place_id) {
        this.place_id = place_id;
    }

    public int getFloornumber() {
        return floornumber;
    }

    public void setFloornumber(int floornumber) {
        this.floornumber = floornumber;
    }

    public int getAllspaces() {
        return allspaces;
    }

    public void setAllspaces(int allspaces) {
        this.allspaces = allspaces;
    }

    public int getFreespaces() {
        return freespaces;
    }

    public void setFreespaces(int freespaces) {
        this.freespaces = freespaces;
    }
}
