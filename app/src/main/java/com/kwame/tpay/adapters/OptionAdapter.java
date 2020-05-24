package com.kwame.tpay.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kwame.tpay.R;
import com.kwame.tpay.listeners.ItemClickListener;
import com.kwame.tpay.models.Auth;
import com.kwame.tpay.models.Option;
import com.kwame.tpay.utils.AppUtils;

import java.util.List;

public class OptionAdapter extends RecyclerView.Adapter<OptionAdapter.WalletVH> {

    private Context context;
    private List<Option> options;
    private ItemClickListener listener;

    public OptionAdapter(Context context, List<Option> options) {
        this.context = context;
        this.options = options;
    }


    public void setItemClickListener(ItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public WalletVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new WalletVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_options, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull WalletVH holder, int position) {
        Option option = options.get(position);

        holder.title.setText(option.getTitle());
        holder.icon.setImageResource(option.getImage());
    }

    @Override
    public int getItemCount() {
        return options.size();
    }

    class WalletVH extends RecyclerView.ViewHolder {
        private ImageView icon;
        private TextView title;

        public WalletVH(@NonNull View itemView) {
            super(itemView);

            icon = itemView.findViewById(R.id.icon);
            title = itemView.findViewById(R.id.title);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(view, getAdapterPosition());
                }
            });

        }
    }
}
