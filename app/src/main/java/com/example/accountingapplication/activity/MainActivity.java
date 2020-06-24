package com.example.accountingapplication.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.accountingapplication.R;
import com.example.accountingapplication.adapter.HomeViewPagerAdapter;
import com.example.accountingapplication.database.MySQLiteOpenHelp;
import com.example.accountingapplication.entity.Account;
import com.example.accountingapplication.fragment.AccountListFragment;
import com.example.accountingapplication.fragment.StaticsFragment;
import com.example.accountingapplication.utils.MyApplication;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ViewPager homeViewPager;
    ImageView accountImg, staticsImg, addAccount;

    private AccountListFragment accountListFragment;
    private StaticsFragment staticsFragment;
    private List<Fragment> fragmentList;
    private HomeViewPagerAdapter homeViewPagerAdapter;

    private List<Account> accountList;

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
        addAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddAccountActivity.class);
                startActivityForResult(intent, 1);
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

    }

    public void initData(){
        MySQLiteOpenHelp.mySQLiteOpenHelp = new MySQLiteOpenHelp(MainActivity.this);
        accountList = new ArrayList<>();
        dbOperation = new MyDBOperation();
        dbOperation.readAccount();
        System.out.println("size====="+accountList.size());
    }

    public class MyDBOperation {
        //相当于请求操作数据库的权限
        SQLiteDatabase db = MySQLiteOpenHelp.getReadDatabase();

        public long insertAccount(Account account) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("sum", account.getSum());
            contentValues.put("date", account.getDate());
            contentValues.put("type",account.getType());
            contentValues.put("info",account.getInfo());
            return db.insert("accounts",null, contentValues);
        }

        public void readAccount(){
            Cursor cursor = db.query("accounts",null,null,null,null,null,null);
            while (cursor.moveToNext()){
                Account dbAccount = new Account();
                dbAccount.setSum(cursor.getString(cursor.getColumnIndex("sum")));
                dbAccount.setDate(cursor.getString(cursor.getColumnIndex("date")));
                dbAccount.setType(cursor.getString(cursor.getColumnIndex("type")));
                dbAccount.setInfo(cursor.getString(cursor.getColumnIndex("info")));
                accountList.add(0, dbAccount);
            }
        }
    }
    public void setHomeViewPagerAdapter(){
        accountListFragment = new AccountListFragment(accountList);
        staticsFragment = new StaticsFragment();

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
        Account account = (Account) data.getExtras().getSerializable("account");
        System.out.println(account.getSum());
        long i = dbOperation.insertAccount(account);
        accountList.add(0,account);
        setHomeViewPagerAdapter();
    }
}
