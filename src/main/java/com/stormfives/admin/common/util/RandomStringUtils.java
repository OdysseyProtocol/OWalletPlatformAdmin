package com.stormfives.admin.common.util;


import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: lyc
 * Date: 2018/3/16
 */
public class RandomStringUtils {

    public static String getRandomString(int length) {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ%-+#$";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }


}
