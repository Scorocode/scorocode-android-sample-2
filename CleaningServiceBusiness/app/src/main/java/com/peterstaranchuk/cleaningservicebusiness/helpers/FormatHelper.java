package com.peterstaranchuk.cleaningservicebusiness.helpers;

import android.content.Context;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.Date;

/**
 * Created by Peter Staranchuk.
 */

public class FormatHelper {

    public static String getFormattedDate(Context context, String unformattedDate) {
        Date date = new Date(unformattedDate);
        DateFormat dateFormat = android.text.format.DateFormat.getLongDateFormat(context.getApplicationContext());
        return dateFormat.format(date);
    }

    public static String getFormattedTime(Context context, String unformattedTime) {
        Date date = new Date(unformattedTime);
        DateFormat timeFormat = android.text.format.DateFormat.getTimeFormat(context.getApplicationContext());
        return timeFormat.format(date);
    }

    public static String formatMoney(Double price) {
        return new DecimalFormat("#.##").format(price);
    }

    public static String formatMoney(String price) {


        return new DecimalFormat("#.##").format(price);
    }
}
