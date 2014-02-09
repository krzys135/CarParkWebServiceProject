package com.park.car.model;

import java.sql.Timestamp;
import java.util.Calendar;

public class PaymentModel {
    private Integer id;
    private String amount, paid;
    private Calendar date;
    private String ticket_id, user_id;
    private Timestamp tstmp;

    public PaymentModel(Integer id, String amount, String paid, Calendar date, String ticket_id, String user_id) {
        this.id = id;
        this.amount = amount;
        this.paid = paid;
        this.date = date;
        this.ticket_id = ticket_id;
        this.user_id = user_id;
    }

    public PaymentModel(Integer id, String amount, String paid, String ticket_id, String user_id, Timestamp tstmp) {
        this.id = id;
        this.amount = amount;
        this.paid = paid;
        this.ticket_id = ticket_id;
        this.user_id = user_id;
        this.tstmp = tstmp;
    }

    public Timestamp getTstmp() {
        return tstmp;
    }

    public void setTstmp(Timestamp tstmp) {
        this.tstmp = tstmp;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPaid() {
        return paid;
    }

    public void setPaid(String paid) {
        this.paid = paid;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public String getTicket_id() {
        return ticket_id;
    }

    public void setTicket_id(String ticket_id) {
        this.ticket_id = ticket_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
