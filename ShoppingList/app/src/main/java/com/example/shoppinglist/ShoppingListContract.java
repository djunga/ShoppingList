package com.example.shoppinglist;

import android.provider.BaseColumns;

public class ShoppingListContract {

    private ShoppingListContract() {
    }

    public static final class ShoppingListEntry implements BaseColumns {
        public static final String TABLE_NAME = "shopping_list";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_NAME = "itemname";
        public static final String COLUMN_PRICE = "price";
        public static final String COLUMN_CATEGORY = "itemcategory";
    }
}


/*
    private static final String CREATE_TABLE_SHOPPING_LIST =
            "create table shopping_list (_id integer primary key autoincrement, "
                    + "itemname text not null, itemcategory text, "
                    + "purchased boolean, price int, "
                    + "categoryimagesrc text);";*/
