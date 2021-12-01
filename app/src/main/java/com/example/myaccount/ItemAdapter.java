package com.example.myaccount;

import android.annotation.SuppressLint;
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
        holder.itemView.setOnClickListener(v -> {
            int position = holder.getAdapterPosition();
            Item item = itemList.get(position);
            //Item preItem = itemList.get(holder.selectedPosition);
           // holder.selectedPosition = position;

        });
        return holder;
    }



    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Item item = itemList.get(position);
        holder.itemImage.setImageResource(item.getId());
        holder.typeName.setText(item.getName());
        if(position== holder.selectedPosition){
           // holder.itemImage.setBackgroundColor(R.color.blue_200);
           // holder.itemImage.setBackgroundResource(R.drawable.blue_circle);
        }
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