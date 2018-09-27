package com.renren.jinkong.kylin.dbtool;

import com.renren.jinkong.kylin.dbtool.core.op.DbInMode;
import com.renren.jinkong.kylin.dbtool.core.op.DbTimeDimension;
import com.renren.jinkong.kylin.dbtool.core.op.ExcelOpType;
import com.renren.jinkong.kylin.dbtool.core.op.ExcelVersion;
import com.renren.jinkong.kylin.dbtool.excel.DirectExcelDbPump;
import com.renren.jinkong.kylin.dbtool.kit.DateKit;
import com.renren.jinkong.kylin.dbtool.model.DbOpDefinition;
import org.junit.Test;

import java.io.File;

/**
 * @author lixin.tian@renren-inc.com
 * @date 2018/9/20 13:24
 */
public class DirectExcelDbPumpTest2 {

    private static final String url = "jdbc:mysql://localhost:3306/springboot_demo?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull";
    private static final String user = "root";
    private static final String password = "root";

    /**
     * 默认添加（直接增加）
     *
     * @throws Exception
     */
    @Test
    public void test0() throws Exception {
        File file = new File("D:\\data\\车商资本金明细2.xls");

        DirectExcelDbPump pump = new DirectExcelDbPump(url, user, password);

        pump.setFile(file);
        pump.setDbTableName("tb_money_detail");

        int i = pump.execute(DateKit.getTimestamp());
        System.out.println(i);
    }

    /**
     * 直接增加
     *
     * @throws Exception
     */
    @Test
    public void test1() throws Exception {
        File file = new File("D:\\data\\车商资本金明细.xls");

        DbOpDefinition definition = new DbOpDefinition();

        definition.setInMode(DbInMode.ADD);
        definition.setHeadRowNum(1);
        definition.setStartRowNum(2);

        DirectExcelDbPump pump = new DirectExcelDbPump(url, user, password);

        pump.setFile(file);
        pump.setDbTableName("tb_money_detail");
        pump.setDefinition(definition);

        int i = pump.execute(DateKit.getTimestamp());
        System.out.println(i);
    }

    /**
     * 全删全增
     *
     * @throws Exception
     */
    @Test
    public void test2() throws Exception {
        File file = new File("D:\\data\\车商资本金明细.xls");

        DbOpDefinition definition = new DbOpDefinition();

        definition.setInMode(DbInMode.DELETE_AND_ADD);
        definition.setHeadRowNum(1);
        definition.setStartRowNum(2);

        DirectExcelDbPump pump = new DirectExcelDbPump(url, user, password);

        pump.setFile(file);
        pump.setDbTableName("tb_money_detail");
        pump.setDefinition(definition);

        int i = pump.execute(DateKit.getTimestamp());
        System.out.println(i);
    }

    /**
     * 月维度替换添加
     *
     * @throws Exception
     */
    @Test
    public void test3() throws Exception {
        File file = new File("D:\\data\\车商资本金明细.xls");

        DbOpDefinition definition = new DbOpDefinition();

        definition.setHeadRowNum(1);
        definition.setStartRowNum(2);
        definition.setInMode(DbInMode.DELETE_DATE_AND_ADD);
        definition.setDtm(DbTimeDimension.MONTH);
        definition.setExcelOpType(ExcelOpType.MONTH);
        definition.setDayOrMonth("2018-09");

        DirectExcelDbPump pump = new DirectExcelDbPump(url, user, password);

        pump.setFile(file);
        pump.setDbTableName("tb_money_detail_his_month");
        pump.setDefinition(definition);

        int i = pump.execute(DateKit.getTimestamp());
        System.out.println(i);
    }

    /**
     * 日维度替换添加
     *
     * @throws Exception
     */
    @Test
    public void test4() throws Exception {
        File file = new File("D:\\data\\车商资本金明细.xls");

        DbOpDefinition definition = new DbOpDefinition();

        definition.setHeadRowNum(1);
        definition.setStartRowNum(2);
        definition.setInMode(DbInMode.DELETE_DATE_AND_ADD);
        definition.setDtm(DbTimeDimension.DAY);
        definition.setExcelOpType(ExcelOpType.DAY);
        definition.setDayOrMonth("2018-09-27");

        DirectExcelDbPump pump = new DirectExcelDbPump(url, user, password);

        pump.setFile(file);
        pump.setDbTableName("tb_money_detail_his_day");
        pump.setDefinition(definition);

        int i = pump.execute(DateKit.getTimestamp());
        System.out.println(i);
    }

    /**
     * 日维度替换添加（简化版本）
     *
     * @throws Exception
     */
    @Test
    public void test5() throws Exception {
        File file = new File("D:\\data\\车商资本金明细.xls");

        DbOpDefinition definition = new DbOpDefinition();

        definition.setHeadRowNum(1);
        definition.setStartRowNum(2);
        definition.setExcelOpType(ExcelOpType.DAY);
        definition.setDayOrMonth("2018-09-27");

        DirectExcelDbPump pump = new DirectExcelDbPump(url, user, password);

        pump.setFile(file);
        pump.setDbTableName("tb_money_detail_his_day");
        pump.setDefinition(definition);

        int i = pump.execute(DateKit.getTimestamp());
        System.out.println(i);
    }

    /**
     * 直接增加
     *
     * @throws Exception
     */
    @Test
    public void test6() throws Exception {
        File file = new File("D:\\data\\车商在用非抵押债.xls");

        DbOpDefinition definition = new DbOpDefinition();

        definition.setInMode(DbInMode.ADD);
        definition.setHeadRowNum(1);
        definition.setStartRowNum(2);
        definition.setVersion(ExcelVersion.VERSION_2003);

        DirectExcelDbPump pump = new DirectExcelDbPump(url, user, password);

        pump.setFile(file);
        pump.setDbTableName("dt_car_dealer_non_mortgage");
        pump.setDefinition(definition);

        int i = pump.execute(DateKit.getTimestamp());
        System.out.println(i);
    }
}
