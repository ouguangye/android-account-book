package com.example.myaccount;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AccountFragment extends Fragment {

    private View rootView;
    private List<Item> itemList = new ArrayList<>();
    protected Item selected_item;
    private SendDataToActivity listener;


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
        RecyclerView recyclerView = getActivity().findViewById(R.id.list_view);
        GridLayoutManager gll = new GridLayoutManager(getActivity(), 5);
        recyclerView.setLayoutManager(gll);
        ItemAdapter itemAdapter = new ItemAdapter(itemList);
        itemAdapter.setOnItemClickListener(position -> {
            selected_item = itemList.get(position);
            listener.send(selected_item);
        });
        recyclerView.setAdapter(itemAdapter);
    }

    public void onAttach(Activity activity) {
        // TODO Auto-generated method stub
        listener=(SendDataToActivity) activity;
        super.onAttach(activity);
    }


    private void initItems() {
        Item yiban = new Item("一般", "yiban", R.mipmap.type_zijinbuchang);
        itemList.add(yiban);

        Item gouwu = new Item("购物", "gouwu", R.mipmap.type_shangchengxiaofei);
        itemList.add(gouwu);

        Item yundong = new Item("运动", "yundong", R.mipmap.type_yundong);
        itemList.add(yundong);

        Item yiliao = new Item("医疗", "yiliao", R.mipmap.type_yiliao);
        itemList.add(yiliao);

        Item xuexi = new Item("学习", "xuexi", R.mipmap.type_xuexi);
        itemList.add(xuexi);

        Item zhufang = new Item("住宿", "zhufang", R.mipmap.type_zhufang);
        itemList.add(zhufang);

        Item weixiu = new Item("维修", "weixiu", R.mipmap.type_weixiu);
        itemList.add(weixiu);

        Item yule = new Item("娱乐", "yule", R.mipmap.type_yule);
        itemList.add(yule);

        Item tongxun = new Item("通讯", "tongxun", R.mipmap.type_tongxun);
        itemList.add(tongxun);

        Item shuiguo = new Item("水果", "shuiguo", R.mipmap.type_shuiguo);
        itemList.add(shuiguo);

        Item shuma = new Item("数码", "shuma", R.mipmap.type_shuma);
        itemList.add(shuma);

        Item meirong = new Item("美容", "meirong", R.mipmap.type_meirong);

    }

    public interface SendDataToActivity{
        public void send(Item item);
    }
}
