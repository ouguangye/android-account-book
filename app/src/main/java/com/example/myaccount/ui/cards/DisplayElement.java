package com.example.myaccount.ui.cards;

public class DisplayElement {
    private String type;
    // value大于0表示income， value小于0表示outcome
    private double value;
    private int graphId;
    private int sid;
    public DisplayElement(String type, double value, int graphId, int sid) {
        this.type = type;
        this.value = value;
        this.graphId = graphId;
        this.sid = sid;

    }
    public String getType() {
        return  type;
    }
    public double getValue() {
        return value;
    }
    public  int getGraphId() {
        return graphId;
    }
    public int getSid() {
        return this.sid;
    }
}
