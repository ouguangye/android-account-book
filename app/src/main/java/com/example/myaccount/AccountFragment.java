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
        ItemAdapter itemAdapter = new ItemAdapter(itemList);
        recyclerView.setAdapter(itemAdapter);

        recyclerView.setHasFixedSize(true);
        GridLayoutManager gll = new GridLayoutManager(getActivity(), 5,RecyclerView.VERTICAL,false);
       /* GridPagerSnapHelper gridPagerSnapHelper = new GridPagerSnapHelper();
        gridPagerSnapHelper.setRow(2).setColumn(5);
        gridPagerSnapHelper.attachToRecyclerView(recyclerView);

        GridPagerUtils.transformAndFillEmptyData(
                new OneRowDataTransform<Item>(2), itemList);

        CirclePageIndicator indicator = (CirclePageIndicator) getActivity().findViewById(R.id.first_page_indicator);
        indicator.setRecyclerView(recyclerView);
        //Note: pageColumn must be config
        indicator.setPageColumn(5);


        indicator.setOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });*/



        recyclerView.setLayoutManager(gll);

        itemAdapter.setOnItemClickListener(position -> {
            View pre_v=recyclerView.getChildAt(selected_item.getId());
            ItemAdapter.ViewHolder pre_viewHolder=(ItemAdapter.ViewHolder)recyclerView.getChildViewHolder(pre_v);
            pre_viewHolder.itemImage.setImageResource(selected_item.getOrigin_id());

            selected_item = itemList.get(position);

            View v=recyclerView.getChildAt(position);
            ItemAdapter.ViewHolder viewHolder=(ItemAdapter.ViewHolder)recyclerView.getChildViewHolder(v);
            viewHolder.itemImage.setImageResource(selected_item.getActive_id());

            listener.send(selected_item);
        });
        selected_item=itemList.get(0);

        listener.send(selected_item);
    }

    public void onAttach(Activity activity) {
        // TODO Auto-generated method stub
        listener=(SendDataToActivity) activity;
        super.onAttach(activity);
    }


    private void initItems() {
        Item canyin = new Item("餐饮","canyin",R.mipmap.canyin_grey,R.mipmap.canyin_blue,0);
        itemList.add(canyin);

        Item chongwu = new Item("宠物","chongwu",R.mipmap.chongwu_grey,R.mipmap.chongwu_blue,1);
        itemList.add(chongwu);

        Item dianqi = new Item("电器","dianqi",R.mipmap.dianqi_grey,R.mipmap.dianqi_blue,2);
        itemList.add(dianqi);

        Item haizi = new Item("孩子","haizi",R.mipmap.haizi_grey,R.mipmap.haizi_blue,3);
        itemList.add(haizi);

        Item hongbao = new Item("红包","hongbao",R.mipmap.hongbao_grey,R.mipmap.hongbao_blue,4);
        itemList.add(hongbao);

        Item huafei = new Item("话费","huafei",R.mipmap.huafei_grey,R.mipmap.huafei_blue,5);
        itemList.add(huafei);

        Item huazhuang = new Item("化妆","huazhuang",R.mipmap.huazhuangpin_grey,R.mipmap.huazhuang_blue,6);
        itemList.add(huazhuang);

        Item jiaotong = new Item("交通","jiaotong",R.mipmap.jiaotong_grey,R.mipmap.jiaotong_blue,7);
        itemList.add(jiaotong);

        Item lingshi = new Item("零食","linghshi",R.mipmap.lingshi_grey,R.mipmap.lingshi_blue,8);
        itemList.add(lingshi);

        Item lvyou = new Item("旅游","lvyou",R.mipmap.lvyou_grey,R.mipmap.lvyou_blue,9);
        itemList.add(lvyou);

        Item shuidian = new Item("水电","shuidian",R.mipmap.shuidian_grey,R.mipmap.shuidianfei_blue,10);
        itemList.add(shuidian);

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
        itemList.add(zhufang);

        Item xuexi = new Item("学习","xuexi",R.mipmap.xuexi_grey,R.mipmap.xuexi_blue,12);
        itemList.add(xuexi);

        Item riyongpin = new Item("日用品","riyongping",R.mipmap.riyong_grey,R.mipmap.riyongpin_blue,13);
        itemList.add(riyongpin);

        Item qita = new Item("其他","qita",R.mipmap.qita_grey,R.mipmap.qita_blue,14);
        itemList.add(qita);

    }

    public interface SendDataToActivity{
        public void send(Item item);
    }
}
