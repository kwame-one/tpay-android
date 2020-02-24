package com.kwame.tpay.contracts.wallet;

import com.kwame.tpay.models.Option;

import java.util.List;

public interface WalletListener {

    void onReturnWalletOptions(List<Option> data);

//    void onScanWalletSuccess();
//
//    void onScanWalletFailure(String message);

    void onActivateWalletSuccess();

    void onActivateWalletFailure(String message);

    void onDeactivateWalletSuccess();

    void onDeactivateWalletFailure(String message);
}
