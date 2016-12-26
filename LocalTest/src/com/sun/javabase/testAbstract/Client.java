package com.sun.javabase.testAbstract;

import com.sun.javabase.testAbstract.impl.AbstractImpl;

/**
 *
 * @author sunx(sunxin@strongit.com.cn)<br/>
 * @version V1.0.0<br/>
 * @see {@link }
 */

public class Client {

    public static void main(String[] args) {
        AbstractParent impl = new AbstractImpl();

        System.out.println(impl.whoAmI());
        System.out.println(impl.testStr);
        System.out.println(impl.testMethod);
    }
}
