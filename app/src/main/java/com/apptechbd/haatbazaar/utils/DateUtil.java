package com.apptechbd.haatbazaar.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtil {
    public static String getTodayDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd'th' MMMM, yyyy", Locale.getDefault());
        Date today = new Date();
        return dateFormat.format(today);
    }
}
