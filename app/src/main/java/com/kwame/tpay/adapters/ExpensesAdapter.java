package com.kwame.tpay.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kwame.tpay.R;
import com.kwame.tpay.models.Expense;

import java.util.List;

public class ExpensesAdapter extends RecyclerView.Adapter<ExpensesAdapter.ExpensesVH> {

    private Context context;
    private List<Expense> expenses;

    public ExpensesAdapter(Context context, List<Expense> expenses) {
        this.context = context;
        this.expenses = expenses;
    }

    @NonNull
    @Override
    public ExpensesVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ExpensesVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_expense, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ExpensesVH holder, int position) {
        Expense expense = expenses.get(position);

        holder.date.setText(expense.getDate());
        holder.time.setText(expense.getTime());
        holder.amount.setText("GHS "+expense.getTotal());
    }

    @Override
    public int getItemCount() {
        return expenses.size();
    }

    class ExpensesVH extends RecyclerView.ViewHolder {

        private TextView date, time, amount;

        public ExpensesVH(@NonNull View itemView) {
            super(itemView);

            date = itemView.findViewById(R.id.date);
            time = itemView.findViewById(R.id.time);
            amount = itemView.findViewById(R.id.amount);
        }
    }
}
