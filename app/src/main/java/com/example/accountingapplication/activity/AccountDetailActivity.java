package com.example.accountingapplication.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.accountingapplication.R;
import com.example.accountingapplication.database.MyDBOperation;
import com.example.accountingapplication.database.MySQLiteOpenHelp;
import com.example.accountingapplication.entity.Account;

import java.util.Calendar;

public class AccountDetailActivity extends AppCompatActivity implements DatePicker.OnDateChangedListener{
    private Account account;
    private int position;
    private EditText sum, info;
    private TextView dateVal;
    private Spinner type;
    private MyDBOperation dbOperation;

    private Context context;
    private StringBuffer date, time;
    private int year, month, day, hour, minute;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_detail);
        context = this;
        initView();
        initData();
        initDateTime();
    }

    private void initView(){
        sum = findViewById(R.id.curSum);
        type = findViewById(R.id.curType);
        dateVal = findViewById(R.id.curDate);
        info = findViewById(R.id.curInfo);
    }
    private void initData(){
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        account = (Account) bundle.getSerializable("account");
        position = bundle.getInt("position");

        sum.setText(account.getSum());
        dateVal.setText(account.getDate());
        info.setText(account.getInfo());

        setSpinnerItemSelectedByValue(type, account.getType());

        date = new StringBuffer();
        time = new StringBuffer();
    }

    public void editAccount(View view){
        account.setSum(sum.getText()+"");
        account.setType(type.getSelectedItem()+"");
        account.setDate(dateVal.getText()+"");
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

    public void selectDate2(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setPositiveButton("设置", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (date.length() > 0) { //清除上次记录的日期
                    date.delete(0, date.length());
                }
                dateVal.setText(date.append(year).append("-").append(month).append("-").append(day));
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        final AlertDialog dialog = builder.create();
        View dialogView = View.inflate(context, R.layout.dialog_date, null);
        final DatePicker datePicker = (DatePicker) dialogView.findViewById(R.id.datePicker);
        dialog.setTitle("设置日期");
        dialog.setView(dialogView);
        dialog.show();
        //初始化日期监听事件
        datePicker.init(year, month - 1, day, this);
    }

    @Override
    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        this.year = year;
        this.month = monthOfYear+1;
        this.day = dayOfMonth;
    }

    private void initDateTime() {
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH)+1;
        day = calendar.get(Calendar.DAY_OF_MONTH);
    }
}
