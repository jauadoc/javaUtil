package com.sun.javabase;
import java.math.BigDecimal;


/**
 *
 * @author sunx(765480365@qq.com)<br/>
 * @version V1.0.0<br/>
 * @see {@link }
 */

public class TestNumber {
    public static void main(String[] args) {
        double test1 = 4d;
        double test2 = 7d;
        System.out.println((test1/test2)*100);//位数过多

        float test3 = 1f;
        float test4 = 7f;
        System.out.println(test3/test4);
        System.out.println((test3/test4)*100);
        System.out.println(new BigDecimal(1).divide(new BigDecimal(3),4, BigDecimal.ROUND_HALF_UP).doubleValue());
    }
}
