package com.park.car.model;

import java.util.Calendar;

public class ArchiveBillsModel extends BillsModel {
    private Calendar exitDateTime;
    private TicketModel ticketModel;

    public TicketModel getTicketModel() {
        return ticketModel;
    }

    public void setTicketModel(TicketModel ticketModel) {
        this.ticketModel = ticketModel;
    }

    public Calendar getExitDateTime() {
        return exitDateTime;
    }

    public void setExitDateTime(Calendar exitDateTime) {
        this.exitDateTime = exitDateTime;
    }
}
