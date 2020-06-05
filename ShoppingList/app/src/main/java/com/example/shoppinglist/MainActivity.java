package com.example.shoppinglist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initAddContactButton();
        ShoppingListDataSource ds = new ShoppingListDataSource(this);
        ArrayList<String> names;

        try {
            ds.open();
            names = ds.getItemName();
            ds.close();
            RecyclerView itemList = findViewById(R.id.rvItems);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
            itemList.setLayoutManager(layoutManager);
            com.example.shoppinglist.ItemAdapter itemAdapter = new com.example.shoppinglist.ItemAdapter(names);
            itemList.setAdapter(itemAdapter);
        }
        catch(Exception e) {
            Toast.makeText(this, "Error retrieving contacts", Toast.LENGTH_LONG).show();
        }

    }

    private void initAddContactButton() {
        Button newContact = findViewById(R.id.buttonAddItem);
        newContact.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FragmentManager fm = getSupportFragmentManager();
                ItemDialog itemDialog = new ItemDialog();
                itemDialog.show(fm, "Add Item");
            }
        });
    }

    public class ItemAdapter extends RecyclerView.Adapter {
        private ArrayList<String> itemData;

        public class ItemViewHolder extends RecyclerView.ViewHolder {
            public TextView textViewItem;
            public ItemViewHolder(@NonNull View itemView) {
                super(itemView);
                textViewItem = itemView.findViewById(R.id.textViewItemName);
            }

            public TextView getItemTextView() { return textViewItem;}
        }

        public ItemAdapter(ArrayList<String> arrayList) { itemData = arrayList;}

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
            return new ItemViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            com.example.shoppinglist.ItemAdapter.ItemViewHolder ivh = (com.example.shoppinglist.ItemAdapter.ItemViewHolder) holder;
            ivh.getItemTextView().setText(itemData.get(position));
        }

        @Override
        public int getItemCount() { return itemData.size(); }
    }



}