package com.example.accountingapplication.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.accountingapplication.R;
import com.example.accountingapplication.adapter.AccountListAdapter;
import com.example.accountingapplication.database.MyDBOperation;
import com.example.accountingapplication.entity.Account;

import java.util.List;

public class AccountListFragment extends Fragment {
    private List<Account> accounts;
    private RecyclerView accountList;
    private MyDBOperation dbOperation;

    public AccountListFragment(List<Account> accounts) {
        this.accounts = accounts;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.account, container, false);
        accountList = view.findViewById(R.id.accountList);
        initData();
        initRv();
        return view;
    }
    private void initData(){
        dbOperation = new MyDBOperation();
    }

    private void initRv(){
        AccountListAdapter accountListAdapter = new AccountListAdapter(accounts);
        accountList.setLayoutManager(new LinearLayoutManager(getActivity()));
        accountList.setAdapter(accountListAdapter);
        accountListAdapter.setOnItemClickListener(new AccountListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, final Account account, final int position) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setItems(new String[]{"查看详情", "删除记录","取消"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case 0:
                                dialog.dismiss();
                                break;
                            case 1:
                                deleteAccount(account, position);
                                break;
                            case 2:
                                dialog.dismiss();
                                break;
                        }
                    }
                });
                final AlertDialog dialog = builder.create();
                dialog.setTitle("选择操作");
                dialog.show();
            }
        });
    }

    public void deleteAccount(Account account, int position){
        dbOperation.deleteAccount(account);
        accounts.remove(position);
        initRv();
        Toast.makeText(getContext(), "删除成功", Toast.LENGTH_SHORT).show();
    }
}
