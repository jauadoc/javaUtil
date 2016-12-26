package com.sun.utils.utils;

/**
 * 系统符号工具
 * @author sunx(sunxin@strongit.com.cn)<br/>
 * @version V1.0.0<br/>
 * @see {@link }
 */

public class B_SystemUtils {

    private static final String lineSeparator = System.getProperty("line.separator", "\n");
    private static final String fileSeparator = System.getProperty("file.separator");

    /**
     * 返回系统分隔符
     * @param path
     * @return
     */
    public static String fileSepartor(String path){

        //\\->str's \ , \\\\->reg's \ ,\\\\\\\\->reg's \\
        path = path.replaceAll("/", "\\\\");
        path = path.replaceAll("\\\\\\\\", fileSeparator);

        return path;
    }

    /**
     * 返回系统换行符
     * @return
     */
    public static String newLine(){

        return lineSeparator;
    }
}
