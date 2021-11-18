package com.example.myaccount;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class AccountAdapter extends ArrayAdapter<Item> {
    private int resourceId;

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Item item = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
        ImageView itemImage = view.findViewById(R.id.account_icon);
        TextView typeName = view.findViewById(R.id.type_name);
        itemImage.setImageResource(item.getId());
        typeName.setText(item.getName());
        return view;
    }

    public AccountAdapter(Context context, int textViewResourceId, List<Item> objects){
        super(context,textViewResourceId,objects);
        resourceId = textViewResourceId;



    }
}
