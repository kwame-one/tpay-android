package com.kwame.tpay.activities;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.kwame.tpay.R;
import com.kwame.tpay.contracts.payment.PaymentPresenterImp;
import com.kwame.tpay.contracts.payment.PaymentView;
import com.kwame.tpay.utils.AppUtils;

public class AddMoneyActivity extends AppCompatActivity implements PaymentView {
    private Context context = AddMoneyActivity.this;
    private PaymentPresenterImp presenterImp;
    private boolean validated = false;
    private ProgressDialog progressDialog;
    private String selectedNetwork;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_money);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.add_money));
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

        presenterImp = new PaymentPresenterImp(this);

        findViewById(R.id.mtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedNetwork = AppUtils.MTN;
                showDialog(selectedNetwork);
            }
        });

        findViewById(R.id.airtelTigo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedNetwork = AppUtils.AIRTELTIGO;
                showDialog(selectedNetwork);
            }
        });

        findViewById(R.id.vodafone).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedNetwork = AppUtils.VODAFONE;
                showDialog(selectedNetwork);
            }
        });

        progressDialog = AppUtils.buildLoading(context, "Initializing Payment, please wait...");
    }


    private void showDialog(final String network) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        ViewGroup viewGroup = findViewById(android.R.id.content);

        final View view = LayoutInflater.from(context).inflate(R.layout.dialog_initiate_transaction, viewGroup, false);
        builder.setView(view);

        TextView heading = view.findViewById(R.id.network);
        ImageView close = view.findViewById(R.id.close);
        final EditText phone = view.findViewById(R.id.phone);
        final EditText amount = view.findViewById(R.id.amount);
        Button debit = view.findViewById(R.id.debit);
        final EditText voucher = view.findViewById(R.id.voucher);

        if (network.equalsIgnoreCase(AppUtils.VODAFONE)) {
            voucher.setVisibility(View.VISIBLE);
            view.findViewById(R.id.voucher_text).setVisibility(View.VISIBLE);
        }


        phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String number = charSequence.toString();


                if(network.equals(AppUtils.MTN) && !(number.startsWith("055") || number.startsWith("054") || number.startsWith("024") || number.startsWith("059"))){
                    phone.setError("Invalid Mtn number");
                }else if(network.equals(AppUtils.AIRTELTIGO) && !(number.startsWith("057") || number.startsWith("027") || number.startsWith("026") || number.startsWith("056"))){
                    phone.setError("Invalid AirtelTigo number");
                }else if(network.equals(AppUtils.VODAFONE) && !(number.startsWith("050") || number.startsWith("020"))){
                    phone.setError("Invalid Vodafone number");
                }else{
                    phone.setError(null);
                    validated = true;
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        heading.setText(network);


        final Dialog dialog = builder.create();

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.hide();
            }
        });

        debit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validated)
                    AppUtils.toast(context, "Invalid Number");
                else {
                    dialog.hide();
                    progressDialog.show();
                    presenterImp.transact(
                            network,
                            phone.getText().toString(),
                            amount.getText().toString(),
                            voucher.getText().toString()
                    );
                }
            }
        });

        dialog.show();

    }

    @Override
    public void onTransactionSuccess() {
        progressDialog.hide();
        if (selectedNetwork.equalsIgnoreCase(AppUtils.MTN)) {
            AppUtils.displayAlert(context, "Authorize Payment", getString(R.string.mtn_instruct));
        }else if (selectedNetwork.equalsIgnoreCase(AppUtils.VODAFONE)) {
            AppUtils.displayAlert(context, "Payment Initialized", "You'll be notified upon successful transaction");
        }else if (selectedNetwork.equalsIgnoreCase(AppUtils.AIRTELTIGO)) {
            AppUtils.displayAlert(context, "Authorize Payment", getString(R.string.tigo_instruct));
        }
    }

    @Override
    public void onTransactionFailure(String message) {
        if (message.equalsIgnoreCase("422")) {
            AppUtils.toast(context, "Please make sure to fill all fields");
        }else {
            AppUtils.toast(context, message);
        }
    }

    @Override
    public void onPaymentError(String message) {

    }

    @Override
    public void onPaymentSuccess() {

    }
}
