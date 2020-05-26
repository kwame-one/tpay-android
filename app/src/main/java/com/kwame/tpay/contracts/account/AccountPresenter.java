package com.kwame.tpay.contracts.account;

import com.kwame.tpay.models.Auth;

public interface AccountPresenter {

    void getAccountOptions();

    void checkDriverAccountBalance();

    void updateAccount(Auth user);

}
