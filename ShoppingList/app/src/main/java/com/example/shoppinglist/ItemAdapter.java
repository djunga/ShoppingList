package com.example.shoppinglist;

import android.content.Context;
import android.database.Cursor;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ShoppingListViewHolder> {
    private Context mContext;
    private Cursor mCursor;

    public ItemAdapter(Context context, Cursor cursor) {
        mContext = context;
        mCursor = cursor;
    }

    public class ShoppingListViewHolder extends RecyclerView.ViewHolder {
        public TextView nameText;
        public TextView countText;

        public ShoppingListViewHolder(View itemView) {
            super(itemView);

            nameText = itemView.findViewById(R.id.textview_main_name);
            countText = itemView.findViewById(R.id.textview_main_amount);
        }
    }

    @Override
    @NonNull
    public ShoppingListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.grocery_item, parent, false);
        return new ShoppingListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShoppingListViewHolder holder, int position) {
        if (!mCursor.moveToPosition(position)) {
            return;
        }

        String name = mCursor.getString(mCursor.getColumnIndex(ShoppingListContract.ShoppingListEntry.COLUMN_NAME));
        int amount = mCursor.getInt(mCursor.getColumnIndex(ShoppingListContract.ShoppingListEntry.COLUMN_PRICE));
        holder.nameText.setText(name);
        holder.countText.setText(String.valueOf(amount));
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    public void swapCursor(Cursor newCursor) {
        if (mCursor != null) {
            mCursor.close();
        }

        mCursor = newCursor;

        if (newCursor != null) {
            notifyDataSetChanged();
        }
    }
}
