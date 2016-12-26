package com.sun.textabstract.text.parse;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;

import javax.annotation.Resource;

import com.sun.textabstract.filter.filterChian.TagFilterChain;
import com.sun.utils.utils.B_IOUtils;

/**
 *
 * @author sunx(sunxin@strongit.com.cn)<br/>
 * @version V1.0.0<br/>
 * @see {@link }
 */

public class DataFileParser {

    @Resource
    private TagFilterChain filterChain;

    /**
     * 解析数据文件为以指定替换符替换html标签的纯文本，用于数据分析
     * @param htmlFile 数据文件
     * @return 数据文件的字符串内容
     * @throws IOException
     */
    public StringBuffer parseDataFileToText(File htmlFile) throws IOException{
        //读取文件内容
        StringBuffer undealDataStr = new StringBuffer();

        BufferedReader htmlFileReader = B_IOUtils.getFileBufferedReader(htmlFile.getAbsolutePath());
        String line = "";
        while((line = htmlFileReader.readLine())!=null){
            undealDataStr.append(line);
        }

        //执行过滤
        filterChain.doFilter(undealDataStr);

        return undealDataStr;
    }
}
