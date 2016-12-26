package com.sun.utils.utils;

import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author sunx(sunxin@strongit.com.cn)<br/>
 * @version V1.0.0<br/>
 * @see {@link }
 */

public class T_DateUtils {

    /**
     * 获取指定日期所在月的第一个星期一
     * @return
     */
    public static Date getOneMonthFirstMonday(Date dayOfOneMonth) {

        Calendar date = Calendar.getInstance();
        date.setTime(dayOfOneMonth);
        date.set(Calendar.DAY_OF_MONTH, 1);
        int i = 1;
        while(date.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY){
            date.set(Calendar.DAY_OF_MONTH, i++);
        }

        Date firstMonday = date.getTime();
        return firstMonday;
    }

    /**
     * 获取两个指定日期的中间日期
     * @return
     */
    public static Date getBetweenDay(Date start, Date end){

        if(end.before(start)){
            Date temp = new Date();
            temp = end;
            end = start;
            start = temp;
        }

        Long startSec = start.getTime();
        Long endSec = end.getTime();

        return new Date(startSec+(endSec-startSec)/2);
    }
}
