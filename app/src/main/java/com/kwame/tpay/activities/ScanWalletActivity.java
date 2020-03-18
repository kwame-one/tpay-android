package com.kwame.tpay.activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.Result;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.kwame.tpay.R;
import com.kwame.tpay.contracts.wallet.WalletListener;
import com.kwame.tpay.contracts.wallet.WalletPresenterImp;
import com.kwame.tpay.models.Option;
import com.kwame.tpay.utils.AppUtils;

import java.util.List;

public class ScanWalletActivity extends AppCompatActivity implements WalletListener {

    private CodeScanner mCodeScanner;
    private WalletPresenterImp presenterImp;
    private ProgressDialog progressDialog;
    private Context context = ScanWalletActivity.this;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_wallet);

        presenterImp = new WalletPresenterImp(this);
        progressDialog = AppUtils.buildLoading(this, "Activating wallet, please wait...");



        Dexter.withActivity(this)
                .withPermission(Manifest.permission.CAMERA)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        scanWallet();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();


    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mCodeScanner != null) mCodeScanner.startPreview();
    }

    @Override
    protected void onPause() {
        if (mCodeScanner != null) mCodeScanner.releaseResources();
        super.onPause();
    }

    @Override
    public void onReturnWalletOptions(List<Option> data) {

    }


    @Override
    public void onActivateWalletSuccess() {

    }

    @Override
    public void onActivateWalletFailure(String message) {

    }

    @Override
    public void onDeactivateWalletSuccess() {
      //  progressDialog.hide();
    }

    @Override
    public void onDeactivateWalletFailure(String message) {
      //  progressDialog.hide();
    }

    @Override
    public void onSetupWalletSuccess() {
        progressDialog.hide();
        Intent intent = new Intent(ScanWalletActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void onWalletSetupWalletFailure(String message) {
        progressDialog.hide();
        AppUtils.toast(context, message);
    }

    @Override
    public void onCheckBalanceSuccess(double balance) {

    }

    @Override
    public void onCheckBalanceFailure(String message) {

    }

    private void scanWallet() {
        CodeScannerView scannerView = findViewById(R.id.scanner_view);
        mCodeScanner = new CodeScanner(ScanWalletActivity.this, scannerView);

        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        AppUtils.toast(ScanWalletActivity.this, result.getText());
                        progressDialog.show();
                        presenterImp.setupWallet(Integer.parseInt(result.getText()));
                    }
                });
            }
        });

        scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCodeScanner.startPreview();
            }
        });
    }
}
