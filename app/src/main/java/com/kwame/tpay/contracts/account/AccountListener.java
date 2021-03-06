package com.kwame.tpay.contracts.account;

import com.kwame.tpay.models.Option;

import java.util.List;

public interface AccountListener {

    void onReturnAccountOptions(List<Option> data);

    void onBalanceSuccess(double amount);

    void onBalanceFailure(String message);

    void onUpdateSuccess();

    void onUpdateFailure(String message);
}
