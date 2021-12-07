package com.example.myaccount.ui.home;

import android.content.AsyncQueryHandler;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.LinearLayout;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.myaccount.R;
import com.example.myaccount.dataBase.Account;
import com.example.myaccount.dataBase.AccountDao;
import com.example.myaccount.dataBase.AccountDataBase;
import com.github.mikephil.charting.interfaces.datasets.ICandleDataSet;

public class HomeViewModel extends ViewModel {
    private MutableLiveData<Context> mContext;
    private MutableLiveData<int[]> Ids;
    private AccountDao accountDao;
    public HomeViewModel() {
        mContext = new MutableLiveData<>();
        Ids = new MutableLiveData<>();

    }

    public LiveData<Context> getContext() { return mContext; }
    public LiveData<int[]> getIds(AccountDataBase accountDataBase){
        accountDao = accountDataBase.getAccountDao();
        return accountDao.queryIds();
    }
}