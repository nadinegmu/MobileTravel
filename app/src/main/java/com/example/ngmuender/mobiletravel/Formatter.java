package com.example.ngmuender.mobiletravel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class Formatter {

    public static String TIMEZONE_CET = "CET";

    private GregorianCalendar cal = new GregorianCalendar();

    SimpleDateFormat dateFormat = new SimpleDateFormat ("dd.MM.yyyy");
    SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
    SimpleDateFormat transportTimeFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");

    public Formatter() {
        // could probably be set in emulator
        dateFormat.setTimeZone(TimeZone.getTimeZone(TIMEZONE_CET));
        timeFormat.setTimeZone(TimeZone.getTimeZone(TIMEZONE_CET));
        transportTimeFormat.setTimeZone(TimeZone.getTimeZone(TIMEZONE_CET));
    }

    public String formatDateStringToTimeString(String timestamp) {

        return formatDateToTimeString(timestamp); }

    public String formatDateToString(Date date) {
        return dateFormat.format(date);
    }

    public String formatDateToTimeString(Date date) {
        return timeFormat.format(date);
    }
    private String formatDateToTimeString(String date) {

        String localPattern = transportTimeFormat.toLocalizedPattern();
        Date d = null;
        try {
            d = transportTimeFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formatDateToTimeString(d);
    }

    public String GetDateFormat(int day, int month, int year) {
        cal.setTimeInMillis(0);
        cal.set(year, month, day, 0, 0, 0);
        return formatDateToString((Date) cal.getTime());
    }

    public int GetDay() {
        return cal.get(Calendar.DAY_OF_MONTH);
    }
    public int GetMonth() {
        return cal.get(Calendar.MONTH);
    }
    public int GetYear() {
        return cal.get(Calendar.YEAR);
    }

    public int GetHour() {
        return  cal.get(Calendar.HOUR_OF_DAY);
    }
    public int GetMinute() {
        return  cal.get(Calendar.MINUTE);
    }

    public String formatDuration(String duration) {
        if (duration == "") {
            return "";
        }
        String[] splitDuration = duration.split("d");
        if (Integer.parseInt(splitDuration[0]) > 0) {
            return "+" + Integer.parseInt(splitDuration[0]);
        }
        else {
            String[] splitTimeDuration = splitDuration[1].split(":");
            return Integer.parseInt(splitTimeDuration[0]) + ":" + splitTimeDuration[1];
        }
    }
}
