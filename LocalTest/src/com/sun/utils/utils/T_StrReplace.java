package com.sun.utils.utils;

/**
 * 热词转为普通字符串工具类
 * @author sunx(sunxin@strongit.com.cn)<br/>
 * @version V1.0.0<br/>
 * @see {@link }
 */

public class T_StrReplace {

    public T_StrReplace(String reg, String replace) {

        this.reg = reg;
        this.replace = replace;
    }

    /**
     * 匹配的字符(特殊字符需要转换\\特殊字符)
     */
    private String reg;

    /**
     * 替换为的字符
     */
    private String replace;

    /**
     * 要替换的字符
     * @param hotword
     * @return
     */
    public String replaceStr(String str){

        return str.replaceAll(reg, replace);
    }
}
