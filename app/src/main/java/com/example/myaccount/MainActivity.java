package com.example.myaccount;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.myaccount.activity.CalendarActivity;
import com.example.myaccount.activity.addActivity.AddActivity;
import com.example.myaccount.dataBase.DataBase;
import com.example.myaccount.dataBase.account.AccountDao;
import com.example.myaccount.dataBase.user.User;
import com.example.myaccount.databinding.ActivityMainBinding;
import com.example.myaccount.ui.home.HomeViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private DataBase accountDataBase;
    private HomeViewModel homeViewModel;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.appBarMain.toolbar);
        binding.appBarMain.fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        //标题栏上的时间
        AppCompatTextView date = findViewById(R.id.tv_home_address);
        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH) + 1;
        int year = cal.get(Calendar.YEAR);
        date.setText(year+"."+month);

        //获取登录页面传来的值
        Intent intent1 = getIntent();
        user = intent1.getParcelableExtra("user");
        Log.d("user",user.getName());


        //跳转到日历页面
        ImageView calendar = this.findViewById(R.id.calendar);
        calendar.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, CalendarActivity.class);
            intent.putExtra("user", user);
            startActivity(intent);
        });


        //跳转到添加AddActivity页面
        FloatingActionButton add_button =  findViewById(R.id.fab);
        add_button.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AddActivity.class);
            intent.putExtra("user", user);
            startActivity(intent);
        });

        //创建数据库
        accountDataBase = DataBase.getInstance(this);

        new FindMonthDataTask().execute();

        //操作碎片
//        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
    }

    //右上角三个点的设置。这里取消了原有的bar,所以此处代码没有用
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    //导航页面
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
    
    public User getUser(){
        return user;
    }

    private void add(){
        final Observer<Context> contextObserver = new Observer<Context>() {
            @Override
            public void onChanged(Context context) {

            }
        };
        homeViewModel.getContext().observe(this, contextObserver);
    }

    private class FindMonthDataTask extends AsyncTask<Void, Void, Void>{

        private AccountDao accountDao;
        private Float[] income;
        private Float[] outcome;
        private float disOut = 0;
        private float disIn = 0;
        private float diff = 0;
        String mon;

        public FindMonthDataTask(){
            accountDao = accountDataBase.getAccountDao();
            Calendar cal = Calendar.getInstance();
            int month = cal.get(Calendar.MONTH) + 1;
            int year = cal.get(Calendar.YEAR);
            mon = year+"-"+month+'%';
            System.out.println("NOW    " + mon);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            income = accountDao.queryIncomeMonth(mon, user.getSid());
            outcome = accountDao.queryOutcomeMonth(mon, user.getSid());
            if (income != null){
                if (income.length > 0){
                    System.out.println("INCOME");
                    for (Float var : income){
                        disIn += var;
                    }
                }
            }
            if (outcome != null){
                System.out.println("OUTCOME");
                if (outcome.length > 0){
                    for (Float var : outcome){
                        disOut += var;
                        System.out.println(disOut);
                    }
                }
            }
            diff = disIn - disOut;
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid){
            TextView out = (TextView) findViewById(R.id.month_outcome);
            out.setText(String.valueOf(disOut));
            TextView in = (TextView) findViewById(R.id.month_income);
            String In = "月收入" + String.valueOf(disIn);
            in.setText(In);
            TextView dif = (TextView) findViewById(R.id.month_diff);
            String difference = "月结余" + String.valueOf(diff);
            dif.setText(difference);
        }
    }

}