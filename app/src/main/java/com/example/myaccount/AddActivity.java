package com.example.myaccount;

import static com.example.myaccount.DateUtils.FORMAT_D;
import static com.example.myaccount.DateUtils.FORMAT_M;
import static com.example.myaccount.DateUtils.FORMAT_Y;
import static com.example.myaccount.DateUtils.FORMAT_YMD;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class AddActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager2 viewPager2;

    private final int activeColor = Color.parseColor("#ffffff");
    private final int normalColor = Color.parseColor("#ffffff");

    private final int activeSize = 17;
    private final int normalSize = 17;

    private ArrayList<Fragment> fragments;
    private TabLayoutMediator mediator;

    private TextView incomeTv;    //收入按钮
    private TextView outcomeTv;   //支出按钮
    private TextView sortTv;     //显示选择的分类
    private TextView moneyTv;     //金额
    private TextView dateTv;      //时间选择
    private TextView cashTv;      //支出方式选择
    private ImageView remarkIv;   //
    private ViewPager2 viewpagerItem;
    private LinearLayout layoutIcon;

    //计算器
    protected boolean isDot;
    protected String num = "0";               //整数部分
    protected String dotNum = ".00";          //小数部分
    protected final int MAX_NUM = 9999999;    //最大整数
    protected final int DOT_NUM = 2;          //小数部分最大位数
    protected int count = 0;
    //选择器
    protected List<String> cardItems;
    protected int selectedPayinfoIndex = 0;      //选择的支付方式序号
    //viewpager数据
    protected int page;
    protected boolean isTotalPage;
    protected int sortPage = -1;

    protected List<View> viewList;
    protected ImageView[] icons;

    //记录上一次点击后的分类
   // public BSort lastBean;

    public boolean isOutcome = true;
    public boolean isEdit = false;
    //old Bill
    private Bundle bundle;

    //选择时间
    protected int mYear;
    protected int mMonth;
    protected int mDay;
    protected String days;

    //备注
    protected String remarkInput = "";
    //protected NoteBean noteBean = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        //页面跳转
        ImageView back_button = findViewById(R.id.back_to_main);
        back_button.setOnClickListener(view -> {
            Intent intent = new Intent(AddActivity.this,MainActivity.class);
            startActivity(intent);
        });

        tabLayout = findViewById(R.id.tab_layout);
        viewPager2 = findViewById(R.id.viewpager_item);

        final String[] tabs = new String[]{"支出", "收入"};

        //禁用预加载
        viewPager2.setOffscreenPageLimit(ViewPager2.OFFSCREEN_PAGE_LIMIT_DEFAULT);
        //Adapter
        viewPager2.setAdapter(new FragmentStateAdapter(getSupportFragmentManager(), getLifecycle()) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                //FragmentStateAdapter内部自己会管理已实例化的fragment对象。
                // 所以不需要考虑复用的问题
                return AccountFragment.newInstance(position);
            }

            @Override
            public int getItemCount() {
                return tabs.length;
            }
        });

        //viewPager 页面切换监听监听
        viewPager2.registerOnPageChangeCallback(changeCallback);

        mediator = new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> {
            //这里可以自定义TabView
            TextView tabView = new TextView(AddActivity.this);

            int[][] states = new int[2][];
            states[0] = new int[]{android.R.attr.state_selected};
            states[1] = new int[]{};

            int[] colors = new int[]{activeColor, normalColor};
            ColorStateList colorStateList = new ColorStateList(states, colors);
            tabView.setText(tabs[position]);
            tabView.setTextSize(normalSize);
            tabView.setTextColor(colorStateList);

            tab.setCustomView(tabView);
        });
        //要执行这一句才是真正将两者绑定起来
        mediator.attach();

        initData(savedInstanceState);
        initWidget();
    }

    private final ViewPager2.OnPageChangeCallback changeCallback = new ViewPager2.OnPageChangeCallback() {
        @Override
        public void onPageSelected(int position) {
            //可以来设置选中时tab的大小
            int tabCount = tabLayout.getTabCount();
            for (int i = 0; i < tabCount; i++) {
                TabLayout.Tab tab = tabLayout.getTabAt(i);
                assert tab != null;
                TextView tabView = (TextView) tab.getCustomView();
                if (tab.getPosition() == position) {
                    assert tabView != null;
                    tabView.setTextSize(activeSize);
                    tabView.setTypeface(Typeface.DEFAULT_BOLD);
                } else {
                    assert tabView != null;
                    tabView.setTextSize(normalSize);
                    tabView.setTypeface(Typeface.DEFAULT);
                }
            }
        }
    };


    protected void initWidget() {
        //incomeTv = findViewById(R.id.tb_note_income);
        //outcomeTv = findViewById(R.id.tb_note_outcome);
        sortTv = findViewById(R.id.item_tb_type_tv);
        moneyTv = findViewById(R.id.tb_note_money);
        dateTv = findViewById(R.id.tb_note_date);
        cashTv = findViewById(R.id.tb_note_cash);
        remarkIv = findViewById(R.id.tb_note_remark);
        viewpagerItem = findViewById(R.id.viewpager_item);
        layoutIcon = findViewById(R.id.layout_icon);

        //设置账单日期
        dateTv.setText(days);
        //设置金额
        moneyTv.setText(num + dotNum);
    }


    protected void initData(Bundle savedInstanceState) {
        //设置日期选择器初始日期
        mYear = Integer.parseInt(DateUtils.getCurYear(FORMAT_Y));
        mMonth = Integer.parseInt(DateUtils.getCurMonth(FORMAT_M));
        mDay = Integer.parseInt(DateUtils.getCurDay(FORMAT_D));
        //设置当前 日期
        days = DateUtils.getCurDateStr("yyyy-MM-dd");

        bundle = getIntent().getBundleExtra("bundle");

        if (bundle != null) {    //edit
            isEdit = true;
            //设置账单日期
            days = DateUtils.long2Str(bundle.getLong("date"), FORMAT_YMD);
            isOutcome = !bundle.getBoolean("income");
            remarkInput = bundle.getString("content");
            DecimalFormat df = new DecimalFormat("######0.00");
            String money = df.format(bundle.getDouble("cost"));
            //小数取整
            num = money.split("\\.")[0];
            //截取小数部分
            dotNum = "." + money.split("\\.")[1];
        }
    }

    @Override
    protected void onDestroy() {
        mediator.detach();
        viewPager2.unregisterOnPageChangeCallback(changeCallback);
        super.onDestroy();
    }
}