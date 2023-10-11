package com.example.miniaspire.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    public static Date getDateFromString(String stringDate) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(stringDate);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getDateString(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd").format(date);
    }

}
