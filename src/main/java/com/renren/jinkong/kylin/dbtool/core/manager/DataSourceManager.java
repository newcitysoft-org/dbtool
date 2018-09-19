package com.renren.jinkong.kylin.dbtool.core.manager;

import com.renren.jinkong.kylin.dbtool.model.DbInfo;
import com.renren.jinkong.kylin.dbtool.plugin.DruidPlugin;

import javax.sql.DataSource;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 数据源管理器
 *
 * @author lixin.tian@renren-inc.com
 * @date 2018/9/19 10:44
 */
public final class DataSourceManager implements DsManager {
    /**
     * 单例实现管理器对象
     *
     */
    private DataSourceManager() {}
    private static final DataSourceManager dsm = new DataSourceManager();
    public static DataSourceManager getDsm() {
        return dsm;
    }

    /**
     * 定义连接和DataSource的映射
     */
    private static final ConcurrentHashMap<String, DataSource> map = new ConcurrentHashMap<>();

    /**
     * 获取数据源
     *
     * @param dbInfo
     * @return
     */
    @Override
    public DataSource getDataSource(DbInfo dbInfo) {
        DataSource dataSource = map.get(dbInfo.getJdbcUrl());

        if(dataSource != null) {
            return dataSource;
        } else {
            dataSource = startDataSource(dbInfo);
            map.put(dbInfo.getJdbcUrl(), dataSource);
        }

        return dataSource;
    }

    @Override
    public DataSource getDataSource(String jdbcUrl, String user, String password) {
        DbInfo info = new DbInfo.DbInfoBuilder().jdbcUrl(jdbcUrl).user(user).password(password).build();

        return getDataSource(info);
    }

    /**
     * 启动数据源
     *
     * @param dbInfo
     * @return
     */
    private DataSource startDataSource(DbInfo dbInfo) {
        DruidPlugin druidPlugin = new DruidPlugin(dbInfo.getJdbcUrl(), dbInfo.getUser(), dbInfo.getPassword());

        druidPlugin.start();
        return druidPlugin.getDataSource();
    }

}
