package com.example.shoppinglist;

import android.content.ContentValues;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.DialogFragment;

public class ItemDialog extends DialogFragment {
    private Item selected_item;
    private boolean done;
    private EditText edit_name;

    public ItemDialog(Item item) {
        super();
        if(item == null) {
            selected_item = new Item();
        }
        done = false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.edit_item, container);

        getDialog().setTitle("Edit Item");

        edit_name = view.findViewById(R.id.editTextName);
        final EditText edit_category = view.findViewById(R.id.editTextCategory);
        Button button_save = view.findViewById(R.id.buttonSave);

        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                selected_item.setName(edit_name.toString());
                selected_item.setCategory(edit_category.toString());
                done = true;
                getDialog().dismiss();
            }
        });
        return view;
    }

    public String getNameText() {
        return edit_name.toString();
    }

    public void setNameText(String str) {
        edit_name.setText(str);
    }

    public boolean getDone() { return done; }
    public void setDone(boolean b) { done = b; }
}
