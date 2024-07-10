package com.apptechbd.haatbazaar.utils;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import static com.apptechbd.haatbazaar.utils.Constants.TAG;

import androidx.appcompat.app.AlertDialog;

import com.apptechbd.haatbazaar.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textview.MaterialTextView;

public class HelperClass {
    public static void logErrorMessage(String errorMessage) {
        Log.d(TAG, errorMessage);
    }

    public void showSnackBar(View layout, String msg) {
        Snackbar snackbar = Snackbar.make(
                layout,
                msg,
                Snackbar.LENGTH_SHORT
        );

        snackbar.setAnimationMode(Snackbar.ANIMATION_MODE_FADE);

//        // Set background color to white (#ffffff)
//        snackbar.getView().setBackgroundColor(Color.parseColor("#000000"));
//
//        // Set text color to black (#000000)
//        TextView snackbarTextView = snackbar.getView().findViewById(com.google.android.material.R.id.snackbar_text);
//        snackbarTextView.setTextSize(16);
//        snackbarTextView.setTextColor(Color.parseColor("#FFFFFF"));

        snackbar.show();
    }

    public AlertDialog showProgressDialog(MaterialAlertDialogBuilder builder, String title, String disclaimer, AlertDialog progressDialog, Context context) {
        builder = new MaterialAlertDialogBuilder(context); // Use MaterialAlertDialogBuilder for Material Design theme

        LayoutInflater li = LayoutInflater.from(context);
        View view = li.inflate(R.layout.progress_alert_dialog, null);

        MaterialTextView textDisclaimer = view.findViewById(R.id.text_disclaimer);

        textDisclaimer.setText(disclaimer);

        builder.setView(view);

        builder.setTitle(title);

        builder.setCancelable(false)
                .setPositiveButton("", null)
                .setNegativeButton("", null);
        progressDialog = builder.show();
        return progressDialog;
    }
}
