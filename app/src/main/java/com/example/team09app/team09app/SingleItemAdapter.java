package com.example.team09app.team09app;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.List;

import static android.support.constraint.Constraints.TAG;

/** This class contains the adapter to have a display using a single item function.
 * @author team 09
 * @version 1.0
 */
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

        // Load Image
        try {
            StringToBitmap s = new StringToBitmap();
            Decompress d = new Decompress();
            Bitmap imageBitmap = null;
            imageBitmap = s.convert(d.decompress(it.getMPicture()));
            holder.itemImage.setImageBitmap(imageBitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }


        /*ContentResolver cr = mCtx.getContentResolver();
        try {
            Uri imageURI = Uri.parse(it.getMPicture());

            Log.d(TAG, "LoadURI: " + it.getMPicture());
            Bitmap imageBitmap;
            try {
                imageBitmap = MediaStore.Images.Media.getBitmap(cr, imageURI);
                //mImageButton.setVisibility(View.INVISIBLE);
                holder.itemImage.setImageBitmap(imageBitmap);
            } catch (IOException e) {
                Log.d(TAG, "ERROR: " + e);
            }
        } catch (Exception e) {
            Log.d(TAG, "Error: Image doesn't exist " + e);
        }*/
    }

    @Override
    public int getItemCount() {
        if (itemList != null)
            return itemList.size();
        else return 0;
    }

    class SingleItemHolder extends RecyclerView.ViewHolder {

        TextView itemNameText, roomText, categoryText, priceText, dateText;
        ImageView itemImage;

        public SingleItemHolder(View iView) {
            super(iView);

            itemNameText = itemView.findViewById(R.id.itemNameText);
            roomText = itemView.findViewById(R.id.roomText);
            categoryText = itemView.findViewById(R.id.categoryText);
            priceText = itemView.findViewById(R.id.priceText);
            dateText = itemView.findViewById(R.id.dateText);
            itemImage = itemView.findViewById(R.id.item_image_id2);
        }
    }
}
