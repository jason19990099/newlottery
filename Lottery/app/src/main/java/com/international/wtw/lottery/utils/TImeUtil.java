package com.international.wtw.lottery.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 18Steven on 2017/9/22. 时间戳和时间之间的转换
 */

public class TImeUtil {

    public static String getDateStringByTimeSTamp(Long timeStamp,String pattern){
        String result = "";
        Date date = new Date(timeStamp*1000);
        SimpleDateFormat sd = new SimpleDateFormat(pattern);
        result = sd.format(date);
        return result;
    }
}
