package com.example.shoppinglist;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.DialogFragment;

public class ItemDialog extends DialogFragment {
    private EditText edit_name;
    private EditText edit_price;
    private MyListener listener;
    private Item selectedItem = null;

    public ItemDialog() {
        super();
    }
    public ItemDialog(Item item) {
        selectedItem = item;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.edit_item, container);
        getDialog().setTitle("Edit Item");

        edit_name = view.findViewById(R.id.editTextName);
        edit_price = view.findViewById(R.id.editTextPrice);
        Button button_save = view.findViewById(R.id.buttonSave);

        if(selectedItem != null) {
            edit_name.setText(selectedItem.getName());
        }

        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                listener.applyTexts(edit_name.getText().toString(), edit_price.getText().toString());
                if(selectedItem == null) {
                    com.example.shoppinglist.MainActivity.newItem();
                }
                else {
                    com.example.shoppinglist.MainActivity.editSelectedItem(selectedItem);
                }
                getDialog().dismiss();
            }
        });
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (MyListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement MyListener");
        }
    }

    public interface MyListener {
        void applyTexts(String name, String category);
    }
}