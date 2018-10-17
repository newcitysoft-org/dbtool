package com.renren.jinkong.kylin.dbtool.core.manager;

import com.renren.jinkong.kylin.dbtool.model.DbInfo;

import javax.sql.DataSource;

/**
 * 数据源管理器
 *
 * @author lixin.tian@renren-inc.com
 * @date 2018/9/19 14:51
 */
public interface DsManager {
    /**
     * 根据DbInfo获取数据源
     *
     * @param dbInfo 数据源信息
     * @return
     */
    DataSource getDataSource(DbInfo dbInfo);

    /**
     * 根据数据库连接信息获取数据源
     *
     * @param jdbcUrl 连接url
     * @param user 用户名
     * @param password 密码
     * @return
     */
    DataSource getDataSource(String jdbcUrl, String user, String password);
}
