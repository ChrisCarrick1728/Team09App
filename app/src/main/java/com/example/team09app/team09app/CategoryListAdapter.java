package com.example.team09app.team09app;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.ItemViewHolder> {

    private Context mCtx;
    private List<Item> itemList;
    private static final String TAG = "CategoryListAdapter";

    public CategoryListAdapter (Context mCtx, List<Item> itemList) {
        this.mCtx = mCtx;
        this.itemList = itemList;
    }

    @Override
    public CategoryListAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.recyclerview_categories, parent, false);
        return new CategoryListAdapter.ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        Item it = itemList.get(position);
        //holder.textViewNumber_id.setText("1");
        holder.textViewCategory_id.setText(it.getMCategory());
    }

    @Override
    public int getItemCount() {
        if(itemList != null)
            return itemList.size();
        else
            return 0;
    }

    class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textViewCategory_id, textViewNumber2_id;

        public ItemViewHolder(View iView) {
            super(iView);

            textViewCategory_id = iView.findViewById(R.id.textViewCategory_id);
            textViewNumber2_id = iView.findViewById(R.id.textViewNumber2_id);

            iView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Item item = itemList.get(getAdapterPosition());

            // ToDo: change to viewSingleRoom when ready
            Intent intent = new Intent(mCtx, BrowseByCategory.class);
            intent.putExtra("room", item);

            mCtx.startActivity(intent);
        }
    }
}
