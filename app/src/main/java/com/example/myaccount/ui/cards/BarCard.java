package com.example.myaccount.ui.cards;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.room.Room;

import com.example.myaccount.R;
import com.example.myaccount.util.FifteenXAxisFormatter;
import com.example.myaccount.dataBase.account.Account;
import com.example.myaccount.dataBase.account.AccountDao;
import com.example.myaccount.dataBase.DataBase;
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

public class BarCard extends LinearLayout {
    private TextView barTitle;
    private ImageView barOption;
    private BarChart barChart;
    private Context context;
    private Dialog bottomDialog;
    private DataBase db;
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
        xAxis.setGranularity(1);
    }

    private int calNumOfDays(Date date) throws Exception{
        date.setHours(0);
        date.setMinutes(0);
        date.setSeconds(0);
        SimpleDateFormat staF = new SimpleDateFormat("yyyy-MM-dd");
        Date sta = staF.parse("1900-01-01");
        String format = staF.format(date);
        System.out.println(format);
        Date help = staF.parse(format);

        return (int)((help.getTime()-sta.getTime())/(24*3600*1000));
    }

    public void refreshBarChart(int choice, Account[] accounts7, Account[] accounts15, int numOfDays) {
        this.setVisibility(View.VISIBLE);
        barChart.clear();
        XAxis xAxis = barChart.getXAxis();
//        xAxis.set
        BarData barData;
        BarDataSet barDataSet;
        if (accounts7.length == 0){
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
                    float avg = 0;
                    System.out.println(String.valueOf(i) + ": ");
                    for (Account acc : accounts7){
                        System.out.println("getDay: " + String.valueOf(acc.getDays()));
                        System.out.println("today: "+ String.valueOf(numOfDays-6+i));
                        if (acc.getDays() == numOfDays-6+i){
                        if (acc.getSign() == 0) {avg += acc.getAmount();}}
                    }
                    out7[i] = avg;
                    if(avg>0)outBarEntries.add(new BarEntry(x7[i], out7[i]));
                }
                xAxis.setValueFormatter(new WeekXAxisFormatter());
                barDataSet = new BarDataSet(outBarEntries, "outcome");
                barDataSet.setColor(R.color.red);
                barData = new BarData(barDataSet);
                barChart.setData(barData);
                barData.setBarWidth(0.3f);
                barChart.setFitBars(false);
                barChart.invalidate();
                break;
            case 1:
                for (int i = 0; i < 7; ++i) {
                    float out = 0;
                    float in = 0;
                    for (Account acc : accounts7){
                        if (acc.getDays() == numOfDays-6+i){
                        if (acc.getSign() == 0) {out += acc.getAmount();}
                        else {in += acc.getAmount();}
                        }
                    }
                    out7[i] = out;
                    in7[i] = in;
                    if(out > 0)outBarEntries.add(new BarEntry(x7[i], out7[i]));
                    if(in > 0)inBarEntries.add(new BarEntry(x7[i], in7[i]));
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
                barData.setBarWidth(0.3f);
                barChart.setFitBars(false);
                barChart.invalidate();
                break;
            case 2:
//                ArrayList<String> datStrs = new ArrayList<String>();
                for (int i = 0; i < 15; ++i) {
                    float avg = 0;
                    for (Account acc : accounts15){
                        if (acc.getDays() == numOfDays-14+i) {
                            if (acc.getSign() == 0) {
                                avg += acc.getAmount();
                            }
                        }
                    }
                    x15[i] = i;
                    out15[i] = avg;
                    if(avg>0)outBarEntries.add(new BarEntry(x15[i], out15[i]));
                }
                xAxis.setValueFormatter(new FifteenXAxisFormatter());
                barDataSet = new BarDataSet(outBarEntries, "outcome");
                barDataSet.setColor(R.color.red);
                barData = new BarData(barDataSet);
                barChart.setData(barData);
                barData.setBarWidth(0.2f);
                barChart.setFitBars(false);
                barChart.invalidate();
                break;
            case 3:
//                ArrayList<String> datStrss = new ArrayList<String>();
                for (int i = 0; i < 15; ++i) {
                    float out = 0;
                    float in = 0;
                    for (Account acc : accounts15){
                        if (acc.getDays() == numOfDays-14+i){
                        if (acc.getSign() == 0) {out += acc.getAmount();}
                        else {in += acc.getAmount();}
                        }
                    }
                    x15[i] = i;
                    out15[i] = out;
                    in15[i] = in;
                    if(out>0)outBarEntries.add(new BarEntry(x15[i], out15[i]));
                    if(in>0)inBarEntries.add(new BarEntry(x15[i], in15[i]));
                }
                xAxis.setValueFormatter(new FifteenXAxisFormatter());
                BarDataSet outSet15 = new BarDataSet(outBarEntries, "outcome");
                outSet15.setColor(R.color.red);
                BarDataSet inSet15 = new BarDataSet(inBarEntries, "income");
                inSet15.setColor(R.color.green);
                List<IBarDataSet> dataSets15 = new ArrayList<IBarDataSet>();
                dataSets15.add(outSet15);
                dataSets15.add(inSet15);
                barData = new BarData(dataSets15);
                barChart.setData(barData);
                barData.setBarWidth(0.2f);
                barChart.setFitBars(false);
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
        WindowManager.LayoutParams lp = bottomDialog.getWindow().getAttributes();
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.dimAmount = 0.6f;
        bottomDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        bottomDialog.setTitle(R.string.title_bottom_dialog);
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
        db = DataBase.getInstance(context);
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
        db = DataBase.getInstance(context);
        QueryLastDayTask task = new QueryLastDayTask(db, numOfDays);
        task.execute();
    }

    @SuppressLint("StaticFieldLeak")
    private class QueryLastDayTask extends AsyncTask<Void, Void, Void>{

        private DataBase accountDataBase;
        private AccountDao accountDao;
        private int numOfDay = 0;

        public QueryLastDayTask(DataBase accountDataBase, int numOfDay){
            this.accountDataBase = accountDataBase;
            this.numOfDay = numOfDay;
        }

        public void queryAccountLastDays() {
            accounts7 = accountDao.queryAll();
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
        protected void onPostExecute(Void aVoid){
            refreshBarChart(0, accounts7, accounts15, numOfDays);
        }
    }
}
