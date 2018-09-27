package com.renren.jinkong.kylin.dbtool.core.op;

import com.renren.jinkong.kylin.dbtool.model.Kv;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lixin.tian@renren-inc.com
 * @date 2018/9/27 15:47
 */
public enum ExcelVersion {

    /**
     * 2003版本
     */
    VERSION_2003("2003"),
    /**
     * 2007版本
     */
    VERSION_2007("2007");

    private String type;

    ExcelVersion(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static List<Kv<String, String>> getTypes() {
        List<Kv<String, String>> kvs = new ArrayList<>();

        ExcelVersion[] values = ExcelVersion.values();

        for (ExcelVersion version : values) {
            kvs.add(new Kv<>(version.toString(), version.type));
        }

        return kvs;
    }

}
