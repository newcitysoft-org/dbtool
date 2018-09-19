package com.renren.jinkong.kylin.dbtool.core.executor;

import com.renren.jinkong.kylin.dbtool.core.DbType;
import com.renren.jinkong.kylin.dbtool.core.executor.DbExecutor;
import com.renren.jinkong.kylin.dbtool.core.executor.DefaultMysqlDbExecutor;

/**
 * @author lixin.tian@renren-inc.com
 * @date 2018/9/19 13:10
 */
public final class DbExecutorFactory {
    public static DbExecutor getExecutor(DbType type) {
        DbExecutor executor = null;

        switch (type) {
            case MYSQL:
                executor = new DefaultMysqlDbExecutor();
            default:
                break;
        }

        return executor;
    }
}
