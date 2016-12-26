package com.sun.javabase;
import java.math.BigDecimal;
import java.text.ParseException;


/**
 *
 * @author sunx(765480365@qq.com)<br/>
 * @version V1.0.0<br/>
 * @see {@link }
 */

public class DoubleFormatTest {

    public static void main(String[] args) throws NumberFormatException, ParseException {
        String str1 = "1";
        String str2 = "7";
        BigDecimal divisor = new BigDecimal(Double.parseDouble(str1));
        BigDecimal divident = new BigDecimal(Double.parseDouble(str2));

        String rate = divisor.divide(divident, 2, BigDecimal.ROUND_HALF_UP).doubleValue()+"";
        System.out.println(rate);
    }

}
