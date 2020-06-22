package com.kwame.tpay.activities;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.archit.calendardaterangepicker.customviews.CalendarListener;
import com.archit.calendardaterangepicker.customviews.DateRangeCalendarView;
import com.kwame.tpay.R;
import com.kwame.tpay.adapters.ExpensesAdapter;
import com.kwame.tpay.contracts.expenses.ExpensePresenterImp;
import com.kwame.tpay.contracts.expenses.ExpenseView;
import com.kwame.tpay.models.Expense;
import com.kwame.tpay.utils.AppUtils;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ExpensesActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, ExpenseView {

    private RecyclerView recyclerView;
    private SwipeRefreshLayout refreshLayout;
    private TextView info;
    private Context context  = ExpensesActivity.this;
    private ExpensesAdapter adapter;
    private List<Expense> expenses =  new ArrayList<>();
    private ExpensePresenterImp presenter;
    private ProgressBar loading;
    private String startDate = "", endDate = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenses);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.expenses));
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

        presenter = new ExpensePresenterImp(this);

        info = findViewById(R.id.info);
        loading = findViewById(R.id.loading);
        refreshLayout = findViewById(R.id.refresh);
        refreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary), getResources().getColor(R.color.colorPrimaryDark));

        refreshLayout.setOnRefreshListener(this);

        recyclerView = findViewById(R.id.recycler_view_expenses);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));

        adapter = new ExpensesAdapter(context, expenses);

        recyclerView.setAdapter(adapter);

        presenter.getExpenses(startDate, endDate);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.filter_by, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.filter) {

            displayDateRange();

            return  true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRefresh() {
        presenter.getExpenses(startDate, endDate);
    }

    @Override
    public void onLoadExpensesSuccess(List<Expense> data) {
        if (refreshLayout.isRefreshing()) refreshLayout.setRefreshing(false);
        loading.setVisibility(View.GONE);

        if (data.size() > 0) {
            recyclerView.setVisibility(View.VISIBLE);
            info.setVisibility(View.GONE);
        }else {
            recyclerView.setVisibility(View.GONE);
            info.setVisibility(View.VISIBLE);
        }
        expenses.clear();
        expenses.addAll(data);

        adapter.notifyDataSetChanged();
    }

    @Override
    public void onLoadExpenseFailure(String message) {
        if (refreshLayout.isRefreshing()) refreshLayout.setRefreshing(false);
        loading.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
        info.setVisibility(View.VISIBLE);
        info.setText(message);
        AppUtils.toast(context, message);
    }


    private void displayDateRange() {
        AlertDialog.Builder alert = new AlertDialog.Builder(context);

        ViewGroup viewGroup = findViewById(android.R.id.content);

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.dialog_date_range, viewGroup, false);

        alert.setView(view);

        final AlertDialog dialog = alert.create();

        DateRangeCalendarView calendarView = view.findViewById(R.id.calendar);
        calendarView.setCalendarListener(new CalendarListener() {
            @Override
            public void onFirstDateSelected(@NotNull Calendar calendar) {

            }

            @Override
            public void onDateRangeSelected(@NotNull Calendar calendar, @NotNull Calendar calendar1) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                startDate = dateFormat.format(calendar.getTime());
                endDate = dateFormat.format(calendar1.getTime());

                refreshLayout.setRefreshing(true);
                presenter.getExpenses(startDate, endDate);

                dialog.dismiss();
            }
        });


        dialog.show();
    }
}
