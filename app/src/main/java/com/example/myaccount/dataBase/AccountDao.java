package com.example.myaccount.dataBase;

import androidx.room.Dao;
import androidx.room.Insert;
@Dao
public interface AccountDao {

    //添加account
    @Insert
    void insertAccount(Account account);

}
