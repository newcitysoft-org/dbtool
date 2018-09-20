package com.renren.jinkong.kylin.dbtool.anno;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 字段映射到列标题
 *
 * @author lixin.tian@renren-inc.com
 * @date 2018/9/20 11:38
 */
@Target(FIELD)
@Retention(RUNTIME)
public @interface Mapping {
    String cellName();
}
