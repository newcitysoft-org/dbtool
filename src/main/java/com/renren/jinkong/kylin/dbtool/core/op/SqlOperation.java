package com.renren.jinkong.kylin.dbtool.core.op;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * SQL操作
 *
 * @author lixin.tian@renren-inc.com
 * @date 2018-09-19 17:49
 *
 */
public abstract class SqlOperation {

        private DataSource dataSource;
        private String sql;

        public SqlOperation(DataSource dataSource, String sql) {
            this.dataSource = dataSource;
            this.sql = sql;
        }

        /**
         * 设置参数
         *
         * @param ps
         */
        public abstract void push(PreparedStatement ps);

        public int execute() {
            int row;

            Connection conn = null;
            PreparedStatement ps = null;
            try{
                // 获取数据库连接
                conn = dataSource.getConnection();
                // SQL预编译
                ps = conn.prepareStatement(sql);
                // 设置参数
                push(ps);
                //执行sql语句
                row = ps.executeUpdate();
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                if(ps != null) {
                    try {
                        ps.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                if(conn != null) {
                    try {
                        conn.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

            }

            return row;
        }
    }