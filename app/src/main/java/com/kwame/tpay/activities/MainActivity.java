package com.kwame.tpay.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.kwame.tpay.R;
import com.kwame.tpay.models.Auth;
import com.kwame.tpay.utils.App;
import com.kwame.tpay.utils.AppUtils;
import com.kwame.tpay.utils.GoodPrefs;

public class MainActivity extends AppCompatActivity {

    private Context context = MainActivity.this;
    private ImageView imageView;
    private TextView phone, name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.profile_image);
        phone = findViewById(R.id.phone);
        name = findViewById(R.id.name);

        displayUserDetails();

        findViewById(R.id.wallet).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, MyWalletActivity.class));
            }
        });

        findViewById(R.id.add_money).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, AddMoneyActivity.class));
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

    @Override
    protected void onStart() {
        super.onStart();

        if (!GoodPrefs.getInstance().getBoolean("loggedIn", false)) {
            startActivity(new Intent(context, LoginActivity.class));
            finish();
            return;
        }

        if (GoodPrefs.getInstance().getObject("user", Auth.class).getRole().equalsIgnoreCase("driver")) {
            startActivity(new Intent(context, DriverMainActivity.class));
            finish();
            return;
        }


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
