package com.vault.assessment.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {


    /**
     * Compares the two dates if they appear on the same day or not.
     * If they appear on the same day and month and year method returns true otherwise false.
     *
     * @param localDateTime1
     * @param localDateTime2
     * @return
     */
    public static boolean compareDates(LocalDateTime localDateTime1, LocalDateTime localDateTime2) {

        int year1 = localDateTime1.getYear();
        int month1 = localDateTime1.getMonthValue();
        int day1 = localDateTime1.getDayOfMonth();

        int year2 = localDateTime2.getYear();
        int month2 = localDateTime2.getMonthValue();
        int day2 = localDateTime2.getDayOfMonth();

        if(year2 == year1 && month1 == month2 && day1 == day2) return true;

        return false;
    }

    /**
     * Method compares the two  dates if they appear on the same week or not.
     * If they appear on the same year and week it returns true otherwise false.
     *
     * @param localDateTime1
     * @param localDateTime2
     * @return
     */
    public static boolean compareWeeksOnDates(LocalDateTime localDateTime1, LocalDateTime localDateTime2) {
        ZoneId zoneId = ZoneId.systemDefault();
        Date date1 = Date.from(localDateTime1.atZone(zoneId).toInstant());
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(date1);

        int year1 = localDateTime1.getYear();
        int week1 = calendar1.get(calendar1.WEEK_OF_YEAR);

        Date date2 = Date.from(localDateTime2.atZone(zoneId).toInstant());
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(date2);
        int year2 = localDateTime2.getYear();
        int week2 = calendar2.get(calendar2.WEEK_OF_YEAR);

        if(year1 == year2 && week1 == week2)  return true;

        return false;
    }
}
