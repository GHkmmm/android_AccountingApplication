package com.example.accountingapplication.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.example.accountingapplication.R;
import com.example.accountingapplication.adapter.AddAccountViewPagerAdapter;
import com.example.accountingapplication.entity.Account;
import com.example.accountingapplication.fragment.AddExpensesFragment;
import com.example.accountingapplication.fragment.AddIncomeFragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddAccountActivity extends AppCompatActivity implements View.OnClickListener, DatePicker.OnDateChangedListener {
    private Context context;
    private List<Fragment> fragmentList;
    private AddExpensesFragment addExpensesFragment;
    private AddIncomeFragment addIncomeFragment;
    private ViewPager addAccountViewPager;
    private AddAccountViewPagerAdapter addAccountViewPagerAdapter;
    private int year, month, day, hour, minute;
    private StringBuffer date, time;
    private String total;
    private Account account;

    private TextView tvDate, totalNum;
    private EditText info;
    private List<Button> buttonList;
    private Button one, two, three, four,five,six,seven,eight,nine,zero,delete,dot;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_account);
        context = this;
        date = new StringBuffer();
        time = new StringBuffer();

        initView();
        initDateTime();
        setAddAccountViewPager();
    }

    public void initView(){
        addAccountViewPager = findViewById(R.id.addAccountViewPager);
        tvDate = findViewById(R.id.tvDate);
        info = findViewById(R.id.info);
        totalNum = findViewById(R.id.totalNum);
        one = findViewById(R.id.one);
        two = findViewById(R.id.two);
        three = findViewById(R.id.three);
        four = findViewById(R.id.four);
        five = findViewById(R.id.five);
        six = findViewById(R.id.six);
        seven = findViewById(R.id.seven);
        eight = findViewById(R.id.eight);
        nine = findViewById(R.id.nine);
        zero = findViewById(R.id.zero);
        delete = findViewById(R.id.delete);
        dot = findViewById(R.id.dot);
        buttonList = new ArrayList<>();
        buttonList.add(one);
        buttonList.add(two);
        buttonList.add(three);
        buttonList.add(four);
        buttonList.add(five);
        buttonList.add(six);
        buttonList.add(seven);
        buttonList.add(eight);
        buttonList.add(nine);
        buttonList.add(zero);
        buttonList.add(delete);
        buttonList.add(dot);
        for (int i=0;i<buttonList.size();i++){
            buttonList.get(i).setOnClickListener(this);
        }
        total = "0";
    }

    /**
     * 设置ViewPager
     */
    public void setAddAccountViewPager(){
        addExpensesFragment = new AddExpensesFragment();
        addIncomeFragment = new AddIncomeFragment();
        fragmentList = new ArrayList<>();

        fragmentList.add(addExpensesFragment);
        fragmentList.add(addIncomeFragment);

        FragmentManager fragmentManager = getSupportFragmentManager();
        addAccountViewPagerAdapter = new AddAccountViewPagerAdapter(fragmentManager, fragmentList);

        addAccountViewPager.setAdapter(addAccountViewPagerAdapter);

        addAccountViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                addAccountViewPager.setCurrentItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * 监听选择日期按钮
     * 弹出日期选择框
     * @param view
     */
    public void selectDate(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setPositiveButton("设置", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (date.length() > 0) { //清除上次记录的日期
                    date.delete(0, date.length());
                }
                tvDate.setText(date.append(String.valueOf(year)).append("-").append(String.valueOf(month)).append("-").append(day));
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

    private void initDateTime() {
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH) + 1;
        day = calendar.get(Calendar.DAY_OF_MONTH);
        hour = calendar.get(Calendar.HOUR);
        minute = calendar.get(Calendar.MINUTE);
        tvDate.setText(year+"-"+month+"-"+day);
    }

    @Override
    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        this.year = year;
        this.month = monthOfYear;
        this.day = dayOfMonth;
    }

    public void addAccount(View view){
        account = new Account();
        account.setSum(total);
        account.setDate(date.toString());
        account.setType("traffic");
        account.setInfo(info.getText().toString());
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable("account",account);
        intent.putExtras(bundle);
        AddAccountActivity.this.setResult(RESULT_OK, intent);
        AddAccountActivity.this.finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.one:
                if (total=="0"){
                    total = "1";
                }else {
                    total = total+"1";
                }
                break;
            case R.id.two:
                if (total=="0"){
                    total = "2";
                }else {
                    total = total+"2";
                }
                break;
            case R.id.three:
                if (total=="0"){
                    total = "3";
                }else {
                    total = total+"3";
                }
                break;
            case R.id.four:
                if (total=="0"){
                    total = "4";
                }else {
                    total = total+"4";
                }
                break;
            case R.id.five:
                if (total=="0"){
                    total = "5";
                }else {
                    total = total+"5";
                }
                break;
            case R.id.six:
                if (total=="0"){
                    total = "6";
                }else {
                    total = total+"6";
                }
                break;
            case R.id.seven:
                if (total=="0"){
                    total = "7";
                }else {
                    total = total+"7";
                }
                break;
            case R.id.eight:
                if (total=="0"){
                    total = "8";
                }else {
                    total = total+"8";
                }
                break;
            case R.id.nine:
                if (total=="0"){
                    total = "9";
                }else {
                    total = total+"9";
                }
                break;
            case R.id.zero:
                if (total.length()!=1){
                    total = total+"0";
                }else {
                    total = "0";
                }
                break;
            case R.id.delete:
                if (total.length()<=1){
                    total = "0";
                }else {
                    total = total.substring(0, total.length()-1);
                }
                break;
            case R.id.dot:
                if (!total.contains(".")){
                    total = total+".";
                }
                break;
        }
        totalNum.setText(total);
    }
}
