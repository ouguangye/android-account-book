package com.example.myaccount.dataBase.room;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Account.class},version = 1,exportSchema = false)
public abstract class AccountDataBase extends RoomDatabase {
    private static final String DB_NAME = "account.db";
    private static volatile AccountDataBase instance;

    public static synchronized AccountDataBase getInstance(Context context){
        if(instance==null) instance = create(context);
        return instance;
    }

    private static AccountDataBase create(final Context context){
        return Room.databaseBuilder(context,AccountDataBase.class,DB_NAME).allowMainThreadQueries().build();
    }

    public abstract AccountDao getAccountDao();;
}
