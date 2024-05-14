package com.apptechbd.haatbazaar.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

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
}
