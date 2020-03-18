package com.kwame.tpay.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.kwame.tpay.R;
import com.kwame.tpay.contracts.change_password.ChangePasswordListener;
import com.kwame.tpay.contracts.change_password.ChangePasswordPresenterImp;
import com.kwame.tpay.utils.AppUtils;

public class ChangePasswordActivity extends AppCompatActivity implements ChangePasswordListener {

    private EditText currentPassword, newPassword, confirmPassword;
    private ChangePasswordPresenterImp presenterImp;
    private ProgressDialog progressDialog;
    private Context context = ChangePasswordActivity.this;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.change_password));
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
        currentPassword = findViewById(R.id.current_password);
        newPassword = findViewById(R.id.password);
        confirmPassword = findViewById(R.id.password_confirm);

        presenterImp = new ChangePasswordPresenterImp(this);
        progressDialog = AppUtils.buildLoading(context, "Changing password, please wait...");


        findViewById(R.id.change_password).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                presenterImp.changePassword(
                        currentPassword.getText().toString(),
                        newPassword.getText().toString(),
                        confirmPassword.getText().toString()
                );
            }
        });
    }

    @Override
    public void onChangePasswordSuccess() {
        progressDialog.hide();
        AppUtils.toast(context, "Password Change Successfully");
    }

    @Override
    public void onChangePasswordFailure(String message) {
        progressDialog.hide();
        AppUtils.toast(context, message);
    }
}
