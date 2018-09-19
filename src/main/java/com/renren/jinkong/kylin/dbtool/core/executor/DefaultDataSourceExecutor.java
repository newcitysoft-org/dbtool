package com.renren.jinkong.kylin.dbtool.core.executor;

import com.renren.jinkong.kylin.dbtool.core.manager.DataSourceManager;
import com.renren.jinkong.kylin.dbtool.core.DbType;
import com.renren.jinkong.kylin.dbtool.core.manager.DsManager;
import com.renren.jinkong.kylin.dbtool.model.DbInfo;

import javax.sql.DataSource;
import java.util.List;

/**
 * @author lixin.tian@renren-inc.com
 * @date 2018/9/19 14:48
 */
public class DefaultDataSourceExecutor implements DbExecutor, DsManager {
    private static final DbExecutor executor = DbExecutorFactory.getExecutor(DbType.MYSQL);
    private static final DataSourceManager dsm = DataSourceManager.getDsm();

    private DbInfo info;

    public DefaultDataSourceExecutor() {

    }

    public DefaultDataSourceExecutor(DbInfo info) {
        this.info = info;
    }

    public DefaultDataSourceExecutor(String jdbcUrl, String user, String password) {
        this.info = new DbInfo.DbInfoBuilder().jdbcUrl(jdbcUrl).user(user).password(password).build();
    }

    public int batchInsert(Class clazz, List list) {
        if(info == null) {
            throw new IllegalArgumentException("数据源信息没有设置");
        }

        return executor.batchInsert(dsm.getDataSource(info), clazz, list);
    }

    public int batchUpdate(Class clazz, List list) {
        if(info == null) {
            throw new IllegalArgumentException("数据源信息没有设置");
        }

        return executor.batchUpdate(dsm.getDataSource(info), clazz, list);
    }

    public int batchDelete(Class clazz, List ids) {
        if(info == null) {
            throw new IllegalArgumentException("数据源信息没有设置");
        }

        return executor.batchDelete(dsm.getDataSource(info), clazz, ids);
    }

    @Override
    public int batchInsert(DataSource source, Class clazz, List list) {
        return executor.batchInsert(source, clazz, list);
    }

    @Override
    public int batchUpdate(DataSource source, Class clazz, List list) {
        return executor.batchUpdate(source, clazz, list);
    }

    @Override
    public int batchDelete(DataSource source, Class clazz, List ids) {
        return executor.batchDelete(source, clazz, ids);
    }

    @Override
    public DataSource getDataSource(DbInfo dbInfo) {
        return dsm.getDataSource(dbInfo);
    }

    @Override
    public DataSource getDataSource(String jdbcUrl, String user, String password) {
        return dsm.getDataSource(jdbcUrl, user, password);
    }
}
