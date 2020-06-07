package com.kwame.tpay.contracts.withdraw;

import com.kwame.tpay.remote.ApiClient;
import com.kwame.tpay.remote.response.BaseResponse;
import com.kwame.tpay.utils.AppUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WithdrawPresenterImp implements WithdrawPresenter {

    private WithdrawListener view;

    public WithdrawPresenterImp(WithdrawListener view) {
        this.view = view;
    }

    @Override
    public void withdraw(String number, String amount) {
        if (number.isEmpty() || amount.isEmpty())
            view.onWithdrawalFailure("Please fill all fields");
        else  {
            Call<BaseResponse> call = ApiClient.createService().debitWallet(
                    "Bearer "+ AppUtils.getToken(),
                    Double.parseDouble(amount), number
            );
            call.enqueue(new Callback<BaseResponse>() {
                @Override
                public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                    if (response.isSuccessful()) {
                        view.onWithdrawalSuccess();
                    }else if (response.code() == 402) {
                        view.onWithdrawalFailure("You do not have enough balance in your account");
                    }else
                        view.onWithdrawalFailure(response.message());
                }

                @Override
                public void onFailure(Call<BaseResponse> call, Throwable t) {
                    view.onWithdrawalFailure(t.getLocalizedMessage());
                }
            });
        }
    }
}
