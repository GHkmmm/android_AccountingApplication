package com.example.accountingapplication.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.app.ActivityOptions;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.example.accountingapplication.R;
import com.example.accountingapplication.adapter.HomeViewPagerAdapter;
import com.example.accountingapplication.database.MyDBOperation;
import com.example.accountingapplication.database.MySQLiteOpenHelp;
import com.example.accountingapplication.database.UserDB;
import com.example.accountingapplication.database.UserDBOperation;
import com.example.accountingapplication.entity.Account;
import com.example.accountingapplication.entity.User;
import com.example.accountingapplication.fragment.AccountListFragment;
import com.example.accountingapplication.fragment.StaticsFragment;
import com.example.accountingapplication.fragment.StaticsLineChartFragment;
import com.example.accountingapplication.utils.MyApplication;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ViewPager homeViewPager;
    private ImageView menu,accountImg, staticsImg, addAccount;
    private TextView month,totalSum, totalIncome, totalExpenses;
    private LinearLayout totalLinearLayout, pagerTop;
    private DrawerLayout dlNav;
    private NavigationView navView;
    private Switch isLockedSwitch;

    int income = 0;
    int expenses = 0;
    int sum = 0;

    private AccountListFragment accountListFragment;
    private StaticsFragment staticsFragment;
    private StaticsLineChartFragment staticsLineChartFragment;
    private List<Fragment> fragmentList;
    private HomeViewPagerAdapter homeViewPagerAdapter;

    public List<Account> accountList;

    private MyApplication myApplication;

    private MyDBOperation dbOperation;

    private UserDBOperation udb;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        udb.insertUser(user);

        initView();
        initData();
        setHomeViewPagerAdapter();
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dlNav.openDrawer(Gravity.LEFT);
            }
        });
        addAccount.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddAccountActivity.class);
                startActivityForResult(intent, 1, ActivityOptions.makeSceneTransitionAnimation(MainActivity.this).toBundle());
            }
        });
        isLockedSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    user.setIsLocked(1);
                    udb.updateUser(user);
                }else {
                    user.setIsLocked(0);
                    udb.updateUser(user);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (myApplication.isLocked==1){
            Intent intent = new Intent(MainActivity.this, PasswordActivity.class);
            intent.putExtra("password", user.getPassword());
            startActivity(intent);
        }
    }

    public void initView(){
        homeViewPager = findViewById(R.id.home_viewPager);
        accountImg = findViewById(R.id.account);
        staticsImg = findViewById(R.id.statics);
        addAccount = findViewById(R.id.addAccount);
        totalSum = findViewById(R.id.totalSum);
        totalLinearLayout = findViewById(R.id.totalLinearLayout);
        pagerTop = findViewById(R.id.pagerTop);
        menu = findViewById(R.id.menu);
        dlNav = findViewById(R.id.dlNav);
        navView = findViewById(R.id.navView);
        isLockedSwitch = navView.getHeaderView(0).findViewById(R.id.isLocked);
        month = findViewById(R.id.month);
        totalIncome = findViewById(R.id.total_income);
        totalExpenses = findViewById(R.id.total_expenses);
    }

    public void initData(){
        Calendar cal = Calendar.getInstance();
        int monthVal = cal.get(Calendar.MONTH )+1;
        month.setText(monthVal+"月");
        MySQLiteOpenHelp.mySQLiteOpenHelp = new MySQLiteOpenHelp(MainActivity.this);
        UserDB.mySQLiteOpenHelp = new UserDB(MainActivity.this);
        accountList = new ArrayList<>();
        dbOperation = new MyDBOperation();
        udb = new UserDBOperation();
        user = new User();
        accountList = dbOperation.readAccount();
        user = udb.readUser();

        myApplication = (MyApplication) getApplication();
        myApplication.isLocked = user.getIsLocked();
        if (myApplication.isLocked==0){
            isLockedSwitch.setChecked(false);
        }else {
            isLockedSwitch.setChecked(true);
        }

        setTotalSum();
    }

    public void calcTotalSum(){
        sum = 0;
        income = 0;
        expenses = 0;
        String category = "收入";
        for (int i=0;i<accountList.size();i++){
            System.out.println(i+"sum======"+accountList.get(i).getSum());
            System.out.println(i+"cate======"+accountList.get(i).getCategory());
            if(category.equals(accountList.get(i).getCategory())){
                sum = Integer.parseInt(accountList.get(i).getSum())+sum;
                income = income + Integer.parseInt(accountList.get(i).getSum());
            }else {
                sum = sum-Integer.parseInt(accountList.get(i).getSum());
                expenses = expenses + Integer.parseInt(accountList.get(i).getSum());
            }
        }
    }
    public void setTotalSum(){
        System.out.println("setTotalSum");
        calcTotalSum();
        totalSum.setText(sum+"");
        totalIncome.setText("收入："+income+"¥");
        totalExpenses.setText("支出："+expenses+"¥");
        if (sum>0){
            totalLinearLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            pagerTop.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        }else {
            totalLinearLayout.setBackgroundColor(getResources().getColor(R.color.redColor));
            pagerTop.setBackgroundColor(getResources().getColor(R.color.redColor));
        }
    }

    public void setHomeViewPagerAdapter(){
        accountListFragment = new AccountListFragment(accountList);
        staticsFragment = new StaticsFragment(accountList);
        staticsLineChartFragment = new StaticsLineChartFragment(accountList);

        fragmentList = new ArrayList<>();
        fragmentList.add(accountListFragment);
        fragmentList.add(staticsFragment);
        fragmentList.add(staticsLineChartFragment);

        FragmentManager fragmentManager = getSupportFragmentManager();
        homeViewPagerAdapter = new HomeViewPagerAdapter(fragmentManager, fragmentList);
        homeViewPager.setAdapter(homeViewPagerAdapter);

        homeViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                homeViewPager.setCurrentItem(position);
                switch (position){
                    case 0:
                        accountImg.setImageResource(R.drawable.account_actived);
                        staticsImg.setImageResource(R.drawable.statics);
                        break;
                    case 1:
                        accountImg.setImageResource(R.drawable.account);
                        staticsImg.setImageResource(R.drawable.statics_actived);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Account account = new Account();
        if (requestCode==1 && data!=null){
            account = (Account) data.getExtras().getSerializable("account");
            account.setId(accountList.get(0).getId()+1);
            long i = dbOperation.insertAccount(account);
            accountList.add(0,account);
            setTotalSum();
            setHomeViewPagerAdapter();
        }else if (data!=null){
            account = (Account) data.getExtras().getSerializable("account");
            int i = data.getExtras().getInt("position");
            System.out.println("i========"+i);
            dbOperation.updateAccount(account);
            accountList.get(i).setInfo(account.getInfo());
            accountList.get(i).setType(account.getType());
            accountList.get(i).setDate(account.getDate());
            accountList.get(i).setSum(account.getSum());
            setTotalSum();
            setHomeViewPagerAdapter();
        }
    }

}
