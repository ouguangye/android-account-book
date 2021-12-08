package com.example.myaccount.dataBase;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "account")
public class Account {

    //序列id
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "sid",typeAffinity = ColumnInfo.INTEGER)
    private int sid;

    //用户对应的id
    @ColumnInfo(name = "uid",typeAffinity = ColumnInfo.INTEGER)
    private int uid;

    //符号, 0表示支出，1表示收入
    @ColumnInfo(name = "sign",typeAffinity = ColumnInfo.INTEGER)
    private int sign;

    //类型的名字，以拼音的形式
    @ColumnInfo(name = "type",typeAffinity = ColumnInfo.TEXT)
    private String type;

    //account的数目
    @ColumnInfo(name = "amount",typeAffinity = ColumnInfo.REAL)
    private double amount;

    //创建的时间，只精确到天
    @ColumnInfo(name = "date",typeAffinity = ColumnInfo.TEXT)
    private String date;

    //已使用天数
    @ColumnInfo(name = "days",typeAffinity = ColumnInfo.INTEGER)
    private int days;

    //相关描述
    @ColumnInfo(name = "des",typeAffinity = ColumnInfo.TEXT)
    private String des;

    public Account(int uid,int sign,String type,double amount,String date,int days,String des){
        setUid(uid);
        setDate(date);
        setSign(sign);
        setType(type);
        setAmount(amount);
        setDays(days);
        setDes(des);
    }


    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getSign() {
        return sign;
    }

    public void setSign(int sign) {
        this.sign = sign;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public int getDays() {return days;}

    public void setDays(int days) {this.days = days;}
}
