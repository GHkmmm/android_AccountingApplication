package com.example.accountingapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.accountingapplication.R;
import com.example.accountingapplication.entity.Account;

import java.util.List;

public class AccountListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Account> accountList;
    private String category = "收入";

    public AccountListAdapter(List<Account> accountList) {
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
        holder.type.setText(account.getType());
        holder.date.setText(account.getDate());
        holder.sum.setText("¥"+account.getSum());
        if (category.equals(account.getCategory())){
            holder.category.setImageResource(R.drawable.green_dot);
        }else {
            holder.category.setImageResource(R.drawable.red_dot);
        }
        switch (account.getType()){
            case "交通":
                holder.icon.setImageResource(R.drawable.traffic_actived);
                break;
            case "餐饮":
                holder.icon.setImageResource(R.drawable.eat_actived);
                break;
            case "衣服":
                holder.icon.setImageResource(R.drawable.clothes_actived);
                break;
            case "房子":
                holder.icon.setImageResource(R.drawable.house_actived);
                break;
            case "购物":
                holder.icon.setImageResource(R.drawable.shopping_actived);
                break;
            case "还款":
                holder.icon.setImageResource(R.drawable.repayment_actived);
                break;
            case "借钱":
                holder.icon.setImageResource(R.drawable.rent_actived);
                break;
            case "话费":
                holder.icon.setImageResource(R.drawable.phonebill_actived);
                break;
            case "电费":
                holder.icon.setImageResource(R.drawable.electricbill_actived);
                break;
            default:
                holder.icon.setImageResource(R.drawable.other_actived);
        }
    }

    @Override
    public int getItemCount() {
        return accountList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView type;
        TextView date;
        TextView sum;
        ImageView category;
        ImageView icon;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            type = itemView.findViewById(R.id.type);
            date = itemView.findViewById(R.id.date);
            sum = itemView.findViewById(R.id.sum);
            category = itemView.findViewById(R.id.insDot);
            icon = itemView.findViewById(R.id.icon);

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

    public void remove(int position){
        accountList.remove(position);
        notifyItemRemoved(position);
    }
}
