package com.international.wtw.lottery.utils;


public class StringUtils {

    public static String formatBankcardId(String bankcardId) {
        if (bankcardId == null || bankcardId.isEmpty()) {
            return null;
        } else {
            String num = bankcardId.replaceAll("(?<=\\d{4})\\d(?=\\d{3})", "*");
            return formatCardNum(num);
        }
    }

    private static String formatCardNum(String text) {
        //先去掉所有的空格,因为有可能用户在输入的过程中有空格
        String originCardNum = text.trim().replace(" ", "");
        int len = originCardNum.length();

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < len; i++) {
            builder.append(originCardNum.charAt(i));
            if (i == 3 || i == 7 || i == 11 || i == 15 || i == 19) {
                //判断是不是最后一位,最后一位不加空格" "
                if (i != len - 1)
                    builder.append(" ");
            }
        }

        return builder.toString();
    }
}
