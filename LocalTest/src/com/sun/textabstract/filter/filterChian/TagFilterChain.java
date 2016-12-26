package com.sun.textabstract.filter.filterChian;

/**
 * 标签过滤器链
 * @author sunx(sunxin@strongit.com.cn)<br/>
 * @version V1.0.0<br/>
 */

public interface TagFilterChain {

    /**
     * 执行下一个过滤
     * @param htmlStr
     */
    public void doFilter(StringBuffer htmlStr);
}
