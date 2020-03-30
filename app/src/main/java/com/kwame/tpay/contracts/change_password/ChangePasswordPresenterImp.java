package com.kwame.tpay.contracts.change_password;

import android.text.TextUtils;
import android.widget.Toast;

import com.kwame.tpay.remote.ApiClient;
import com.kwame.tpay.remote.response.BaseResponse;
import com.kwame.tpay.utils.AppUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordPresenterImp implements ChangePasswordPresenter {

    private ChangePasswordListener listener;

    public ChangePasswordPresenterImp(ChangePasswordListener listener) {
        this.listener = listener;
    }

    @Override
    public void changePassword(String current, String newPassword, String confirmPassword) {
        if (current.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty())
            listener.onChangePasswordFailure("Please fill all fields");
        else if (!TextUtils.equals(newPassword, confirmPassword))
            listener.onChangePasswordFailure("Passwords do not match");
        else {
            Call<BaseResponse> call = ApiClient.createService().changePassword(
                    "Bearer "+ AppUtils.getToken(),
                    current, newPassword, confirmPassword
            );

            call.enqueue(new Callback<BaseResponse>() {
                @Override
                public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                    if (response.isSuccessful())
                        listener.onChangePasswordSuccess();
                    else if (response.code() == 401)
                        listener.onChangePasswordFailure("Invalid password");
                    else if (response.code() == 422)
                        listener.onChangePasswordFailure("Please fill all fields");
                    else
                        listener.onChangePasswordFailure(response.message());

                }

                @Override
                public void onFailure(Call<BaseResponse> call, Throwable t) {
                    listener.onChangePasswordFailure(t.getMessage());
                }
            });
        }
    }
}
