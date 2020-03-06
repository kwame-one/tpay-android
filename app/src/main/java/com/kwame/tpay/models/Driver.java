package com.kwame.tpay.models;

import com.google.gson.annotations.SerializedName;

public class Driver {

    @SerializedName("id")
    private String id;

    @SerializedName("vehicle_model")
    private String vehicleModel;

    @SerializedName("vehicle_number")
    private String vehicleNumber;

    @SerializedName("balance")
    private double balance;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVehicleModel() {
        return vehicleModel;
    }

    public void setVehicleModel(String vehicleModel) {
        this.vehicleModel = vehicleModel;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
