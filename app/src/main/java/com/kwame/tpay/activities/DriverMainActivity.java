package com.kwame.tpay.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.kwame.tpay.R;
import com.kwame.tpay.models.Auth;
import com.kwame.tpay.utils.AppUtils;
import com.kwame.tpay.utils.GoodPrefs;

public class DriverMainActivity extends AppCompatActivity {

    private Context context = DriverMainActivity.this;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_driver);


        ImageView imageView = findViewById(R.id.profile_image);
        TextView phone = findViewById(R.id.phone);
        TextView name = findViewById(R.id.name);

        Auth user = AppUtils.getUser();
        name.setText(user.getOtherNames()+" "+user.getSurname());
        phone.setText(user.getContact());


        findViewById(R.id.payment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        findViewById(R.id.withdraw).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        findViewById(R.id.account).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, MyAccountActivity.class));
            }
        });

        findViewById(R.id.transactions).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, TransactionsActivity.class));
            }
        });

        findViewById(R.id.settings).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, SettingsActivity.class));
            }
        });


        findViewById(R.id.logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               showAlert();
            }
        });
    }

    private void showAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Are you sure you won't to logout?");
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        }).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                GoodPrefs.getInstance().clearSession();
                startActivity(new Intent(context, LoginActivity.class));
                finish();
            }
        });

        builder.create().show();
    }
}
