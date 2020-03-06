package com.kwame.tpay.models;

import com.google.gson.annotations.SerializedName;

public class Transaction {

    @SerializedName("id")
    private int id;

    @SerializedName("total")
    private double total;

    @SerializedName("type")
    private String type;

    @SerializedName("date")
    private String date;

    @SerializedName("code")
    private String transactionId;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }
}
