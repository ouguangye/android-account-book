package com.example.myaccount.dataBase.nomal;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "account_db";
    private static final int DATABASE_VERSION = 16;

    public MyDBHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    public MyDBHelper(Context context,String databaseName,int version){
        super(context,databaseName,null,version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
       createTable(sqLiteDatabase,"income");
       createTable(sqLiteDatabase,"outcome");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    protected void createTable(SQLiteDatabase db,String s){
        String strSQL = "create table " + s + " (sid integer primary key autoincrement, date text, type text, amount float)";
        db.execSQL(strSQL);
    }
}
