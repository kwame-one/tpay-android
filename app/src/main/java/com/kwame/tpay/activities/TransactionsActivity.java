package com.kwame.tpay.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.kwame.tpay.R;
import com.kwame.tpay.adapters.TransactionAdapter;
import com.kwame.tpay.contracts.transactions.TransactionsPresenterImp;
import com.kwame.tpay.contracts.transactions.TransactionsView;
import com.kwame.tpay.models.Transaction;

import java.util.ArrayList;
import java.util.List;

public class TransactionsActivity extends AppCompatActivity implements TransactionsView {

    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private ProgressBar loading;
    private TransactionsPresenterImp presenterImp;
    private TransactionAdapter adapter;
    private List<Transaction> transactions = new ArrayList<>();
    private TextView info;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transactions);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.transactions));
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

        presenterImp.getTransactions();
    }

    private void init() {
        refreshLayout = findViewById(R.id.refresh);
        recyclerView = findViewById(R.id.recycler_view_transactions);
        loading = findViewById(R.id.loading);
        info = findViewById(R.id.info);

        refreshLayout.setColorSchemeColors(
                getResources().getColor(R.color.colorPrimary),
                getResources().getColor(R.color.colorPrimaryDark)
        );

        presenterImp = new TransactionsPresenterImp(this);

        adapter = new TransactionAdapter(this, transactions);
    }

    @Override
    public void onTransactionsSuccess(List<Transaction> data) {
        if (refreshLayout.isRefreshing())
            refreshLayout.setRefreshing(false);

        loading.setVisibility(View.GONE);

        if (data.size() > 0) {

            transactions.clear();
            transactions.addAll(data);

            info.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);

        }else {

            info.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);

            info.setText("No Transactions Available");
        }

        adapter.notifyDataSetChanged();


    }

    @Override
    public void onTransactionsFailure(String message) {
        if (refreshLayout.isRefreshing())
            refreshLayout.setRefreshing(false);

        loading.setVisibility(View.GONE);
        info.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        
        info.setText(message);


    }
}
