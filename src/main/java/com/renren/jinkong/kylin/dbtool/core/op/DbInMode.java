package com.renren.jinkong.kylin.dbtool.core.op;

/**
 * 数据库插入执行操作模式
 *
 * @author lixin.tian@renren-inc.com
 * @date 2018/9/25 16:31
 */
public enum DbInMode {
    /**
     * 增加前删除
     */
    DELETE_AND_ADD,
    /**
     * 直接增加
     */
    ADD;
}
