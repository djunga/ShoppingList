package com.example.shoppinglist;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.fragment.app.FragmentManager;

public class MainActivity extends AppCompatActivity {
    private SQLiteDatabase mDatabase;
    private ItemAdapter mAdapter;
/*    private EditText mEditTextName;
    private TextView mTextViewAmount;*/
    private EditText edit_name;
    private EditText edit_category;
    private Button button_save;
    private int mAmount = 0;
    private Item selected_item;
    private boolean modifyDB;
    private ItemDialog itemDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ShoppingListDBHelper dbHelper = new ShoppingListDBHelper(this);
        mDatabase = dbHelper.getWritableDatabase();
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ItemAdapter(this, getAllItems());
        recyclerView.setAdapter(mAdapter);
        initNewButton();
/*        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        Button buttonClick = (Button)rootView.findViewById(R.id.button);*/
    }

    private void initNewButton() {
        Button buttonNew = findViewById(R.id.button_new);
        buttonNew.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FragmentManager fm = getSupportFragmentManager();
                itemDialog = new ItemDialog(null);
                itemDialog.show(fm, "Add Item");
                //itemDialog.getDone()
/*                LayoutInflater inflater = LayoutInflater.from(itemDialog.getContext());
                View theInflatedView = inflater.inflate(R.layout.edit_item, null);*/
                Log.d("INITNEWBUTTON", "before bool");
                //newItem();
            }
        });
    }

/*    public void done() {
        itemDialog.onDismiss(itemDialog) {

        }
    }*/

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