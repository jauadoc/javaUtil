package com.sun.javabase;

/**
 *
 * @author sunx(sunxin@strongit.com.cn)<br/>
 * @version V1.0.0<br/>
 * @see {@link }
 */

public class Str {

    public static void main(String[] args) {
        //replaceAll 中 replacement中\替换时会被丢失
        String str = "{test}asdf";
        String test = "D:\\a\\b\\c\\d";
        System.out.println(str.replaceAll("\\{test\\}", test));
    }
}
