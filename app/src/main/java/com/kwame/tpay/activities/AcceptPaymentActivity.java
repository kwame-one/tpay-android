package com.kwame.tpay.activities;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.kwame.tpay.R;
import com.kwame.tpay.contracts.payment.PaymentPresenterImp;
import com.kwame.tpay.contracts.payment.PaymentView;
import com.kwame.tpay.utils.AppUtils;

public class AcceptPaymentActivity extends AppCompatActivity implements PaymentView {

    private Context context = AcceptPaymentActivity.this;
    private EditText amount;
    private static int PAYMENT = 100;
    private PaymentPresenterImp presenter;
    private ProgressDialog loading;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept_payment);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.accept_payment));
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

        presenter = new PaymentPresenterImp(this);

        amount = findViewById(R.id.amount);
        Button scan = findViewById(R.id.scan);

        loading =  AppUtils.buildLoading(context, "Deducting, please wait");


        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (amount.getText().toString().isEmpty())
                    AppUtils.toast(context, "Amount is required");
                else
                    startActivityForResult(new Intent(context, InitializePaymentActivity.class), PAYMENT);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PAYMENT && resultCode == RESULT_OK && data != null) {
            int walletId = Integer.parseInt(data.getStringExtra("result"));
            loading.show();
            presenter.makePayment(walletId, amount.getText().toString());

        }

    }


    @Override
    public void onTransactionSuccess(String link) {

    }

    @Override
    public void onTransactionFailure(String message) {

    }

    @Override
    public void onPaymentError(String message) {
        loading.hide();
        AppUtils.toast(context, message);
    }

    @Override
    public void onPaymentSuccess() {
        loading.hide();
        AppUtils.toast(context, "success");
    }
}
