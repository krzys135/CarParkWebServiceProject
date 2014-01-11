package com.park.car.model;

import java.util.List;

public class ResponseModel {

    private String message;
    private SpaceModel spaceModel;
    private TicketModel ticketModel;
    private BillsModel billsModel;
    private List<ArchiveBillsModel> archiveBillsModelList;

    public List<ArchiveBillsModel> getArchiveBillsModelList() {
        return archiveBillsModelList;
    }

    public void setArchiveBillsModelList(List<ArchiveBillsModel> archiveBillsModelList) {
        this.archiveBillsModelList = archiveBillsModelList;
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
