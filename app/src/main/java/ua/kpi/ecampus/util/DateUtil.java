package ua.kpi.ecampus.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtil {
    /**
     * format of the date
     */
    public static String FORMAT = "yyyy-MM-dd";

    /**
     * Get current date in specified format
     *
     * @return current date
     */
    public static String getCurrentDate() {
        DateFormat dateFormat = new SimpleDateFormat(FORMAT);
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static String getCurrentDate(String format) {
        DateFormat dateFormat = new SimpleDateFormat(format);
        Date date = new Date();
        return dateFormat.format(date);
    }

    /**
     * Convert string into date object
     *
     * @param date string
     * @return date object
     */
    public static Date convert(String date) {
        try {
            Date thedate = new SimpleDateFormat(FORMAT, Locale
                    .GERMAN).parse
                    (date);
            return thedate;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
