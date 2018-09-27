package com.renren.jinkong.kylin.dbtool.core.op;

import com.renren.jinkong.kylin.dbtool.model.Kv;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据库插入执行操作模式
 *
 * @author lixin.tian@renren-inc.com
 * @date 2018/9/25 16:31
 */
public enum DbInMode {
    /**
     * 增加前删除
     */
    DELETE_AND_ADD("插入前删除已有数据"),
    /**
     * 插入前删除某天数据
     */
    DELETE_DATE_AND_ADD("插入前按数据维度删除"),
    /**
     * 直接增加
     */
    ADD("直接添加数据");

    private String name;

    DbInMode(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static List<Kv<String, String>> getModes(ExcelOpType type) {
        List<Kv<String, String>> kvs = new ArrayList<>();
        List<DbInMode> inModes = new ArrayList<>();

        switch (type) {
            case MONTH:
            case DAY:
                inModes.add(DELETE_DATE_AND_ADD);
                inModes.add(ADD);
                break;
            case ALL:
                inModes.add(DELETE_AND_ADD);
                inModes.add(ADD);
            default:
                break;
        }

        for (DbInMode inMode : inModes) {
            kvs.add(new Kv<>(inMode.toString(), inMode.name));
        }

        return kvs;
    }

    public static void main(String[] args) {
        System.out.println(get("DELETE_DATE_AND_ADD").toString());
    }

    public static DbInMode get(String mode) {
        return DbInMode.valueOf(mode);
    }
}
