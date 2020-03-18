package com.kwame.tpay.contracts.change_password;

public interface ChangePasswordListener {

    void onChangePasswordSuccess();

    void onChangePasswordFailure(String message);
}
