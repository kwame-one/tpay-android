package com.kwame.tpay.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.kwame.tpay.R;
import com.kwame.tpay.contracts.sign_up.SignUpListener;
import com.kwame.tpay.contracts.sign_up.SignUpPresenterImp;
import com.kwame.tpay.utils.AppUtils;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class SetupDriverActivity extends AppCompatActivity implements SignUpListener {

    private EditText model, number;
    private SignUpPresenterImp presenterImp;

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
        Intent intent = new Intent(SetupDriverActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void onVehicleSetupFailure(String message) {
        AppUtils.toast(SetupDriverActivity.this, message);
    }

    private void init() {

        presenterImp = new SignUpPresenterImp(this);

        TextView header = findViewById(R.id.header);
        header.setText("Welcome Freeman");

        model = findViewById(R.id.model);
        number = findViewById(R.id.vehicle_num);

        findViewById(R.id.save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenterImp.setUpVehicle(
                        model.getText().toString(),
                        number.getText().toString()
                );
            }
        });
    }
}
