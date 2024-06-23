package com.apptechbd.haatbazaar.utils;

import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import static com.apptechbd.haatbazaar.utils.Constants.TAG;

import com.google.android.material.snackbar.Snackbar;

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
}
