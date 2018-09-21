package com.renren.jinkong.kylin.dbtool;

import com.renren.jinkong.kylin.dbtool.excel.ExcelDbPump;
import org.junit.Test;

import java.io.File;

/**
 * @author lixin.tian@renren-inc.com
 * @date 2018/9/20 13:24
 */
public class ExcelDbPumpTest2 {

    private static final String url = "jdbc:mysql://localhost:3306/springboot_demo?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull";
    private static final String user = "root";
    private static final String password = "root";

    @Test
    public void test() throws Exception {
        File file = new File("D:\\data\\各城市再投入明细修改版2.xls");
        ExcelDbPump pump = new ExcelDbPump(url, user, password);

        // 设置文件对象
        pump.setFile(file);
        // 设置映射类
        pump.setClazz(CityMoneyDetail.class);
        // 设置起始行号
        pump.setHeadRowNum(1);
        pump.setEndRowNum(115);

        int i = pump.execute();
        System.out.println(i);
    }

}
