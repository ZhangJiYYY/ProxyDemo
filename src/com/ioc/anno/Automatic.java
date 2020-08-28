package com.ioc.anno;

import java.lang.annotation.*;

/**
 * Component标识组件内标识需自动注入属性
 */
@Documented
@Target(value= ElementType.FIELD)
@Retention(value= RetentionPolicy.RUNTIME)
public @interface Automatic {

    /**
     * 指定获取
     * @return
     */
    public String name() default "";

    public String value() default "";
}
