package com.renren.jinkong.kylin.dbtool.core;

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
