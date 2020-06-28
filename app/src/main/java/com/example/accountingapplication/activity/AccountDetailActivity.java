package com.example.accountingapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.accountingapplication.R;
import com.example.accountingapplication.database.MyDBOperation;
import com.example.accountingapplication.database.MySQLiteOpenHelp;
import com.example.accountingapplication.entity.Account;

public class AccountDetailActivity extends AppCompatActivity {
    private Account account;
    private int position;
    private EditText sum, date, info;
    private Spinner type;
    private MyDBOperation dbOperation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_detail);
        initView();
        initData();
    }

    private void initView(){
        sum = findViewById(R.id.curSum);
        type = findViewById(R.id.curType);
        date = findViewById(R.id.curDate);
        info = findViewById(R.id.curInfo);
    }
    private void initData(){
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        account = (Account) bundle.getSerializable("account");
        position = bundle.getInt("position");

        sum.setText(account.getSum());
        date.setText(account.getDate());
        info.setText(account.getInfo());

        setSpinnerItemSelectedByValue(type, account.getType());
    }

    public void editAccount(View view){
        account.setSum(sum.getText()+"");
        account.setType(type.getSelectedItem()+"");
        account.setDate(date.getText()+"");
        account.setInfo(info.getText()+"");
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable("account",account);
        bundle.putInt("position", position);
        intent.putExtras(bundle);
        AccountDetailActivity.this.setResult(RESULT_OK, intent);
        AccountDetailActivity.this.finish();
    }

    public static void setSpinnerItemSelectedByValue(Spinner spinner,String value){
        SpinnerAdapter apsAdapter= spinner.getAdapter(); //得到SpinnerAdapter对象
        int k= apsAdapter.getCount();
        for(int i=0;i<k;i++){
            if(value.equals(apsAdapter.getItem(i).toString())){
                spinner.setSelection(i,true);// 默认选中项
                break;
            }
        }
    }
}
