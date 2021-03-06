package com.kwame.tpay.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kwame.tpay.R;
import com.kwame.tpay.adapters.OptionAdapter;
import com.kwame.tpay.contracts.account.AccountListener;
import com.kwame.tpay.contracts.account.AccountPresenterImp;
import com.kwame.tpay.listeners.ItemClickListener;
import com.kwame.tpay.models.Option;
import com.kwame.tpay.utils.AppUtils;

import java.util.ArrayList;
import java.util.List;

public class MyAccountActivity extends AppCompatActivity implements AccountListener {

    private OptionAdapter adapter;
    private List<Option> options = new ArrayList<>();
    private Context context = MyAccountActivity.this;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.my_account));
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        init();

    }

    private void init() {

        progressDialog = AppUtils.buildLoading(context, "Checking balance, please wait...");

        final AccountPresenterImp accountPresenterImp = new AccountPresenterImp(this);

        RecyclerView recyclerView = findViewById(R.id.recycler_view_account_options);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
        recyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));

        adapter = new OptionAdapter(context, options);
        recyclerView.setAdapter(adapter);

        accountPresenterImp.getAccountOptions();

        adapter.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                if (position == 0) { // account details
                    startActivity(new Intent(context, AccountDetailsActivity.class));
                }else if (position == 1) { // change password
                    startActivity(new Intent(context, ChangePasswordActivity.class));
                }else if (position == 2) { // check driver balance
                    progressDialog.show();
                    accountPresenterImp.checkDriverAccountBalance();
                }
            }
        });
    }

    @Override
    public void onReturnAccountOptions(List<Option> data) {
        options.clear();
        options.addAll(data);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onBalanceSuccess(double amount) {
        progressDialog.hide();
        AppUtils.displayAlert(context, "Account Balance", "Your current balance is Ghs "+amount);
//        AppUtils.toast(context, "Your balance is GHC "+amount);
    }

    @Override
    public void onBalanceFailure(String message) {
        progressDialog.hide();
        AppUtils.toast(context, message);
    }

    @Override
    public void onUpdateSuccess() {

    }

    @Override
    public void onUpdateFailure(String message) {

    }


}
