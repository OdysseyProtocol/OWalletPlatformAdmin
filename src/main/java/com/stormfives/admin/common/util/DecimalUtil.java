package com.stormfives.admin.common.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**

 * Created by tlw on 2017/7/20.
 */
public class DecimalUtil {

    /**

     * @param b
     * @return
     */
    public static String formatTwoDecimalPlaces(BigDecimal b){
        DecimalFormat twoDecimalPlaces = new DecimalFormat("0.00");
        return twoDecimalPlaces.format(b);
    }

}
