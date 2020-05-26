package com.kwame.tpay.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.kwame.tpay.R;
import com.kwame.tpay.contracts.account.AccountListener;
import com.kwame.tpay.contracts.account.AccountPresenterImp;
import com.kwame.tpay.models.Auth;
import com.kwame.tpay.models.Driver;
import com.kwame.tpay.models.Option;
import com.kwame.tpay.utils.AppUtils;

import java.util.List;

public class AccountDetailsActivity extends AppCompatActivity implements AccountListener {

    private CardView vehicleDetails;
    private EditText surname, otherNames, phone,vehicleModel, vehicleNumber;
    private AccountPresenterImp presenter;
    private ProgressDialog progressDialog;
    private Context context = AccountDetailsActivity.this;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_details);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.account_details));
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

        final Auth user = AppUtils.getUser();
        presenter = new AccountPresenterImp(this);
        progressDialog = AppUtils.buildLoading(context, "Updating Details, please wait...");

        vehicleDetails = findViewById(R.id.car_details);
        surname = findViewById(R.id.surname);
        otherNames = findViewById(R.id.other_names);
        phone = findViewById(R.id.phone);
        vehicleModel = findViewById(R.id.model);
        vehicleNumber = findViewById(R.id.vehicle_num);


        surname.setText(user.getSurname());
        otherNames.setText(user.getOtherNames());
        phone.setText(user.getContact());

        phone.setInputType(InputType.TYPE_NULL);


        if (user.getRole().equalsIgnoreCase("driver")) {
            vehicleDetails.setVisibility(View.VISIBLE);

            vehicleNumber.setText(user.getDriver().getVehicleNumber());
            vehicleModel.setText(user.getDriver().getVehicleModel());


        }


        findViewById(R.id.update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Auth userDetails = new Auth(surname.getText().toString(), otherNames.getText().toString());

                if (user.getRole().equalsIgnoreCase("driver"))
                    userDetails.setDriver(new Driver(vehicleModel.getText().toString(), vehicleNumber.getText().toString()));

                progressDialog.show();
                presenter.updateAccount(userDetails);
            }
        });


    }

    @Override
    public void onReturnAccountOptions(List<Option> data) {

    }

    @Override
    public void onBalanceSuccess(double amount) {

    }

    @Override
    public void onBalanceFailure(String message) {

    }

    @Override
    public void onUpdateSuccess() {
        progressDialog.hide();
        AppUtils.toast(context, "Details updated successfully");
    }

    @Override
    public void onUpdateFailure(String message) {
        progressDialog.hide();
        AppUtils.toast(context, message);
    }
}
