package com.renren.jinkong.kylin.dbtool;

import com.renren.jinkong.kylin.dbtool.excel.ExcelDbPump;
import org.junit.Test;

import java.io.File;

/**
 * @author lixin.tian@renren-inc.com
 * @date 2018/9/20 13:24
 */
public class ExcelDbPumpTest {

    private static final String url = "jdbc:mysql://localhost:3306/springboot_demo?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull";
    private static final String user = "root";
    private static final String password = "root";

    @Test
    public void test() throws Exception {
        File file = new File("01.xls");
        ExcelDbPump pump = new ExcelDbPump(url, user, password);

        pump.set(file, MoneyDetail.class);

        int i = pump.execute();
        System.out.println(i);
    }

    @Test
    public void test2() throws Exception {
        File file = new File("车商资本金明细.xls");
        ExcelDbPump pump = new ExcelDbPump(url, user, password);

        // 设置文件对象
        pump.setFile(file);
        // 设置映射类
        pump.setClazz(MoneyDetail.class);
        // 设置起始行号
        pump.setStartRowNum(1);

        int i = pump.execute();
        System.out.println(i);
    }

}
