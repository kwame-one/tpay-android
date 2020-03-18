package com.kwame.tpay.contracts.wallet;

public interface WalletPresenter {

    void getWalletOptions();

    void setupWallet(int id);

    void activateWallet();

    void checkWalletBalance();

    void deactivateWallet();
}
