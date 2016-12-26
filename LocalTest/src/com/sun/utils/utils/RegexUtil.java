package com.sun.utils.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author sunx(sunxin@strongit.com.cn)<br/>
 * @version V1.0.0<br/>
 * @see {@link }
 */

public class RegexUtil {

    /**
     * 正则分割
     * @param reg正则表达式
     * @param str分割字符串
     * @return
     */
    public static String[] regSplit(String reg, String str){

        Pattern pattern = Pattern.compile(reg);
        return pattern.split(str);
    }

    /**
     * 是否包含匹配内容
     * @param reg 正则表达式
     * @param str 匹配字符串
     * @return
     */
    public static boolean isMatch(String reg, String str){
        return Pattern.matches(reg, str);
    }

    public static String[] matchGroup(String reg, String str){
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(str);
        String[] matchGroup = new String[matcher.groupCount()];
        int index = 0;
        while (matcher.find()) {
            matchGroup[index] = matcher.group();
            index++;
        }
        return matchGroup;
    }

}
