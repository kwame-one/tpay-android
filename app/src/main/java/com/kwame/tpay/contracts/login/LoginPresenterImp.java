package com.kwame.tpay.contracts.login;

import com.kwame.tpay.remote.ApiClient;
import com.kwame.tpay.remote.response.AuthResponse;
import com.kwame.tpay.utils.AppUtils;
import com.kwame.tpay.utils.GoodPrefs;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

            Call<AuthResponse> call = ApiClient.createService().login(phone, password);
            call.enqueue(new Callback<AuthResponse>() {
                @Override
                public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {

                    if (response.isSuccessful()){
                        if (response.body()!= null) {

                            GoodPrefs.getInstance().saveObject("user", response.body().getUser());

                            if (response.body().getMessage().equalsIgnoreCase("driver not setup"))
                                loginListener.onDriverNotSetup();
                            else if (response.body().getMessage().equalsIgnoreCase("wallet not set up"))
                                loginListener.onWalletNotScanned();
                            else {
                                AppUtils.saveLogin();
                                loginListener.onLoginSuccess();
                            }
                        }

                    }else if (response.code() == 401)
                        loginListener.onLoginFailure("Invalid credentials, please try again");
                    else if (response.code() == 422)
                        loginListener.onLoginFailure("Unprocessed entity");
                    else
                        loginListener.onLoginFailure(response.message());
                }

                @Override
                public void onFailure(Call<AuthResponse> call, Throwable t) {
                    loginListener.onLoginFailure(t.getLocalizedMessage());
                }
            });


        }
    }
}
