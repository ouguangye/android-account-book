package com.example.myaccount.dataBase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface AccountDao {

    //添加account
    @Insert
    public void insertAccount(Account account);

    @Query("SELECT * FROM account")
    public Account[] queryAll();

    @Query("SELECT sid FROM account")
    public LiveData<int[]> queryIds();

    @Query("SELECT date FROM account")
    public String queryDate();

    @Query("SELECT * FROM account WHERE days > :days")
    public Account[] queryLastDays(int days);

    @Query("SELECT * FROM account WHERE date LIKE :month")
    public Account[] queryMonth(String month);
}
