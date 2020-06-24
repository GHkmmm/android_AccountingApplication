package com.example.accountingapplication.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.accountingapplication.R;
import com.example.accountingapplication.adapter.AccountListAdapter;
import com.example.accountingapplication.entity.Account;

import java.util.List;

public class AccountListFragment extends Fragment {
    private List<Account> accounts;
    private RecyclerView accountList;

    public AccountListFragment(List<Account> accounts) {
        this.accounts = accounts;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.account, container, false);
        accountList = view.findViewById(R.id.accountList);
        initView();
        initRv();
        return view;
    }
    private void initView(){

    }

    private void initRv(){
        AccountListAdapter accountListAdapter = new AccountListAdapter(accounts);
        accountList.setLayoutManager(new LinearLayoutManager(getActivity()));
        accountList.setAdapter(accountListAdapter);
    }
}
