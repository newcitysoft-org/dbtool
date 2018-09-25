package com.renren.jinkong.kylin.dbtool.core.executor;

import com.renren.jinkong.kylin.dbtool.core.MetaBuilder;
import com.renren.jinkong.kylin.dbtool.core.manager.DataSourceManager;
import com.renren.jinkong.kylin.dbtool.core.op.DbInMode;
import com.renren.jinkong.kylin.dbtool.kit.StrKit;
import com.renren.jinkong.kylin.dbtool.model.DbInfo;
import com.renren.jinkong.kylin.dbtool.model.TableMeta;

import javax.sql.DataSource;
import java.util.List;

/**
 * @author lixin.tian@renren-inc.com
 * @date 2018/9/19 14:48
 */
public class DirectDataSourceExecutor {
    private DataSource dataSource;
    private String dbTableName;
    private DbInMode inMode;

    public DirectDataSourceExecutor(DbInfo info, String dbTableName, DbInMode inMode) {
        dataSource = DataSourceManager.getDsm().getDataSource(info);
        // 设置对象属性
        this.dbTableName = dbTableName;
        this.inMode = inMode;
    }

    public int batchInsert(List list) {
        if(dataSource == null) {
            throw new IllegalArgumentException("未获取数据源！");
        }

        if(StrKit.isBlank(dbTableName)) {
            throw new IllegalArgumentException("未设置数据表！");
        }

        TableMeta tableMeta = new MetaBuilder(dataSource).build(dbTableName);
        DirectMysqlDbExecutor executor = new DirectMysqlDbExecutor(dataSource, tableMeta, inMode);

        return executor.batchInsert(list);
    }
}
