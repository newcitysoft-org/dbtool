package com.renren.jinkong.kylin.dbtool.core.op;

import com.renren.jinkong.kylin.dbtool.model.Kv;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lixin.tian@renren-inc.com
 * @date 2018/9/26 12:43
 */
public enum  DbTimeDimension {
    /**
     * 月维度
     */
    MONTH("月", "record_month"),
    /**
     * 日期维度
     */
    DAY("日", "record_dt");

    private String name;
    private String value;

    DbTimeDimension(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public static List<Kv<String, String>> getDims() {
        List<Kv<String, String>> kvs = new ArrayList<Kv<String, String>>();

        DbTimeDimension[] values = DbTimeDimension.values();

        for (DbTimeDimension timeDimension : values) {
            kvs.add(new Kv<String, String>(timeDimension.toString(), timeDimension.name));
        }

        return kvs;
    }

    public static void main(String[] args) {
        System.out.println(get("MONTH").toString());
    }

    public static DbTimeDimension get(String mode) {
        return valueOf(mode);
    }
}
