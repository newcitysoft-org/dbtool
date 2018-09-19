package com.renren.jinkong.kylin.dbtool.core.executor;

import com.renren.jinkong.kylin.dbtool.core.SqlGenerator;
import com.renren.jinkong.kylin.dbtool.core.op.SqlOperation;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.util.List;

import static com.renren.jinkong.kylin.dbtool.kit.ReflectKit.getFields;
import static com.renren.jinkong.kylin.dbtool.kit.ReflectKit.getIdField;

/**
 * 数据库执行器
 *
 * @author lixin.tian@renren-inc.com
 * @date 2018/9/19 10:55
 */
public class DefaultMysqlDbExecutor implements DbExecutor {

    @Override
    public int batchInsert(DataSource source, Class clazz, List list) {
        int rows = 0;
        // 反射获取SQL和所有域
        String sql = SqlGenerator.generateSql(clazz);
        List<Field> fields = getFields(clazz);

        for (Object obj : list) {
            int row = insert(source, sql, fields, obj);

            rows += row;
        }

        return rows;
    }

    @Override
    public int batchUpdate(DataSource source, Class clazz, List list) {
        int rows = 0;
        // 反射获取SQL和所有域
        String sql = SqlGenerator.generateUpdateSql(clazz);
        List<Field> fields = getFields(clazz);
        Field idField = getIdField(clazz);

        for (Object obj : list) {
            int row = update(source, sql, fields, idField, obj);

            rows += row;
        }

        return rows;
    }

    @Override
    public int batchDelete(DataSource source, Class clazz, List ids) {
        // 反射获取SQL和所有域
        String sql = SqlGenerator.generateDeleteSql(clazz);
        System.out.println(sql);
        Field idField = getIdField(clazz);
        int rows = 0;

        for (Object id : ids) {
            int row = delete(source, sql, idField, id);

            rows += row;
        }

        return rows;
    }

    /**
     * 单独增加
     *
     * @param dataSource 数据源
     * @param sql SQL
     * @param fieldList 域
     * @param obj 插入对象
     * @return
     */
    private int insert(DataSource dataSource, String sql, List<Field> fieldList, Object obj) {
        int row = new SqlOperation(dataSource, sql) {

            @Override
            public void push(PreparedStatement ps) {
                // 设置值
                try {
                    for (int i = 0; i < fieldList.size(); i++) {
                        Field field = fieldList.get(i);

                        field.setAccessible(true);

                        ps.setObject(i + 1, field.get(obj));

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.execute();

        return row;
    }

    private int update(DataSource dataSource, String sql, List<Field> fieldList, Field idField, Object obj) {
        int row = new SqlOperation(dataSource, sql) {

            @Override
            public void push(PreparedStatement ps) {
                // 设置值
                try {
                    for (int i = 0; i < fieldList.size(); i++) {
                        Field field = fieldList.get(i);

                        field.setAccessible(true);

                        ps.setObject(i + 1, field.get(obj));

                    }

                    // 获取id的域
                    idField.setAccessible(true);
                    ps.setObject(fieldList.size() + 1, idField.get(obj));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.execute();

        return row;
    }

    private int delete(DataSource dataSource, String sql, Field idField, Object id) {
        int row = new SqlOperation(dataSource, sql) {
            @Override
            public void push(PreparedStatement ps) {
                try {
                    idField.setAccessible(true);
                    ps.setObject(1, id);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.execute();

        return row;
    }


}
