package com.example.myaccount.dataBase.user;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDao {

    //添加用户
    @Insert
    public void addUser(User user);


    //获得密码
    @Query("SELECT * FROM user WHERE username = :name AND password = :password")
    public List<User> getUser(String name, String password);


}
