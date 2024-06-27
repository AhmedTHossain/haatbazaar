package com.apptechbd.haatbazaar.utils;

import androidx.core.util.Pair;
import androidx.fragment.app.FragmentManager;

import com.apptechbd.haatbazaar.R;
import com.google.android.material.chip.Chip;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textview.MaterialTextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateUtil {
    public static String getTodayDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM, yyyy", Locale.getDefault());
        Date today = new Date();
        return dateFormat.format(today);
    }

    public static String getLastPeriod(String period) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM, yyyy", Locale.getDefault());

        if (period.equals("week")) {
            calendar.add(Calendar.DAY_OF_YEAR, -6); // Go back 6 days for the starting date
            Date startDate = calendar.getTime();
            Date endDate = new Date(); // End date is today

            return dateFormat.format(startDate) + " - " + dateFormat.format(endDate);
        } else if (period.equals("month")) {
            calendar.add(Calendar.DAY_OF_YEAR, -29); // Go back 29 days for the starting date
            Date startDate = calendar.getTime();
            Date endDate = new Date(); // End date is today

            return dateFormat.format(startDate) + " - " + dateFormat.format(endDate);
        } else {
            return "Invalid period";
        }
    }

    public static void showMaterialDateRangePicker(FragmentManager fragmentManager, MaterialTextView textDateRange, Chip chipCustom) {
        MaterialDatePicker.Builder<Pair<Long, Long>> builder = MaterialDatePicker.Builder.dateRangePicker();

        // Optional: Set title text for the dialog
        builder.setTitleText("Select Date Range");
        builder.setTheme(R.style.CustomMaterialDateRangePicker);

        // Build the MaterialDatePicker
        MaterialDatePicker<Pair<Long, Long>> materialDatePicker = builder.build();

        // Handle positive button click (date selection)
        materialDatePicker.addOnPositiveButtonClickListener(selection -> {
            // Extract start and end dates from the selection
            Long startDate = selection.first;
            Long endDate = selection.second;

            // Process or display the selected date range here
            //  e.g., update UI elements or perform actions based on dates
            String formattedStartDate = formatDate(startDate);
            String formattedEndDate = formatDate(endDate);

            String dateRange = formattedStartDate + " - " + formattedEndDate;
            textDateRange.setText(dateRange);
        });

        materialDatePicker.addOnNegativeButtonClickListener(selection -> {
            // Handle negative button click (cancel)
            chipCustom.setChecked(false);
        });

        // Show the MaterialDatePicker dialog
        materialDatePicker.show(fragmentManager, "DATE_RANGE_PICKER");
    }

    // Optional method to format the date (replace with your preferred formatting logic)
    private static String formatDate(Long timestamp) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM, yyyy", Locale.getDefault());
        return dateFormat.format(new Date(timestamp));
    }

    public static String getCurrentEpochTimeString() {
        // Get the current time in milliseconds
        long currentTimeMillis = System.currentTimeMillis();

        // Get the system default time zone
        TimeZone timeZone = TimeZone.getDefault();

        // Calculate the offset from UTC (in milliseconds)
        int offset = timeZone.getRawOffset();

        // Adjust the current time to epoch time in the device's time zone
        long epochMilli = currentTimeMillis - offset;

        // Convert epoch time in milliseconds to String
        return String.valueOf(epochMilli);
    }

}
