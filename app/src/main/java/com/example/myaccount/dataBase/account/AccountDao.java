package com.example.myaccount.dataBase.account;

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

    @Query("SELECT sid FROM account WHERE uid = :uid")
    public LiveData<int[]> queryIds(int uid);

    @Query("SELECT date FROM account WHERE uid = :uid")
    public String queryDate(int uid);

    @Query("SELECT * FROM account WHERE days > :days AND uid = :uid")
    public Account[] queryLastDays(int days, int uid);

    @Query("SELECT * FROM account WHERE date LIKE :month AND uid = :uid")
    public Account[] queryMonth(String month, int uid);
}
