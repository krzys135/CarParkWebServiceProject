package com.park.car.model;

public class ResponseModel {

    private String message;
    private SpaceModel spaceModel;
    private TicketModel ticketModel;

    public SpaceModel getSpaceModel() {
        return spaceModel;
    }

    public void setSpaceModel(SpaceModel spaceModel) {
        this.spaceModel = spaceModel;
    }

    public TicketModel getTicketModel() {
        return ticketModel;
    }

    public void setTicketModel(TicketModel ticketModel) {
        this.ticketModel = ticketModel;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
