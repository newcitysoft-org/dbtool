package com.renren.jinkong.kylin.dbtool.core.op;

import com.renren.jinkong.kylin.dbtool.model.Kv;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lixin.tian@renren-inc.com
 * @date 2018/9/26 17:38
 */
public enum ExcelType {
    /**
     * 月维度
     */
    ALL("全量表"),
    /**
     * 日期维度
     */
    MONTH("月维度历史表"),
    /**
     *
     */
    DAY("日维度历史表");

    private String type;

    ExcelType(String type) {
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

        ExcelType[] values = ExcelType.values();

        for (ExcelType excelType : values) {
            kvs.add(new Kv<>(excelType.toString(), excelType.type));
        }

        return kvs;
    }

    public static void main(String[] args) {
        System.out.println(get("MONTH").toString());
    }

    public static ExcelType get(String mode) {
        return ExcelType.valueOf(mode);
    }

}
