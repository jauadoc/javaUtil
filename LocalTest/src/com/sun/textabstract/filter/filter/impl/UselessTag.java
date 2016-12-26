package com.sun.textabstract.filter.filter.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.sun.textabstract.filter.filter.TagFilter;
import com.sun.textabstract.filter.filterChian.TagFilterChain;

/**
 * 无用的标签过滤
 * @author sunx(sunxin@strongit.com.cn)<br/>
 * @version V1.0.0<br/>
 * @see {@link TagFilter}
 */

public class UselessTag implements TagFilter {

    @Override
    public void init() {
    }

    @Override
    public void doFilter(StringBuffer htmlStr, TagFilterChain chain) {

        if(htmlStr.length()<1){
            //TODO Log htmlFile Error
        }
        //script、css、link、注释、以及文档声明 正则
        String reg = "<script.*?>.*?</script>|<style[^>]*?>[\\s\\S]*?<\\/style>|<link.*?>|<!--.*?-->|<!DOCTYPE.*?>";
        //去掉回车和tab，以及干扰的<、>等
        String tempStr = htmlStr.toString().replaceAll("\n|\t", "");
        tempStr = tempStr.replaceAll(">{2,9999}", ">");
        tempStr = tempStr.replaceAll("<{2,9999}", "<");

        tempStr = tempStr.replaceAll(reg, "");

        htmlStr = new StringBuffer(tempStr);

        chain.doFilter(htmlStr);
    }

    @Override
    public void destory() {
    }

    public static void main(String[] args) {
        Pattern pattern = Pattern.compile("\\w(t.*?)t");
        Matcher matcher = pattern.matcher("testsettestsettestset");
        while (matcher.find()){
            System.out.println(matcher.group());
        }

    }
}
