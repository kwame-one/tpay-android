package com.kwame.tpay.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.kwame.tpay.R;
import com.kwame.tpay.contracts.login.LoginListener;
import com.kwame.tpay.contracts.login.LoginPresenterImp;
import com.kwame.tpay.utils.AppUtils;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class LoginActivity extends AppCompatActivity implements LoginListener {

    private EditText phone, password;
    private LoginPresenterImp loginPresenterImp;
    private ProgressDialog progressDialog;
    private Context context = LoginActivity.this;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        init();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onLoginSuccess() {
        progressDialog.hide();
        startActivity(new Intent(context, MainActivity.class));
        finish();
    }

    @Override
    public void onLoginFailure(String message) {
        progressDialog.hide();
        AppUtils.toast(context, message);
    }

    @Override
    public void onDriverNotSetup() {
        progressDialog.hide();
    //    Intent intent = new Intent(context, SetupDriverActivity.class);
        startActivity(new Intent(context, SetupDriverActivity.class));
        finish();

    }

    @Override
    public void onWalletNotScanned() {
        progressDialog.hide();
        startActivity(new Intent(context, ScanWalletActivity.class));
        finish();
    }

    private void init() {
        loginPresenterImp = new LoginPresenterImp(this);

        phone = findViewById(R.id.phone);
        password = findViewById(R.id.password);
        Button login = findViewById(R.id.login);
        TextView signUp = findViewById(R.id.sign_up);


        progressDialog = AppUtils.buildLoading(this, "Logging in, please wait");


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                loginPresenterImp.login(phone.getText().toString(), password.getText().toString());
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, SignUpActivity.class));
            }
        });
    }
}
