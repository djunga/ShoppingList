package com.example.shoppinglist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

public class ShoppingListDataSource {
    private SQLiteDatabase database;
    private ShoppingListDBHelper dbHelper;

    public ShoppingListDataSource(Context context) {
        dbHelper = new ShoppingListDBHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public boolean insertItem(Item i) {
        boolean didSucceed = false;
        try {
            ContentValues initialValues = new ContentValues();

            initialValues.put("itemname", i.getName());
            initialValues.put("itemcategory", i.getCategory());
            initialValues.put("purchased", i.isPurchased());
            initialValues.put("price", i.getPrice());
            initialValues.put("categoryimgsrc", i.getCategoryImgSrc());

            didSucceed = database.insert("shopping_list", null, initialValues) > 0;
        }
        catch(Exception e) {
            //Do nothing -will return false if there is an exception
        }
        return didSucceed;
    }

    public boolean updateItem(Item i) {
        boolean didSucceed = false;
        try {
            Long rowId = (long) i.getItemID();
            ContentValues updateValues = new ContentValues();

            updateValues.put("itemname", i.getName());
            updateValues.put("itemcategory", i.getCategory());
            updateValues.put("purchased", i.isPurchased());
            updateValues.put("price", i.getPrice());
            updateValues.put("categoryimgsrc", i.getCategoryImgSrc());

            didSucceed = database.update("shopping_list", updateValues, "_id=" + rowId, null) > 0;
        }
        catch (Exception e) {
            //Do nothing -will return false if there is an exception
        }
        return didSucceed;
    }

    public int getLastItemId() {
        int lastId;
        try {
            String query = "Select MAX(_id) from shopping_list";
            Cursor cursor = database.rawQuery(query, null);

            cursor.moveToFirst();
            lastId = cursor.getInt(0);
            cursor.close();
        }
        catch (Exception e) {
            lastId = -1;
        }
        return lastId;
    }

    public ArrayList<String> getItemName() {
        ArrayList<String> itemNames = new ArrayList<>();
        try {
            String query = "Select itemname from shopping_list";
            Cursor cursor = database.rawQuery(query, null);

            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                itemNames.add(cursor.getString(0));
                cursor.moveToNext();
            }
            cursor.close();
        }
        catch (Exception e) {
            itemNames = new ArrayList<String>();
        }
        return itemNames;
    }

    public Item getSpecificItem(int id) {
        Item item = new Item();
        String query = "SELECT  * FROM contact WHERE _id =" + id;
        Cursor cursor = database.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            item.setItemID(cursor.getInt(0));
            item.setName(cursor.getString(1));
            item.setCategory(cursor.getString(2));
  /*          item.setDescription(cursor.getString(3));
            item.setPrice(cursor.getFloat(4));*/
            cursor.close();
        }
        return item;
    }
}
