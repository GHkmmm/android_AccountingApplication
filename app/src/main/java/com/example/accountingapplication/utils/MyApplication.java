package com.example.accountingapplication.utils;

import android.app.Application;

import com.example.accountingapplication.database.UserDBOperation;
import com.example.accountingapplication.entity.User;

public class MyApplication extends Application {
    public int isLocked;
    public String password;
    private User user;
    public UserDBOperation userDBOperation;

    @Override
    public void onCreate() {
        super.onCreate();
        user = new User();
//        userDBOperation = new UserDBOperation();
//
//        user = userDBOperation.readUser();
//        if (user.getIsLocked()==0){
//            isLocked = false;
//        }else {
//            isLocked = true;
//        }
//        password = user.getPassword();
    }
}
