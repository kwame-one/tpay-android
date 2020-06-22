package com.kwame.tpay.contracts.expenses;

import com.kwame.tpay.remote.ApiClient;
import com.kwame.tpay.remote.response.ExpensesResponse;
import com.kwame.tpay.utils.AppUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExpensePresenterImp implements ExpensePresenter {

    private ExpenseView expenseView;

    public ExpensePresenterImp(ExpenseView expenseView) {
        this.expenseView = expenseView;
    }

    @Override
    public void getExpenses(String start, String end) {
        Call<ExpensesResponse> call = ApiClient.createService().getExpenses(
                "Bearer "+ AppUtils.getToken(),
                start, end
        );

        call.enqueue(new Callback<ExpensesResponse>() {
            @Override
            public void onResponse(Call<ExpensesResponse> call, Response<ExpensesResponse> response) {
                if (response.isSuccessful()) {
                    expenseView.onLoadExpensesSuccess(response.body().getData());
                }else {
                    expenseView.onLoadExpenseFailure(response.message());
                }
            }

            @Override
            public void onFailure(Call<ExpensesResponse> call, Throwable t) {
                expenseView.onLoadExpenseFailure(t.getLocalizedMessage());
            }
        });
    }
}
