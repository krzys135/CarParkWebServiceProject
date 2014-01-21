package com.park.car.model;

import java.util.List;

public class ResponseModel {

    private String message, message2;
    private SpaceModel spaceModel;
    private TicketModel ticketModel;
    private BillsModel billsModel;
    private PaymentModel paymentModel;
    private List<ArchiveBillsModel> archiveBillsModelList;

    public String getMessage2() {
        return message2;
    }

    public void setMessage2(String message2) {
        this.message2 = message2;
    }

    public PaymentModel getPaymentModel() {
        return paymentModel;
    }

    public void setPaymentModel(PaymentModel paymentModel) {
        this.paymentModel = paymentModel;
    }

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
