package com.example.myaccount.activity.addActivity;

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

import com.example.myaccount.R;

import java.util.ArrayList;
import java.util.List;

public class AccountRVFragment extends Fragment {

    private List<Item> itemList = new ArrayList<>();
    protected Item selected_item;
    private SendDataToActivity listener;
    private RecyclerView recyclerView;

    public static AccountRVFragment newInstance(int position, ArrayList<Item>list) {
        Bundle args = new Bundle();
        args.putInt("position",position);
        args.putParcelableArrayList("list", list);
        AccountRVFragment fragment = new AccountRVFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        assert getArguments() != null;
        if(getArguments().getInt("position")==0)return inflater.inflate(R.layout.fragment_outcome_rv, container, false);
        else return inflater.inflate(R.layout.fragment_income_rv, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        assert getArguments() != null;
        itemList = getArguments().getParcelableArrayList("list");

        if(getArguments().getInt("position")==0) recyclerView = requireActivity().findViewById(R.id.outcome_rv_view);
        else recyclerView = requireActivity().findViewById(R.id.income_rv_view);
        ItemAdapter itemAdapter = new ItemAdapter(itemList);
        recyclerView.setAdapter(itemAdapter);

        recyclerView.setHasFixedSize(true);
        GridLayoutManager gll = new GridLayoutManager(getActivity(), 5,RecyclerView.VERTICAL,false);

        //以下代码是当类型过多的时候实现分页功能
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

        //点击触发事件
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

        //默认值为第一位
        selected_item=itemList.get(0);

        //给Activity发送信息
        listener.send(selected_item);
    }

    public void onAttach(@NonNull Activity activity) {
        // TODO Auto-generated method stub
        listener=(SendDataToActivity) activity;
        super.onAttach(activity);
    }


    //通信的接口
    public interface SendDataToActivity {
        void send(Item item);
    }
}