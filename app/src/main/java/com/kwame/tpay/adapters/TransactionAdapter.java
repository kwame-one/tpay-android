package com.kwame.tpay.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kwame.tpay.R;
import com.kwame.tpay.models.Transaction;

import java.util.List;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.TransactionVH> {

    private Context context;
    private List<Transaction> transactions;

    public TransactionAdapter(Context context, List<Transaction> transactions) {
        this.context = context;
        this.transactions = transactions;
    }

    @NonNull
    @Override
    public TransactionVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TransactionVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_transaction, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionVH holder, int position) {

    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }

    class TransactionVH extends RecyclerView.ViewHolder {

        private TextView type, total, date;

        public TransactionVH(@NonNull View itemView) {
            super(itemView);
            type = itemView.findViewById(R.id.type);
            total = itemView.findViewById(R.id.total);
            date = itemView.findViewById(R.id.date);
        }
    }
}
