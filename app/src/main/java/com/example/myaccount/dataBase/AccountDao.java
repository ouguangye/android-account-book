package com.example.myaccount.dataBase;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface AccountDao {

    //添加account
    @Insert
    void insertAccount(Account account);

    @Query("SELECT * FROM account WHERE age")

}
