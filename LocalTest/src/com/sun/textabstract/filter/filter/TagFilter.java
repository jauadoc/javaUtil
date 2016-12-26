package com.sun.textabstract.filter.filter;

import com.sun.textabstract.filter.filterChian.TagFilterChain;

/**
 *
 * @author sunx(sunxin@strongit.com.cn)<br/>
 * @version V1.0.0<br/>
 */

public interface TagFilter {

    public void init();

    /**
     * 正则消除部分标签
     * @param htmlStr 页面文件中的字符串
     * @param chain tag过滤链
     */
    public void doFilter(StringBuffer htmlStr, TagFilterChain chain);

    public void destory();
}
