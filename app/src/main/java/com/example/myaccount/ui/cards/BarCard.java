package com.example.myaccount.ui.cards;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.room.Room;

import com.example.myaccount.R;
import com.example.myaccount.dataBase.Account;
import com.example.myaccount.dataBase.AccountDao;
import com.example.myaccount.dataBase.AccountDataBase;
import com.example.myaccount.util.WeekXAxisFormatter;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BarCard extends LinearLayout{
    private TextView barTitle;
    private ImageView barOption;
    private BarChart barChart;
    private Context context;
    private Dialog bottomDialog;
    private AccountDataBase db;
    public Account[] accounts7;
    public Account[] accounts15;
    private int numOfDays = 0;


    public void initBarChart(){
        barChart = (BarChart) findViewById(R.id.bar_card_chart);
        barChart.setDrawGridBackground(false);
        barChart.getXAxis().setDrawGridLines(false);
        barChart.getAxisLeft().setDrawGridLines(false);
        barChart.getAxisLeft().setEnabled(false);
        barChart.getAxisRight().setEnabled(false);
        barChart.setPinchZoom(false);
        barChart.setDrawBarShadow(false);
        Description description = new Description();
        description.setText("");
        barChart.setDescription(description);
        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//        xAxis.setLabelRotationAngle(90);
        xAxis.setTextColor(Color.GRAY);
    }

    private int calNumOfDays(Date date) throws Exception{
        SimpleDateFormat staF = new SimpleDateFormat("1900-01-01");
        Date sta = staF.parse("1900-01-01");
        date.setHours(0);
        date.setMinutes(0);
        date.setSeconds(0);
        return (int)((date.getTime()-sta.getTime())/(24*3600*1000));
    }

    public void refreshBarChart(int choice, Account[] accounts7, Account[] accounts15, int numOfDays) {
        this.setVisibility(View.VISIBLE);
        barChart.clear();
        XAxis xAxis = barChart.getXAxis();
        BarData barData;
        BarDataSet barDataSet;
        if (accounts7 == null){
            this.setVisibility(View.GONE);
            return;
        }
        float[] x7 = {1, 2, 3, 4, 5, 6, 7};
        float[] out7 = new float[7];
        float[] in7 = new float[7];
        float[] x15 = new float[15];
        float[] out15 = new float[15];
        float[] in15 = new float[15];
        List<BarEntry> outBarEntries = new ArrayList<BarEntry>();
        List<BarEntry> inBarEntries = new ArrayList<BarEntry>();
        switch (choice) {
            case 0:
                for (int i = 0; i < 7; ++i) {
                    Log.d("!!","aaa");
                    float avg = 0;
                    int size = 0;
                    for (Account acc : accounts7){
                        Log.d("!!","abb");
                        if (acc.getDays() > numOfDays-6+i)
                            break;
                        if (acc.getDays() < numOfDays-6+i)
                            continue;
                        if (acc.getSign() == 0) {avg += acc.getAmount();Log.d("!!!!!!", String.valueOf(acc.getAmount())); size++;}
                    }
                    avg /= size;
                    out7[i] = avg;
                    outBarEntries.add(new BarEntry(x7[i], out7[i]));
                }
                xAxis.setValueFormatter(new WeekXAxisFormatter());
                barDataSet = new BarDataSet(outBarEntries, "outcome");
//                barDataSet.setColor(R.color.red);
                barData = new BarData(barDataSet);
                barChart.setData(barData);
                barData.setBarWidth(0.9f);
                barChart.setFitBars(true);
                barChart.invalidate();
                break;
            case 1:
                for (int i = 0; i < 7; ++i) {
                    float out = 0;
                    int sOut = 0;
                    float in = 0;
                    int sIn = 0;
                    for (Account acc : accounts7){
                        if (acc.getDays() > numOfDays-6+i)
                            break;
                        if (acc.getDays() < numOfDays-6+i)
                            continue;
                        if (acc.getSign() == 0) {out += acc.getAmount(); sOut++;}
                        else {in += acc.getAmount(); sIn++;}
                    }
                    out /= sOut;
                    in /= sIn;
                    out7[i] = out;
                    in7[i] = in;
                    outBarEntries.add(new BarEntry(x7[i], out7[i]));
                    inBarEntries.add(new BarEntry(x7[i], in7[i]));
                }
                xAxis.setValueFormatter(new WeekXAxisFormatter());
                BarDataSet outSet = new BarDataSet(outBarEntries, "outcome");
                outSet.setColor(R.color.red);
                BarDataSet inSet = new BarDataSet(inBarEntries, "income");
                inSet.setColor(R.color.green);
                List<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
                dataSets.add(outSet);
                dataSets.add(inSet);
                barData = new BarData(dataSets);
                barChart.setData(barData);
                barData.setBarWidth(0.9f);
                barChart.setFitBars(true);
                barChart.invalidate();
                break;
            case 2:
//                ArrayList<String> datStrs = new ArrayList<String>();
                for (int i = 0; i < 15; ++i) {
                    float avg = 0;
                    int size = 0;
                    for (Account acc : accounts15){
//                        String datStr = acc.getDate();
//                        if (!datStrs.contains(datStr))
//                            datStrs.add(datStr);
                        if (acc.getDays() > numOfDays-14+i)
                            break;
                        if (acc.getDays() < numOfDays-14+i)
                            continue;
                        if (acc.getSign() == 0) {avg += acc.getAmount(); size++;}
                    }
                    x15[i] = i;
                    avg /= size;
                    out15[i] = avg;
                    outBarEntries.add(new BarEntry(x15[i], out15[i]));
                }
                barDataSet = new BarDataSet(outBarEntries, "outcome");
                barDataSet.setColor(R.color.red);
                barData = new BarData(barDataSet);
                barChart.setData(barData);
                barData.setBarWidth(0.9f);
                barChart.setFitBars(true);
                barChart.invalidate();
                break;
            case 3:
//                ArrayList<String> datStrss = new ArrayList<String>();
                for (int i = 0; i < 15; ++i) {
                    float out = 0;
                    int sOut = 0;
                    float in = 0;
                    int sIn = 0;
                    for (Account acc : accounts15){
//                        String datStr = acc.getDate();
//                        if (!datStrss.contains(datStr))
//                            datStrss.add(datStr);
                        if (acc.getDays() > numOfDays-14+i)
                            break;
                        if (acc.getDays() < numOfDays-14+i)
                            continue;
                        if (acc.getSign() == 0) {out += acc.getAmount(); sOut++;}
                        else {in += acc.getAmount(); sIn++;}
                    }
                    x15[i] = i;
                    out /= sOut;
                    in /= sIn;
                    out15[i] = out;
                    in15[i] = in;
                    outBarEntries.add(new BarEntry(x15[i], out15[i]));
                    inBarEntries.add(new BarEntry(x15[i], in15[i]));
                }
                xAxis.setValueFormatter(new WeekXAxisFormatter());
                BarDataSet outSet15 = new BarDataSet(outBarEntries, "outcome");
                outSet15.setColor(R.color.red);
                BarDataSet inSet15 = new BarDataSet(inBarEntries, "income");
                inSet15.setColor(R.color.green);
                List<IBarDataSet> dataSets15 = new ArrayList<IBarDataSet>();
                dataSets15.add(outSet15);
                dataSets15.add(inSet15);
                barData = new BarData(dataSets15);
                barChart.setData(barData);
                barData.setBarWidth(0.9f);
                barChart.setFitBars(true);
                barChart.invalidate();
                break;
            default:
                break;
        }
    }


    public void changeBarTitle(int choice){
        switch (choice){
            case 0:
            case 1:
                barTitle.setText("最近7日汇总");
                break;
            case 2:
            case 3:
                barTitle.setText("最近15日汇总");
                break;
            default:
                break;
        }
    }


    public void initBottomDialog(){
        bottomDialog = new Dialog(context, R.style.BottomDialog);
        View contentView = LayoutInflater.from(context).inflate(
                R.layout.bar_bottom_dialog,null);
        bottomDialog.setContentView(contentView);
        ViewGroup.LayoutParams layoutParams = contentView.getLayoutParams();
        layoutParams.width = getResources().getDisplayMetrics().widthPixels;
        contentView.setLayoutParams(layoutParams);
        bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
        bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        TextView sevenOutcome = bottomDialog.findViewById(R.id.seven_outcome);
        TextView sevenIncome = bottomDialog.findViewById(R.id.seven_income_and_outcome);
        TextView fifteenOutcome = bottomDialog.findViewById(R.id.fifteen_outcome);
        TextView fifteenIncome = bottomDialog.findViewById(R.id.fifteen_income_and_outcome);
        sevenOutcome.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                int choice = 0;
                bottomDialog.hide();
                changeBarTitle(choice);
                refreshBarChart(0,accounts7, accounts15, numOfDays);
            }
        });
        sevenIncome.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                int choice = 1;
                bottomDialog.hide();
                changeBarTitle(choice);
                refreshBarChart(1,accounts7, accounts15, numOfDays);
            }
        });
        fifteenOutcome.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                int choice = 2;
                bottomDialog.hide();
                changeBarTitle(choice);
                refreshBarChart(2,accounts7, accounts15,numOfDays);
            }
        });
        fifteenIncome.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                int choice = 3;
                bottomDialog.hide();
                changeBarTitle(choice);
                refreshBarChart(3,accounts7, accounts15,numOfDays);
            }
        });
    }


    public void initBarOption(){
        barOption = (ImageView) findViewById(R.id.bar_card_option);
        barOption.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                bottomDialog.show();
            }
        });
    }

    public void refresh(){
        db = Room.inMemoryDatabaseBuilder(getContext(), AccountDataBase.class).build();
        QueryLastDayTask task = new QueryLastDayTask(db,numOfDays);
        task.execute();
    }


    public BarCard(Context context) {
        super(context);
        this.context = context;
        LayoutInflater.from(context).inflate(R.layout.barchart_card,this);
        barTitle = (TextView) findViewById(R.id.bar_card_title);
        changeBarTitle(0);
        initBarChart();
        initBottomDialog();
        initBarOption();
        try {
            numOfDays = calNumOfDays(new Date());
        } catch (Exception e) {
            e.printStackTrace();
        }
        db = Room.inMemoryDatabaseBuilder(context, AccountDataBase.class).build();
        QueryLastDayTask task = new QueryLastDayTask(db, numOfDays);
        task.execute();
    }

    private class QueryLastDayTask extends AsyncTask<Void, Void, Void>{

        private AccountDataBase accountDataBase;
        private AccountDao accountDao;
        private int numOfDay = 0;

        public QueryLastDayTask(AccountDataBase accountDataBase, int numOfDay){
            this.accountDataBase = accountDataBase;
            this.numOfDay = numOfDay;
        }

        public void queryAccountLastDays() {
            accounts7 = accountDao.queryLastDays(numOfDay-7);
            if (accounts7 == null)
                Log.d("dasgasfhbsfgsfdahwefh",String.valueOf(numOfDays));
            accounts15 = accountDao.queryLastDays(numOfDay-15);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            if (accountDataBase != null)
                accountDao = accountDataBase.getAccountDao();
            if (numOfDay > 0) {
                queryAccountLastDays();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... avoid){
            super.onProgressUpdate(avoid);
        }

        @Override
        protected void onPostExecute(Void aVoid){
            accountDataBase.close();
            refreshBarChart(0, accounts7, accounts15, numOfDays);
            super.onPostExecute(aVoid);
        }
    }
}
