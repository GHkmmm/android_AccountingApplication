package com.example.accountingapplication.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.accountingapplication.entity.User;

public class UserDB extends SQLiteOpenHelper {
    //新建一个SQLiteOpenHelper的对象，在之后初始化时会用到
    public static UserDB mySQLiteOpenHelp;
    // 数据库版本号,在更新时系统便是根据version来判断，若version号低于则会启动升级程序
    private static final int version = 1;
    //设置你自己的数据库名称
    public static final String DATABASE_NAME = "User.db";
    //这是在初始化之后有增、删、改、查之后的需要时，需要借组SQLiteDatabase来进行操作

    private static SQLiteDatabase dbForWrite;

    private static SQLiteDatabase dbForRead;

    //这里是构造方法，主要实现SQLiteOpenHelper的初始化工作，注意：若SQLiteOpenHelper没有初始化，则在使用时会报空指针异常
    //即，需要明确指出Context即环境，否则会报NULLPOINT错误
    //这是系统提供的初始化构造方法，你也可以使用虾下面的构造方法来实现数据库自己命名
//    public MySQLiteOpenHelp(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
//        super(context, name, factory, version);
//    }
    public UserDB(Context context) {
        super(context, DATABASE_NAME, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String TABLEUSER = "create table user("+"id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "username TEXT,"+
                "password TEXT,"+
                "isLocked interger default 1"+
                ");";
        db.execSQL(TABLEUSER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        String sql = "Alter table accountList add column category Text ";
//        db.execSQL(sql);
    }

    // 创建或打开一个数据库。这和getWritableDatabase()返回的对象是同一个，
    // 除非一些因素要求数据库只能以read-only的方式被打开，比如磁盘满了。
    // 在这种情况下，一个只读的数据库对象将被返回。如果这个问题被修改掉，将来调用getWritableDatabase()就可能成功，而这时read-only数据库对象将被关闭，并且读写对象将被返回。

    public static SQLiteDatabase getReadDatabase() {
        if (dbForRead == null) {
            dbForRead = mySQLiteOpenHelp.getReadableDatabase();
        }
        return dbForRead;
    }

    // 创建或打开一个数据库，用于读写。该方法第一次被调用的时候，数据库被打开，并且onCreate(SQLiteDatabase)，onUpgrade(SQLiteDatabase，int，int)
    // 或onOpen(SQLiteDatabase)将被调用。
    public static SQLiteDatabase getWriteDatabase() {
        if (dbForWrite == null) {
            dbForWrite = mySQLiteOpenHelp.getWritableDatabase();
        }
        return dbForWrite;
    }
}
