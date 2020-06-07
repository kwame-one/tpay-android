package com.kwame.tpay.contracts.withdraw;

public interface WithdrawListener {

    void onWithdrawalSuccess();

    void onWithdrawalFailure(String message);
}
