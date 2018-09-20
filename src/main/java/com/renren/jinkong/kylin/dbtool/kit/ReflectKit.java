package com.renren.jinkong.kylin.dbtool.kit;

import com.renren.jinkong.kylin.dbtool.anno.GeneratedValue;
import com.renren.jinkong.kylin.dbtool.anno.Id;
import com.renren.jinkong.kylin.dbtool.anno.Mapping;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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

    public static Object convertType(Field field, String value) throws ParseException {
        Object result = null;

        String type = field.getGenericType().toString();

        switch (type) {
            case "class java.lang.String":
                result = value;
                break;
            case "class java.lang.Integer":
            case "int":
                result = Integer.parseInt(value);
                break;
            case "class java.lang.Double":
            case "double":
                result = Double.parseDouble(value);
                break;
            case "class java.lang.Boolean":
            case "boolean":
                result = Boolean.parseBoolean(value);
                break;
            case "class java.util.Date":
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                result = sdf.parse(value);
                break;
            default:
                break;
        }

        return result;
    }

    public static Object transferToObject(Class clazz, Map<String, String> dataMap) throws Exception {
        Object obj = clazz.newInstance();

        List<Field> fields = getFields(clazz);

        for(Field field : fields) {
            // 获取映射的注解
            Mapping mapping = field.getAnnotation(Mapping.class);
            // 获取每列的值
            String cellName = mapping.cellName();
            String cellValue = dataMap.get(cellName);
            // 获取实际类型属性值
            Object value = convertType(field, cellValue);
            // 设置属性可访问
            field.setAccessible(true);
            // 设置数值
            field.set(obj, value);
        }

        return obj;
    }

    public static List transferToList(Class clazz, List<Map<String, String>> dataListMap) throws Exception{
        List list = new LinkedList();

        for(Map<String, String> dataMap : dataListMap) {
            list.add(transferToObject(clazz, dataMap));
        }

        return list;
    }
}
