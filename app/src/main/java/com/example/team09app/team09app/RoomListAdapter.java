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

/** This class contains the adapter to have the list of rooms display function.
 * @author team 09
 * @version 1.0
 */
public class RoomListAdapter extends RecyclerView.Adapter<RoomListAdapter.ItemViewHolder> {

    private Context mCtx;
    private RoomObject rList;
    private static final String TAG = "RoomListAdapter";

    public RoomListAdapter (Context mCtx, RoomObject r) {
        this.mCtx = mCtx;
        rList = r;
    }

    @Override
    public RoomListAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.recyclerview_rooms, parent, false);
        return new RoomListAdapter.ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        Item it = rList.getItem().get(position);
        Integer num = rList.getNumItems().get(position);
        holder.textViewNumber_id.setText(num.toString());

        holder.textViewRoom_id.setText(it.getMRoom());
    }

    @Override
    public int getItemCount() {
        if(rList != null)
            return rList.getItem().size();
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
            Item item = rList.getItem().get(getAdapterPosition());

            Log.d(TAG, "onClick: " + item.getMRoom());
            Intent intent = new Intent(mCtx, ItemsByRoom.class);
            intent.putExtra("room", item);

            mCtx.startActivity(intent);
        }
    }
}
