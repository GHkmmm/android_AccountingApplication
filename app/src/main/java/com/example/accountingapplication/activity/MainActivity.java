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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.accountingapplication.R;
import com.example.accountingapplication.adapter.HomeViewPagerAdapter;
import com.example.accountingapplication.database.MyDBOperation;
import com.example.accountingapplication.database.MySQLiteOpenHelp;
import com.example.accountingapplication.entity.Account;
import com.example.accountingapplication.fragment.AccountListFragment;
import com.example.accountingapplication.fragment.StaticsFragment;
import com.example.accountingapplication.utils.MyApplication;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ViewPager homeViewPager;
    private ImageView menu,accountImg, staticsImg, addAccount;
    private TextView totalSum;
    private LinearLayout totalLinearLayout, pagerTop;
    private DrawerLayout dlNav;
    private NavigationView navView;

    private AccountListFragment accountListFragment;
    private StaticsFragment staticsFragment;
    private List<Fragment> fragmentList;
    private HomeViewPagerAdapter homeViewPagerAdapter;

    public List<Account> accountList;

//    private SQLiteDatabase db;
    private MyDBOperation dbOperation;

    private MyApplication myApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
    }

    @Override
    protected void onResume() {
        super.onResume();
        myApplication = (MyApplication) getApplication();
        if (myApplication.isLocked){
            Intent intent = new Intent(MainActivity.this, PasswordActivity.class);
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
    }

    public void initData(){
        MySQLiteOpenHelp.mySQLiteOpenHelp = new MySQLiteOpenHelp(MainActivity.this);
        accountList = new ArrayList<>();
        dbOperation = new MyDBOperation();
        accountList = dbOperation.readAccount();
        setTotalSum();
    }

    public int calcTotalSum(){
        int sum = 0;
        String category = "收入";
        for (int i=0;i<accountList.size();i++){
            if(category.equals(accountList.get(i).getCategory())){
                sum = Integer.parseInt(accountList.get(i).getSum())+sum;
            }else {
                sum = sum-Integer.parseInt(accountList.get(i).getSum());
            }
        }
        return sum;
    }
    public void setTotalSum(){
        totalSum.setText(calcTotalSum()+"");
        if (calcTotalSum()>0){
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

        fragmentList = new ArrayList<>();
        fragmentList.add(accountListFragment);
        fragmentList.add(staticsFragment);

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
        if (data!=null){
            account = (Account) data.getExtras().getSerializable("account");
            System.out.println(account.getSum());
            long i = dbOperation.insertAccount(account);
            accountList.add(0,account);
            setTotalSum();
            setHomeViewPagerAdapter();
        }
    }

}
