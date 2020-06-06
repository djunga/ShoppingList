/*
package com.example.shoppinglist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.DialogFragment;

public class ItemDialog extends DialogFragment {

    Item selected_item;
    public interface SaveItemListener {
        void didFinishItemDialog(Item finishedItem);
    }

    public ItemDialog(Item item) {
        if(item == null) {
            selected_item = new Item();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.edit_item, container);

        getDialog().setTitle("Edit Item");

        final EditText edit_name = view.findViewById(R.id.editTextName);
        final EditText edit_category = view.findViewById(R.id.editTextCategory);
        Button button_save = view.findViewById(R.id.buttonSave);

        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                selected_item.setName(edit_name.toString());
                selected_item.setCategory(edit_category.toString());
                //this.notifyDataSetChanged();
                //saveItem(selected_item);
                getDialog().dismiss();
            }
        });
        return view;
    }
    private void saveItem(Item item) {
        SaveItemListener activity = (SaveItemListener) getActivity();
        activity.didFinishItemDialog(item);
        getDialog().dismiss();
    }
}
*/
