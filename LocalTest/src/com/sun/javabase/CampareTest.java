package com.sun.javabase;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 集合排序
 * @author sunx(765480365@qq.com)<br/>
 * @version V1.0.0<br/>
 * @see {@link }
 */

public class CampareTest {
    public static void main(String[] args) {
        String s6 = "2016年第10月";
        String s1 = "2016年第1月";
        String s5 = "2016年第12月";
        String s4 = "2016年第6月";
        String s3 = "2016年第4月";
        String s7 = "2016年第11月";
        String s2 = "2016年第3月";

        List<String> list = new ArrayList<String>();
        list.add(s1);
        list.add(s2);
        list.add(s3);
        list.add(s4);
        list.add(s5);
        list.add(s6);
        list.add(s7);

        Collections.sort(list,new Comparator<String>() {

            @Override
            public int compare(String o1, String o2) {
                int month1 = Integer.parseInt(o1.substring(o1.indexOf("第")+1, o1.indexOf("月")));
                int month2 = Integer.parseInt(o2.substring(o2.indexOf("第")+1, o2.indexOf("月")));
                return month1-month2;
            }
        });
        for(String temp: list){
            System.out.println(temp);
        }
    }
}
