package com.kwame.tpay.contracts.account;

import com.kwame.tpay.R;
import com.kwame.tpay.models.Option;
import com.kwame.tpay.utils.AppUtils;

import java.util.ArrayList;
import java.util.List;

public class AccountPresenterImp implements AccountPresenter {

    private AccountListener accountListener;

    public AccountPresenterImp(AccountListener accountListener) {
        this.accountListener = accountListener;
    }

    @Override
    public void getAccountOptions() {
        List<Option> options = new ArrayList<>();

        options.add(new Option(R.drawable.ic_info_outline, "Account Details"));
        options.add(new Option(R.drawable.ic_lock_outline, "Change Password"));

        if(AppUtils.getUser().getRole().equalsIgnoreCase("driver"))
            options.add(new Option(R.drawable.ic_wallet, "Check Balance"));

        accountListener.onReturnAccountOptions(options);
    }
}
