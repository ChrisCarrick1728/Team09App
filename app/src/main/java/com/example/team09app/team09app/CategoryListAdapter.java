package com.example.team09app.team09app;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/** This class contains the adapter to have the list of categories displayed correctly.
 * @author team 09
 * @version 1.0
 */
public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.ItemViewHolder> {

    private Context mCtx;
    private CategoryObject c;
    private static final String TAG = "CategoryListAdapter";

    public CategoryListAdapter (Context mCtx, CategoryObject c) {
        this.mCtx = mCtx;
        this.c = c;
    }

    @Override
    public CategoryListAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.recyclerview_categories, parent, false);
        return new CategoryListAdapter.ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        Item it = c.getItem().get(position);
        Integer num = c.getNumItems().get(position);
        holder.textViewNumber2_id.setText(num.toString());
        holder.textViewCategory_id.setText(it.getMCategory());
    }

    @Override
    public int getItemCount() {
        if(c != null)
            return c.getItem().size();
        else
            return 0;
    }

    class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textViewCategory_id, textViewNumber2_id;

        public ItemViewHolder(View iView) {
            super(iView);

            textViewCategory_id = iView.findViewById(R.id.textViewCategory_id);
            Log.d("Test_Num", textViewCategory_id.getText().toString());
            textViewNumber2_id = iView.findViewById(R.id.textViewCategoryNumber2_id);
            Log.d("Test_Num", textViewNumber2_id.getText().toString());

            iView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Item item = c.getItem().get(getAdapterPosition());

            Intent intent = new Intent(mCtx, ItemsByCategory.class);
            intent.putExtra("category", item.getMCategory());

            mCtx.startActivity(intent);
        }
    }
}
