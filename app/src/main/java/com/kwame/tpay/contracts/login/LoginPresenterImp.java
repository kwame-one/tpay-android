package com.kwame.tpay.contracts.login;

import com.kwame.tpay.utils.AppUtils;

public class LoginPresenterImp implements LoginPresenter {

    private LoginListener loginListener;

    public LoginPresenterImp(LoginListener loginListener) {
        this.loginListener = loginListener;
    }

    @Override
    public void login(String phone, String password) {
       // String[] strs = {phone, password};

        if (phone.isEmpty())
            loginListener.onLoginFailure("Phone is required");
        else if (password.isEmpty())
            loginListener.onLoginFailure("Password is required");
        else {

            loginListener.onLoginSuccess();
        }
    }
}
