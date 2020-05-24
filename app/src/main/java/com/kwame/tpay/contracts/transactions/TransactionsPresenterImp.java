package com.kwame.tpay.contracts.transactions;

import com.kwame.tpay.remote.ApiClient;
import com.kwame.tpay.remote.response.TransactionsResponse;
import com.kwame.tpay.utils.AppUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransactionsPresenterImp implements TransactionsPresenter{

    private TransactionsView transactionsView;

    public TransactionsPresenterImp(TransactionsView transactionsView) {
        this.transactionsView = transactionsView;
    }

    @Override
    public void getTransactions() {
        Call<TransactionsResponse> call = ApiClient.createService().getTransactions(
                "Bearer "+ AppUtils.getToken()
        );

        call.enqueue(new Callback<TransactionsResponse>() {
            @Override
            public void onResponse(Call<TransactionsResponse> call, Response<TransactionsResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null)
                        transactionsView.onTransactionsSuccess(response.body().getTransactions());
                }else
                    transactionsView.onTransactionsFailure(response.message());
            }

            @Override
            public void onFailure(Call<TransactionsResponse> call, Throwable t) {
                transactionsView.onTransactionsFailure(t.getMessage());
            }
        });
    }
}
