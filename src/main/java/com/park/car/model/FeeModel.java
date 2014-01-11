package com.park.car.model;

import java.sql.Timestamp;

public class FeeModel {
    private int id, order, duration, maxdur, maxsum, charge, segment, place_id;
    private Timestamp validfrom, validto;

    public FeeModel(int id, int order, int duration, int maxdur, int maxsum, int charge, int segment,
                    Timestamp validfrom, Timestamp validto, int place_id) {
        this.id = id;
        this.order = order;
        this.duration = duration;
        this.maxdur = maxdur;
        this.maxsum = maxsum;
        this.charge = charge;
        this.segment = segment;
        this.place_id = place_id;
        this.validfrom = validfrom;
        this.validto = validto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getMaxdur() {
        return maxdur;
    }

    public void setMaxdur(int maxdur) {
        this.maxdur = maxdur;
    }

    public int getMaxsum() {
        return maxsum;
    }

    public void setMaxsum(int maxsum) {
        this.maxsum = maxsum;
    }

    public int getCharge() {
        return charge;
    }

    public void setCharge(int charge) {
        this.charge = charge;
    }

    public int getSegment() {
        return segment;
    }

    public void setSegment(int segment) {
        this.segment = segment;
    }

    public int getPlace_id() {
        return place_id;
    }

    public void setPlace_id(int place_id) {
        this.place_id = place_id;
    }

    public Timestamp getValidfrom() {
        return validfrom;
    }

    public void setValidfrom(Timestamp validfrom) {
        this.validfrom = validfrom;
    }

    public Timestamp getValidto() {
        return validto;
    }

    public void setValidto(Timestamp validto) {
        this.validto = validto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FeeModel feeModel = (FeeModel) o;

        if (charge != feeModel.charge) return false;
        if (duration != feeModel.duration) return false;
        if (id != feeModel.id) return false;
        if (maxdur != feeModel.maxdur) return false;
        if (maxsum != feeModel.maxsum) return false;
        if (order != feeModel.order) return false;
        if (place_id != feeModel.place_id) return false;
        if (segment != feeModel.segment) return false;
        if (validfrom != null ? !validfrom.equals(feeModel.validfrom) : feeModel.validfrom != null) return false;
        if (validto != null ? !validto.equals(feeModel.validto) : feeModel.validto != null) return false;

        return true;
    }

}
