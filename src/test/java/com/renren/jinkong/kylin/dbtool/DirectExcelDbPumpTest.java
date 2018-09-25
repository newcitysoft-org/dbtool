//package com.renren.jinkong.kylin.dbtool;
//
//import com.renren.jinkong.kylin.dbtool.core.op.DbInMode;
//import com.renren.jinkong.kylin.dbtool.excel.DirectExcelDbPump;
//import org.junit.Test;
//
//import java.io.File;
//
///**
// * @author lixin.tian@renren-inc.com
// * @date 2018/9/20 13:24
// */
//public class DirectExcelDbPumpTest {
//
//    private static final String url = "jdbc:mysql://localhost:3306/springboot_demo?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull";
//    private static final String user = "root";
//    private static final String password = "root";
//
//    @Test
//    public void test() throws Exception {
//        File file = new File("D:\\data\\车商资本金明细.xls");
//        DirectExcelDbPump pump = new DirectExcelDbPump(url, user, password);
//        // 设置文件对象
//        pump.setFile(file);
//        // 设置映射类
//        pump.setDbTableName("tb_money_detail");
//        // 设置起始行号
//        pump.setHeadRowNum(1);
//        // 设置插入模式
//        pump.setInMode(DbInMode.DELETE_AND_ADD);
//        int i = pump.execute();
//        System.out.println(i);
//    }
//
//}
