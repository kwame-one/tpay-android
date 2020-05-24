package com.kwame.tpay.contracts.transactions;

import com.kwame.tpay.models.Transaction;

import java.util.List;

public interface TransactionsView {

    void onTransactionsSuccess(List<Transaction> transactions);

    void onTransactionsFailure(String message);
}
