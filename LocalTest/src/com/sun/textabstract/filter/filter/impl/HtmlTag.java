package com.sun.textabstract.filter.filter.impl;

import com.sun.textabstract.filter.constant.FilterConstant;
import com.sun.textabstract.filter.filter.TagFilter;
import com.sun.textabstract.filter.filterChian.TagFilterChain;

/**
 * 去除所有html标签，a标签除外（为了获取附件地址）
 * @author sunx(sunxin@strongit.com.cn)<br/>
 * @version V1.0.0<br/>
 * @see {@link TagFilter}
 */

public class HtmlTag implements TagFilter{

    @Override
    public void init() {
    }

    @Override
    public void doFilter(StringBuffer htmlStr, TagFilterChain chain) {

        //除a以外所有html标签
        String reg = "<[^>|^a].*?>";

        //去除多余替换符
        String tempStr = htmlStr.toString().replaceAll(reg, "");
        reg = FilterConstant.replaceChar+"\\s*"+FilterConstant.replaceChar;
        tempStr = tempStr.replaceAll(reg, "");

        htmlStr = new StringBuffer(tempStr);

    }

    @Override
    public void destory() {
    }

}
