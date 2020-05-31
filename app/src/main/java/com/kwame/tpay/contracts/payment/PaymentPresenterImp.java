package com.kwame.tpay.contracts.payment;

import com.kwame.tpay.remote.ApiClient;
import com.kwame.tpay.remote.response.BaseResponse;
import com.kwame.tpay.utils.AppUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentPresenterImp implements PaymentPresenter {

    private PaymentView view;

    public PaymentPresenterImp(PaymentView view) {
        this.view = view;
    }

    @Override
    public void transact(String network, String phone, String amount, String voucher) {
        if (phone.isEmpty() || amount.isEmpty())
            view.onTransactionFailure("Please fill all fields");
        else if(phone.length() < 10) {
            view.onTransactionFailure("Phone number is invalid");
        }else if(voucher.isEmpty() && (phone.startsWith("020") || phone.startsWith("050")) ) {
            view.onTransactionFailure("Voucher is required");
        } else {
            Call<BaseResponse> call = ApiClient.createService().creditWallet(
                    "Bearer "+AppUtils.getToken(),
                    Double.parseDouble(amount),
                    phone, voucher
            );

            call.enqueue(new Callback<BaseResponse>() {
                @Override
                public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                    if (response.isSuccessful()) {
                        view.onTransactionSuccess();
                    }else if (response.code() == 422) {
                        view.onTransactionFailure("422");
                    }else {
                        view.onTransactionFailure(response.message());
                    }
                }

                @Override
                public void onFailure(Call<BaseResponse> call, Throwable t) {
                    view.onTransactionFailure(t.getLocalizedMessage());
                }
            });
        }
    }

    @Override
    public void makePayment(int walletId, String amount) {
        if (amount.isEmpty())
            view.onPaymentError("Amount is required");
        else {
            Call<BaseResponse> call = ApiClient.createService().acceptPayment(
                    "Bearer "+ AppUtils.getToken(),
                    walletId, Double.parseDouble(amount)
            );

            call.enqueue(new Callback<BaseResponse>() {
                @Override
                public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                    if (response.isSuccessful())
                        view.onPaymentSuccess();
                    else if (response.code() == 403)
                        view.onPaymentError("Wallet has been deactivated");
                    else if (response.code() == 402)
                        view.onPaymentError("You do not have enough balance in your wallet");
                    else
                        view.onPaymentError(response.message());
                }

                @Override
                public void onFailure(Call<BaseResponse> call, Throwable t) {
                    view.onPaymentError(t.getMessage());
                }
            });
        }
    }
}
