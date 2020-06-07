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
    private SQLiteDatabase mDatabase;
    private ItemAdapter mAdapter;
    private EditText edit_name;
    private EditText edit_category;
    private String name;
    private String category;
    private TextView nameText;
    private Button button_save;
    private int mAmount = 0;
    private Item selected_item;
    private boolean modifyDB;
    private static ArrayList<Item> itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ShoppingListDBHelper dbHelper = new ShoppingListDBHelper(this);
        mDatabase = dbHelper.getWritableDatabase();
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ItemAdapter(this, getAllItems());
        itemList = new ArrayList<>();
        recyclerView.setAdapter(mAdapter);
        initNewButton();
/*        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        Button buttonClick = (Button)rootView.findViewById(R.id.button);*/
    }

    @Override
    public void applyTexts(String name, String category) {
/*        textViewUsername.setText(username);
        textViewPassword.setText(password);*/
        TextView n = findViewById(R.id.textview_name_item);
        n.setText(name);
    }
    private void initNewButton() {
        Button buttonNew = findViewById(R.id.button_new);
        buttonNew.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ItemDialog itemDialog = new ItemDialog();
                itemDialog.show(getSupportFragmentManager(), "New Item");
                Log.d("INITNEWBUTTON", "before bool");
                //name =
                //newItem();
            }
        });
    }

    private void newItem() {
        Log.d("NEWITEM", "inside");
        edit_name = findViewById(R.id.editTextName);
        if (edit_name.getText().toString().trim().length() == 0 || mAmount == 0) {
            return;
        }
        String name = edit_name.getText().toString();
        ContentValues cv = new ContentValues();
        cv.put(ShoppingListContract.ShoppingListEntry.COLUMN_NAME, name);
        //cv.put(ShoppingListContract.ShoppingListEntry.COLUMN_PRICE, mAmount);
        mDatabase.insert(ShoppingListContract.ShoppingListEntry.TABLE_NAME, null, cv);
        mAdapter.swapCursor(getAllItems());
        edit_name.getText().clear();
    }
    private Cursor getAllItems() {
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

