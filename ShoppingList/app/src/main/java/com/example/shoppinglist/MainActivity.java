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

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.fragment.app.FragmentManager;

public class MainActivity extends AppCompatActivity implements ItemDialog.MyListener {
    private static SQLiteDatabase mDatabase;
    private static ItemAdapter mAdapter;
    private static String edit_name;
    private String category;
    private TextView nameText;
    private Button button_save;
    private static int mAmount = 0;
    private static ArrayList<Item> itemList;
    private ItemDialog itemDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameText = (TextView) findViewById(R.id.textview_main_name);

        ShoppingListDBHelper dbHelper = new ShoppingListDBHelper(this);
        mDatabase = dbHelper.getWritableDatabase();
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ItemAdapter(this, getAllItems());
        recyclerView.setAdapter(mAdapter);
        itemList = new ArrayList<>();
        initNewButton();
    }

    @Override
    public void applyTexts(String name, String category) {
        edit_name = name;
    }

    private void initNewButton() {
        final Button buttonNew = findViewById(R.id.button_new);
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
        String name = edit_name;
        ContentValues cv = new ContentValues();
        cv.put(ShoppingListContract.ShoppingListEntry.COLUMN_NAME, name);
        mDatabase.insert(ShoppingListContract.ShoppingListEntry.TABLE_NAME, null, cv);
        mAdapter.swapCursor(getAllItems());
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

