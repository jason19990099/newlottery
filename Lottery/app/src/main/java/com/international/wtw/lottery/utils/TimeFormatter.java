package com.international.wtw.lottery.utils;

import android.support.v4.util.ArrayMap;

/**
 * Created by wuya on 2017/5/15.
 */

public class TimeFormatter {

    public static String formatTime(int remainTimeToEnd) {

        int minute = remainTimeToEnd / 60;
        int second = remainTimeToEnd % 60;
        StringBuilder sb = new StringBuilder();
        if (minute < 10) {

            sb.append("0");
            sb.append(String.valueOf(minute));
            sb.append(":");
        } else {
            sb.append(String.valueOf(minute));
            sb.append(":");
        }
        if (second < 10) {

            sb.append("0");
            sb.append(String.valueOf(second));
        } else {
            sb.append(String.valueOf(second));
        }

        return sb.toString();

    }

    public static String seconds2Time(int seconds) {
        if (seconds < 0) {
            return "--:--";
        }
        int temp;
        StringBuilder sb = new StringBuilder();

        temp = seconds / 3600;
        if (temp != 0) {
            sb.append((temp < 10) ? "0" + temp + ":" : "" + temp + ":");
        }

        temp = seconds % 3600 / 60;
        sb.append((temp < 10) ? "0" + temp + ":" : "" + temp + ":");

        temp = seconds % 3600 % 60;
        sb.append((temp < 10) ? "0" + temp : "" + temp);

        return sb.toString();
    }

    public static String getHour(int seconds) {
        if (seconds < 0) {
            return "00";
        }
        int hour = seconds / 3600;
        return (hour < 10) ? "0" + hour : "" + hour;
    }

    public static String getMinuteOfHour(int seconds) {
        if (seconds < 0) {
            return "00";
        }
        int minute = seconds % 3600 / 60;
        return (minute < 10) ? "0" + minute : "" + minute;
    }

    public static String getSecondOfMinute(int seconds) {
        if (seconds < 0) {
            return "00";
        }
        int second = seconds % 3600 % 60;
        return (second < 10) ? "0" + second : "" + second;
    }

    public static ArrayMap<String, String> formatTimeInfos(ArrayMap<String, String> timeRemainInfos, long remainTimeToEnd) {
        int mi = 60;
        int hh = mi * 60;
        long hour = remainTimeToEnd / hh;
        long minute = (remainTimeToEnd - hour * hh) / mi;
        long second = (remainTimeToEnd - hour * hh - minute * mi) % mi;
        if (hour < 10) {
            timeRemainInfos.put("HOUR", "0" + String.valueOf(hour));
        } else {
            timeRemainInfos.put("HOUR", String.valueOf(hour));
        }
        if (minute < 10) {
            timeRemainInfos.put("MINUTE", "0" + String.valueOf(minute));
        } else {
            timeRemainInfos.put("MINUTE", String.valueOf(minute));
        }
        if (second < 10) {
            timeRemainInfos.put("SECOND", "0" + String.valueOf(second));
        } else {
            timeRemainInfos.put("SECOND", String.valueOf(second));
        }
        return timeRemainInfos;
    }
}
