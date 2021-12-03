package com.example.myaccount.dataBase;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Account.class},version = 1,exportSchema = false)
public abstract class AccountDataBase extends RoomDatabase {
    private static final String DB_NAME = "account";
    private static AccountDataBase accountInstance;

    public static synchronized AccountDataBase getInstance(Context context){
        if(accountInstance==null){
            accountInstance = Room
                            .databaseBuilder(context.getApplicationContext(),AccountDataBase.class,DB_NAME)
                            .build();
        }
        return accountInstance;
    }

    public abstract AccountDao getAccountDao();;
}
