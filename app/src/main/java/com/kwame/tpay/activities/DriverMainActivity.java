package com.kwame.tpay.activities;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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

import com.kwame.tpay.R;
import com.kwame.tpay.models.Auth;
import com.kwame.tpay.utils.AppUtils;
import com.kwame.tpay.utils.GoodPrefs;

public class DriverMainActivity extends AppCompatActivity {

    private Context context = DriverMainActivity.this;
    private ImageView imageView;
    private TextView phone, name;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_driver);


        imageView = findViewById(R.id.profile_image);
        phone = findViewById(R.id.phone);
        name = findViewById(R.id.name);

        displayUserDetails();


        findViewById(R.id.payment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, AcceptPaymentActivity.class));
            }
        });

        findViewById(R.id.withdraw).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
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


    private void showDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        ViewGroup viewGroup = findViewById(android.R.id.content);

        final View view = LayoutInflater.from(context).inflate(R.layout.initiate_transaction, viewGroup, false);
        builder.setView(view);

        TextView heading = view.findViewById(R.id.network);
        ImageView close = view.findViewById(R.id.close);
        final EditText phone = view.findViewById(R.id.phone);
        final EditText amount = view.findViewById(R.id.amount);
        Button withdraw = view.findViewById(R.id.debit);

        withdraw.setText("Withdraw");
        heading.setText("Withdraw Money");

        final Dialog dialog = builder.create();

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.hide();
            }
        });

        withdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        dialog.show();

    }

    @Override
    protected void onResume() {
        super.onResume();
        displayUserDetails();
    }

    private void displayUserDetails() {

        Auth user = GoodPrefs.getInstance().getObject("user", Auth.class);
        if (user != null){
            name.setText(user.getOtherNames()+" "+user.getSurname());
            phone.setText(user.getContact());
        }
    }

}
