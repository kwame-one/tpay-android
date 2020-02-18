package com.kwame.tpay.contracts.sign_up;

public class SignUpPresenterImp implements SignUpPresenter {

    private SignUpListener signUpListener;

    public SignUpPresenterImp(SignUpListener signUpListener) {
        this.signUpListener = signUpListener;
    }

    @Override
    public void signUp(String phone, String password, String passwordConfirmation) {

    }
}
