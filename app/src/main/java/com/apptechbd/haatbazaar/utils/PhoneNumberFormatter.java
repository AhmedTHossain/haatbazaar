package com.apptechbd.haatbazaar.utils;

import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.widget.EditText;

public class PhoneNumberFormatter {

    public static void formatPhoneNumber(final EditText editText) {

        // Set the maximum length of the phone number with hyphens
        final int maxLength = 13;
        editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});

        editText.addTextChangedListener(new TextWatcher() {

            boolean isFormatting;
            String previousText = "";

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                previousText = s.toString();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Do nothing on text changing
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (isFormatting) {
                    return;
                }

                isFormatting = true;

                String input = s.toString();

                // Remove hyphens from the previous input
                input = input.replace("-", "");

                StringBuilder formatted = new StringBuilder();
                int digitCount = 0;

                for (int i = 0; i < input.length(); i++) {
                    char c = input.charAt(i);
                    if (Character.isDigit(c)) {
                        formatted.append(c);
                        digitCount++;
                        if (digitCount == 4 || digitCount == 7) {
                            formatted.append("-");
                        }
                    }
                }

                // Update the EditText with the formatted phone number
                editText.removeTextChangedListener(this);
                editText.setText(formatted.toString());
                editText.setSelection(formatted.length());
                editText.addTextChangedListener(this);

                isFormatting = false;
            }
        });
    }
}

