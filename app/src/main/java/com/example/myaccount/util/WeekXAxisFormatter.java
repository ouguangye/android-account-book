package com.example.myaccount.util;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.formatter.ValueFormatter;

public class WeekXAxisFormatter extends ValueFormatter {
    private String[] days = {"-6天", "-5天", "-4天", "-3天", "前天", "昨天", "今天"};
    @Override
    public String getAxisLabel(float value, AxisBase axis){
        return days[(int)value-1];
    }
}
