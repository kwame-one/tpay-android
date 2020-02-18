package com.kwame.tpay.contracts.login;

import com.kwame.tpay.utils.AppUtils;

public class LoginPresenterImp implements LoginPresenter {

    private LoginListener loginListener;

    public LoginPresenterImp(LoginListener loginListener) {
        this.loginListener = loginListener;
    }

    @Override
    public void login(String phone, String password) {
        String[] strs = {phone, password};

        if (!AppUtils.isValid(strs))
            loginListener.onLoginFailure("Please fill all fields");
        else {

            loginListener.onLoginSuccess();
        }
    }
}
