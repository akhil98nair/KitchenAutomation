package com.miniproject.kitchenautomation;

import com.google.android.material.snackbar.Snackbar;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class CarRecyclerViewDataAdapter extends RecyclerView.Adapter<GroceryRecyclerViewItemHolder> {

    private List<GroceryRecyclerViewItem> carItemList;

    public CarRecyclerViewDataAdapter(List<GroceryRecyclerViewItem> carItemList) {
        this.carItemList = carItemList;
    }

    @Override
    public GroceryRecyclerViewItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Get LayoutInflater object.
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        // Inflate the RecyclerView item layout xml.
        View carItemView = layoutInflater.inflate(R.layout.activity_card_view_item, parent, false);

        // Get car title text view object.
        final TextView carTitleView = (TextView)carItemView.findViewById(R.id.card_view_image_title);
        final TextView kg = (TextView)carItemView.findViewById(R.id.kg);
        // Get car image view object.
        final ImageView carImageView = (ImageView)carItemView.findViewById(R.id.card_view_image);
        // When click the image.
        carImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get car title text.
                String carTitle = carTitleView.getText().toString();
                // Create a snackbar and show it.
                Snackbar snackbar = Snackbar.make(carImageView, "You click " + carTitle +" image", Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        });

        // Create and return our custom Car Recycler View Item Holder object.
        GroceryRecyclerViewItemHolder ret = new GroceryRecyclerViewItemHolder(carItemView);
        return ret;
    }

    @Override
    public void onBindViewHolder(GroceryRecyclerViewItemHolder holder, int position) {
        if(carItemList!=null) {
            // Get car item dto in list.
            GroceryRecyclerViewItem carItem = carItemList.get(position);

            if(carItem != null) {
                // Set car item title.
                holder.getCarTitleText().setText(carItem.getCarName());
                holder.getGrocery_kg().setText(carItem.getKg() + " KG");
                // Set car image resource id.
                holder.getCarImageView().setImageResource(carItem.getCarImageId());
            }
        }
    }

    @Override
    public int getItemCount() {
        int ret = 0;
        if(carItemList!=null)
        {
            ret = carItemList.size();
        }
        return ret;
    }
}
