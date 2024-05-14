package com.apptechbd.haatbazaar.utils;

import android.content.Context;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.apptechbd.haatbazaar.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class OtpView {
    public OtpView() {
    }

    public void setupOtpInput(LinearLayout otpLayout) {
        // Get all TextInputEditText elements
        List<TextInputEditText> otpFields = new ArrayList<>();
        for (int i = 0; i < otpLayout.getChildCount(); i++) {
            View child = otpLayout.getChildAt(i);
            if (child instanceof TextInputLayout) {
                TextInputLayout textInputLayout = (TextInputLayout) child;
                TextInputEditText editText = (TextInputEditText) textInputLayout.getEditText();
                otpFields.add(editText);
            }
        }

        // Set input filter to allow only single digit
        InputFilter[] filters = new InputFilter[1];
        filters[0] = new InputFilter.LengthFilter(1);
        for (TextInputEditText editText : otpFields) {
            editText.setFilters(filters);
        }

        // Add TextWatcher to each EditText
        for (int i = 0; i < otpFields.size(); i++) {
            TextInputEditText currentField = otpFields.get(i);
            int finalI = i;
            currentField.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    // Not required for this functionality
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (s.length() == 0) { // Handle empty field first (potential optimization)
                        if (finalI > 0) {
                            otpFields.get(finalI - 1).requestFocus();
                        }
                        return;
                    }

                    if (!Character.isDigit(s.charAt(start))) { // Check for single digit at start
                        currentField.setText("");
                        return;
                    }

                    // Move to next field if needed
                    if (s.length() == 1 && finalI < otpFields.size() - 1) {
                        otpFields.get(finalI + 1).requestFocus();
                    }
                }


                @Override
                public void afterTextChanged(Editable s) {
                    // Not required for this functionality
                }
            });
        }
    }

}
