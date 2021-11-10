package com.example.myaccount.dataBase;

import android.database.Cursor;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;
@Dao
public interface AccountDao {
    @Insert
    void insertAccount(Account account);
    @Query("SELECT * FROM account WHERE uid IN (:userIds)")
    List<Account> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM account")
    Cursor getAccountCursor();
}
