package com.example.team09app.team09app;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/** This class contains the adapter to have a display using a single room function correctly.
 * @author team 09
 * @version 1.0
 */
public class SingleRoomAdapter extends RecyclerView.Adapter<SingleRoomAdapter.ItemViewHolder>  {

    private Context mCtx;
    private List<Item> itemList;

    public SingleRoomAdapter(Context mCtx, List<Item> itemList) {
        this.mCtx = mCtx;
        this.itemList = itemList;
    }

    @Override
    public SingleRoomAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.recyclerview_items, parent, false);
        return new SingleRoomAdapter.ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        Item it = itemList.get(position);
        holder.textViewName.setText(it.getMName());
    }

    // getItemCount() is called many times, and when it is first called,
    // mItems has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (itemList != null)
            return itemList.size();
        else return 0;
    }

    class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textViewName;

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
