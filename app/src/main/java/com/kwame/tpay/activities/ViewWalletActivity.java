package com.kwame.tpay.activities;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.kwame.tpay.R;
import com.kwame.tpay.models.Auth;
import com.kwame.tpay.utils.GoodPrefs;
import com.squareup.picasso.Picasso;

public class ViewWalletActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_wallet);


        ImageView imageView = findViewById(R.id.image);
        Auth user = GoodPrefs.getInstance().getObject("user", Auth.class);

        Picasso.get().load(user.getWallet().getQRCode()).placeholder(R.drawable.picture).into(imageView);

    }
}
