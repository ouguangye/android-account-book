package com.example.myaccount.activity.addActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myaccount.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BlankFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BlankFragment extends Fragment {

    private List<Item> itemList = new ArrayList<>();
    protected Item selected_item;
    private SendDataToActivity listener;

    public BlankFragment(ArrayList<Item> list) {
       itemList =list;
    }


    // TODO: Rename and change types and number of parameters
    public static BlankFragment newInstance(char sign, ArrayList<Item> list) {
        Bundle args = new Bundle();
        BlankFragment fragment = new BlankFragment(list);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_blank, container, false);
    }

    public void onAttach(Activity activity) {
        // TODO Auto-generated method stub
        listener = (SendDataToActivity) activity;
        super.onAttach(activity);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        RecyclerView recyclerView = getActivity().findViewById(R.id.fragment_blank);
        ItemAdapter itemAdapter = new ItemAdapter(itemList);
        recyclerView.setAdapter(itemAdapter);

        recyclerView.setHasFixedSize(true);
        GridLayoutManager gll = new GridLayoutManager(getActivity(), 5, RecyclerView.VERTICAL, false);

        recyclerView.setLayoutManager(gll);

        itemAdapter.setOnItemClickListener(position -> {
            View pre_v = recyclerView.getChildAt(selected_item.getId());
            ItemAdapter.ViewHolder pre_viewHolder = (ItemAdapter.ViewHolder) recyclerView.getChildViewHolder(pre_v);
            pre_viewHolder.itemImage.setImageResource(selected_item.getOrigin_id());

            selected_item = itemList.get(position);

            View v = recyclerView.getChildAt(position);
            ItemAdapter.ViewHolder viewHolder = (ItemAdapter.ViewHolder) recyclerView.getChildViewHolder(v);
            viewHolder.itemImage.setImageResource(selected_item.getActive_id());

            listener.send(selected_item);
        });
        selected_item = itemList.get(0);

        listener.send(selected_item);
    }
}