package com.example.accountingapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.accountingapplication.R;
import com.example.accountingapplication.entity.Account;

import java.util.List;

public class AccountListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Account> accountList;

    public AccountListAdapter(List<Account> accountList) {
        System.out.println(accountList.size());
        this.accountList = accountList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.account_list_item, parent, false);
        RecyclerView.ViewHolder holder = new MyViewHolder(item);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        MyViewHolder holder = (MyViewHolder) viewHolder;
        Account account = accountList.get(position);
        System.out.println("sum====="+account.getSum());
        holder.type.setText(account.getType());
        holder.date.setText(account.getDate());
        holder.sum.setText("¥"+account.getSum());
    }

    @Override
    public int getItemCount() {
        return accountList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView type;
        TextView date;
        TextView sum;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            type = itemView.findViewById(R.id.type);
            date = itemView.findViewById(R.id.date);
            sum = itemView.findViewById(R.id.sum);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    if(onItemClickListener != null){
                        onItemClickListener.onItemClick(v, accountList.get(getLayoutPosition()), getLayoutPosition());
                    }
                }
            });
        }
    }

    public interface OnItemClickListener{
        public void onItemClick(View view, Account account, int position);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }
}
