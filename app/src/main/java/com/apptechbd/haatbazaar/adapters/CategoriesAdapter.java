package com.apptechbd.haatbazaar.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.apptechbd.haatbazaar.R;
import com.apptechbd.haatbazaar.interfaces.OnCategoryClickListener;
import com.apptechbd.haatbazaar.models.Category;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.ViewHolder> {
    private ArrayList<Category> categories;

    private static final int TYPE_COW = 0;
    private static final int TYPE_GOAT = 1;
    private static final int TYPE_SHEEP = 2;
    private static final int TYPE_CAMEL = 3;
    private OnCategoryClickListener listener;

    public CategoriesAdapter(ArrayList<Category> categories, OnCategoryClickListener listener) {
        this.categories = categories;
        this.listener = listener;
    }

    @Override
    public int getItemViewType(int position) {
        String category = categories.get(position).getCategoryName();
        switch (category) {
            case "cow":
                return TYPE_COW;
            case "goat":
                return TYPE_GOAT;
            case "sheep":
                return TYPE_SHEEP;
            case "camel":
                return TYPE_CAMEL;
            default:
                return -1;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_COW:
                return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_category_cow, parent, false));
            case TYPE_GOAT:
                return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_category_goat, parent, false));
            case TYPE_SHEEP:
                return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_category_sheep, parent, false));
            case TYPE_CAMEL:
                return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_category_camel, parent, false));
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriesAdapter.ViewHolder holder, int position) {
        if (categories.get(position).isSelected()) {
            holder.getRootLayout().setCardBackgroundColor(ResourcesCompat.getColor(holder.getRootLayout().getResources(), R.color.md_theme_onSurface, null));
            holder.textView.setTextColor(ResourcesCompat.getColor(holder.getRootLayout().getResources(), R.color.md_theme_surface, null));
            holder.view.setBackgroundColor(ResourcesCompat.getColor(holder.getRootLayout().getResources(), R.color.md_theme_surface, null));
        }
        holder.getRootLayout().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Category category = categories.get(holder.getAdapterPosition());
                category.setSelected(!category.isSelected());
                listener.onCategoryClick(category);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView textView;
        private MaterialCardView rootLayout;
        private View view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.text_category);
            imageView = itemView.findViewById(R.id.image_category);
            rootLayout = itemView.findViewById(R.id.cardview);
            view = itemView.findViewById(R.id.view);
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

        public MaterialCardView getRootLayout() {
            return rootLayout;
        }

        public void setRootLayout(MaterialCardView rootLayout) {
            this.rootLayout = rootLayout;
        }

        public View getView() {
            return view;
        }

        public void setView(View view) {
            this.view = view;
        }
    }
}
