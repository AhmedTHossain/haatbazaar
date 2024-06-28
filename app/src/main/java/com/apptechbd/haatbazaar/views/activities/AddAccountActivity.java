package com.apptechbd.haatbazaar.views.activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.apptechbd.haatbazaar.R;
import com.apptechbd.haatbazaar.databinding.ActivityAddAccountBinding;
import com.apptechbd.haatbazaar.models.StaffAccount;
import com.apptechbd.haatbazaar.utils.BaseActivity;
import com.apptechbd.haatbazaar.utils.DateUtil;
import com.apptechbd.haatbazaar.utils.HelperClass;
import com.apptechbd.haatbazaar.utils.PhoneNumberFormatter;
import com.apptechbd.haatbazaar.utils.PopUpWindow;
import com.apptechbd.haatbazaar.utils.StaffIdGenerator;
import com.apptechbd.haatbazaar.viewmodels.AddAccountViewModel;
import com.apptechbd.haatbazaar.viewmodels.LoginViewModel;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Objects;

public class AddAccountActivity extends BaseActivity implements View.OnClickListener {
    private ActivityAddAccountBinding binding;
    private Uri selectedImageUri = null;
    private boolean isNameEntered = false;
    private boolean isPhoneEntered = false;
    private boolean isImagePicked = false;
    private boolean isEmailEntered = false;
    private AddAccountViewModel addAccountViewModel;

    private ActivityResultLauncher<Intent> pickImageLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    // Get the selected image URI from the Intent
                    Intent data = result.getData();
                    if (data != null) {
                        selectedImageUri = data.getData();
                        binding.shapeableImageViewProfilePhoto.setImageURI(selectedImageUri);

                        // Implement image picking logic here (using Intent or library)
                        // Once image is picked, update isImagePicked flag to true
                        isImagePicked = true;
                        updateButtonState();
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

        initViewModel();

        PhoneNumberFormatter.formatPhoneNumber(binding.inputedittextFieldPhone);
        binding.buttonAddAccount.setEnabled(false);

        binding.buttonAddPhoto.setOnClickListener(this);
        binding.shapeableImageViewProfilePhoto.setOnClickListener(this);

        binding.buttonAddAccount.setOnClickListener(this);

        // Add TextWatcher to nameEditText
        binding.inputEditTextFieldName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()) {
                    isNameEntered = true;
                    updateButtonState();
                } else {
                    isNameEntered = false;
                    updateButtonState();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        // Add TextWatcher to phoneEditText
        binding.inputedittextFieldPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int count, int after) {
                if (!s.toString().trim().isEmpty()) {
                    isPhoneEntered = true;
                    updateButtonState();
                } else {
                    isPhoneEntered = false;
                    updateButtonState();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void initViewModel() {
        addAccountViewModel = new ViewModelProvider(this).get(AddAccountViewModel.class);
    }

    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickImageLauncher.launch(intent);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_add_photo) {
            openImagePicker();
        }
        if (v.getId() == R.id.shapeableImageView_profile_photo)
            new PopUpWindow().showPopupWindow(binding.main, selectedImageUri);

        if (v.getId() == R.id.button_add_account) {
            try {

                String admin = getAdminAccount().getId();
                String created = DateUtil.getCurrentEpochTimeString();
                String email = binding.inputedittextFieldEmail.getText().toString();
                String id = new StaffIdGenerator().generateStaffId(Objects.requireNonNull(binding.inputedittextFieldEmail.getText()).toString());
                String name = binding.inputEditTextFieldName.getText().toString();
                String phone = binding.inputedittextFieldPhone.getText().toString();

                StaffAccount staffAccount = new StaffAccount(id, name, email, phone, "", admin, created, true);

                // Upload the selected image to Firebase Storage
                if (selectedImageUri != null) {
                    uploadImageToFirebase(this, selectedImageUri, staffAccount);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void updateButtonState() {
        Log.d("TAG", "updateButtonState: " + isNameEntered + " " + isPhoneEntered + " " + isImagePicked);
        binding.buttonAddAccount.setEnabled(isNameEntered && isPhoneEntered && isImagePicked);
    }

    public void uploadImageToFirebase(Context context, Uri imageUri, StaffAccount staffAccount) {

        // Get Firebase Storage instance
        FirebaseStorage storage = FirebaseStorage.getInstance();

        // Create a reference to the image location in Firebase Storage
        StorageReference storageRef = storage.getReference().child("staff_photos/" + staffAccount.getId() + ".jpeg");

        // Upload the image to Firebase Storage
        storageRef.putFile(imageUri)
                .addOnSuccessListener(taskSnapshot -> {
                    // Upload success
                    staffAccount.setPhoto(String.valueOf(imageUri));
                    Log.d("TAG", "uploadImageToFirebase: " + staffAccount.getPhoto());
                    addAccountViewModel.addStaff(staffAccount);
                    addAccountViewModel.ifStaffAdded.observe(this, ifStaffAdded -> {
                        if (ifStaffAdded) {
                            new HelperClass().showSnackBar(binding.main, "New Staff Account added successfully!");
                        } else
                            new HelperClass().showSnackBar(binding.main, "Failed to add the new staff account!");
                    });
                })
                .addOnFailureListener(exception -> {
                    // Upload failed

                });
    }
}
