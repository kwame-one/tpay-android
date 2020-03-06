package com.kwame.tpay.remote.response;

import com.google.gson.annotations.SerializedName;
import com.kwame.tpay.models.Auth;

public class AuthResponse {

    @SerializedName("status")
    private String status;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private Auth user;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Auth getUser() {
        return user;
    }

    public void setUser(Auth user) {
        this.user = user;
    }
}
