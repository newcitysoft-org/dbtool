package com.renren.jinkong.kylin.dbtool.core.executor;

import com.renren.jinkong.kylin.dbtool.core.DirectSqlGenerator;
import com.renren.jinkong.kylin.dbtool.core.TypeMapping;
import com.renren.jinkong.kylin.dbtool.core.op.DbInMode;
import com.renren.jinkong.kylin.dbtool.core.op.SqlOperation;
import com.renren.jinkong.kylin.dbtool.model.ColumnMeta;
import com.renren.jinkong.kylin.dbtool.model.TableMeta;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @author lixin.tian@renren-inc.com
 * @date 2018/9/25 13:50
 */
public class DirectMysqlDbExecutor {

    private DataSource source;
    private TableMeta tableMeta;
    private DbInMode dbInMode;

    public DirectMysqlDbExecutor(DataSource source, TableMeta tableMeta, DbInMode inMode) {
        this.source = source;
        this.tableMeta = tableMeta;
        this.dbInMode = inMode;
    }

    public int batchInsert(List<Map<String, String>> list) {
        int rows = 0;

        String sql = DirectSqlGenerator.generateSql(tableMeta);

        System.out.println(dbInMode);
        if(this.dbInMode == DbInMode.DELETE_AND_ADD) {
            delete(tableMeta);
        }

        for (Map<String, String> map : list) {
            int row = insert(sql, map);
            rows += row;
        }

        return rows;
    }

    public int insert(String sql, Map<String, String> map) {
        int row = new SqlOperation(source, sql) {

            @Override
            public void push(PreparedStatement ps) {
                // 按照SQL生成器的字段顺序设置值
                List<ColumnMeta> columnMetas = tableMeta.columnMetas;

                if(columnMetas != null && columnMetas.size() > 0) {
                    for (int i = 0; i < columnMetas.size(); i++) {
                        ColumnMeta meta = columnMetas.get(i);
                        // 获取字段备注
                        String remarks = meta.getRemarks();
                        // 强映射获取数值
                        String value = map.get(remarks);
                        // 设置值
                        try {
                            Object o = TypeMapping.convertType(meta.getJavaType(), value);
                            ps.setObject(i + 1, o);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }.execute();

        return row;
    }

    public int delete(TableMeta tableMeta) {
        String sql = "delete  from " + tableMeta.getName();

       int row = new SqlOperation(source, sql) {

            @Override
            public void push(PreparedStatement ps) {

            }
        }.execute();

        return row;
    }
}
