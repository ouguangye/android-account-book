package com.example.myaccount;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import io.blackbox_vision.materialcalendarview.view.CalendarView;


public class CalendarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        CalendarView calendarView = (CalendarView) findViewById(R.id.calendar_view);
        //calendarView.setWeekDayLabels(new String[]{"日", "一", "二", "三", "四", "五", "六"});

    }


}