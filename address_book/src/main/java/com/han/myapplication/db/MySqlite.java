package com.han.myapplication.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by aaa on 2017/3/28.
 */

public class MySqlite extends SQLiteOpenHelper {

    public MySqlite(Context context) {
        // 参数2:数据库名字
        // 参数4：数据库版本 只能升 不能降
        super(context, "han.db", null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table people(id Integer primary key,name text,age text,type_id Integer)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("alert table people add nick text");
    }
}
