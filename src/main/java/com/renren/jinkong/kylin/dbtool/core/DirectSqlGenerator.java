package com.renren.jinkong.kylin.dbtool.core;

import com.renren.jinkong.kylin.dbtool.model.ColumnMeta;
import com.renren.jinkong.kylin.dbtool.model.TableMeta;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 直接方式的动态SQL生成器
 *
 * @author lixin.tian@renren-inc.com
 * @date 2018/9/19 17:11
 */
public class DirectSqlGenerator {

    private static final ConcurrentHashMap<String, String> sqlMap = new ConcurrentHashMap<String, String>();

    /**
     * 生成动态SQL语句
     *
     * @param tableMeta
     * @return
     */
    public static String generateSql(TableMeta tableMeta) {
        String sql = sqlMap.get(tableMeta.getName());

        if(sql == null) {
            int count = 0;
            StringBuilder sb = new StringBuilder();

            sb.append("INSERT INTO ");
            sb.append(tableMeta.getName());
            sb.append("(");

            // 增加字段参数
            List<ColumnMeta> columnMetas = tableMeta.getColumnMetas();

            for (int i = 0; i < columnMetas.size(); i++) {
                ColumnMeta columnMeta = columnMetas.get(i);

                String name = columnMeta.getName();

                if(!"id".equals(name)) {
                    sb.append(name + ",");
                    count++;
                }
            }

            // 替换最后一个,号为)
            int index = sb.lastIndexOf(",");
            sb.replace(index, index + 1, ")");

            sb.append(" VALUES(");
            // 增加占位符

            for (int i = 0; i < count; i++) {
                sb.append("?,");
            }

            // 替换最后一个,号为)
            index = sb.lastIndexOf(",");
            sb.replace(index, index + 1, ")");

            sql = sb.toString();

            sqlMap.put(tableMeta.getName(), sql);
        }


        return sql;
    }
}
