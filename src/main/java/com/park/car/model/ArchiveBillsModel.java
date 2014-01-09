package com.park.car.model;

import java.util.Calendar;

public class ArchiveBillsModel extends BillsModel {
    private Calendar exitDateTime;

    public Calendar getExitDateTime() {
        return exitDateTime;
    }

    public void setExitDateTime(Calendar exitDateTime) {
        this.exitDateTime = exitDateTime;
    }
}
