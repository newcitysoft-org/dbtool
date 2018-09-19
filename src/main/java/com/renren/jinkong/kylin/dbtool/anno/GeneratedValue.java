package com.renren.jinkong.kylin.dbtool.anno;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author lixin.tian@renren-inc.com
 * @date 2018/9/19 13:57
 */
@Target(FIELD)
@Retention(RUNTIME)
public @interface GeneratedValue {
}
