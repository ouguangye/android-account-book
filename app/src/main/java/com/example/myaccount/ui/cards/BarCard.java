package com.example.myaccount.ui.cards;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myaccount.R;
import com.example.myaccount.util.WeekXAxisFormatter;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BarCard extends LinearLayout{
    private TextView barTitle;
    private ImageView barOption;
    private BarChart barChart;
    private Context context;
    private Dialog bottomDialog;


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


    public void refreshBarChart(int choice) {
        barChart.clear();
        XAxis xAxis = barChart.getXAxis();
        switch (choice) {
            case 0:
                float[] x = {1, 2, 3, 4, 5, 6, 7};
                float[] y = {234, 32, 90, 45, 78, 300, 12};
                List<BarEntry> barEntries = new ArrayList<BarEntry>();
                for (int i = 0; i < 7; ++i) {
                    barEntries.add(new BarEntry(x[i], y[i]));
                }
                xAxis.setValueFormatter(new WeekXAxisFormatter());
                BarDataSet barDataSet = new BarDataSet(barEntries, "outcome");
                BarData barData = new BarData(barDataSet);
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
                refreshBarChart(0);
            }
        });
        sevenIncome.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                int choice = 1;
                bottomDialog.hide();
                changeBarTitle(choice);
                refreshBarChart(1);
            }
        });
        fifteenOutcome.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                int choice = 2;
                bottomDialog.hide();
                changeBarTitle(choice);
                refreshBarChart(2);
            }
        });
        fifteenIncome.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                int choice = 3;
                bottomDialog.hide();
                changeBarTitle(choice);
                refreshBarChart(3);
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


    public BarCard(Context context) {
        super(context);
        this.context = context;
        LayoutInflater.from(context).inflate(R.layout.barchart_card,this);
        barTitle = (TextView) findViewById(R.id.bar_card_title);
        initBarChart();
        initBottomDialog();
        initBarOption();
        refreshBarChart(0);
    }
}
