package com.example.accountingapplication.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.accountingapplication.entity.Account;

import java.util.ArrayList;
import java.util.List;

public class MyDBOperation {

    //相当于请求操作数据库的权限
    SQLiteDatabase db = MySQLiteOpenHelp.getReadDatabase();

    public long insertAccount(Account account) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("sum", account.getSum());
        contentValues.put("date", account.getDate());
        contentValues.put("type",account.getType());
        contentValues.put("info",account.getInfo());
        return db.insert("accountList",null, contentValues);
    }

    public List<Account> readAccount(){
        List<Account> accountList = new ArrayList<>();

        Cursor cursor = db.query("accountList",null,"state=?",new String[]{"1"},null,null,null);
        while (cursor.moveToNext()){
            Account dbAccount = new Account();
            dbAccount.setSum(cursor.getString(cursor.getColumnIndex("sum")));
            dbAccount.setDate(cursor.getString(cursor.getColumnIndex("date")));
            dbAccount.setType(cursor.getString(cursor.getColumnIndex("type")));
            dbAccount.setInfo(cursor.getString(cursor.getColumnIndex("info")));
            dbAccount.setId(cursor.getInt(cursor.getColumnIndex("id")));
            accountList.add(0, dbAccount);
        }
        return accountList;
    }

    public void deleteAccount(Account account){
        int id = account.getId();
        System.out.println("deletedId========"+id);
        ContentValues contentValues = new ContentValues();
        contentValues.put("state", "0");
        db.update("accountList", contentValues, "id=?", new String[]{id+""});
    }
}