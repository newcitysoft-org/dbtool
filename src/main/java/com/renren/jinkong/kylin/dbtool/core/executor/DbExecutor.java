package com.renren.jinkong.kylin.dbtool.core.executor;

import javax.sql.DataSource;
import java.util.List;

/**
 * @author lixin.tian@renren-inc.com
 * @date 2018/9/19 12:48
 */
public interface DbExecutor {
    /**
     * 批量插入方法
     *
     * @param source
     * @param clazz
     * @param list
     * @return
     */
    int batchInsert(DataSource source, Class clazz, List list);

    /**
     * 批量更新方法
     *
     * @param source
     * @param clazz
     * @param list
     * @return
     */
    int batchUpdate(DataSource source, Class clazz, List list);

    /**
     * 批量删除
     *
     * @param source
     * @param clazz
     * @param ids
     * @return
     */
    int batchDelete(DataSource source, Class clazz, List ids);
}
