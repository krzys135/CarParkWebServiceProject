package com.park.car.model;

public class SegmentModel {
    private int id;
    private int floor_id;
    private String seg;
    private int allspaces;
    private int freespaces;

    public SegmentModel(int id, int floor_id, String seg, int allspaces, int freespaces) {
        this.id = id;
        this.floor_id = floor_id;
        this.seg = seg;
        this.allspaces = allspaces;
        this.freespaces = freespaces;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFloor_id() {
        return floor_id;
    }

    public void setFloor_id(int floor_id) {
        this.floor_id = floor_id;
    }

    public String getSeg() {
        return seg;
    }

    public void setSeg(String seg) {
        this.seg = seg;
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
