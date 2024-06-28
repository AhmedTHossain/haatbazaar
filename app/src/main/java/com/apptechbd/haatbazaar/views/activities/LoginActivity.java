package com.apptechbd.haatbazaar.views.activities;

import static com.apptechbd.haatbazaar.utils.Constants.RC_SIGN_IN;
import static com.apptechbd.haatbazaar.utils.HelperClass.logErrorMessage;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.apptechbd.haatbazaar.R;
import com.apptechbd.haatbazaar.databinding.ActivityLoginBinding;
import com.apptechbd.haatbazaar.models.AdminAccount;
import com.apptechbd.haatbazaar.utils.BaseActivity;
import com.apptechbd.haatbazaar.utils.HelperClass;
import com.apptechbd.haatbazaar.viewmodels.LoginViewModel;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.materialswitch.MaterialSwitch;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.Locale;

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private ActivityLoginBinding binding;
    private FirebaseAuth mAuth;
    private GoogleSignInClient googleSignInClient;
    private LoginViewModel loginViewModel;

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

        if (getSignInStatus()) {
            if (getSignedInUserType().equals("admin"))
                startActivity(new Intent(this, AdminMainActivity.class));
            else
                startActivity(new Intent(this, MainActivity.class));
            finish();
        }

        initViewModel();

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

        initGoogleSignInClient();

        binding.buttonLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == binding.buttonLogin.getId())
            login();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount googleSignInAccount = task.getResult(ApiException.class);
                if (googleSignInAccount != null) {
                    getGoogleAuthCredential(googleSignInAccount);
                }
            } catch (ApiException e) {
                logErrorMessage(e.getMessage());
            }
        }
    }

    private void initViewModel() {
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
    }

    private void login() {
//        binding.progressView.setVisibility(View.VISIBLE);
        googleSignInClient.signOut();
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void initGoogleSignInClient() {
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);
    }

    private void getGoogleAuthCredential(GoogleSignInAccount googleSignInAccount) {
        String googleTokenId = googleSignInAccount.getIdToken();
        AuthCredential googleAuthCredential = GoogleAuthProvider.getCredential(googleTokenId, null);
        signInWithGoogleAuthCredential(googleAuthCredential);
    }

    private void signInWithGoogleAuthCredential(AuthCredential googleAuthCredential) {
        loginViewModel.signInWithGoogle(googleAuthCredential);
        loginViewModel.authenticatedUserLiveData.observe(this, authenticatedUser -> {
            if (authenticatedUser != null) {
                new HelperClass().showSnackBar(binding.main, "Hello " + authenticatedUser.getDisplayName());

                loginViewModel.checkIfAdminUser(authenticatedUser.getUid(), binding.main, this);
                loginViewModel.isAdminUser.observe(this, isAdmin -> {
                    if (isAdmin != null) {
                        saveSignedInUserType("admin");

                        //retrieving admin user data
                        String uid = authenticatedUser.getUid();

                        //Storing the retrieved admin account locally
                        isAdmin.setId(uid);
                        storeAdminAccount(isAdmin);

                        //creating local copy of admin user profile

                        startActivity(new Intent(this, AdminMainActivity.class));
                    } else {
                        saveSignedInUserType("user");
                        startActivity(new Intent(this, MainActivity.class));
                    }

                    finish();
                });

                saveSignInStatus(true);
            } else
                saveSignInStatus(false);
        });
    }
}