package com.example.myaccount.activity.addActivity;

import static com.example.myaccount.util.DateUtils.FORMAT_D;
import static com.example.myaccount.util.DateUtils.FORMAT_M;
import static com.example.myaccount.util.DateUtils.FORMAT_Y;
import static com.example.myaccount.util.DateUtils.FORMAT_YMD;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.myaccount.MainActivity;
import com.example.myaccount.R;
import com.example.myaccount.dataBase.Account;
import com.example.myaccount.dataBase.AccountDao;
import com.example.myaccount.dataBase.AccountDataBase;
import com.example.myaccount.util.DateUtils;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class AddActivity extends AppCompatActivity implements View.OnClickListener, AccountRVFragment.SendDataToActivity {
    //使用Dao访问数据库
    private AccountDao accountDao;
    private TabLayout tabLayout;//标题栏
    private ViewPager2 viewPager2;
    private ImageView BackToMain;

    //标题栏点击触发前和触发后的状态
    private final int activeColor = Color.parseColor("#ffffff");
    private final int normalColor = Color.parseColor("#ffffff");
    private final int activeSize = 17;
    private final int normalSize = 17;

    private TabLayoutMediator mediator;//中介器，连接标题栏和viewpager2

    private TextView moneyTv;     //金额
    private TextView dateTv;      //时间选择
    private TextView cashTv;      //支出方式选择
    private ImageView remarkIv;   //
    private  TextView confirmIv;//确认键
    private EditText desIv;//输入框
    private ImageView clearIv;//清空键

    //计算器
    protected boolean isDot;
    protected String num = "0";               //整数部分
    protected String dotNum = ".00";          //小数部分
    protected final int MAX_NUM = 9999999;    //最大整数
    protected final int DOT_NUM = 2;          //小数部分最大位数
    protected int count = 0;


    public boolean isOutcome = true;
    public boolean isEdit = false;

    //old Bill
    private Bundle bundle;

    //选择时间
    protected int mYear;
    protected int mMonth;
    protected int mDay;
    protected String days;
    protected int numOfDays;

    //备注
    protected String remarkInput = "";

    //之前选择的那个类型
    protected Item selected_item;

    //目前处在的页面，0表示支出页面，1表示收入页面
    private int currentPosition = 0;

    //支出和收入的类型列表
    private final ArrayList<Item> OutcomeItemList = new ArrayList<>();
    private final ArrayList<Item> IncomeItemList = new ArrayList<>();

    //数据库
    private AccountDataBase accountDataBase;

    //初始化点击事件
    protected void initClick() {
        BackToMain.setOnClickListener(this);
        dateTv.setOnClickListener(this);
        remarkIv.setOnClickListener(this);
        confirmIv.setOnClickListener(this);
        clearIv.setOnClickListener(this);
    }

    //初始化图标
    protected void initIcons(){
        initIncomeItems();
        initOutcomeItems();
    }
    //初始化支出的图标列表
    private void initOutcomeItems() {
        Item canyin = new Item("餐饮","canyin",R.mipmap.canyin_grey,R.mipmap.canyin_blue,0);
        OutcomeItemList.add(canyin);

        Item chongwu = new Item("宠物","chongwu",R.mipmap.chongwu_grey,R.mipmap.chongwu_blue,1);
        OutcomeItemList.add(chongwu);

        Item dianqi = new Item("电器","dianqi",R.mipmap.dianqi_grey,R.mipmap.dianqi_blue,2);
        OutcomeItemList.add(dianqi);

        Item haizi = new Item("孩子","haizi",R.mipmap.haizi_grey,R.mipmap.haizi_blue,3);
        OutcomeItemList.add(haizi);

        Item hongbao = new Item("红包","hongbao",R.mipmap.hongbao_grey,R.mipmap.hongbao_blue,4);
        OutcomeItemList.add(hongbao);

        Item huafei = new Item("话费","huafei",R.mipmap.huafei_grey,R.mipmap.huafei_blue,5);
        OutcomeItemList.add(huafei);

        Item huazhuang = new Item("化妆","huazhuang",R.mipmap.huazhuangpin_grey,R.mipmap.huazhuang_blue,6);
        OutcomeItemList.add(huazhuang);

        Item jiaotong = new Item("交通","jiaotong",R.mipmap.jiaotong_grey,R.mipmap.jiaotong_blue,7);
        OutcomeItemList.add(jiaotong);

        Item lingshi = new Item("零食","linghshi",R.mipmap.lingshi_grey,R.mipmap.lingshi_blue,8);
        OutcomeItemList.add(lingshi);

        Item lvyou = new Item("旅游","lvyou",R.mipmap.lvyou_grey,R.mipmap.lvyou_blue,9);
        OutcomeItemList.add(lvyou);

        Item shuidian = new Item("水电","shuidian",R.mipmap.shuidian_grey,R.mipmap.shuidianfei_blue,10);
        OutcomeItemList.add(shuidian);

//        Item yifu = new Item("衣服","yifu",R.mipmap.yifu_grey,R.mipmap.yifu_blue,11);
//        itemList.add(yifu);
//
//        Item yiliao = new Item("医疗","yiliao",R.mipmap.yiliao_grey,R.mipmap.yiliao_blue,12);
//        itemList.add(yiliao);
//
//        Item yundong = new Item("运动","yundong",R.mipmap.yundong_grey,R.mipmap.yundong_blue,13);
//        itemList.add(yundong);
//
//        Item yvle = new Item("娱乐","yule",R.mipmap.yvle_grey,R.mipmap.yvle_blue,14);
//        itemList.add(yvle);

        Item zhufang = new Item("住房","zhufang",R.mipmap.zhufang_grey,R.mipmap.zhufang_blue,11);
        OutcomeItemList.add(zhufang);

        Item xuexi = new Item("学习","xuexi",R.mipmap.xuexi_grey,R.mipmap.xuexi_blue,12);
        OutcomeItemList.add(xuexi);

        Item riyongpin = new Item("日用品","riyongping",R.mipmap.riyong_grey,R.mipmap.riyongpin_blue,13);
        OutcomeItemList.add(riyongpin);

        Item qita = new Item("其他","qita",R.mipmap.qita_grey,R.mipmap.qita_blue,14);
        OutcomeItemList.add(qita);

    }
    //初始化收入的图标列表
    public void initIncomeItems(){
        Item gongzi = new Item("工资","gongzi",R.mipmap.gongzi_grey,R.mipmap.gongzi_blue,0);
        IncomeItemList.add(gongzi);

        Item gupiao = new Item("股票","gupiao",R.mipmap.gupiao_grey,R.mipmap.gupiao_blue,1);
        IncomeItemList.add(gupiao);

        Item hongbao = new Item("红包","hongbao",R.mipmap.hongbao_grey,R.mipmap.hongbao_blue,2);
        IncomeItemList.add(hongbao);

        Item jianzhi = new Item("兼职","jianzhi",R.mipmap.jianzhi_grey,R.mipmap.jianzhi_blue,3);
        IncomeItemList.add(jianzhi);

        Item shenghuofei = new Item("生活费","shenghuofei",R.mipmap.shuidian_grey,R.mipmap.shuidianfei_blue,4);
        IncomeItemList.add(shenghuofei);
    }

    //计算天数值（从1900年1月1日开始）
    private int calNumOfDays(Date date) throws Exception{
        SimpleDateFormat staF = new SimpleDateFormat("yyyy-mm-dd");
        Date sta = staF.parse("1900-01-01");
        date.setHours(0);
        date.setMinutes(0);
        date.setSeconds(0);
        return (int)((date.getTime()-sta.getTime())/(24*3600*1000));
    }

    //初始化数据
    protected void initData(Bundle savedInstanceState) throws Exception{
        //设置日期选择器初始日期
        mYear = Integer.parseInt(DateUtils.getCurYear(FORMAT_Y));
        mMonth = Integer.parseInt(DateUtils.getCurMonth(FORMAT_M));
        mDay = Integer.parseInt(DateUtils.getCurDay(FORMAT_D));
        //设置当前 日期
        days = DateUtils.getCurDateStr("yyyy-MM-dd");
        SimpleDateFormat sdf = new SimpleDateFormat(days);
        Date calHelp = sdf.parse(days);
        numOfDays = calNumOfDays(calHelp);
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

    //初始化组件
    @SuppressLint("SetTextI18n")
    protected void initWidget() {
        moneyTv = findViewById(R.id.tb_note_money);
        dateTv = findViewById(R.id.tb_note_date);
        cashTv = findViewById(R.id.tb_note_cash);
        remarkIv = findViewById(R.id.tb_note_remark);
        BackToMain = findViewById(R.id.back_to_main);
        confirmIv = findViewById(R.id.tb_calc_num_done);
        desIv = findViewById(R.id.item_tb_type_tv);
        clearIv = findViewById(R.id.tb_note_clear);
        //设置账单日期
        dateTv.setText(days);
        //设置金额
        moneyTv.setText(num + dotNum);
    }

    //tabLayout与viewpager2相连接
    protected void connectedTabAndViewpager(){
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
                currentPosition = position;
                if(position==0)return AccountRVFragment.newInstance(position,OutcomeItemList);
                else return AccountRVFragment.newInstance(position,IncomeItemList);
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
    }

    //viewPager页面切换的callBack函数，主要用来改变tab的样式
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

    //计算器

    //输入数字
    @SuppressLint("SetTextI18n")
    protected void calcMoney(int money) {
        if (num.equals("0") && money == 0)
            return;
        if (isDot) {
            if (count < DOT_NUM) {
                count++;
                dotNum += money;
                moneyTv.setText(num + dotNum);
            }
        } else if (Integer.parseInt(num) < MAX_NUM) {
            if (num.equals("0"))
                num = "";
            num += money;
            moneyTv.setText(num + dotNum);
        }
    }

    //计算器清零操作
    @SuppressLint("SetTextI18n")
    public void doClear() {
        num = "0";
        count = 0;
        dotNum = ".00";
        isDot = false;
        moneyTv.setText("0.00");
    }

    //计算器退格键
    @SuppressLint("SetTextI18n")
    public void doDelete() {
        if (isDot) {
            if (count > 0) {
                dotNum = dotNum.substring(0, dotNum.length() - 1);
                count--;
            }
            if (count == 0) {
                isDot = false;
                dotNum = ".00";
            }
        } else {
            if (num.length() > 0)
                num = num.substring(0, num.length() - 1);
            if (num.length() == 0)
                num = "0";
        }
        moneyTv.setText(num + dotNum);
    }

    //时间选择器
    public void showTimeSelector() {
        new DatePickerDialog(this, (DatePicker datePicker, int i, int i1, int i2) -> {
            mYear = i;
            mMonth = i1;
            mDay = i2;
            if (mMonth + 1 < 10) {
                if (mDay < 10) {
                    days = mYear + "-" + "0" +
                            (mMonth + 1) + "-" + "0" + mDay;
                } else {
                    days = mYear + "-" + "0" +
                            (mMonth + 1) + "-" + mDay;
                }

            } else {
                if (mDay < 10) {
                    days = mYear + "-" +
                            (mMonth + 1) + "-" + "0" + mDay;
                } else {
                    days = mYear + "-" +
                            (mMonth + 1) + "-" + mDay;
                }

            }
            dateTv.setText(days);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
            Date calHelp = null;
            try {
                calHelp = sdf.parse(days);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            try {
                numOfDays = calNumOfDays(calHelp);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }, mYear, mMonth, mDay).show();
    }

    //提交
    public void doCommit(){
        @SuppressLint("SimpleDateFormat") final SimpleDateFormat sdf = new SimpleDateFormat(" HH:mm:ss");
//        System.out.println(days);
        System.out.println(numOfDays);
        final String crDate = days;
        if ((num + dotNum).equals("0.00")) {
            Toast.makeText(this, "抱歉，你还没输入金额", Toast.LENGTH_SHORT).show();
        }
        else{
            new InsertAccountTask(currentPosition,selected_item.getType(),Double.parseDouble((String) moneyTv.getText()),crDate,numOfDays,desIv.getText().toString()).execute();
            Toast.makeText(this,"添加成功",Toast.LENGTH_SHORT).show();
        }
    }

    //清空
    public void clear(){
        doClear();
        desIv.setText("");
    }

    //所有点击事件
    @SuppressLint({"NonConstantResourceId", "SetTextI18n"})
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_to_main:
                Intent intent = new Intent(AddActivity.this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.tb_note_date://日期
                showTimeSelector();
                break;
            case R.id.tb_note_remark://备注
                //showContentDialog();
                break;
            case R.id.tb_calc_num_done://确定
                doCommit();
                break;
            case R.id.tb_calc_num_1:
                calcMoney(1);
                break;
            case R.id.tb_calc_num_2:
                calcMoney(2);
                break;
            case R.id.tb_calc_num_3:
                calcMoney(3);
                break;
            case R.id.tb_calc_num_4:
                calcMoney(4);
                break;
            case R.id.tb_calc_num_5:
                calcMoney(5);
                break;
            case R.id.tb_calc_num_6:
                calcMoney(6);
                break;
            case R.id.tb_calc_num_7:
                calcMoney(7);
                break;
            case R.id.tb_calc_num_8:
                calcMoney(8);
                break;
            case R.id.tb_calc_num_9:
                calcMoney(9);
                break;
            case R.id.tb_calc_num_0:
                calcMoney(0);
                break;
            case R.id.tb_calc_num_dot:
                if (dotNum.equals(".00")) {
                    isDot = true;
                    dotNum = ".";
                }
                moneyTv.setText(num + dotNum);
                break;
            case R.id.tb_note_clear://清空
                doClear();
                break;
            case R.id.tb_calc_num_del://删除
                doDelete();
                break;
        }
    }

    //Activity和Fragment传递数据
    public void send(Item item){
        selected_item = item;
        cashTv.setText(selected_item.getName());
    }

    //生命周期函数
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        //调用初始化函数
        initIcons();
        try {
            initData(savedInstanceState);
        } catch (Exception e) {
            e.printStackTrace();
        }
        initWidget();
        initClick();

        //数据库实例化
        accountDataBase = AccountDataBase.getInstance(this);

        //连接
        connectedTabAndViewpager();
    }

    @Override
    protected void onDestroy() {
        mediator.detach();
        viewPager2.unregisterOnPageChangeCallback(changeCallback);
        super.onDestroy();
    }

    //多线程
    //添加数据
    @SuppressLint("StaticFieldLeak")
    private class InsertAccountTask extends AsyncTask<Void,Void,Void>{
        int sign;
        String type;
        double amount;
        String date;
        String des;
        int numOfDay;

       public InsertAccountTask(int sign,String type,double amount,String date,int numOfDay,String des){
            this.sign = sign;
            this.type = type;
            this.amount = amount;
            this.date = date;
            this.des = des;
            this.numOfDay = numOfDay;
        }

        @Override
        protected Void doInBackground(Void... voids) {
//            System.out.println(date);
            Log.d("!1!!", String.valueOf(numOfDay));
            accountDataBase.getAccountDao().insertAccount(new Account(0,sign,type,amount,date,numOfDay,des));
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            clear();
        }
    }
}