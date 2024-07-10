package com.apptechbd.haatbazaar.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.apptechbd.haatbazaar.R;
import com.apptechbd.haatbazaar.interfaces.OnQuantityAddClickListener;
import com.apptechbd.haatbazaar.interfaces.OnQuantitySubtractClickListener;
import com.apptechbd.haatbazaar.models.Quantity;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.Locale;

public class QuantityAdapter extends RecyclerView.Adapter<QuantityAdapter.ViewHolder> {
    private ArrayList<Quantity> quantitiesPurchased;
    private OnQuantityAddClickListener onQuantityAddClickListener;
    private OnQuantitySubtractClickListener onQuantitySubtractClickListener;

    public QuantityAdapter(ArrayList<Quantity> quantitiesPurchased, OnQuantityAddClickListener onQuantityAddClickListener, OnQuantitySubtractClickListener onQuantitySubtractClickListener) {
        this.quantitiesPurchased = quantitiesPurchased;
        this.onQuantityAddClickListener = onQuantityAddClickListener;
        this.onQuantitySubtractClickListener = onQuantitySubtractClickListener;
    }

    @NonNull
    @Override
    public QuantityAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_set_quantity, parent, false);
        return new QuantityAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuantityAdapter.ViewHolder holder, int position) {
        switch (quantitiesPurchased.get(position).getName()) {
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

        String qtyStr = String.format(Locale.US, "%02d", quantitiesPurchased.get(position).getQuantity());
        holder.textView.setText(qtyStr);

        holder.plusLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int qty = Integer.parseInt(holder.textView.getText().toString());
                qty++;

                String qtyStr = String.format(Locale.US, "%02d", qty);
                holder.textView.setText(qtyStr);
                onQuantityAddClickListener.onQuantityAddClick(position, qty);
            }
        });
        holder.minusLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int qty = Integer.parseInt(holder.textView.getText().toString());
                if (qty > 1) {
                    qty--;
                    String qtyStr = String.format(Locale.US, "%02d", qty);
                    holder.textView.setText(qtyStr);
                    onQuantitySubtractClickListener.onQuantitySubtractClick(position, qty);
                } else {
                    qty--;
                    onQuantitySubtractClickListener.onQuantitySubtractClick(position, qty);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return quantitiesPurchased.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private MaterialCardView plusLayout, minusLayout;
        private ImageView imageView;
        private TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            plusLayout = itemView.findViewById(R.id.card_plus);
            minusLayout = itemView.findViewById(R.id.card_minus);
            imageView = itemView.findViewById(R.id.imageview_animal);
            textView = itemView.findViewById(R.id.text_quantity);
        }

        public MaterialCardView getPlusLayout() {
            return plusLayout;
        }

        public void setPlusLayout(MaterialCardView plusLayout) {
            this.plusLayout = plusLayout;
        }

        public MaterialCardView getMinusLayout() {
            return minusLayout;
        }

        public void setMinusLayout(MaterialCardView minusLayout) {
            this.minusLayout = minusLayout;
        }

        public ImageView getImageView() {
            return imageView;
        }

        public void setImageView(ImageView imageView) {
            this.imageView = imageView;
        }

        public TextView getTextView() {
            return textView;
        }

        public void setTextView(TextView textView) {
            this.textView = textView;
        }
    }
}
