package com.example.myaccount.ui.cards;

import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.myaccount.R;
import com.example.myaccount.dataBase.DataBase;
import com.example.myaccount.dataBase.account.Account;
import com.example.myaccount.dataBase.account.AccountDao;
import com.example.myaccount.dataBase.user.User;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DataCard extends Card {
    private DataBase db;
    //private Account account;
    private Context context;
    Account[] accounts7;
    private int numOfDays = 0;
    private List<DisplayElement> elementList = new ArrayList<>();
    ListView listView;
    Query query;
    User user;

    private int calNumOfDays(Date date) throws Exception {
        date.setHours(0);
        date.setMinutes(0);
        date.setSeconds(0);
        SimpleDateFormat staF = new SimpleDateFormat("yyyy-MM-dd");
        Date sta = staF.parse("1900-01-01");
        String format = staF.format(date);
        Date help = staF.parse(format);

        return (int) ( (help.getTime() - sta.getTime()) / (24 * 3600 * 1000) );
    }

    private int getResourceId(String str) {
        switch (str) {
            case "canyin":
                return R.mipmap.canyin_blue;
            case "chongwu":
                return R.mipmap.chongwu_blue;
            case "dianqi":
                return R.mipmap.dianqi_blue;
            case "haizi":
                return R.mipmap.haizi_blue;
            case "hongbao":
                return R.mipmap.hongbao_blue;
            case "huafei":
                return R.mipmap.huafei_blue;
            case "huazhuang":
                return R.mipmap.huazhuang_blue;
            case "jiaotong":
                return R.mipmap.jiaotong_blue;
            case "lingshi":
                return R.mipmap.lingshi_blue;
            case "lvyou":
                return R.mipmap.lvyou_blue;
            case "shuidian":
                return R.mipmap.shuidianfei_blue;
            case "zhufang":
                return R.mipmap.zhufang_blue;
            case "xuexi":
                return R.mipmap.xuexi_blue;
            case "riyongpin":
                return R.mipmap.riyongpin_blue;
            case "qita":
                return R.mipmap.qita_blue;
            case "gongzi":
                return R.mipmap.gongzi_blue;
            case "gupiao":
                return R.mipmap.gupiao_blue;
            case "jianzhi":
                return R.mipmap.jianzhi_blue;
            case "shenghuofei":
                return R.mipmap.shenghuofei_blue;

        }
        return R.mipmap.canyin_blue;
    }

    private String getChinese(String str) {
        switch (str) {
            case "canyin":
                return "餐饮";
            case "chongwu":
                return "宠物";
            case "dianqi":
                return "电器";
            case "haizi":
                return "孩子";
            case "hongbao":
                return "红包";
            case "huafei":
                return "话费";
            case "huazhuang":
                return "化妆";
            case "jiaotong":
                return "交通";
            case "lingshi":
                return "粮食";
            case "lvyou":
                return "旅游";
            case "shuidian":
                return "水电";
            case "zhufang":
                return "住房";
            case "xuexi":
                return "学习";
            case "riyongpin":
                return "日用品";
            case "qita":
                return "其他";
            case "gongzi":
                return "工资";
            case "gupiao":
                return "股票";
            case "jianzhi":
                return "兼职";
            case "shenghuofei":
                return "生活费";

        }
        return "餐饮";
    }


    private void resetListView() {
        this.setVisibility(View.VISIBLE);
        if(accounts7.length == 0) {
            this.setVisibility(View.GONE);
            return;
        }
        elementList.clear();
        for(Account acc: accounts7) {
            DisplayElement ele = new DisplayElement( getChinese(acc.getType()), acc.getAmount(), getResourceId(acc.getType()), acc.getSid());
            elementList.add(ele);
        }
        ElementAdapter adapter = new ElementAdapter(context, R.layout.data_card, elementList);
        listView.setAdapter(adapter);
    }

    public void initDatas() {
        resetListView();
    }

    public DataCard(Context context, User user) {
        super(context);
        this.context = context;
        this.user = user;
        LayoutInflater.from(context).inflate(R.layout.data_listview, this);
        db = DataBase.getInstance(context);
        listView = (ListView) findViewById(R.id.list_view);
        try {
            numOfDays = calNumOfDays(new Date());
        } catch (Exception e) {
            e.printStackTrace();
        }
        query = new Query(db, numOfDays);
        query.execute();
        //initDa
    }

    public void refresh() {
        db = DataBase.getInstance(context);
        query = new Query(db, numOfDays);
        query.execute();
    }

    // 内部类 用于异步访问数据库
    private class Query extends AsyncTask<Void, Void, Void> {
        private DataBase accountDataBase;
        private AccountDao accountDao;
        private int numOfDay = 0;

        public Query(DataBase accountDataBase, int numOfDay) {
            this.accountDataBase = accountDataBase;
            this.numOfDay = numOfDay;
        }

        private void queryData() {
            accounts7 = accountDao.queryAll(user.getSid());
        }

        @Override
        protected Void doInBackground(Void... voids) {
            if(accountDataBase != null) {
                accountDao = accountDataBase.getAccountDao();
            }
            queryData();
            return null;
        }

        @Override
        protected void onPostExecute(Void avoid) {
            resetListView();
            return;
        }
    }

    private class DeleteTask extends AsyncTask<Void, Void, Void> {
        private DataBase accountDataBase;
        private AccountDao accountDao;
        private int sid;

        public DeleteTask(DataBase accountDataBase, int sid) {
            this.accountDataBase = accountDataBase;
            this.sid = sid;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            if(accountDataBase != null) {
                accountDao = accountDataBase.getAccountDao();
                accountDao.deleteBySid((long) this.sid);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void avoid) {
            //resetListView();
            return;
        }
    }

    private class ElementAdapter extends ArrayAdapter<DisplayElement> {
        private int resourceId;

        public ElementAdapter(Context context, int textViewResourceId, List<DisplayElement> objects) {
            super(context, textViewResourceId, objects);
            resourceId = textViewResourceId;
        }

        private String convertValue(double x) {
            if (x < 0) {
                return "-" + x;
            } else return x + "";
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            DisplayElement element = getItem(position);
            View view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            ImageView elementImage = (ImageView) view.findViewById(R.id.data_image);
            TextView elementName = (TextView) view.findViewById(R.id.data_name);
            TextView elementValue = (TextView) view.findViewById(R.id.data_value);
            elementImage.setImageResource(element.getGraphId());
            elementName.setText(element.getType());
            elementValue.setText(convertValue(element.getValue()));
            Button btn = (Button) view.findViewById(R.id.data_btn);
            btn.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    DeleteTask deleteTask = new DeleteTask(db, element.getSid());
                    deleteTask.execute();
                    query = new Query(db, numOfDays);
                    query.execute();                }
            });
            return view;
        }
    }

}


