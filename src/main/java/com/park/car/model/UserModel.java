package com.park.car.model;

import java.util.List;

public class UserModel {
    private Integer id = -1;
    private String email = null;
    private List<TicketModel> ticketModelList = null;
    private AccountModel accountModel = null;

    public UserModel() {
    }

    public UserModel(Integer id, String email) {
        this.id = id;
        this.email = email;
    }

    public UserModel(Integer id, String email, AccountModel accountModel) {
        this.id = id;
        this.email = email;
        this.accountModel = accountModel;
    }

    public UserModel(Integer id, String email, List<TicketModel> ticketModelList, AccountModel accountModel) {
        this.id = id;
        this.email = email;
        this.ticketModelList = ticketModelList;
        this.accountModel = accountModel;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<TicketModel> getTicketModelList() {
        return ticketModelList;
    }

    public void setTicketModelList(List<TicketModel> ticketModelList) {
        this.ticketModelList = ticketModelList;
    }

    public AccountModel getAccountModel() {
        return accountModel;
    }

    public void setAccountModel(AccountModel accountModel) {
        this.accountModel = accountModel;
    }
}
