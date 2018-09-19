package com.renren.jinkong.kylin.dbtool.core;

import com.renren.jinkong.kylin.dbtool.anno.GeneratedValue;
import com.renren.jinkong.kylin.dbtool.anno.Id;
import com.renren.jinkong.kylin.dbtool.anno.Table;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 数据库执行器
 *
 * @author lixin.tian@renren-inc.com
 * @date 2018/9/19 10:55
 */
public class DefaultMysqlDbExecutor implements DbExecutor {

    private static final ConcurrentHashMap<Class, String> sqlMap = new ConcurrentHashMap<>();

    @Override
    public int batchInsert(DataSource source, Class clazz, List list) {
        int rows = 0;
        // 反射获取SQL和所有域
        String sql = generateSql(clazz);
        List<Field> fields = getFields(clazz);

        for (Object obj : list) {
            int row = insert(source, sql, fields, obj);

            rows += row;
        }

        return rows;
    }

    /**
     * 反射获取所有域
     *
     * @param clazz
     * @return
     */
    private List<Field> getFields(Class clazz) {
        List<Field> fieldList = new LinkedList<>();

        Field[] fields = clazz.getDeclaredFields();

        for(Field field : fields) {
            Id id = field.getAnnotation(Id.class);
            GeneratedValue generatedValue = field.getAnnotation(GeneratedValue.class);
            if(id != null && generatedValue != null) {
                continue;
            } else {
                fieldList.add(field);
            }

        }

        return fieldList;
    }

    /**
     * 生成动态SQL语句
     *
     * @param table
     * @return
     */
    public String generateSql(Class table) {

        Table annotation = (Table) table.getAnnotation(Table.class);
        String sql = sqlMap.get(table);

        if(sql == null) {
            StringBuilder sb = new StringBuilder();

            sb.append("INSERT INTO ");
            sb.append(annotation.name());
            sb.append("(");

            // 增加字段参数
            List<Field> fields = getFields(table);

            for (int i = 0; i < fields.size(); i++) {
                Field field = fields.get(i);

                sb.append(humpTransferToField(field.getName()) + ",");
            }

            // 替换最后一个,号为)
            int index = sb.lastIndexOf(",");
            sb.replace(index, index + 1, ")");

            sb.append(" VALUES(");
            // 增加占位符

            for (int i = 0; i < fields.size(); i++) {
                sb.append("?,");
            }

            // 替换最后一个,号为)
            index = sb.lastIndexOf(",");
            sb.replace(index, index + 1, ")");

            sql = sb.toString();

            sqlMap.put(table, sql);
        }


        return sql;
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
    public int insert(DataSource dataSource, String sql, List<Field> fieldList, Object obj) {
        int row = 0;

        Connection conn = null;
        PreparedStatement ps = null;
        try{
            // 获取数据库连接
            conn = dataSource.getConnection();
            // SQL预编译
            ps = conn.prepareStatement(sql);
            // 设置值
            for(int i = 0; i < fieldList.size(); i++) {
                Field field = fieldList.get(i);

                field.setAccessible(true);
                ps.setObject(i+1, field.get(obj));
            }

            //执行sql语句
            row = ps.executeUpdate();
            System.out.println("插入成功(*￣︶￣)");
        } catch (Exception e) {
            e.printStackTrace();
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

    /**
     * 驼峰式转为数据库字段
     * 例如：
     * frontName -> front_name
     * frontNameAgeSs -> front_name_age_ss
     *
     * @param humpStr
     * @return
     */
    private static String humpTransferToField(String humpStr) {
        StringBuilder sb = new StringBuilder();

        char[] chars = humpStr.toCharArray();

        for(int i = 0; i < chars.length; i++) {
            // 'A' = 65
            // 'Z' = 90
            if(chars[i] >= 65 && chars[i] <= 90) {
                sb.append("_" + String.valueOf(chars[i]).toLowerCase());
            } else {
                sb.append(chars[i]);
            }
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println(humpTransferToField("frontName"));
        System.out.println(humpTransferToField("frontNameAgeSs"));
    }
}
