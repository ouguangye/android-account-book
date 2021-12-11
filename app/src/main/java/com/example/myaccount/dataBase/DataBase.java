package com.example.myaccount.dataBase;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.myaccount.dataBase.account.Account;
import com.example.myaccount.dataBase.account.AccountDao;
import com.example.myaccount.dataBase.user.User;
import com.example.myaccount.dataBase.user.UserDao;

@Database(entities = {Account.class, User.class},version = 1,exportSchema = false)
public abstract class DataBase extends RoomDatabase {
    private static final String DB_NAME = "account";
    private static DataBase accountInstance;

    public static synchronized DataBase getInstance(Context context){
        if(accountInstance==null){
            accountInstance = Room
                            .databaseBuilder(context.getApplicationContext(), DataBase.class,DB_NAME)
                            .build();
        }
        return accountInstance;
    }

    public abstract AccountDao getAccountDao();
    public abstract UserDao getUserDao();
}
