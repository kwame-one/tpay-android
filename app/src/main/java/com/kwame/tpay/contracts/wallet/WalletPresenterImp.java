package com.kwame.tpay.contracts.wallet;

import com.kwame.tpay.R;
import com.kwame.tpay.models.Auth;
import com.kwame.tpay.models.Option;
import com.kwame.tpay.remote.ApiClient;
import com.kwame.tpay.remote.response.WalletResponse;
import com.kwame.tpay.utils.AppUtils;
import com.kwame.tpay.utils.GoodPrefs;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        Call<WalletResponse> call = ApiClient.createService().activateWallet(
                "Bearer "+ AppUtils.getToken(), id
        );

        call.enqueue(new Callback<WalletResponse>() {
            @Override
            public void onResponse(Call<WalletResponse> call, Response<WalletResponse> response) {
                if (response.isSuccessful()) {
                    Auth user = AppUtils.getUser();
                    if (response.body() != null) {
                        user.setWallet(response.body().getWallet());
                        GoodPrefs.getInstance().saveObject("user", user);
                        AppUtils.saveLogin();
                        walletListener.onActivateWalletSuccess();
                    }
                }else
                    walletListener.onActivateWalletFailure(response.message());
            }

            @Override
            public void onFailure(Call<WalletResponse> call, Throwable t) {
                walletListener.onActivateWalletFailure(t.getLocalizedMessage());
            }
        });

    }

    @Override
    public void deactivateWallet() {
        walletListener.onDeactivateWalletSuccess();
    }
}
