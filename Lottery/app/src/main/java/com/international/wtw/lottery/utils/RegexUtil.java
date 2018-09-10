package com.international.wtw.lottery.utils;

import java.util.regex.Pattern;

/**
 * Created by Yuan on 2017/9/29.
 * 描述：正则相关 校验数据是否符合相应格式
 */

public class RegexUtil {

    /**
     * 正则：用户名，取值范围为a-z,A-Z,0-9,6-15位
     */
    public static final String REGEX_USERNAME = "^[a-zA-Z0-9]{6,15}$";

    /**
     * 正则：登录密码，取值范围为a-z,A-Z,0-9,6-15位
     */
    public static final String REGEX_PASSWORD = "^[a-zA-Z0-9]{6,15}$";

    public static final String REGEX_CHINESE = "^[\\u4e00-\\u9fa5]+$";

    public static boolean isUsername(String text) {
        return isMatch(REGEX_USERNAME, text);
    }

    public static boolean isPassword(String text) {
        return isMatch(REGEX_PASSWORD, text);
    }

    public static boolean isChinese(String text) {
        return isMatch(REGEX_CHINESE, text);
    }

    /**
     * string是否匹配regex正则表达式字符串
     *
     * @param regex 正则表达式字符串
     * @param text  要匹配的字符串
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isMatch(String regex, String text) {
        return text != null && text.length() != 0 && Pattern.matches(regex, text);
    }
}
