package com.example.myaccount.dataBase.user;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user")
public class User {
    //序列id
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "sid",typeAffinity = ColumnInfo.INTEGER)
    private int sid;

    @ColumnInfo(name = "username",typeAffinity = ColumnInfo.TEXT)
    private String name;

    @ColumnInfo(name = "password",typeAffinity = ColumnInfo.TEXT)
    private String password;

    public int getSid() {
        return sid;
    }

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}