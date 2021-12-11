package com.example.myaccount.util;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.ValueFormatter;

public class FifteenXAxisFormatter extends ValueFormatter {
    private String[] days = new String[15];
    @Override
    public String getAxisLabel(float value, AxisBase axis){
        for (int i=-14;i<=0;++i){
            days[i+14] = String.valueOf(i)+"天";
        }
        return days[(int)value-1];
    }
}
