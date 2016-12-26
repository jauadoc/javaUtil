package com.sun.utils.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 * 集合工具
 * @author sunx(sunxin@strongit.com.cn)<br/>
 * @version V1.0.0<br/>
 * @see {@link }
 */

public class B_CollectionUtil {

    /**
     * map值去重
     * @param map
     * @return Map<Object, Object>
     */
    public static Map<Object, Object> removeDuplicate(Map<Object, Object> map){
        Map<Object, Object> valueAsKeyMap = new HashMap<Object, Object>();
        Map<Object, Object> returnMap = new HashMap<Object, Object>();
        //TreeMap:对map按key值排序
        TreeMap<Object, Object> tempMap = new TreeMap<Object, Object>(map);
        Iterator<Object> it = tempMap.keySet().iterator();
        while (it.hasNext()) {
            Object key = it.next();
            Object value = tempMap.get(key);
            if(valueAsKeyMap.containsKey(value)){
                continue;
            }else{
                valueAsKeyMap.put(value, value);
                returnMap.put(key, value);
            }
        }
        return returnMap;
    }
}
