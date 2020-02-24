package com.kwame.tpay.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.kwame.tpay.R;
import com.kwame.tpay.contracts.sign_up.SignUpListener;
import com.kwame.tpay.contracts.sign_up.SignUpPresenterImp;
import com.kwame.tpay.utils.AppUtils;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class SignUpActivity extends AppCompatActivity implements SignUpListener {

    private EditText surname, otherNames, phone, password;
    private SignUpPresenterImp presenter;
    private RadioGroup userType;
    private RadioButton user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        init();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onSignUpSuccess() {
        if (user.getText().toString().equalsIgnoreCase("driver")) { // driver
            startActivity(new Intent(SignUpActivity.this, SetupDriverActivity.class));
        }else { // user
            startActivity(new Intent(SignUpActivity.this, ScanWalletActivity.class));
        }

//        Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(intent);
    }

    @Override
    public void onSignUpFailure(String message) {
        AppUtils.toast(SignUpActivity.this, message);
    }

    @Override
    public void onVehicleSetupSuccess() {

    }

    @Override
    public void onVehicleSetupFailure(String message) {

    }

    private void init() {

        presenter = new SignUpPresenterImp(this);

        surname = findViewById(R.id.surname);
        otherNames = findViewById(R.id.other_names);
        phone = findViewById(R.id.phone);
        password = findViewById(R.id.password);
        userType = findViewById(R.id.user_type);

        findViewById(R.id.sign_up).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user = findViewById(userType.getCheckedRadioButtonId());
                presenter.signUp(
                        surname.getText().toString(),
                        otherNames.getText().toString(),
                        phone.getText().toString(),
                        password.getText().toString(),
                        user.getText().toString()
                );
            }
        });

        findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
