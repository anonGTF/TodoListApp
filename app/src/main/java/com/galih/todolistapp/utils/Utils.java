package com.galih.todolistapp.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Utils {

    public static String getDateFromMillis(long millis) {
        Date date = new Date(millis);
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        return formatter.format(date);
    }

    public static long getTodayMillis() {
        Calendar calendar = Calendar.getInstance();
        return calendar.getTimeInMillis();
    }
}
