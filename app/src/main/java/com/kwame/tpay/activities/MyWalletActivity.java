package com.kwame.tpay.activities;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kwame.tpay.R;
import com.kwame.tpay.adapters.OptionAdapter;
import com.kwame.tpay.contracts.wallet.WalletListener;
import com.kwame.tpay.contracts.wallet.WalletPresenterImp;
import com.kwame.tpay.listeners.ItemClickListener;
import com.kwame.tpay.models.Option;
import com.kwame.tpay.utils.AppUtils;

import java.util.ArrayList;
import java.util.List;

public class MyWalletActivity extends AppCompatActivity implements WalletListener {

    private List<Option> options = new ArrayList<>();
    private OptionAdapter adapter;
    private Context context = MyWalletActivity.this;
    private WalletPresenterImp presenterImp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_wallet);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.my_wallet));
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        init();
    }


    private void init() {
        presenterImp = new WalletPresenterImp(this);

        RecyclerView recyclerView = findViewById(R.id.recycler_view_wallet_options);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
        recyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));

        adapter = new OptionAdapter(context, options);
        recyclerView.setAdapter(adapter);

        presenterImp.getWalletOptions();


        adapter.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                AppUtils.toast(context, options.get(position).getTitle());
                if (position == 0) { // activate wallet

                }else if (position == 1) { // deactivate wallet

                }else if(position == 2) { // check balance

                }else if(position == 3) { // expenses

                }
            }
        });


    }

    @Override
    public void onReturnWalletOptions(List<Option> data) {
        options.addAll(data);
        adapter.notifyDataSetChanged();
    }
}
