package com.kwame.tpay.contracts.login;

public interface LoginListener {

    void onLoginSuccess();

    void onLoginFailure(String message);

    void onDriverNotSetup();

    void onWalletNotScanned();
}
