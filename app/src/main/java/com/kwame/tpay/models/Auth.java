package com.kwame.tpay.models;

import com.google.gson.annotations.SerializedName;

public class Auth {

    @SerializedName("id")
    private int id;

    @SerializedName("surname")
    private String surname;

    @SerializedName("othernames")
    private String otherNames;

    @SerializedName("contact")
    private String contact;

    @SerializedName("role")
    private String role;

    @SerializedName("wallet")
    private Wallet wallet;

    @SerializedName("driver")
    private Driver driver;

    @SerializedName("access_token")
    private String token;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getOtherNames() {
        return otherNames;
    }

    public void setOtherNames(String otherNames) {
        this.otherNames = otherNames;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
