package com.example.shoppinglist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.fragment.app.FragmentManager;

public class MainActivity extends AppCompatActivity implements ItemDialog.MyListener {
    private static SQLiteDatabase mDatabase;
    private static ItemAdapter mAdapter;
    private static String edit_name;
    private static String edit_category;
    private static String edit_description;
    private static float price;
    //private static String category_imgsrc;
    private static boolean purchased;

    //private String category;
    private TextView nameText;
    private Button button_save;
    //private static int mAmount = 0;
    public static ArrayList<Item> itemList;
    private ItemDialog itemDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameText = findViewById(R.id.textview_main_name);

        ShoppingListDBHelper dbHelper = new ShoppingListDBHelper(this);
        mDatabase = dbHelper.getWritableDatabase();
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ItemAdapter(this, getAllItems());
        recyclerView.setAdapter(mAdapter);
        //////
        mAdapter.setOnItemClickListener(new ItemAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                changeItem(position, "Clicked");
            }
            @Override
            public void onDeleteClick(int position) {
                removeItem(position);
            }
        });
        /////
        itemList = new ArrayList<>();
        initNewButton();
    }

    public void removeItem(int position) {
        itemList.remove(position);
        mAdapter.notifyItemRemoved(position);
    }
    public void changeItem(int position, String text) {
        //itemList.get(position).changeItem(text);
        Item item = itemList.get(position);
        itemDialog = new ItemDialog(item);
        itemDialog.show(getSupportFragmentManager(), "Edit Item");
        mAdapter.notifyItemChanged(position);
    }

    @Override
    public void applyChanges(ContentValues cv) {
        //cv.put(edit_name, );
        edit_name = (String) cv.get("name");
        edit_category = (String) cv.get("category");
        edit_description = (String) cv.get("description");
        price = (float) Float.parseFloat((String) cv.get("price"));
        purchased = (boolean) Boolean.parseBoolean((String) cv.get("purchased"));
    }

    private void initNewButton() {
        final FloatingActionButton buttonNew = findViewById(R.id.button_new);
        buttonNew.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                itemDialog = new ItemDialog();
                itemDialog.show(getSupportFragmentManager(), "New Item");
            }
        });
    }

    public static void newItem() {
        if (edit_name.length() == 0) {
            return;
        }
        Item new_item = new Item();
        new_item.setName(edit_name);
        new_item.setCategory(edit_category);
        new_item.setPrice(price);
        new_item.setDescription(edit_description);
        //new_item.setCategoryImgSrc(category_imgsrc);
        new_item.setPurchased(purchased);

        itemList.add(0, new_item);

        ContentValues cv = new ContentValues();
        cv.put(ShoppingListContract.ShoppingListEntry.COLUMN_NAME, edit_name);
        cv.put(ShoppingListContract.ShoppingListEntry.COLUMN_CATEGORY, edit_category);
        cv.put(ShoppingListContract.ShoppingListEntry.COLUMN_DESCRIPTION, edit_description);
        //cv.put(ShoppingListContract.ShoppingListEntry.COLUMN_IMAGESRC, category_imgsrc);
        cv.put(ShoppingListContract.ShoppingListEntry.COLUMN_PRICE, price);
        cv.put(ShoppingListContract.ShoppingListEntry.COLUMN_PURCHASED, purchased);
        long id = mDatabase.insert(ShoppingListContract.ShoppingListEntry.TABLE_NAME, null, cv);
        new_item.setItemID((int) id);
        removeBadItems();
        mAdapter.swapCursor(getAllItems());
    }

    public static void editSelectedItem(Item item) {
        removeBadItems();
        item.setName(edit_name);
        item.setCategory(edit_category);
        item.setPrice(price);
        item.setDescription(edit_description);
        //item.setCategoryImgSrc(category_imgsrc);
        item.setPurchased(purchased);
        ContentValues cv = new ContentValues();
        cv.put(ShoppingListContract.ShoppingListEntry.COLUMN_NAME, edit_name);
        cv.put(ShoppingListContract.ShoppingListEntry.COLUMN_CATEGORY, edit_category);
        cv.put(ShoppingListContract.ShoppingListEntry.COLUMN_DESCRIPTION, edit_description);
        //cv.put(ShoppingListContract.ShoppingListEntry.COLUMN_IMAGESRC, category_imgsrc);
        cv.put(ShoppingListContract.ShoppingListEntry.COLUMN_PRICE, price);
        cv.put(ShoppingListContract.ShoppingListEntry.COLUMN_PURCHASED, purchased);
        String where = "_id=" +item.getItemID();
        mDatabase.update("shopping_list", cv, where, null);
        mAdapter.swapCursor(getAllItems());

    }

    public static void removeBadItems() {
        for(int i=0; i<itemList.size(); i++) {
            if(itemList.get(i).getItemID() == -1) {
                itemList.remove(i);
            }
        }
    }
    private static Cursor getAllItems() {
        return mDatabase.query(
                ShoppingListContract.ShoppingListEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                ShoppingListContract.ShoppingListEntry.COLUMN_ID + " DESC"
        );
    }


}

