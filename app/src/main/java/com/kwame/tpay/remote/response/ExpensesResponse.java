package com.kwame.tpay.remote.response;

import com.google.gson.annotations.Expose;
import com.kwame.tpay.models.Expense;

import java.util.List;

public class ExpensesResponse {

    @Expose
    private String status;

    @Expose
    private String message;

    @Expose
    private List<Expense> data;


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

    public List<Expense> getData() {
        return data;
    }

    public void setData(List<Expense> data) {
        this.data = data;
    }
}
