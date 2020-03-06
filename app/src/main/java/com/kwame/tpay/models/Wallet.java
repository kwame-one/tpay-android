package com.kwame.tpay.models;

import com.google.gson.annotations.SerializedName;

public class Wallet {

    @SerializedName("id")
    private int id;

    @SerializedName("qr_code")
    private String QRCode;

    @SerializedName("balance")
    private double balance;

    @SerializedName("status")
    private String status;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQRCode() {
        return QRCode;
    }

    public void setQRCode(String QRCode) {
        this.QRCode = QRCode;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
