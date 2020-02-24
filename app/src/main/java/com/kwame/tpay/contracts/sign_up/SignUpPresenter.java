package com.kwame.tpay.contracts.sign_up;

public interface SignUpPresenter {

    void signUp(String surname, String otherNames, String phone, String password, String userType);

    void setUpVehicle(String model, String number);
}
