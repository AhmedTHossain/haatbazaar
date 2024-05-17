package com.apptechbd.haatbazaar.views.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.apptechbd.haatbazaar.R;
import com.apptechbd.haatbazaar.databinding.ActivityAddAccountBinding;
import com.apptechbd.haatbazaar.utils.PhoneNumberFormatter;
import com.apptechbd.haatbazaar.utils.PopUpWindow;

public class AddAccountActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityAddAccountBinding binding;
    private Uri selectedImageUri = null;
    private ActivityResultLauncher<Intent> pickImageLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    // Get the selected image URI from the Intent
                    Intent data = result.getData();
                    if (data != null) {
                        selectedImageUri = data.getData();
                        binding.shapeableImageViewProfilePhoto.setImageURI(selectedImageUri);
                    }
                }
            }
    );
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityAddAccountBinding.inflate(getLayoutInflater(), getWindow().getDecorView().findViewById(android.R.id.content), false);

        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        PhoneNumberFormatter.formatPhoneNumber(binding.inputedittextFieldPhone);
        binding.buttonAddAccount.setEnabled(false);

        binding.buttonAddPhoto.setOnClickListener(this);
        binding.shapeableImageViewProfilePhoto.setOnClickListener(this);

    }

    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickImageLauncher.launch(intent);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_add_photo)
            openImagePicker();
        if (v.getId() == R.id.shapeableImageView_profile_photo)
            new PopUpWindow().showPopupWindow(binding.main, selectedImageUri);
    }

}