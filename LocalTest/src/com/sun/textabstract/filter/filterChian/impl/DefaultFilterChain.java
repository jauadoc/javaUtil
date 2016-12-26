package com.sun.textabstract.filter.filterChian.impl;

import java.util.ArrayList;
import java.util.List;

import com.sun.textabstract.filter.filter.TagFilter;
import com.sun.textabstract.filter.filterChian.TagFilterChain;

/**
 *
 * @author sunx(sunxin@strongit.com.cn)<br/>
 * @version V1.0.0<br/>
 * @see {@link TagFilterChain}
 */

public class DefaultFilterChain implements TagFilterChain{

    List<TagFilter> tagFilters = new ArrayList<TagFilter>();

    private int currentPosition = 0;

    @Override
    public void doFilter(StringBuffer htmlStr) {
        if(currentPosition < tagFilters.size()){
            TagFilter nextTagFilter = tagFilters.get(currentPosition);
            nextTagFilter.doFilter(htmlStr, this);
            currentPosition++;
        }else{
            // TODO
        }
    }

    public void addFilter(TagFilter tagFilter){
        tagFilters.add(tagFilter);
    }
}
