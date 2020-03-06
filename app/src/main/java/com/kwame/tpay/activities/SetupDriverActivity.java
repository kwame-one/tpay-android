package com.kwame.tpay.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.kwame.tpay.R;
import com.kwame.tpay.contracts.sign_up.SignUpListener;
import com.kwame.tpay.contracts.sign_up.SignUpPresenterImp;
import com.kwame.tpay.models.Auth;
import com.kwame.tpay.utils.AppUtils;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class SetupDriverActivity extends AppCompatActivity implements SignUpListener {

    private EditText model, number;
    private SignUpPresenterImp presenterImp;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_driver);

        init();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onSignUpSuccess() {

    }

    @Override
    public void onSignUpFailure(String message) {

    }

    @Override
    public void onVehicleSetupSuccess() {
        progressDialog.hide();
        Intent intent = new Intent(SetupDriverActivity.this, DriverMainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void onVehicleSetupFailure(String message) {
        progressDialog.hide();
        AppUtils.toast(SetupDriverActivity.this, message);
    }

    private void init() {

        presenterImp = new SignUpPresenterImp(this);

        TextView header = findViewById(R.id.header);

        Auth user = AppUtils.getUser();
        if (user != null)
            header.setText("Welcome "+user.getOtherNames()+" "+user.getSurname());

        model = findViewById(R.id.model);
        number = findViewById(R.id.vehicle_num);

        progressDialog = AppUtils.buildLoading(this, "Saving details, please wait...");

        findViewById(R.id.save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                presenterImp.setUpVehicle(
                        model.getText().toString(),
                        number.getText().toString()
                );
            }
        });
    }
}
