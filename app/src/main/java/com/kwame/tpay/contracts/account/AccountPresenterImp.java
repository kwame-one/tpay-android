package com.kwame.tpay.contracts.account;

import com.kwame.tpay.R;
import com.kwame.tpay.models.Auth;
import com.kwame.tpay.models.Option;
import com.kwame.tpay.remote.ApiClient;
import com.kwame.tpay.remote.response.AuthResponse;
import com.kwame.tpay.remote.response.DriverResponse;
import com.kwame.tpay.utils.AppUtils;
import com.kwame.tpay.utils.GoodPrefs;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountPresenterImp implements AccountPresenter {

    private AccountListener accountListener;

    public AccountPresenterImp(AccountListener accountListener) {
        this.accountListener = accountListener;
    }

    @Override
    public void getAccountOptions() {
        List<Option> options = new ArrayList<>();

        options.add(new Option(R.drawable.ic_info_outline, "Account Details"));
        options.add(new Option(R.drawable.ic_lock_outline, "Change Password"));

        if(AppUtils.getUser().getRole().equalsIgnoreCase("driver"))
            options.add(new Option(R.drawable.ic_wallet, "Check Balance"));

        accountListener.onReturnAccountOptions(options);
    }

    @Override
    public void checkDriverAccountBalance() {
        Call<DriverResponse> call = ApiClient.createService().getDriverAccountBalance(
                "Bearer "+AppUtils.getToken()
        );

        call.enqueue(new Callback<DriverResponse>() {
            @Override
            public void onResponse(Call<DriverResponse> call, Response<DriverResponse> response) {
                if (response.isSuccessful())
                    accountListener.onBalanceSuccess(response.body().getDriver().getBalance());
                else
                    accountListener.onBalanceFailure(response.message());
            }

            @Override
            public void onFailure(Call<DriverResponse> call, Throwable t) {
                accountListener.onBalanceFailure(t.getLocalizedMessage());
            }
        });
    }

    @Override
    public void updateAccount(Auth user) {
        boolean validated = false;
        if (user.getSurname().isEmpty())
            accountListener.onUpdateFailure("Surname field is required");
        else if (user.getOtherNames().isEmpty())
            accountListener.onUpdateFailure("Other names field is required");
        else validated = true;

        if (user.getDriver() != null) {
            if (user.getDriver().getVehicleModel().isEmpty()) {
                accountListener.onUpdateFailure("Vehicle Model field is required");
                validated = false;
            }
            else if (user.getDriver().getVehicleNumber().isEmpty()) {
                accountListener.onUpdateFailure("Vehicle Number field is required");
                validated = false;
            }
            else validated = true;
        }

        if (validated) {

            Call<AuthResponse> call = ApiClient.createService().updateDetails(
                    "Bearer "+AppUtils.getToken(),
                    user.getOtherNames(),
                    user.getSurname(),
                    user.getDriver() != null ? user.getDriver().getVehicleNumber() : null,
                    user.getDriver() != null ? user.getDriver().getVehicleModel() : null
            );

            call.enqueue(new Callback<AuthResponse>() {
                @Override
                public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                    if (response.isSuccessful()) {
                        Auth user = response.body().getUser();
                        user.setToken(AppUtils.getToken());
                        GoodPrefs.getInstance().saveObject("user", response.body().getUser());

                        accountListener.onUpdateSuccess();
                    }else
                        accountListener.onUpdateFailure(response.message());
                }

                @Override
                public void onFailure(Call<AuthResponse> call, Throwable t) {
                    accountListener.onUpdateFailure(t.getLocalizedMessage());
                }
            });

        }
    }
}
