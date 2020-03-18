package com.kwame.tpay.contracts.change_password;

public class ChangePasswordPresenterImp implements ChangePasswordPresenter {

    private ChangePasswordListener listener;

    public ChangePasswordPresenterImp(ChangePasswordListener listener) {
        this.listener = listener;
    }

    @Override
    public void changePassword(String current, String newPassword, String confirmPassword) {

    }
}
