package com.kwame.tpay.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kwame.tpay.R;
import com.kwame.tpay.adapters.OptionAdapter;
import com.kwame.tpay.contracts.wallet.WalletListener;
import com.kwame.tpay.contracts.wallet.WalletPresenterImp;
import com.kwame.tpay.listeners.ItemClickListener;
import com.kwame.tpay.models.Option;
import com.kwame.tpay.utils.AppUtils;

import java.util.ArrayList;
import java.util.List;

public class MyWalletActivity extends AppCompatActivity implements WalletListener {

    private List<Option> options = new ArrayList<>();
    private OptionAdapter adapter;
    private Context context = MyWalletActivity.this;
    private WalletPresenterImp presenterImp;
    private ProgressDialog progressDialog;
    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_wallet);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.my_wallet));
        toolbar.setSubtitle(AppUtils.getUser().getWallet().getStatus().toUpperCase());
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        init();
    }


    private void init() {
        presenterImp = new WalletPresenterImp(this);

        RecyclerView recyclerView = findViewById(R.id.recycler_view_wallet_options);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
        recyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));

        adapter = new OptionAdapter(context, options);
        recyclerView.setAdapter(adapter);

        presenterImp.getWalletOptions();

        progressDialog = AppUtils.buildLoading(context, "");


        adapter.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
//                AppUtils.toast(context, options.get(position).getTitle());
                if (position == 0) { // activate wallet
                    showAlert("Are you sure you want to activate your wallet?", 1);
                }else if (position == 1) { // deactivate wallet
                    showAlert("Are you sure you want to deactivate your wallet?", 0);
                }else if(position == 2) { // check balance
                    checkBalance();
                }else if(position == 3) { // expenses

                }else if(position == 4) { // view qr code
                    startActivity(new Intent(context, ViewWalletActivity.class));
                }

            }
        });


    }

    @Override
    public void onReturnWalletOptions(List<Option> data) {
        options.addAll(data);
        adapter.notifyDataSetChanged();
    }


    @Override
    public void onActivateWalletSuccess() {
        progressDialog.hide();
        toolbar.setSubtitle(AppUtils.getUser().getWallet().getStatus().toUpperCase());
        AppUtils.toast(context, "Wallet activated successfully");
    }

    @Override
    public void onActivateWalletFailure(String message) {
        progressDialog.hide();
        AppUtils.toast(context, message);
    }

    @Override
    public void onDeactivateWalletSuccess() {
        progressDialog.hide();
        toolbar.setSubtitle(AppUtils.getUser().getWallet().getStatus().toUpperCase());
        AppUtils.toast(context, "Wallet deactivated successfully");
    }

    @Override
    public void onDeactivateWalletFailure(String message) {
        progressDialog.hide();
        AppUtils.toast(context, message);
    }

    @Override
    public void onSetupWalletSuccess() {

    }

    @Override
    public void onWalletSetupWalletFailure(String message) {
//        AppUtils.toast(context, message);
    }

    @Override
    public void onCheckBalanceSuccess(double balance) {
        progressDialog.hide();
        AppUtils.displayAlert(context, "Account Balance", "Your current balance is Ghs "+balance);
       // AppUtils.toast(context, "Balance is Ghs "+balance);
    }

    @Override
    public void onCheckBalanceFailure(String message) {
        progressDialog.hide();
        AppUtils.toast(context, message);
    }

    private void showAlert(String message, final int status) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message);
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        }).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (status == 1) {
                    progressDialog.setMessage("Activating wallet, please wait...");
                    presenterImp.activateWallet();
                }else {
                    progressDialog.setMessage("Deactivating wallet, please wait...");
                    presenterImp.deactivateWallet();
                }

                progressDialog.show();
            }
        });

        builder.create().show();
    }

    private void checkBalance() {
        progressDialog.setMessage("Loading balance, please wait...");
        presenterImp.checkWalletBalance();
        progressDialog.show();
    }
}
