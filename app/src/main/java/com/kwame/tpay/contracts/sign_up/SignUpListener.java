package com.kwame.tpay.contracts.sign_up;

public interface SignUpListener {

    void onSignUpSuccess();

    void onSignUpFailure(String message);
}
