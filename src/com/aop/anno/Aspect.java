package com.aop.anno;

import java.lang.annotation.*;

/**
 * 标识动态代理
 */
@Documented
@Target(value= ElementType.TYPE)
@Retention(value= RetentionPolicy.RUNTIME)
public @interface Aspect {

    /**
     * 切面唯一标识
     * @return
     */
    public String value() default "";

    /**
     * 切面唯一标识
     * @return
     */
    public String name() default "";

    /**
     * 需代理接口路径
     * @return
     */
    public String execution() default "";

    /**
     * 动态代理的方法
     * @return
     */
    public String[] method() default {""};
}
