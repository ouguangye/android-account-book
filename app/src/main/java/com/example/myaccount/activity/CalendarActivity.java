package com.example.myaccount.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myaccount.MainActivity;
import com.example.myaccount.R;
import com.example.myaccount.dataBase.user.User;


public class CalendarActivity extends AppCompatActivity {

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        Intent intent1 = getIntent();
        user = intent1.getParcelableExtra("user");

        //回退按钮，点击回退到主页面
        ImageView back_button = findViewById(R.id.back_to_main);
        back_button.setOnClickListener(view -> {
            Intent intent = new Intent(CalendarActivity.this, MainActivity.class);
            intent.putExtra("user",user);
            startActivity(intent);
        });

    }


}