package com.kwame.tpay.contracts.wallet;

public interface WalletPresenter {

    void getWalletOptions();

    void activateWallet(int id);

    void deactivateWallet();
}
