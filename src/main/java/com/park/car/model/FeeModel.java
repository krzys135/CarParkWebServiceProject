package com.park.car.model;

public class FeeModel {
    private int id, order, duration, maxdur, maxsum, charge, segment, place_id;
    private TicketModel validfrom, validto;

    public FeeModel(int id, int order, int duration, int maxdur, int maxsum, int charge, int segment,
                    TicketModel validfrom, TicketModel validto, int place_id) {
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

    public TicketModel getValidfrom() {
        return validfrom;
    }

    public void setValidfrom(TicketModel validfrom) {
        this.validfrom = validfrom;
    }

    public TicketModel getValidto() {
        return validto;
    }

    public void setValidto(TicketModel validto) {
        this.validto = validto;
    }
}
