package com.park.car.model;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by Jarek on 26.01.14.
 */
public class SpacelogModel {
    private String prevstate;
    private String newstate;
    private String prevsensor;
    private String newsensor;
    private Timestamp date;
    private int space_id;
    private int user_id;
    private int ticket_id;

    public SpacelogModel(String prevstate, String newstate, String prevsensor, String newsensor, Timestamp date, int space_id, int user_id, int ticket_id) {
        this.prevstate = prevstate;
        this.newstate = newstate;
        this.prevsensor = prevsensor;
        this.newsensor = newsensor;
        this.date = date;
        this.space_id = space_id;
        this.user_id = user_id;
        this.ticket_id = ticket_id;
    }

    public String getPrevstate() {
        return prevstate;
    }

    public void setPrevstate(String prevstate) {
        this.prevstate = prevstate;
    }

    public String getNewstate() {
        return newstate;
    }

    public void setNewstate(String newstate) {
        this.newstate = newstate;
    }

    public String getPrevsensor() {
        return prevsensor;
    }

    public void setPrevsensor(String prevsensor) {
        this.prevsensor = prevsensor;
    }

    public String getNewsensor() {
        return newsensor;
    }

    public void setNewsensor(String newsensor) {
        this.newsensor = newsensor;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public int getSpace_id() {
        return space_id;
    }

    public void setSpace_id(int space_id) {
        this.space_id = space_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getTicket_id() {
        return ticket_id;
    }

    public void setTicket_id(int ticket_id) {
        this.ticket_id = ticket_id;
    }
}
