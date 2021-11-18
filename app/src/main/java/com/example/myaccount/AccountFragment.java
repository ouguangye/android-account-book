package com.example.myaccount;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class AccountFragment extends Fragment {

    private View rootView;
    private List<Item>itemList = new ArrayList<>();

    public static AccountFragment newInstance(int sign) {
        Bundle args = new Bundle();
        args.putInt("sign", sign);
        AccountFragment fragment = new AccountFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_account, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initItems();
        AccountAdapter adapter = new AccountAdapter(getActivity(),R.layout.item,itemList);
        ListView listView = getActivity().findViewById(R.id.list_view);
        listView.setAdapter(adapter);
    }


    private void initItems(){
        Item fork = new Item("三餐","fork",R.drawable.fork);
        itemList.add(fork);

        Item snack = new Item("零食","snack",R.drawable.snacks);
        itemList.add(snack);

        Item cloth = new Item("衣服","cloth",R.drawable.clothing);
        itemList.add(cloth);

        Item study = new Item("学习","study",R.drawable.study);
        itemList.add(study);

        Item traffic = new Item("交通","traffic",R.drawable.traffic);
        itemList.add(traffic);
    }
}

