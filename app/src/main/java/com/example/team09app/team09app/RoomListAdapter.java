package com.example.team09app.team09app;

import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class RoomListAdapter extends RecyclerView.Adapter<RoomListAdapter.ItemViewHolder> {

    private Context mCtx;
    private List<Item> itemList;
    private static final String TAG = "RoomListAdapter";

    public RoomListAdapter (Context mCtx, List<Item> itemList) {
        this.mCtx = mCtx;
        this.itemList = itemList;
    }

    @Override
    public RoomListAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.recyclerview_rooms, parent, false);
        return new RoomListAdapter.ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        Item it = itemList.get(position);
        //holder.textViewNumber_id.setText("1");
        holder.textViewRoom_id.setText(it.getMRoom());
        // get number of items in each room
        // TODO: Need to figure out how to count number of items in each room
        /*int numItemsInRoom = DatabaseClient
                .getInstance(mCtx)
                .getItemRoomDatabase()
                .itemDao()
                .getNumRoom();
        Log.d(TAG, "numItemsInRoom: " + numItemsInRoom);*/
        /*if(numItemsInRoom > 0)
            holder.textViewNumber_id.setText(numItemsInRoom);*/
    }

    @Override
    public int getItemCount() {
        if(itemList != null)
            return itemList.size();
        else
            return 0;
    }

    class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textViewRoom_id, textViewNumber_id;

        public ItemViewHolder(View iView) {
            super(iView);

            textViewRoom_id = iView.findViewById(R.id.textViewRoom_id);
            textViewNumber_id = iView.findViewById(R.id.textViewNumber_id);

            iView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Item item = itemList.get(getAdapterPosition());

            // ToDo: change to viewSingleRoom when ready
            Intent intent = new Intent(mCtx, BrowseByRoom.class);
            intent.putExtra("room", item);

            mCtx.startActivity(intent);
        }
    }
}
