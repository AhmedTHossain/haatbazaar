package com.apptechbd.haatbazaar.views.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioGroup;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.apptechbd.haatbazaar.R;
import com.apptechbd.haatbazaar.databinding.ActivityAddAccountBinding;
import com.apptechbd.haatbazaar.interfaces.OnLanguageChangeListener;
import com.apptechbd.haatbazaar.models.Account;
import com.apptechbd.haatbazaar.utils.BaseActivity;
import com.apptechbd.haatbazaar.utils.DateUtil;
import com.apptechbd.haatbazaar.utils.HelperClass;
import com.apptechbd.haatbazaar.utils.PhoneNumberFormatter;
import com.apptechbd.haatbazaar.utils.PopUpWindow;
import com.apptechbd.haatbazaar.utils.StaffIdGenerator;
import com.apptechbd.haatbazaar.viewmodels.AddAccountViewModel;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.radiobutton.MaterialRadioButton;
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
    private MaterialAlertDialogBuilder builder;
    private int position;

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
        position = getIntent().getIntExtra("position", -1);
        switch (position){
            case 0:
                binding.inputEditTextEmail.setVisibility(View.VISIBLE);
                break;
            case 1:
                binding.inputEditTextEmail.setVisibility(View.GONE);
                break;
        }

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

    @Override
    protected void onResume() {
        super.onResume();
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

                Account account = new Account(id, name, email, phone, "", admin, created, true);

                // Upload the selected image to Firebase Storage
                if (selectedImageUri != null) {
                    uploadImageToFirebase(this, selectedImageUri, account);
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

    public void uploadImageToFirebase(Context context, Uri imageUri, Account account) {
        showProgressDialog("Staff");
        // Get Firebase Storage instance
        FirebaseStorage storage = FirebaseStorage.getInstance();

        // Create a reference to the image location in Firebase Storage
        StorageReference storageRef = storage.getReference().child("staff_photos/");
        //Define the filename for the image
        String filename = account.getId() + ".jpg";
        // Create a reference to the image file within the "staff_photos" folder
        StorageReference imageRef = storageRef.child(filename);

        // Upload the image to Firebase Storage
        imageRef.putFile(imageUri)
                .addOnSuccessListener(taskSnapshot -> {
                    // Upload success
                    account.setPhoto("staff_photos/" + account.getId() + ".jpg");
                    Log.d("TAG", "uploadImageToFirebase: " + account.getPhoto());
                    addAccountViewModel.addStaff(account);
                    addAccountViewModel.ifStaffAdded.observe(this, ifStaffAdded -> {
                        if (ifStaffAdded) {
                            new HelperClass().showSnackBar(binding.main, "New staff account added successfully!");
                        } else
                            new HelperClass().showSnackBar(binding.main, "Failed to add the new staff account!");
                        super.onBackPressed();
                    });
                })
                .addOnFailureListener(exception -> {
                    // Upload failed

                });
    }

    private void showProgressDialog(String accountType) {
        builder = new MaterialAlertDialogBuilder(this); // Use MaterialAlertDialogBuilder for Material Design theme

        LayoutInflater li = LayoutInflater.from(this);
        View view = li.inflate(R.layout.progress_alert_dialog, null);

        builder.setView(view);

        builder.setTitle("Creating Account");

        builder.setCancelable(false)
                .setPositiveButton("", null)
                .setNegativeButton("", null);
        builder.show();
    }
}
