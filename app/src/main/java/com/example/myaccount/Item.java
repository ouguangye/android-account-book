package com.example.myaccount;

import android.os.Parcel;
import android.os.Parcelable;

public class Item implements Parcelable {
    private String name;
    private String type;
    private int  origin_id;
    private int active_id;

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

    public int getId() {
        return id;
    }

    public Item(String name, String type, int origin_id, int active_id, int id) {
        this.name = name;
        this.type = type;
        this.origin_id = origin_id;
        this.active_id = active_id;
        this.id = id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int id;

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
}
