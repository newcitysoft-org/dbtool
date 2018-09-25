package com.renren.jinkong.kylin.dbtool;

import com.renren.jinkong.kylin.dbtool.core.MetaBuilder;
import com.renren.jinkong.kylin.dbtool.core.manager.DataSourceManager;
import com.renren.jinkong.kylin.dbtool.model.DbInfo;
import com.renren.jinkong.kylin.dbtool.model.TableMeta;
import org.junit.Test;

import javax.sql.DataSource;

/**
 * @author lixin.tian@renren-inc.com
 * @date 2018/9/25 15:13
 */
public class MetaBuilderTest {

    private static final String url = "jdbc:mysql://localhost:3306/springboot_demo?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull";
    private static final String user = "root";
    private static final String password = "root";

    public static DbInfo dbInfo = new DbInfo(url, user, password);

    @Test
    public void test() {
        DataSource dataSource = DataSourceManager.getDsm().getDataSource(dbInfo);

        MetaBuilder builder = new MetaBuilder(dataSource);

        TableMeta meta = builder.build("tb_money_detail");

        System.out.println(meta);
    }
}
