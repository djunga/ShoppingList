package com.example.shoppinglist;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ShoppingListViewHolder> {
    private Context mContext;
    private Cursor mCursor;
    private OnItemClickListener mListener;

    public ItemAdapter(Context context, Cursor cursor) {
        mContext = context;
        mCursor = cursor;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
        void onDeleteClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public class ShoppingListViewHolder extends RecyclerView.ViewHolder {
        public TextView nameText;
        public TextView priceText;
        public TextView descriptionText;
        public CheckBox purchasedCheckBox;
        public ImageView img;

        public ShoppingListViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            nameText = itemView.findViewById(R.id.textViewName);
            //Resources res = com.example.shoppinglist.MainActivity.getResources();

            priceText = itemView.findViewById(R.id.textViewPrice);
            descriptionText = itemView.findViewById(R.id.textViewDescription);
            purchasedCheckBox = itemView.findViewById(R.id.checkBoxMain);
            img = itemView.findViewById(R.id.imageViewCategory);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });

            final Button buttonHideDetails = itemView.findViewById(R.id.buttonHideDetails);
            descriptionText = itemView.findViewById(R.id.textViewDescription);
            buttonHideDetails.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if(buttonHideDetails.getText().toString().equals("hide details")) {
                        buttonHideDetails.setText(R.string.show_details);
                        //TextView desc = v.findViewById(R.id.textViewDescription);
                        descriptionText.setVisibility(View.INVISIBLE);
                    }
                    else {
                        buttonHideDetails.setText(R.string.hide_details);
                        //TextView desc = v.findViewById(R.id.textViewDescription);
                        descriptionText.setVisibility(View.VISIBLE);
                    }
                }
            });

        }
    }

    @Override
    @NonNull
    public ShoppingListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new ShoppingListViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ShoppingListViewHolder holder, int position) {
        if (!mCursor.moveToPosition(position)) {
            return;
        }

        String name = mCursor.getString(mCursor.getColumnIndex(ShoppingListContract.ShoppingListEntry.COLUMN_NAME));
        float price = mCursor.getFloat(mCursor.getColumnIndex(ShoppingListContract.ShoppingListEntry.COLUMN_PRICE));
        String p = String.format(java.util.Locale.US,"%.2f", price);
        String description = mCursor.getString(mCursor.getColumnIndex(ShoppingListContract.ShoppingListEntry.COLUMN_DESCRIPTION));
        String category = mCursor.getString(mCursor.getColumnIndex(ShoppingListContract.ShoppingListEntry.COLUMN_CATEGORY));
        String purchased_str = mCursor.getString(mCursor.getColumnIndex(ShoppingListContract.ShoppingListEntry.COLUMN_PURCHASED));
        boolean purchased = Boolean.parseBoolean(purchased_str);
        holder.nameText.setText(name);
        holder.priceText.setText("$"+p);
        holder.descriptionText.setText(description);
        holder.purchasedCheckBox.setChecked(purchased);
        if(category.toLowerCase().equals("clothes")) {
            holder.img.setImageResource(R.drawable.shirt2);
        }
        else if(category.toLowerCase().equals("books")) {
            holder.img.setImageResource(R.drawable.ic_baseline_book_24);
        }
        else if(category.toLowerCase().equals("food")) {
            holder.img.setImageResource(R.drawable.food);
        }
        Item loadedItem = new Item();
        loadedItem.setName(name);

        MainActivity.itemList.add(loadedItem);
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
