package com.example.myaccount.dataBase.user;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user")
public class User implements Parcelable {
    //序列id
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "sid",typeAffinity = ColumnInfo.INTEGER)
    private int sid;

    @ColumnInfo(name = "username",typeAffinity = ColumnInfo.TEXT)
    private String name;

    @ColumnInfo(name = "password",typeAffinity = ColumnInfo.TEXT)
    private String password;

    protected User(Parcel in) {
        sid = in.readInt();
        name = in.readString();
        password = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(sid);
        dest.writeString(name);
        dest.writeString(password);
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }


}