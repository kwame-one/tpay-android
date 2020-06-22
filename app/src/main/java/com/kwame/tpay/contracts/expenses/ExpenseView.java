package com.kwame.tpay.contracts.expenses;

import com.kwame.tpay.models.Expense;

import java.util.List;

public interface ExpenseView {

    void onLoadExpensesSuccess(List<Expense> data);

    void onLoadExpenseFailure(String message);
}
