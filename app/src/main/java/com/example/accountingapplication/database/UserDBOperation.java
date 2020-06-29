package com.example.accountingapplication.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.accountingapplication.entity.User;

public class UserDBOperation {

    SQLiteDatabase db = UserDB.getReadDatabase();

    public long insertUser(User user){
        System.out.println("username======="+user.getUsername());
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", user.getUsername());
        contentValues.put("password", user.getPassword());
        contentValues.put("isLocked",user.getIsLocked());
        return db.insert("user",null, contentValues);
    }

    public User readUser(){
        User user = new User();
        Cursor cursor = db.query("user",null,null,null,null,null,null);
        while (cursor.moveToNext()){
            System.out.println("111111");
            user.setPassword(cursor.getString(cursor.getColumnIndex("password")));
            user.setIsLocked(cursor.getInt(cursor.getColumnIndex("isLocked")));
        }
        return user;
    }

    public void updateUser(User user){
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", user.getUsername());
        contentValues.put("password", user.getPassword());
        contentValues.put("isLocked",user.getIsLocked());

        db.update("user", contentValues, "id=?", new String[]{"2"});
    }
}
