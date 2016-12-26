package com.sun.javabase.testAbstract;

/**
 *
 * @author sunx(sunxin@strongit.com.cn)<br/>
 * @version V1.0.0<br/>
 * @see {@link }
 */

public abstract class AbstractParent {

    private Class<? extends AbstractParent> clazz = getClass();

    protected String testStr = "parent";

    protected String testMethod = whoAmI();

    public String whoAmI(){
        return clazz.getName();
    }

    static{
        System.out.println("static");
    }
}
