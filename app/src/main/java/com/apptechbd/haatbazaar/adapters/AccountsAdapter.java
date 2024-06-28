package com.apptechbd.haatbazaar.adapters;

import static com.apptechbd.haatbazaar.utils.Constants.TAG;

import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.apptechbd.haatbazaar.R;
import com.apptechbd.haatbazaar.interfaces.OnAccountRemoveClickListener;
import com.apptechbd.haatbazaar.models.Account;
import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class AccountsAdapter extends RecyclerView.Adapter<AccountsAdapter.ViewHolder> {
    private ArrayList<Account> accounts;
    private OnAccountRemoveClickListener listener;
    public AccountsAdapter(ArrayList<Account> accounts, OnAccountRemoveClickListener listener) {
        this.accounts = accounts;
        this.listener = listener;
        Log.d(TAG, "setStaffAccounts: " + accounts.size());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_active_account, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.getNameTextView().setText(accounts.get(position).getName());
        holder.getPhoneTextView().setText(accounts.get(position).getPhone());

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference().child("staff_photos/");
        //Define the filename for the image
        String filename = accounts.get(position).getId() + ".jpg";
        // Create a reference to the image file within the "staff_photos" folder
        StorageReference imageRef = storageRef.child(filename);

        imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                String imageUrl = uri.toString();
                Glide.with(holder.getPhotoImageView().getContext())
                        .load(imageUrl)
                        .into(holder.getPhotoImageView());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

//        Glide.with(holder.getPhotoImageView().getContext()).load(accounts.get(position).getPhoto()).into(holder.getPhotoImageView());
        holder.getRemoveButtonView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onAccountRemoveClick(holder.getAdapterPosition());
            }});
    }

    @Override
    public int getItemCount() {
        return accounts.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private MaterialTextView nameTextView, phoneTextView;
        private CircleImageView photoImageView;
        private MaterialButton removeButtonView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.text_account_name);
            phoneTextView = itemView.findViewById(R.id.text_account_phone);
            photoImageView = itemView.findViewById(R.id.circleimageview_account_photo);
            removeButtonView = itemView.findViewById(R.id.button_remove_account);
        }

        public MaterialTextView getNameTextView() {
            return nameTextView;
        }

        public void setNameTextView(MaterialTextView nameTextView) {
            this.nameTextView = nameTextView;
        }

        public MaterialTextView getPhoneTextView() {
            return phoneTextView;
        }

        public void setPhoneTextView(MaterialTextView phoneTextView) {
            this.phoneTextView = phoneTextView;
        }

        public CircleImageView getPhotoImageView() {
            return photoImageView;
        }

        public void setPhotoImageView(CircleImageView photoImageView) {
            this.photoImageView = photoImageView;
        }

        public MaterialButton getRemoveButtonView() {
            return removeButtonView;
        }

        public void setRemoveButtonView(MaterialButton removeButtonView) {
            this.removeButtonView = removeButtonView;
        }
    }
}
