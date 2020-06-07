package com.kwame.tpay.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.kwame.tpay.R;
import com.kwame.tpay.adapters.TransactionAdapter;
import com.kwame.tpay.contracts.transactions.TransactionsPresenterImp;
import com.kwame.tpay.contracts.transactions.TransactionsView;
import com.kwame.tpay.models.Transaction;

import java.util.ArrayList;
import java.util.List;

public class TransactionsActivity extends AppCompatActivity implements TransactionsView, SwipeRefreshLayout.OnRefreshListener {

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
        refreshLayout.setOnRefreshListener(this);

        recyclerView = findViewById(R.id.recycler_view_transactions);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(TransactionsActivity.this, RecyclerView.VERTICAL,false));
        recyclerView.addItemDecoration(new DividerItemDecoration(TransactionsActivity.this, DividerItemDecoration.VERTICAL));

        loading = findViewById(R.id.loading);
        info = findViewById(R.id.info);

        refreshLayout.setColorSchemeColors(
                getResources().getColor(R.color.colorPrimary),
                getResources().getColor(R.color.colorPrimaryDark)
        );

        presenterImp = new TransactionsPresenterImp(this);
        presenterImp.getTransactions();

        adapter = new TransactionAdapter(this, transactions);

        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onTransactionsSuccess(List<Transaction> data) {
        if (refreshLayout.isRefreshing())
            refreshLayout.setRefreshing(false);

        loading.setVisibility(View.GONE);

        if (data.size() > 0) {

            transactions.clear();
            transactions.addAll(data);
//            for(int i=0; i<data.size(); i++) {
//                Transaction t = transactions.get(i);
//
//                transactions.add(new Transaction(t.getId(), t.getTotal(), t.getType(), t.getDate(), t.getTransactionId()));
//            }


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

    @Override
    public void onRefresh() {
        presenterImp.getTransactions();
    }
}
