package com.apptechbd.haatbazaar.views.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.apptechbd.haatbazaar.R;
import com.apptechbd.haatbazaar.databinding.ActivityLoginBinding;
import com.apptechbd.haatbazaar.utils.BaseActivity;
import com.apptechbd.haatbazaar.utils.OtpView;
import com.apptechbd.haatbazaar.utils.PhoneNumberFormatter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.materialswitch.MaterialSwitch;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private ActivityLoginBinding binding;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());

        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        PhoneNumberFormatter.formatPhoneNumber(binding.inputedittextFieldPhone);

        MaterialSwitch languageSwitchButton = findViewById(R.id.view_language_toggle);
        languageSwitchButton.setChecked(getSavedLocale().getLanguage().equals("bn"));
        languageSwitchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    saveLocale("bn");
                    setLocale(new Locale("bn"));
                } else {
                    saveLocale("en");
                    setLocale(Locale.ENGLISH);
                }
                recreate(); // Reload the activity to apply the language change
            }
        });

        binding.buttonLogin.setOnClickListener(this);

        new OtpView().setupOtpInput(binding.layoutOtpView.getRoot());
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == binding.buttonLogin.getId())
            login();
    }

    private void login() {
        String phoneNumber = Objects.requireNonNull(binding.inputedittextFieldPhone.getText()).toString().trim();
        if (phoneNumber.isEmpty())
            Snackbar.make(binding.buttonLogin, "Please enter phone number first!", Snackbar.LENGTH_LONG).show();
        else
            // Send phone verification code
            sendVerificationCode("+88"+phoneNumber.replaceAll("-",""));
    }

    private void sendVerificationCode(String phoneNumber) {
        binding.inputEditTextPhone.setVisibility(View.GONE);
        binding.layoutOtpView.getRoot().setVisibility(View.VISIBLE);

        Log.d("LoginActivity","phone number sent: "+phoneNumber);
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Timeout unit
                this,               // Activity (for callback)
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        super.onCodeSent(s, forceResendingToken);
//                        binding.inputEditTextPhone.setVisibility(View.GONE);
//                        binding.layoutOtpView.getRoot().setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                        signInWithPhoneAuthCredential(phoneAuthCredential);
                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {
                        Log.w("LoginActivity", "Phone verification failed" + e.getMessage());
                        Snackbar.make(binding.buttonLogin, "Verification failed:"+ e.getMessage(), Snackbar.LENGTH_LONG).show();
                    }
                });
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, proceed to next activity
//                            startActivity(new Intent(LoginActivity.this, YourSignedInActivity.class));
//                            finish();
                        } else {
                            Log.w("LoginActivity", "SignInWithCredential:failure", task.getException());
                            Snackbar.make(binding.buttonLogin, "Sign in failed!", Snackbar.LENGTH_LONG).show();
                        }
                    }
                });
    }
}