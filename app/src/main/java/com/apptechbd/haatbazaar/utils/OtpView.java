package com.apptechbd.haatbazaar.utils;

import android.content.Context;
import android.text.Editable;
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

public class OtpView extends LinearLayout {

    private int otpLength;
    private EditText[] otpFields;
    private OtpListener listener;

    public OtpView(Context context) {
        super(context);
        init(context, null);
    }

    public OtpView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, @Nullable AttributeSet attrs) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.otp_view, this, true);

        otpLength = 4; // Set your desired OTP length here
        otpFields = new EditText[otpLength];

        for (int i = 0; i < otpLength; i++) {
            otpFields[i] = (EditText) getChildAt(i);

            // Set Material Design text appearance
//            otpFields[i].setBackgroundResource(R.drawable.background_otp_field);
//            otpFields[i].setTextColor(ContextCompat.getColor(context, R.color.text_color_primary));

            int finalI = i;
            otpFields[i].addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (s.length() == 1) {
                        if (finalI < otpLength - 1) {
                            otpFields[finalI + 1].requestFocus();
                        } else if (listener != null) {
                            listener.onOtpComplete(getOtp());
                        }
                    } else if (s.length() == 0) {
                        if (finalI > 0) {
                            otpFields[finalI - 1].requestFocus();
                        }
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });
        }
    }

    public void setOtpListener(OtpListener listener) {
        this.listener = listener;
    }

    public String getOtp() {
        StringBuilder sb = new StringBuilder();
        for (EditText editText : otpFields) {
            sb.append(editText.getText().toString().trim());
        }
        return sb.toString();
    }

    public interface OtpListener {
        void onOtpComplete(String otp);
    }
}
