package com.renren.jinkong.kylin.dbtool.core.manager;

import com.renren.jinkong.kylin.dbtool.model.DbInfo;

import javax.sql.DataSource;

/**
 * @author lixin.tian@renren-inc.com
 * @date 2018/9/19 14:51
 */
public interface DsManager {
    DataSource getDataSource(DbInfo dbInfo);
    DataSource getDataSource(String jdbcUrl, String user, String password);
}
