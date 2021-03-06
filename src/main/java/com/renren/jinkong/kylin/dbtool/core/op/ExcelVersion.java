package com.renren.jinkong.kylin.dbtool.core.op;

import com.renren.jinkong.kylin.dbtool.kit.StrKit;
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
    VERSION_2003("97-2003.xls"),
    /**
     * 2007版本
     */
    VERSION_2007("2007.xlsx");

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
        List<Kv<String, String>> kvs = new ArrayList<Kv<String, String>>();

        ExcelVersion[] values = ExcelVersion.values();

        for (ExcelVersion version : values) {
            kvs.add(new Kv<String, String>(version.toString(), version.type));
        }

        return kvs;
    }

    public static ExcelVersion get(String version) {
        if(version == null || StrKit.isBlank(version)) {
            return VERSION_2007;
        }

        return valueOf(version);
    }
}
