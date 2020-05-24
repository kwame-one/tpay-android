package com.kwame.tpay.contracts.payment;

public class PaymentPresenterImp implements PaymentPresenter {

    private PaymentView view;

    public PaymentPresenterImp(PaymentView view) {
        this.view = view;
    }

    @Override
    public void transact(String network, String phone, String amount) {
        if (phone.isEmpty() || amount.isEmpty())
            view.onTransactionFailure("Please fill all fields");
        else if(phone.length() < 10) {
            view.onTransactionFailure("Phone number is invalid");
        }else {

        }
    }

    @Override
    public void makePayment(int walletId, String amount) {
        if (amount.isEmpty())
            view.onPaymentError("Amount is required");
        else {
            view.onPaymentSuccess();
        }
    }
}
