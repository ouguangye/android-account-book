package com.example.myaccount.ui.home;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myaccount.dataBase.account.AccountDao;
import com.example.myaccount.dataBase.DataBase;

public class HomeViewModel extends ViewModel {
    private MutableLiveData<Context> mContext;
    private MutableLiveData<int[]> Ids;
    private AccountDao accountDao;
    public HomeViewModel() {
        mContext = new MutableLiveData<>();
        Ids = new MutableLiveData<>();

    }

    public LiveData<Context> getContext() { return mContext; }
    public LiveData<int[]> getIds(DataBase accountDataBase){
        accountDao = accountDataBase.getAccountDao();
        return accountDao.queryIds();
    }
}