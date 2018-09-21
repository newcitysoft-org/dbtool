package com.renren.jinkong.kylin.dbtool;

import com.renren.jinkong.kylin.dbtool.core.executor.DefaultDataSourceExecutor;
import com.renren.jinkong.kylin.dbtool.excel.ExcelKit;
import org.junit.Test;

import java.io.File;
import java.util.List;

/**
 * @author lixin.tian@renren-inc.com
 * @date 2018/9/20 13:24
 */
public class DbTest5 {

    private static final String url = "jdbc:mysql://localhost:3306/springboot_demo?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull";
    private static final String user = "root";
    private static final String password = "root";

    @Test
    public void testAdd() throws Exception {
        File file = new File("D:\\data\\student.xls");
        ExcelKit excelKit = new ExcelKit(file, Student.class);
        DefaultDataSourceExecutor executor = new DefaultDataSourceExecutor(url, user, password);

        List list = excelKit.dataExcelMapToList();
        int i = executor.batchInsert(Student.class, list);
        System.out.println(i);
    }

    @Test
    public void testDogAdd() throws Exception {
        File file = new File("D:\\data\\dog.xls");
        ExcelKit excelKit = new ExcelKit(file, Dog.class);
        DefaultDataSourceExecutor executor = new DefaultDataSourceExecutor(url, user, password);

        List list = excelKit.dataExcelMapToList();
        int i = executor.batchInsert(Dog.class, list);
        System.out.println(i);
    }
}
