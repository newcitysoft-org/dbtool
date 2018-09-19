package com.renren.jinkong.kylin.dbtool.anno;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 标记表名
 *
 * @author lixin.tian@renren-inc.com
 * @date 2018/9/19 10:38
 */
@Target(TYPE)
@Retention(RUNTIME)
public @interface Table {
    /**
     * 表名称
     *
     * @return
     */
    String name() default "";
}
