package com.example.myaccount.activity.addActivity;

import android.os.Parcel;
import android.os.Parcelable;

public class Item implements Parcelable { //实现Parcelable接口是为了方便传参
    private String name; //类型的名字
    private String type; //类型的拼音模式
    private int  origin_id; //类型的原来灰色图片
    private int active_id; //类型被点击后的蓝色图片
    private int id; //在队列中的位置


    public Item(String name, String type, int origin_id, int active_id, int id) {
        this.name = name;
        this.type = type;
        this.origin_id = origin_id;
        this.active_id = active_id;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrigin_id() {
        return origin_id;
    }

    public void setOrigin_id(int origin_id) {
        this.origin_id = origin_id;
    }

    public int getActive_id() {
        return active_id;
    }

    public void setActive_id(int active_id) {
        this.active_id = active_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    //实现Parcel的接口
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(type);
        parcel.writeInt(origin_id);
        parcel.writeInt(active_id);
        parcel.writeInt(id);
    }

    protected Item(Parcel in) {
        name = in.readString();
        type = in.readString();
        origin_id = in.readInt();
        active_id = in.readInt();
        id = in.readInt();
    }

    public static final Creator<Item> CREATOR = new Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };
}
