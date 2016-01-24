package com.example.ngmuender.mobiletravel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateFormatter {
    private Calendar cal = Calendar.getInstance();

    public String GetDateNow() {
        return GetStringFromDate(new Date());
    }
    public String GetTimeNow() {
        return GetStringFromTime(new Date());
    }
    public String GetTimeFromDate(String dateString) { return GetStringFromTime(dateString); }

    private String GetStringFromDate(Date date) {
        SimpleDateFormat ft = new SimpleDateFormat ("dd.MM.yyyy");
        return ft.format(date);
    }

    private String GetStringFromTime(Date date) {
        SimpleDateFormat ft = new SimpleDateFormat ("HH:mm");
        return ft.format(date);
    }
    private String GetStringFromTime(String date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd'T'HH:mm:ssZ");
        Date d = null;
        try {
            d = ft.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return GetStringFromTime(d);
    }

    public String GetDateFormat(int day, int month, int year) {
        cal.setTimeInMillis(0);
        cal.set(year, month, day, 0, 0, 0);
        return GetStringFromDate((Date)cal.getTime());
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
}
