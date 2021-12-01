package com.example.myaccount;

public class Item {
    private String name;
    private String type;
    private int  origin_id;
    private int active_id;

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

    public Item(String name, String type, int origin_id, int active_id) {
        this.name = name;
        this.type = type;
        this.origin_id = origin_id;
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

}
