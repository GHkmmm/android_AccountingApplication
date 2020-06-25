package com.example.accountingapplication.utils;

import android.app.Application;

public class MyApplication extends Application {
    public Boolean isLocked;
    public String password;

    @Override
    public void onCreate() {
        super.onCreate();
        isLocked = false;
        password = "hgm";
    }
}
