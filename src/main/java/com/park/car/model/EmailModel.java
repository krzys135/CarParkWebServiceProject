package com.park.car.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect
public class EmailModel {
    private String email;

    public EmailModel() {
    }

    public EmailModel(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}