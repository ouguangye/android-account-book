package com.example.myaccount.activity.addActivity;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myaccount.R;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {
    private final List<Item>itemList;
    private OnItemClickListener listener;
    private Item preItem;


    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView itemImage;
        TextView typeName;
        View itemView;

        public ViewHolder(@NonNull View view) {
            super(view);
            itemView = view;
            itemImage = itemView.findViewById(R.id.account_icon);
            typeName = itemView.findViewById(R.id.type_name);
        }
    }

    public ItemAdapter(List<Item>itemList){
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);
        return new ViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Item item = itemList.get(position);
        preItem = item;
        if(position==0){
            holder.itemImage.setImageResource(item.getActive_id());
        }
        else holder.itemImage.setImageResource(item.getOrigin_id());
        holder.typeName.setText(item.getName());
        holder.itemView.setOnClickListener(view -> {
            if(listener!=null){
                listener.onItemClick(position);
            }
        });
    }

    public void setOnItemClickListener(OnItemClickListener listenser) {
        this.listener = listenser;
    }


    @Override
    public int getItemCount() {
        return itemList.size();
    }

    //adapter和fragment交互的接口
    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}