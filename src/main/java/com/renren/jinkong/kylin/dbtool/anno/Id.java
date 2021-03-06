package com.renren.jinkong.kylin.dbtool.anno;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 主键标记
 *
 * @author lixin.tian@renren-inc.com
 * @date 2018/9/19 13:56
 */
@Target(FIELD)
@Retention(RUNTIME)
public @interface Id {
}
