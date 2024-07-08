package com.apptechbd.haatbazaar.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.apptechbd.haatbazaar.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class PriceAdapter extends RecyclerView.Adapter<PriceAdapter.ViewHolder> {
    private ArrayList<String> cartList;

    public PriceAdapter(ArrayList<String> cartList) {
        this.cartList = cartList;
    }

    @NonNull
    @Override
    public PriceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_set_price, parent, false);
        return new PriceAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PriceAdapter.ViewHolder holder, int position) {
        switch (cartList.get(position)) {
            case "cow":
                holder.imageView.setImageResource(R.drawable.image_cow);
                break;
            case "goat":
                holder.imageView.setImageResource(R.drawable.image_goat);
                break;
            case "sheep":
                holder.imageView.setImageResource(R.drawable.image_sheep);
                break;
            case "camel":
                holder.imageView.setImageResource(R.drawable.image_camel);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextInputEditText priceText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageview_animal);
            priceText = itemView.findViewById(R.id.inputEditText_quantity);
        }

        public ImageView getImageView() {
            return imageView;
        }

        public void setImageView(ImageView imageView) {
            this.imageView = imageView;
        }

        public TextInputEditText getPriceText() {
            return priceText;
        }

        public void setPriceText(TextInputEditText priceText) {
            this.priceText = priceText;
        }
    }
}
