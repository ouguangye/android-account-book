package com.example.myaccount;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {
    private List<Item>itemList;
    private OnItemClickListener listener;


    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView itemImage;
        TextView typeName;
        View itemView;
        int selectedPosition=0;

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
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }



    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Item item = itemList.get(position);
        holder.itemImage.setImageResource(item.getId());
        holder.typeName.setText(item.getName());
        holder.itemView.setOnClickListener(view -> {
            if(listener!=null){
                Log.e("click", "!");
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

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}