package com.kwame.tpay.contracts.wallet;

import com.kwame.tpay.models.Option;

import java.util.List;

public interface WalletListener {

    void onReturnWalletOptions(List<Option> data);
}
