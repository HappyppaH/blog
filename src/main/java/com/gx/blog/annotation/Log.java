package com.gx.blog.annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)      // 只能打在方法上
@Retention(RetentionPolicy.RUNTIME)  // 运行时生效
@Documented
public @interface Log {
    String value() default "";   // 操作描述，比如 "新增用户"
}