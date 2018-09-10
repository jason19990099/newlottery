package com.international.wtw.lottery.utils;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by Administrator on 2016/3/21.
 */
public class DateUtil {
    public static String[] weekName = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};

    /**
     * 获取某年、某月个总天数
     */
    public static int getMonthDays(int year, int month) {
        if (month > 12) {
            month = 1;
            year += 1;
        } else if (month < 1) {
            month = 12;
            year -= 1;
        }
        int[] arr = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        int days = 0;
        if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
            arr[1] = 29;
        }
        try {
            days = arr[month - 1];
        } catch (Exception e) {
            e.getStackTrace();
        }

        return days;
    }

    /**
     * 获取当前年份
     */
    public static int getYear() {
        return Calendar.getInstance().get(Calendar.YEAR);
    }

    /**
     * 获取当前月份
     */
    public static int getMonth() {
        return Calendar.getInstance().get(Calendar.MONTH) + 1; //从0计数，这里加1
    }

    /**
     * 获取当前年月日
     */
    public static CustomDate getCurrentDate() {
        CustomDate customDate;
        customDate = new CustomDate(getYear(), getMonth(), getCurrentMonthDay());
        return customDate;
    }

    /**
     * 一月中的第几天（今天几号）
     */
    public static int getCurrentMonthDay() {
        return Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 一周中的第几天（1代表周日）
     */
    public static int getWeekDay() {
        return Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 几点钟（24小时制）
     */
    public static int getMinute() {
        return Calendar.getInstance().get(Calendar.MINUTE);
    }

    public static int getSecond() {
        return Calendar.getInstance().get(Calendar.SECOND);
    }

    /**
     * 获取下一周的日历
     */
    public static CustomDate getNextSunday() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, 7 - getWeekDay() + 1);
        CustomDate date = new CustomDate(c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1, c.get(Calendar.DAY_OF_MONTH));
        return date;
    }

    public static int getHour() {
        return Calendar.getInstance().get(Calendar.HOUR_OF_DAY); //从0计数，这里加1
    }

    public static int getMin() {
        return Calendar.getInstance().get(Calendar.MINUTE); //从0计数，这里加1
    }

    /**
     * 将某个日期添加或减少几天后得到的日期数组
     */
    public static int[] getWeekSunday(int year, int month, int day, int pervious) {
        int[] time = new int[3];
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, day);
        c.add(Calendar.DAY_OF_MONTH, pervious);
        time[0] = c.get(Calendar.YEAR);
        time[1] = c.get(Calendar.MONTH) + 1;
        time[2] = c.get(Calendar.DAY_OF_MONTH);
        return time;
    }

    /**
     * 某年某月的第一天是周几
     */
    public static int getWeekDayFromDate(int year, int month) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getDateFromString(year, month));
        int week_index = cal.get(Calendar.DAY_OF_WEEK) - 1;//(1至7再减1及0-6,0代表周日)
        if (week_index < 0) {
            week_index = 0;
        }
        return week_index;
    }

    /**
     * 将年、月转化为日期(yyyy-MM-dd)格式
     */
    @SuppressLint("SimpleDateFormat")
    public static Date getDateFromString(int year, int month) {
        String dateString = year + "-" + (month > 9 ? month : ("0" + month)) + "-01";
        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            date = sdf.parse(dateString);
        } catch (Exception e) {

        }
        return date;
    }

    /**
     * 判断日期是否为当天
     */
    public static boolean isToday(CustomDate date) {
        return (date.year == DateUtil.getYear() && date.month == DateUtil.getMonth() && date.day == DateUtil.getCurrentMonthDay());
    }

    /**
     * 判断日期是否为当月
     */
    public static boolean isCurrentMonth(CustomDate date) {
        return (date.year == DateUtil.getYear() && date.month == DateUtil.getMonth());
    }

    /**
     * 转换时间戳
     */
    public static String timeStamp2Date(long timeStamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String sd = sdf.format(new Date(timeStamp));
        return sd;
    }

    public static String timeStamp2Date2(long timeStamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
        String sd = sdf.format(new Date(timeStamp));
        return sd;
    }

    public static Date getDateFromString(String dateString) {
        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            date = sdf.parse(dateString);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return date;
    }

    public static CustomDate getCustomDate(Date date) {
        if (date == null)
            return new CustomDate();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        CustomDate customDate = new CustomDate(year, month, day);
        return customDate;
    }

    public static CustomDate getCustomDate(String dateString) {
        Date date = getDateFromString(dateString);
        CustomDate customDate = getCustomDate(date);
        return customDate;
    }

    public static CustomDate getCustomDate(long theDate) {
        String dateString = timeStamp2Date(theDate);
        Date date = getDateFromString(dateString);
        CustomDate customDate = getCustomDate(date);
        return customDate;
    }

    public static String getCurrenDateString() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String dates = year + "-" + month + "-" + day;
        return dates;
    }

    public static String getCurrentTimeString() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        //        int min = calendar.get(Calendar.MINUTE);
        //        String times=year+"-"+month+"-"+day+" "+hour+":"+min;
        String times = year + "-" + month + "-" + day + " " + hour;
        return times;
    }

    public static int getDifMonths(CustomDate date) {
        int months = getYear() * 12 + getMonth() - date.year * 12 - date.getMonth();
        return months;
    }

    public static boolean isEqual(CustomDate date1, CustomDate date2) {
        if ((date1.year == date2.year) && (date1.month == date2.month) && (date1.day == date2.day)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 将时间戳转换为时间
     */
    public static String convertDateTime(String times) {
        String date = "";
        if (TextUtils.isEmpty(times))
            return date;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Long time = Long.valueOf(times + "000");
        date = format.format(time);
        return date;
    }

    /**
     * 将时间戳转换为时间
     */
    public static String convertTime(String times) {
        String date = "";
        if (TextUtils.isEmpty(times))
            return date;
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss", Locale.CHINA);
        Long time = Long.valueOf(times + "000");
        format.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        date = format.format(time);
        return date;
    }
}


























