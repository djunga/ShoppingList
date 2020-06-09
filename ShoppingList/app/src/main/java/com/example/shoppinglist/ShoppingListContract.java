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
        //// Added this stuff:
        public static final String COLUMN_DESCRIPTION = "itemdescription";
        //public static final String COLUMN_IMAGESRC = "categoryimagesrc";
        public static final String COLUMN_PURCHASED = "purchased";
    }
}
