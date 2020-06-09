package com.example.shoppinglist;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Arrays;

import androidx.fragment.app.DialogFragment;

public class ItemDialog extends DialogFragment {
    private EditText edit_name;
    private EditText edit_price;
    private EditText edit_description;
    private Spinner spinner;
    private CheckBox purchased;
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
        edit_description = view.findViewById(R.id.editTextDescription);
        spinner = view.findViewById(R.id.spinner);
        purchased = view.findViewById(R.id.checkBoxPurchased);
        Button button_save = view.findViewById(R.id.buttonSave);

        if(selectedItem != null) {
            edit_name.setText(selectedItem.getName());
            edit_price.setText(selectedItem.getPrice()+"");
            edit_description.setText(selectedItem.getDescription());
            Resources res = getResources();
            String[] arr = res.getStringArray(R.array.categories);
            ArrayList<String> a = new ArrayList<>(Arrays.asList(arr));
            int position = 0;
            for(int i=0; i<a.size(); i++) {
                if(a.get(i).equals(selectedItem.getCategory())) {
                    position = i;
                    break;
                }
            }
            spinner.setSelection(position);
            if(selectedItem.isPurchased()) {
                purchased.setChecked(true);
            }
            else {
                purchased.setChecked(false);
            }
        }

        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                ContentValues newCV = new ContentValues();
                newCV.put("name", edit_name.getText().toString());
                newCV.put("price", edit_price.getText().toString());
                newCV.put("description", edit_description.getText().toString());
                newCV.put("category", spinner.getSelectedItem().toString());
                //if(purchased.isChecked()) { purchased = true;}
                if(purchased.isChecked()) { newCV.put("purchased", "true");}
                else { newCV.put("purchased", "false"); }
                String p = purchased.getText().toString();
                //newCV.put("purchased", purchased.getText().toString());
                listener.applyChanges(newCV);
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

/*    public ContentValues generateOldCV(Item item) {
        *//*
        * Here we generate a set of values, numbered:
        * 0. name
        * 1. category
        * 2. description
        * 3. categoryimgsrc
        * 4. price
        * 5. purchased
        * *//*
        ContentValues cv = new ContentValues();
        if(item == null) {}
        else {
                cv.put("name", item.getName());
                cv.put("category", item.getCategory());
                cv.put("description", item.getDescription());
                cv.put("categoryimgsrc", item.getCategoryImgSrc());
                cv.put("price", item.getPrice());
        }
        return cv;
    }*/
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
        void applyChanges(ContentValues cv);
    }
}