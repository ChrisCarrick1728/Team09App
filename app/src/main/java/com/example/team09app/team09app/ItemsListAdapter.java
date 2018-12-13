package com.example.team09app.team09app;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/** This class contains the adapter for the list all items display to function
 * @author team 09
 * @version 1.0
 */
public class ItemsListAdapter extends RecyclerView.Adapter<ItemsListAdapter.ItemViewHolder> {

    private Context mCtx;
    private List<Item> itemList;

    public ItemsListAdapter(Context mCtx, List<Item> itemList) {
        this.mCtx = mCtx;
        this.itemList = itemList;
        Log.d("Test TAG", "itemList size: " + itemList.size());
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d("Test TAG", "ItemViewHolder started");
        View view = LayoutInflater.from(mCtx).inflate(R.layout.recyclerview_items, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        Log.d("Test TAG", "onBindViewHolder started");
        Log.d("Test TAG", "position: " + position);
        Item it = itemList.get(position);
        holder.textViewName.setText(it.getMName());
    }

    // getItemCount() is called many times, and when it is first called,
    // mItems has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (itemList != null){
            Log.d("Test TAG", "Item Count: " + itemList.size());
            return itemList.size();
        }
        else return 0;
    }

    class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textViewName;
        //textViewRoom, textViewCategory, textViewPrice, textViewDate;

        public ItemViewHolder(View iView) {
            super(iView);

            textViewName = iView.findViewById(R.id.textViewName);

            iView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Item item = itemList.get(getAdapterPosition());

            Intent intent = new Intent(mCtx, ViewSingleActivity.class);
            intent.putExtra("item", item);

            mCtx.startActivity(intent);
        }
    }
}

