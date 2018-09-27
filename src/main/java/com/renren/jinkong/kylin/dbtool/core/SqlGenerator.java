package com.renren.jinkong.kylin.dbtool.core;

import com.renren.jinkong.kylin.dbtool.anno.Table;

import java.lang.reflect.Field;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import static com.renren.jinkong.kylin.dbtool.kit.ReflectKit.getFields;
import static com.renren.jinkong.kylin.dbtool.kit.ReflectKit.getIdField;

/**
 * 单边动态SQL生成器
 *
 * @author lixin.tian@renren-inc.com
 * @date 2018/9/19 17:11
 */
public class SqlGenerator {

    private static final ConcurrentHashMap<Class, String> sqlMap = new ConcurrentHashMap<Class, String>();
    private static final ConcurrentHashMap<Class, String> sqlUpdateMap = new ConcurrentHashMap<Class, String>();
    private static final ConcurrentHashMap<Class, String> sqlDeleteMap = new ConcurrentHashMap<Class, String>();

    /**
     * 生成动态SQL语句
     *
     * @param table
     * @return
     */
    public static String generateSql(Class table) {

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
     * 生成动态SQL更新语句
     *
     * @param table
     * @return
     */
    public static String generateUpdateSql(Class table) {

        Table annotation = (Table) table.getAnnotation(Table.class);
        String sql = sqlUpdateMap.get(table);

        if(sql == null) {
            StringBuilder sb = new StringBuilder();

            sb.append("UPDATE ");
            sb.append(annotation.name());
            sb.append(" SET ");

            // 增加字段参数
            List<Field> fields = getFields(table);

            for (int i = 0; i < fields.size(); i++) {
                Field field = fields.get(i);

                sb.append(humpTransferToField(field.getName()) + "=?,");
            }

            // 替换最后一个英文逗号
            int index = sb.lastIndexOf(",");
            sb.replace(index, index + 1, "");

            Field idField = getIdField(table);


            sb.append(" WHERE ");
            sb.append(humpTransferToField(idField.getName()));
            sb.append("=?");

            sql = sb.toString();

            sqlUpdateMap.put(table, sql);
        }


        return sql;
    }

    /**
     * 生成动态删除SQL
     *
     * @param table
     * @return
     */
    public static String generateDeleteSql(Class table) {
        Table annotation = (Table) table.getAnnotation(Table.class);
        String sql = sqlDeleteMap.get(table);

        if(sql == null) {
            StringBuilder sb = new StringBuilder();

            sb.append("DELETE FROM ");
            sb.append(annotation.name());

            Field idField = getIdField(table);

            sb.append(" WHERE ");
            sb.append(humpTransferToField(idField.getName()));
            sb.append("=?");
            sql = sb.toString();

            sqlDeleteMap.put(table, sql);
        }

        return sql;
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
}
