package com.kwame.tpay.contracts.sign_up;

public class SignUpPresenterImp implements SignUpPresenter {

    private SignUpListener signUpListener;

    public SignUpPresenterImp(SignUpListener signUpListener) {
        this.signUpListener = signUpListener;
    }

    @Override
    public void signUp(String surname, String otherNames, String phone, String password, String userType) {
        if (surname.isEmpty())
            signUpListener.onSignUpFailure("Surname is required");
        else if (otherNames.isEmpty())
            signUpListener.onSignUpFailure("Other names is required");
        else if (phone.isEmpty())
            signUpListener.onSignUpFailure("Phone is required");
        else if (password.isEmpty())
            signUpListener.onSignUpFailure("Password is required");
//        else if (userType.isEmpty())
//            signUpListener.onSignUpFailure("Please choose either normal user or driver");
        else {
            signUpListener.onSignUpSuccess();
        }
    }

    @Override
    public void setUpVehicle(String model, String number) {
        if (model.isEmpty())
            signUpListener.onVehicleSetupFailure("Vehicle model is required");
        else if (number.isEmpty())
            signUpListener.onVehicleSetupFailure("Vehicle number is required");
        else {
            signUpListener.onVehicleSetupSuccess();
        }

    }
}
