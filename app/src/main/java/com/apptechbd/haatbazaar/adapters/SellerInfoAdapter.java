package com.apptechbd.haatbazaar.adapters;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.apptechbd.haatbazaar.R;
import com.apptechbd.haatbazaar.interfaces.OnSellerInfoEnteredListener;
import com.apptechbd.haatbazaar.models.Account;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class SellerInfoAdapter extends RecyclerView.Adapter<SellerInfoAdapter.ViewHolder> {
    private ArrayList<Sale> salesArrayList;
    private ArrayList<Account> sellerAccounts;
    private String[] sellers;
    private ArrayList<String> sellerArrayList;
    private Context context;
    private OnSellerInfoEnteredListener listener;

    public SellerInfoAdapter(ArrayList<Sale> salesArrayList, ArrayList<Account> sellerAccounts, Context context, OnSellerInfoEnteredListener listener) {
        this.salesArrayList = salesArrayList;
        this.sellerAccounts = sellerAccounts;
        this.context = context;
        this.listener = listener;
        sellerArrayList = new ArrayList<>();
        for (Account account : sellerAccounts)
            sellerArrayList.add(account.getName());

        sellers = sellerArrayList.toArray(new String[0]);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_set_seller_information, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textViewPrice.setText(String.valueOf(salesArrayList.get(position).getPrice()));
        switch (salesArrayList.get(position).getCategory()) {
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
        Log.d("SellerInfoAdapter", "number o sellers inside SellerInfoAdapter in array = " + sellers.length);
        Log.d("SellerInfoAdapter", "number o sellers inside SellerInfoAdapter in arraylist = " + sellerArrayList.size());

        holder.autoCompleteTextViewSellerName.setDropDownAnchor(R.id.autocomplete_text_seller_name);
        holder.autoCompleteTextViewSellerName.setAdapter(new ArrayAdapter<>(context, android.R.layout.simple_dropdown_item_1line, sellers));
        holder.autoCompleteTextViewSellerName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d("SellerInfoAdapter", "seller name = " + s.toString());
                if (!s.toString().isEmpty())
                    listener.onSellerInfoEntered(s.toString(), position);
                else
                    listener.onSellerInfoEntered("", position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return salesArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private MaterialTextView textViewPrice;
        private MaterialAutoCompleteTextView autoCompleteTextViewSellerName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageview_animal);
            textViewPrice = itemView.findViewById(R.id.text_price_of_animal);
            autoCompleteTextViewSellerName = itemView.findViewById(R.id.autocomplete_text_seller_name);
        }

        public ImageView getImageView() {
            return imageView;
        }

        public void setImageView(ImageView imageView) {
            this.imageView = imageView;
        }

        public MaterialTextView getTextViewPrice() {
            return textViewPrice;
        }

        public void setTextViewPrice(MaterialTextView textViewPrice) {
            this.textViewPrice = textViewPrice;
        }

        public MaterialAutoCompleteTextView getAutoCompleteTextViewSellerName() {
            return autoCompleteTextViewSellerName;
        }

        public void setAutoCompleteTextViewSellerName(MaterialAutoCompleteTextView autoCompleteTextViewSellerName) {
            this.autoCompleteTextViewSellerName = autoCompleteTextViewSellerName;
        }
    }
}
