package com.example.myaccount.dataBase.user;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface UserDao {

    //添加用户
    @Insert
    public void addUser(User user);


    //获得密码
    @Query("SELECT * FROM user WHERE username = :name")
    public User getPassword(String name);


}
