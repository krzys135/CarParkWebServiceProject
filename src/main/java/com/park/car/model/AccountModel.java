package com.park.car.model;

import java.util.List;

public class AccountModel {
    private double amount;
    private List<PaymentModel> paymentModelList;

    public List<PaymentModel> getPaymentModelList() {
        return paymentModelList;
    }

    public void setPaymentModelList(List<PaymentModel> paymentModelList) {
        this.paymentModelList = paymentModelList;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
