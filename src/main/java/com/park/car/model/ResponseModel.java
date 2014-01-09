package com.park.car.model;

public class ResponseModel {

    private String message;
    private SpaceModel spaceModel;
    private TicketModel ticketModel;
    private BillsModel billsModel;
    private ArchiveBillsModel archiveBillsModel;

    public ArchiveBillsModel getArchiveBillsModel() {
        return archiveBillsModel;
    }

    public void setArchiveBillsModel(ArchiveBillsModel archiveBillsModel) {
        this.archiveBillsModel = archiveBillsModel;
    }

    public BillsModel getBillsModel() {
        return billsModel;
    }

    public void setBillsModel(BillsModel billsModel) {
        this.billsModel = billsModel;
    }

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
