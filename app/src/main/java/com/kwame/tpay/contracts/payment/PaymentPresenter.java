package com.kwame.tpay.contracts.payment;

public interface PaymentPresenter {

    void transact(String network, String phone, String amount, String voucher);

   void makePayment(int walletId, String amount);
}
