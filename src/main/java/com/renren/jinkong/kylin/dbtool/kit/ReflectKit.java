package com.renren.jinkong.kylin.dbtool.kit;

import com.renren.jinkong.kylin.dbtool.anno.GeneratedValue;
import com.renren.jinkong.kylin.dbtool.anno.Id;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

/**
 * @author lixin.tian@renren-inc.com
 * @date 2018/9/19 17:13
 */
public final class ReflectKit {

    private ReflectKit() {}

    /**
     * 反射获取所有域
     *
     * @param clazz
     * @return
     */
    public static List<Field> getFields(Class clazz) {
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
     * 获取JavaBean对象主键
     *
     * @param table
     * @return
     */
    public static Field getIdField(Class table) {
        Field[] fields = table.getDeclaredFields();

        for(Field field : fields) {
            Id id = field.getAnnotation(Id.class);
            if(id != null) {
                return field;
            }
        }

        return null;
    }
}
