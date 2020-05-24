package com.kwame.tpay.contracts.payment;

public interface PaymentView {

    void onTransactionSuccess();

    void onTransactionFailure(String message);

    void onPaymentError(String message);

    void onPaymentSuccess();
}
