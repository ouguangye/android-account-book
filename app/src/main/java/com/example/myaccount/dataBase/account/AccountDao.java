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

    @Query("SELECT * FROM account WHERE uid = :uid")
    public Account[] queryAll(int uid);

    @Query("SELECT sid FROM account WHERE uid = :uid")
    public LiveData<int[]> queryIds(int uid);

    @Query("SELECT date FROM account WHERE uid = :uid")
    public String queryDate(int uid);

    @Query("SELECT * FROM account WHERE days > :days AND uid = :uid")
    public Account[] queryLastDays(int days, int uid);

    @Query("SELECT amount FROM account WHERE date LIKE :month AND uid = :uid AND sign = 1")
    public Float[] queryIncomeMonth(String month, int uid);

    @Query("SELECT amount FROM account WHERE date LIKE :month AND uid = :uid AND sign = 0")
    public Float[] queryOutcomeMonth(String month, int uid);

    @Query("DELETE FROM account WHERE sid = :Sid")
    public void deleteBySid(long Sid);

}
