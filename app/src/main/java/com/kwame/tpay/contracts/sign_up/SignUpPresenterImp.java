package com.kwame.tpay.contracts.sign_up;

import com.kwame.tpay.remote.ApiClient;
import com.kwame.tpay.remote.response.AuthResponse;
import com.kwame.tpay.remote.response.DriverResponse;
import com.kwame.tpay.utils.AppUtils;
import com.kwame.tpay.utils.GoodPrefs;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        else if (userType.isEmpty())
            signUpListener.onSignUpFailure("Please choose either normal user or driver");
        else {
            Call<AuthResponse> call = ApiClient.createService().register(
                    surname, otherNames, phone, password, 2
            );

            call.enqueue(new Callback<AuthResponse>() {
                @Override
                public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                    if (response.isSuccessful()){
                        if (response.body()!= null) {
                            GoodPrefs.getInstance().saveObject("user", response.body().getUser());
                            signUpListener.onSignUpSuccess();
                        }
                    }else if (response.code() == 422)
                        signUpListener.onSignUpFailure("Phone number already taken");
                    else
                        signUpListener.onSignUpFailure(response.message());
                }

                @Override
                public void onFailure(Call<AuthResponse> call, Throwable t) {
                    signUpListener.onSignUpFailure(t.getLocalizedMessage());
                }
            });
        }
    }

    @Override
    public void setUpVehicle(String model, String number) {
        if (model.isEmpty())
            signUpListener.onVehicleSetupFailure("Vehicle model is required");
        else if (number.isEmpty())
            signUpListener.onVehicleSetupFailure("Vehicle number is required");
        else {
            Call<DriverResponse> call = ApiClient.createService().saveDriverDetails(
                    "Bearer "+ AppUtils.getToken(),
                    model, number
            );

            call.enqueue(new Callback<DriverResponse>() {
                @Override
                public void onResponse(Call<DriverResponse> call, Response<DriverResponse> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            AppUtils.updateDriver(response.body().getDriver());
                            AppUtils.saveLogin();
                            signUpListener.onVehicleSetupSuccess();
                        }
                    }else
                        signUpListener.onVehicleSetupFailure(response.message());

                }

                @Override
                public void onFailure(Call<DriverResponse> call, Throwable t) {
                    signUpListener.onVehicleSetupFailure(t.getLocalizedMessage());
                }
            });
        }

    }
}
