package com.example.team09app.team09app;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class SingleItemAdapter extends RecyclerView.Adapter<SingleItemAdapter.SingleItemHolder> {

    private Context mCtx;
    private List<Item> itemList;

    public SingleItemAdapter (Context mCtx, List<Item> itemList) {
        this.mCtx = mCtx;
        this.itemList = itemList;
    }

    @Override
    public SingleItemAdapter.SingleItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.recyclerview_single_item, parent, false);
        return new SingleItemAdapter.SingleItemHolder(view);
    }

    @Override
    public void onBindViewHolder(SingleItemAdapter.SingleItemHolder holder, int position) {
        Item it = itemList.get(position);
        holder.itemNameText.setText(it.getMName());
        holder.roomText.setText(it.getMRoom());
        holder.categoryText.setText(it.getMCategory());
        holder.priceText.setText(it.getMPrice());
        holder.dateText.setText(it.getMDate());
    }

    @Override
    public int getItemCount() {
        if (itemList != null)
            return itemList.size();
        else return 0;
    }

    class SingleItemHolder extends RecyclerView.ViewHolder {

        TextView itemNameText, roomText, categoryText, priceText, dateText;

        public SingleItemHolder(View iView) {
            super(iView);

            itemNameText = itemView.findViewById(R.id.itemNameText);
            roomText = itemView.findViewById(R.id.roomText);
            categoryText = itemView.findViewById(R.id.categoryText);
            priceText = itemView.findViewById(R.id.priceText);
            dateText = itemView.findViewById(R.id.dateText);
        }
    }
}
