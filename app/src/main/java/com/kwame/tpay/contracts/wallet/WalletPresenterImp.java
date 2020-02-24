package com.kwame.tpay.contracts.wallet;

import com.kwame.tpay.R;
import com.kwame.tpay.models.Option;

import java.util.ArrayList;
import java.util.List;

public class WalletPresenterImp implements WalletPresenter {

    private WalletListener walletListener;

    public WalletPresenterImp(WalletListener walletListener) {
        this.walletListener = walletListener;
    }

    @Override
    public void getWalletOptions() {
        List<Option> options = new ArrayList<>();

//        options.add(new Option(R.drawable.ic_info_outline, "Information"));
        options.add(new Option(R.drawable.ic_activate, "Activate Wallet"));
        options.add(new Option(R.drawable.ic_block, "Deactivate Wallet"));
        options.add(new Option(R.drawable.ic_balance, "Check Balance"));
        options.add(new Option(R.drawable.ic_expenses, "Expenses"));
        options.add(new Option(R.drawable.ic_qr_code, "View QR Code"));


        walletListener.onReturnWalletOptions(options);
    }

    @Override
    public void activateWallet(int id) {
        walletListener.onActivateWalletSuccess();
    }

    @Override
    public void deactivateWallet() {
        walletListener.onDeactivateWalletSuccess();
    }
}
