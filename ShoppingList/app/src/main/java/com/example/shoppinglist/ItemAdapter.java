package com.example.shoppinglist;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter {
    private ArrayList<String> itemData;

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewItem;
        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            //textViewItem = itemView.findViewById(R.id.textView...);
        }

        public TextView getItemTextView() { return textViewItem;}
    }

    public ItemAdapter(ArrayList<String> arrayList) { itemData = arrayList;}

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.simple_item_view, parent, false);
        return new ItemViewHolder(v);
    }
}
