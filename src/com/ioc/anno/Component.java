package com.ioc.anno;

import java.lang.annotation.*;

/**
 * 组件注解
 * @Author Zhangji
 */
@Documented
@Target(value= ElementType.TYPE)
@Retention(value= RetentionPolicy.RUNTIME)
public @interface Component {

    public String name() default "";
    public String value() default "";

}
