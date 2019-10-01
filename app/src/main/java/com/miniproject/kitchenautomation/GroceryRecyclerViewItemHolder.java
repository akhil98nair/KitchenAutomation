package com.miniproject.kitchenautomation;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class GroceryRecyclerViewItemHolder extends RecyclerView.ViewHolder {

    private TextView carTitleText = null;
    private TextView grocery_kg = null;
    private ImageView carImageView = null;

    public GroceryRecyclerViewItemHolder(View itemView) {
        super(itemView);

        if(itemView != null)
        {
            carTitleText = (TextView)itemView.findViewById(R.id.card_view_image_title);
            grocery_kg = (TextView)itemView.findViewById(R.id.kg);

            carImageView = (ImageView)itemView.findViewById(R.id.card_view_image);
        }
    }

    public TextView getCarTitleText() {
        return carTitleText;
    }
    public TextView getGrocery_kg() {
        return grocery_kg;
    }

    public ImageView getCarImageView() {
        return carImageView;
    }
}
