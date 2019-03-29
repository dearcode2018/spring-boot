package com.hua.annotation;

import java.lang.annotation.*;

/**
 * 多数据源注解
 * @author Evan
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataSource {
    String name() default "";
}
