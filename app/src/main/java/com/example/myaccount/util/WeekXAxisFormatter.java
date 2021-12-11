package com.example.myaccount.util;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.formatter.ValueFormatter;

public class WeekXAxisFormatter extends ValueFormatter {
    private String[] days = {"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};
    @Override
    public String getAxisLabel(float value, AxisBase axis){
        return days[(int)value-1];
    }
}
