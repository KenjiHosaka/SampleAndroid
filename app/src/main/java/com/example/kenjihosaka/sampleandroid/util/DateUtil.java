package com.example.kenjihosaka.sampleandroid.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    public static Date getToday() {
        return getDayBeforeTarget(0);
    }

    public static Date getYesterday() {
        return getDayBeforeTarget(1);
    }

    public static Date getDayBeforeTarget(int before) {
        Date beforeDay = null;
        try {
            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date());
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            cal.add(Calendar.DATE, (-1 * before));
            beforeDay = cal.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return beforeDay;
    }

    public static Date getDateFromString(String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static int diffMinute(Date from, Date to) {
        int result = 0;
        try {
            result = (int)((to.getTime() - from.getTime()) / 60000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
